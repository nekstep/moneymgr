package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    /**
     * Save UserDto to UserRepository
     * @param userDto   Resulting User object in repository
     */
    void saveUser(UserDto userDto);

    /**
     * Find user by email
     * @param email User email
     * @return      Object of type User or null if not found
     */
    User findUserByEmail(String email);

    /**
     * Set a role for a user depending on parameter
     * @param email     User email
     * @param roleName  Role name
     * @param value     true to grant role, false to revoke role
     * @return          true if role was granted, false if role was revoked
     * @throws UsernameNotFoundException    If a user with provided email does not exist
     */
    Boolean setUserRole(String email, String roleName, Boolean value) throws UsernameNotFoundException;

    /**
     * Find all users in DTO format
     * @return  List of UserDto of all system users
     */
    List<UserDto> findAllUsers();
}
