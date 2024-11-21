package com.booksms.authentication.core.entity;

import com.booksms.authentication.core.constant.STATIC_VAR;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer address;
    private String password;
    private Boolean isVerified;
    private Boolean isFirstVisit;
    private Boolean isBlocked;
    private Integer failAttempt;
    private LocalDateTime lockTime;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name= "role_id")
    )
    private Set<Role> roles;

    public void setRoles(Role role) {
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public Boolean isGlobalAdmin(){
        if(roles.size() == 1){
            return false;
        }

        Role adminRole = roles.stream().filter(role -> role.getName().equals(STATIC_VAR.GLOBAL_ADMIN_ROLE_NAME)).findFirst().orElse(null);
        return adminRole == null;
    }
}
