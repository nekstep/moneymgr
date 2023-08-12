package com.cfcons.moneymgr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.cfcons.moneymgr.config.AppRoles;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private List<RoleDto> roles;

    public Boolean isAdmin() {
        String adminRole = AppRoles.getRoleName(AppRoles.ADMIN_ROLE);
        
        return roles.stream()
            .filter(x -> x.getName().equals(adminRole))
            .count() > 0;
    }
}
