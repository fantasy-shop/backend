package net.supercoding.backend.domain.user.controller;

// 임포트 갱신 커밋용 주석

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.supercoding.backend.domain.user.dto.login.LoginRequestDto;
import net.supercoding.backend.domain.user.dto.login.LoginResponseDto;
import net.supercoding.backend.domain.user.dto.login.PasswordChangeRequest;
import net.supercoding.backend.domain.user.dto.profile.UserAuthResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileUpdateRequestDto;
import net.supercoding.backend.domain.user.dto.signup.SignupRequestDto;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.security.CustomUserDetails;
import net.supercoding.backend.domain.user.security.jwt.JwtTokenProvider;
import net.supercoding.backend.domain.user.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인용 메서드
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
        User user = userService.authenticate(dto); // email, password 검증
        String token = jwtTokenProvider.createToken(user.getEmail());

        LoginResponseDto responseDto = LoginResponseDto.of(user, token);
        return ResponseEntity.ok().body(responseDto);
    }


    // 내 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserProfileResponseDto profile = userService.getMyProfile(user);
        return ResponseEntity.ok(profile);
    }

    // 내 프로필 수정 (form-data, 이미지 포함)
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDto> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UserProfileUpdateRequestDto updateDto
    ) throws IOException {
        User updatedUser = userService.updateUserProfile(userDetails.getUser().getUserPk(), updateDto);
        UserProfileResponseDto dto = userService.getMyProfile(updatedUser);
        return ResponseEntity.ok(dto);
    }


    // 토큰 정보를 날릴 때
    @GetMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> getAuthenticatedUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new UserAuthResponseDto(userDetails.getUser()));
    }

    // 회원탈퇴 API
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdrawUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestHeader("Authorization") String authorizationHeader) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // 1. 사용자 삭제
            userService.deleteUserById(userDetails.getUser().getUserPk());

            // 2. JWT 블랙리스트 등록
            String token = authorizationHeader.replace("Bearer ", "");
            long expiration = jwtTokenProvider.getExpiration(token); // JWT 남은 만료 시간(ms)
            redisTemplate.opsForValue().set("blacklist:" + token, "logout", expiration, TimeUnit.MILLISECONDS);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("회원탈퇴 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 로그아웃 api
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            long expiration = jwtTokenProvider.getExpiration(token);
            if (expiration > 0) {
                redisTemplate.opsForValue().set("blacklist:" + token, "logout", expiration, TimeUnit.MILLISECONDS);
            }
        }
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경 api
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request, Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();

        boolean success = userService.changePassword(userDetails, request.getCurrentPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "현재 비밀번호가 일치하지 않습니다."));
        }
    }
}





