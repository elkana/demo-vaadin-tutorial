package com.example.demovaadintutorial.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demovaadintutorial.model.Contact;

public interface IContactRepo extends JpaRepository<Contact, Long> {
    @Query("from Contact c where lower(c.firstName) like lower(concat('%', ?1, '%')) "+
        "or lower(c.lastName) like lower(concat('%', ?1, '%'))")
    List<Contact> search(String filter);
}
