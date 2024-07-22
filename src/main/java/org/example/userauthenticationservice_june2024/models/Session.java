package org.example.userauthenticationservice_june2024.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseModel {

    @ManyToOne
    private User user;

    private String token;

    @Enumerated(EnumType.ORDINAL)
    private SessionState sessionState;
}

//1         1
//session : user
//m          1