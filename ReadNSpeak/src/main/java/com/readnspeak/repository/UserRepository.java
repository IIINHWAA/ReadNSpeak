package com.readnspeak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.readnspeak.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // JpaRepository에서 기본적으로 제공하는 save() 메서드를 사용할 수 있음
}