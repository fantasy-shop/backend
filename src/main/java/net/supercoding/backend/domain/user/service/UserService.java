package net.supercoding.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.SignupRequestDto;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.repository.UserRepository;
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
                .phoneNumber("010-1234-5678")
                .build();

        userRepository.save(user);
    }
}
