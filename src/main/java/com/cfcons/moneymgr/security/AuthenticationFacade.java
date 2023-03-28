package com.cfcons.moneymgr.security;

import com.cfcons.moneymgr.entity.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    User getCurrentUser();
}
