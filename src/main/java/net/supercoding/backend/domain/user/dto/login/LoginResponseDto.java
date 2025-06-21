package net.supercoding.backend.domain.user.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import net.supercoding.backend.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseDto {
    private String token;

    private Long userPk;
    private String email;
    private String userName;
    private String phoneNumber;
    private String address;
    private String profileImageUrl;
    private LocalDateTime createAt;
    private String userStatus;
    @JsonProperty("isAdmin")
    private boolean isAdmin;


    public static LoginResponseDto of(User user, String token) {
        return LoginResponseDto.builder()
                .token(token)
                .userPk(user.getUserPk())
                .email(user.getEmail())
                .userName(user.getUserName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .createAt(LocalDateTime.now())
                .userStatus(user.getUserStatus().name())
                .isAdmin(user.getIsAdmin())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
