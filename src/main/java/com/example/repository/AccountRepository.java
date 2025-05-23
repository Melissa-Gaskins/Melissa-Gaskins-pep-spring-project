package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    Account findByUsernameAndPassword(String username, String password);
}
