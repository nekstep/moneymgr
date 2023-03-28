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

    /**
     * Save UserDto to UserRepository
     * @param userDto   Resulting User object in repository
     */
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Assign USER role by default
        Role role = roleService.getRoleByNameOrCreate(AppRoles.getRoleName(AppRoles.USER_ROLE));
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }


    /**
     * Find user by email
     * @param email User email
     * @return      Object of type User or null if not found
     */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Set a role for a user depending on parameter
     * @param email     User email
     * @param roleName  Role name
     * @param value     true to grant role, false to revoke role
     * @return          true if role was granted, false if role was revoked
     * @throws UsernameNotFoundException    If a user with provided email does not exist
     */
    @Override
    public Boolean setUserRole(String email, String roleName, Boolean value) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        // Throw an exception if there is no such user in the system
        if (user == null) {
            throw new UsernameNotFoundException("User not found: "+ email);
        }

        // User exists - assign the role and save to repository
        Role role = roleService.getRoleByNameOrCreate(roleName);

        user.updateRole(role, value);

        userRepository.save(user);

        // Double check if the role has been assigned
        return user.hasRole(role);
    }

    /**
     * Find all users in DTO format
     * @return  List of UserDto of all system users
     */
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        // Convert all found users to DTO format and return a list.
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert User to UserDto object.
     *
     * @param user  User
     * @return      UserDto
     */
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        // Populate roles list with RoleDto objects
        userDto.setRoles(user.getRoles().stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList()));

        return userDto;
    }

    /**
     * Convert Role to RoleDto object
     * @param role  Role
     * @return      RoleDto
     */
    private RoleDto mapToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());

        return roleDto;
    }
}
