package com.cfcons.moneymgr.service.implementation;

import com.cfcons.moneymgr.config.AppRoles;
import com.cfcons.moneymgr.dto.RoleDto;
import com.cfcons.moneymgr.dto.UserDto;
import com.cfcons.moneymgr.entity.Role;
import com.cfcons.moneymgr.entity.User;
import com.cfcons.moneymgr.repository.UserRepository;
import com.cfcons.moneymgr.service.RoleService;
import com.cfcons.moneymgr.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleService.getRoleByNameOrCreate(AppRoles.getRoleName(AppRoles.USER_ROLE));
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean setUserRole(String email, String roleName, Boolean value) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: "+ email);
        }

        Role role = roleService.getRoleByNameOrCreate(roleName);

        user.updateRole(role, value);

        userRepository.save(user);

        return user.hasRole(role);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        userDto.setRoles(user.getRoles().stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList()));

        return userDto;
    }

    private RoleDto mapToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());

        return roleDto;
    }
}
