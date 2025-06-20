package net.supercoding.backend.domain.user.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.supercoding.backend.domain.user.entity.User;

@Data
public class UserAuthResponseDto {
    private Long userPk;
//    private String email;
//    private String userName;
//    private String phoneNumber;
//    private String address;
//    private String profileImageUrl;
//    private LocalDateTime createAt;
//    private String userStatus;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public UserAuthResponseDto(User user) {
        this.userPk = user.getUserPk();
//        this.email = user.getEmail();
//        this.userName = user.getUserName();
//        this.address = user.getAddress();
//        this.phoneNumber = user.getPhoneNumber();
//        this.profileImageUrl = user.getProfileImageUrl();
//        this.createAt = user.getCreateAt();
//        this.userStatus = user.getUserStatus().name();
        this.isAdmin = user.getIsAdmin();
    }
}

