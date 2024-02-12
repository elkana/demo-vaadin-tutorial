package com.example.demovaadintutorial.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AEntity {
    @Id
    @GeneratedValue
    private Long id;

    public boolean isPersisted() { return id != null; }
}
