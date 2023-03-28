package com.cfcons.moneymgr.repository;

import com.cfcons.moneymgr.entity.Account;
import com.cfcons.moneymgr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUser(User user);
}
