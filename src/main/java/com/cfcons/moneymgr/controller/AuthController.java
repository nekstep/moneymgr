package com.cfcons.moneymgr.controller;

import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Authentication controller for the application
 */
@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Handle home page request
     *
     * @return  View name for main page
     */
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    /**
     * Show new user registration form
     *
     * @param model Model
     * @return      View to render
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    /**
     * Check new user for errors and save to database
     *
     * @param userDto   User data
     * @param result    Binding
     * @param model     Model
     * @return          View name to render
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        // Check if the user with this email already exists
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email",null,
                    "There is already an account registered with this email");
        }

        // Check for errors in submitted data
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        // Save user to database and return success
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    /**
     * Display login form
     *
     * @return  View name
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
