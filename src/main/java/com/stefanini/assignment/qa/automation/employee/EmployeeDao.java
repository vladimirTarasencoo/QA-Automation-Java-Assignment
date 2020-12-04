package com.stefanini.assignment.qa.automation.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<EmployeeEntity, Long> {}
