package net.supercoding.backend.domain.user.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private String address;
    private Boolean isAdmin;
}
