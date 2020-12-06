package com.stefanini.assignment.qa.automation.employee;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class EmployeeService {

  @Autowired
  private EmployeeMapper mapper;

  @Autowired
  private EmployeeDao employeeDao;

  @Transactional
  @Validated
  public Long create(@Valid Employee employee) {
    EmployeeEntity employeeEntity = mapper.toEmployeeEntity(employee);
    employeeDao.save(employeeEntity);

    return employeeEntity.getId();
  }

  @Transactional
  @Validated
  public List<Employee> getEmployees() {
    List<Employee> result = new ArrayList<>();
    employeeDao.findAll().forEach(employeeEntity -> result.add(mapper.toEmployee(employeeEntity)));

    return result;
  }

}
