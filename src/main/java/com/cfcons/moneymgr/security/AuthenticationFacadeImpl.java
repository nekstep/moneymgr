package com.cfcons.moneymgr.security;

import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserRepository userRepository;

    /**
     * Get authentication data
     *
     * @return  Authentication for current session
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Shortcut to get currently logged in user
     *
     * @return  User object for current user
     */
    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(getAuthentication().getName());
    }
}
