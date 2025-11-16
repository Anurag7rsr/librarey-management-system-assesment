package com.Library.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true,nullable = false)
    private  String email;
    private String password;
    private String role;
    private Instant createdAt= Instant.now();

}
