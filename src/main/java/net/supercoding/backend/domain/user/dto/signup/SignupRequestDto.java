package net.supercoding.backend.domain.user.dto.signup;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private String address;
}
