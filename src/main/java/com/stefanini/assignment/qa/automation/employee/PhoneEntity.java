package com.stefanini.assignment.qa.automation.employee;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phone")
@Getter
@Setter
@EqualsAndHashCode
public class PhoneEntity implements Serializable {

  @Id
  private Long id;

  private String type;
  private String isdCode;
  private String phoneNumber;
  private String extension;

}
