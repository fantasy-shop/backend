package net.supercoding.backend.domain.user.dto.login;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
