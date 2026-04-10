package com.aierken.aierken_practice.repository;

import com.aierken.aierken_practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join u.accounts a where a.accountNumber = :accountNumber")
    Optional<User> findByAccountNumber(@Param("accountNumber") String accountNumber);

    User findByName(String name);

    User findByEmail(String email);

    void deleteById(Long id);
}
