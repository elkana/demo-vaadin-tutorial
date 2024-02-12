package com.example.demovaadintutorial.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.demovaadintutorial.model.Contact;

public interface IContactRepo extends JpaRepository<Contact, Long> {
    
}
