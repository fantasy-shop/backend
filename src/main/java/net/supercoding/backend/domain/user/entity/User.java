package net.supercoding.backend.domain.user.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.supercoding.backend.domain.user.enums.UserStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPk;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column(nullable = false)
    private boolean isAdmin = false;

    @JsonProperty("isAdmin")
    public boolean getIsAdmin() {
        return isAdmin;
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Column
    @Builder.Default
    private String profileImageUrl = "https://yonghun16.duckdns.org/fantasyshop/assets/noavatar-BWii5hIO.png";
}
