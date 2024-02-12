package com.example.demovaadintutorial.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company extends AEntity {
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Contact> employees;
}
