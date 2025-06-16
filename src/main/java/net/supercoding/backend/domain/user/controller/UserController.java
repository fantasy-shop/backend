package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.login.LoginRequestDto;
import net.supercoding.backend.domain.user.dto.profile.UserAuthResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileResponseDto;
import net.supercoding.backend.domain.user.dto.profile.UserProfileUpdateRequestDto;
import net.supercoding.backend.domain.user.dto.signup.SignupRequestDto;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.security.jwt.JwtTokenProvider;
import net.supercoding.backend.domain.user.security.oauth.CustomUserDetails;
import net.supercoding.backend.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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

    // 내 프로필 수정
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDto> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UserProfileUpdateRequestDto updateDto
    ) {
        User updatedUser = userService.updateUserProfile(userDetails.getUser().getUserPk(),updateDto);
        UserProfileResponseDto profile = userService.getMyProfile(updatedUser);
        return ResponseEntity.ok(profile);
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
}





