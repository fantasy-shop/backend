package net.supercoding.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.LoginRequestDto;
import net.supercoding.backend.domain.user.dto.SignupRequestDto;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(SignupRequestDto dto) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .userName(dto.getUserName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build();

        userRepository.save(user);
    }

    // 로그인 검증 메서드
    public User authenticate(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("유저 없음"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호 틀림");
            // 실무에선 해킹 위험때문에 "이메일 또는 비밀번호가 일치하지 않습니다" 응답
        }

        return user;
    }
}
