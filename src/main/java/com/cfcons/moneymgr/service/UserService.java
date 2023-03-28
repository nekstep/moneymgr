package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    Boolean setUserRole(String email, String roleName, Boolean admin) throws UsernameNotFoundException;

    List<UserDto> findAllUsers();
}
