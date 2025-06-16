package net.supercoding.backend.domain.user.dto.profile;

import lombok.Data;
import net.supercoding.backend.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
public class UserAuthResponseDto {
    private Long userPk;
    private String email;
    private String userName;
    private String phoneNumber;
    private String profileImageUrl;
    private LocalDateTime createAt;
    private String userStatus;
    private Boolean isAdmin;

    public UserAuthResponseDto(User user) {
        this.userPk = user.getUserPk();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImageUrl = user.getProfileImageUrl();
        this.createAt = user.getCreateAt();
        this.userStatus = user.getUserStatus().name();
        this.isAdmin = user.getIsAdmin();
    }
}

