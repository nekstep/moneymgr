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

    @Override
    public Role getRoleByName(String roleName) { return roleRepository.findByName(roleName); }

    @Override
    public Role addRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);

        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByNameOrCreate(String roleName) {
        Role role = getRoleByName(roleName);

        if (role == null) {
            role = addRole(roleName);
        }

        return role;
    }
}
