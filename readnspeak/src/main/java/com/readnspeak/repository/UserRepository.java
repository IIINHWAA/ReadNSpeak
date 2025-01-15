package com.readnspeak.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.readnspeak.entity.Users;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findByUsername(String username);
}