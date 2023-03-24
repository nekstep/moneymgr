package com.cfcons.moneymgr.repository;

import com.cfcons.moneymgr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
