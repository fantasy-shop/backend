package net.supercoding.backend.domain.user.controller;

// 임포트 갱신 커밋용 주석

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.LoginRequestDto;
import net.supercoding.backend.domain.user.dto.SignupRequestDto;
import net.supercoding.backend.domain.user.dto.UserAuthResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileUpdateRequestDto;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.security.CustomUserDetails;
import net.supercoding.backend.domain.user.security.jwt.JwtTokenProvider;
import net.supercoding.backend.domain.user.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

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

        return ResponseEntity.ok().body(Map.of("token", token));
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
    public ResponseEntity<Void> withdrawUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // 인증 정보 없으면 401 Unauthorized
        }

        userService.deleteUserById(userDetails.getUser().getUserPk());
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 로그아웃 api
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            long expiration = jwtTokenProvider.getExpiration(token);
            if (expiration > 0) {
                redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
            }
        }
        return ResponseEntity.ok().build();
    }

}





