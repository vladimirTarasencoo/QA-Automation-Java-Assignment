package com.stefanini.assignment.qa.automation.employee;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity create(@RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder) {
    Long id = employeeService.create(employee);

    final URI uri = uriComponentsBuilder.path("/v1/employees/{id}")
        .build(id);

    if (employee.getDateOfBirth().isAfter(LocalDate.now())) {
      return new ResponseEntity<> ("Invalid date of birth", BAD_REQUEST);
    }

    if (employee.getStartDate().isAfter(LocalDate.now())) {
      return new ResponseEntity<> ("Invalid start date", BAD_REQUEST);
    }

    return ResponseEntity.created(uri)
        .build();
  }

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity create(UriComponentsBuilder uriComponentsBuilder) {
    List<Employee> employeeList = employeeService.getEmployees();

    final URI uri = uriComponentsBuilder.path("/v1/employees/")
            .build().toUri();

    return ResponseEntity.accepted().body(employeeList);
  }

}
