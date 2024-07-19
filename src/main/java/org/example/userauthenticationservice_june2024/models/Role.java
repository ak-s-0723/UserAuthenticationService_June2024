package org.example.userauthenticationservice_june2024.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Role extends BaseModel {
    private String value;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> user;
}
