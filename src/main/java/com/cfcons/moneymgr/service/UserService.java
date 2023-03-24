package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
