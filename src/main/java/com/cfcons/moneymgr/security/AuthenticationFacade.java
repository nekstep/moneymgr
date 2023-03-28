package com.cfcons.moneymgr.security;

import com.cfcons.moneymgr.entity.User;
import org.springframework.security.core.Authentication;

/**
 * Facade to access authentication data
 */
public interface AuthenticationFacade {
    /**
     * Get authentication data
     *
     * @return  Authentication for current session
     */
    Authentication getAuthentication();

    /**
     * Shortcut to get currently logged in user
     *
     * @return  User object for current user
     */
    User getCurrentUser();
}
