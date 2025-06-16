package net.supercoding.backend.domain.user.dto.profile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserProfileUpdateRequestDto {
    private String userName;
    private String phoneNumber;
    private String address;
    private MultipartFile profileImage;
}
