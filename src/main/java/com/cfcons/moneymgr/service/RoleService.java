package com.cfcons.moneymgr.service;

import com.cfcons.moneymgr.entity.Role;

public interface RoleService {

    /**
     * Find role by it's name
     *
     * @param roleName  Role name
     * @return          Role object or null if not found
     */
    Role getRoleByName(String roleName);

    /**
     * Add role to the system
     *
     * @param roleName  Role name
     * @return          Role object of the newly added role
     */
    Role addRole(String roleName);

    /**
     * Get Role object for a particular role name, create if non-existent.
     *
     * @param roleName  Role name
     * @return          Role object of existing or newly created role
     */
    Role getRoleByNameOrCreate(String roleName);
}
