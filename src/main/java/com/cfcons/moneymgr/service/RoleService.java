package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.entity.Role;

public interface RoleService {
    Role getRoleByName(String roleName);

    Role addRole(String roleName);

    Role getRoleByNameOrCreate(String roleName);
}
