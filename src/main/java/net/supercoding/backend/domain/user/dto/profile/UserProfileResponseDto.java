package net.supercoding.backend.domain.user.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import net.supercoding.backend.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserProfileResponseDto {
    private Long userPk;
    private String userName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private LocalDateTime createAt;
    private String userStatus;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public static UserProfileResponseDto from(User user) {
        return UserProfileResponseDto.builder()
                .userPk(user.getUserPk())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .createAt(user.getCreateAt())
                .userStatus(user.getUserStatus().name())
                .isAdmin(user.getIsAdmin())
                .build();
    }
}
