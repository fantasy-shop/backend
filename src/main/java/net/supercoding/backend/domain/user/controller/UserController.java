package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.SignupRequestDto;
import net.supercoding.backend.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("회원가입 성공");
    }
}
