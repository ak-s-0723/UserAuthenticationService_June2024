package org.example.userauthenticationservice_june2024.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseModel {
    private Long id;

    private Status status;
}
