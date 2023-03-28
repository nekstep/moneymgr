package com.cfcons.moneymgr.repository;

import com.cfcons.moneymgr.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find role by name
     * @param name  Name of a role
     * @return      Object of type Role or null if not found
     */
    Role findByName(String name);
}
