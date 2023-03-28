package com.cfcons.moneymgr.service.implementation;

import com.cfcons.moneymgr.entity.Role;
import com.cfcons.moneymgr.repository.RoleRepository;
import com.cfcons.moneymgr.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Find role by it's name
     *
     * @param roleName  Role name
     * @return          Role object or null if not found
     */
    @Override
    public Role getRoleByName(String roleName) { return roleRepository.findByName(roleName); }

    /**
     * Add role to the system
     *
     * @param roleName  Role name
     * @return          Role object of the newly added role
     */
    @Override
    public Role addRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);

        return roleRepository.save(role);
    }

    /**
     * Get Role object for a particular role name, create if non-existent.
     *
     * @param roleName  Role name
     * @return          Role object of existing or newly created role
     */
    @Override
    public Role getRoleByNameOrCreate(String roleName) {

        // Check whether a role with this name already exists
        Role role = getRoleByName(roleName);

        // If not - create it
        if (role == null) {
            role = addRole(roleName);
        }

        return role;
    }
}
