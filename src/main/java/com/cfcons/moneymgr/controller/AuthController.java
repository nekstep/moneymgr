package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handle home page requests
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handle registration
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email",null,
                    "There is already an account registered with this email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users")
    public String users(Model model, Authentication authentication) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("current_user", authentication.getName());
        return "users";
    }
}