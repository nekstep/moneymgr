package com.cfcons.moneymgr.repository;

import com.cfcons.moneymgr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     *
     * @param email     User email
     * @return          Object of type User or null, if there is no such user.
     */
    User findByEmail(String email);

}
