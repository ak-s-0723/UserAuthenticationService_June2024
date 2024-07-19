package org.example.userauthenticationservice_june2024.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String email;
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}

//1       m
//user  : role
//m        1
//
//
//m : m