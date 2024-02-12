package com.example.demovaadintutorial.repo;

import org.springframework.data.repository.ListCrudRepository;
import com.example.demovaadintutorial.model.Company;

public interface ICompanyRepo extends ListCrudRepository<Company, Long>{
    
}
