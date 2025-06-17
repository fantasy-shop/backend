package net.supercoding.backend.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private String address;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
}
