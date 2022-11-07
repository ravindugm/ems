package com.practicecode.secureclient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    @Column(length = 60)
    private String password;
    private String role;
    private boolean enabled = false;
}
