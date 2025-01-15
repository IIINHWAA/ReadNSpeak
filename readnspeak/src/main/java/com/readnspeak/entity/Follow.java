package com.readnspeak.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int followId;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Users follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private Users following;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
