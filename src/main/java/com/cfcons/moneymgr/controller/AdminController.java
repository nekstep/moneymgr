package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.config.AppRoles;
import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.model.AdminRoleModifyRequest;
import com.cfcons.moneymgr.model.AdminRoleModifyResponse;
import com.cfcons.moneymgr.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE_DELIMITER = ",";

    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model, Authentication authentication) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("current_user", authentication.getName());
        return "users";
    }

    @PostMapping(value = "/users/adminrole", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> setAdminPrivilege(@Valid AdminRoleModifyRequest request, Errors errors) {
        AdminRoleModifyResponse response = new AdminRoleModifyResponse();

        if (errors.hasErrors()) {
            response.setMessage(errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(ERROR_MESSAGE_DELIMITER)));
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Boolean roleValue = userService.setUserRole(request.getEmail(),
                    AppRoles.getRoleName(AppRoles.ADMIN_ROLE),
                    request.getIsadmin());

            response.setMessage(SUCCESS_MESSAGE);
            response.setEmail(request.getEmail());
            response.setIsadmin(roleValue);
        } catch (UsernameNotFoundException e) {
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

}
