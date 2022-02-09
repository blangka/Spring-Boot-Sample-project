package com.hkmc.sample.repo.jpa;

import com.google.common.io.Files;
import com.hkmc.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    Optional<User> findByAccount(String account);

    Optional<User> findByEmail(String name);
}
