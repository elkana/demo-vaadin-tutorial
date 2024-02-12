package com.example.demovaadintutorial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact extends AEntity {
    public enum Status {
        ImportedLead, NotContacted, Contacted, Customer, ClosedLost
    }

    private String firstName;
    private String lastName;

    @ManyToOne
    private Company company;

    private Status status;

    private String email;
}
