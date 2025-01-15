package com.readnspeak.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password_hash;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    private boolean verified = false; 

    public enum Role {
        USER, ADMIN
    }
    
}
