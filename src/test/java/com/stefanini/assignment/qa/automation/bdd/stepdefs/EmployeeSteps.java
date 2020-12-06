package com.stefanini.assignment.qa.automation.bdd.stepdefs;

import static com.stefanini.assignment.qa.automation.bdd.CucumberTestContext.ACTUAL_RESULT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.stefanini.assignment.qa.automation.bdd.CucumberTestContext;
import com.stefanini.assignment.qa.automation.employee.Employee;
import com.stefanini.assignment.qa.automation.employee.Phone;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

/**
 * Step Definition Class for Employee.
 *
 * <p>Uses Java Lambda style step definitions so that we don't need to worry
 * about method naming for each step definition</p>
 */
public class EmployeeSteps extends AbstractSteps implements En {

    public EmployeeSteps() {

        Given("user wants to create an employee with the following attributes", (DataTable employeeDt) -> {
            testContext().reset();
            List<Employee> employeeList = employeeDt.asList(Employee.class);

            // First row of DataTable has the employee attributes hence calling get(0) method.
            super.testContext()
                    .setPayload(employeeList.get(0));
        });

        And("with the following phone numbers", (DataTable phoneDt) -> {
            List<Phone> phoneList = phoneDt.asList(Phone.class);
            super.testContext()
                    .getPayload(Employee.class)
                    .setPhones(phoneList);
        });

        When("user saves the new employee {string}", (String testContext) -> {
            String createEmployeeUrl = "/v1/employees";
            Employee employee = super.testContext().getPayload(Employee.class);
            switch (testContext) {
                case "WITH ALL REQUIRED FIELDS":
                    break;
                case "WITH EMPTY ID":
                    employee.setId(null);
                    break;
                case "WITH EMPTY FIRST_NAME":
                    employee.setFirstName(null);
                    break;
                case "WITH EMPTY LAST_NAME":
                    employee.setLastName(null);
                    break;
                case "WITH EMPTY DATE_OF_BIRTH":
                    employee.setDateOfBirth(null);
                    break;
                case "WITH EMPTY START_DATE":
                    employee.setStartDate(null);
                    break;
                case "WITH EMPTY EMPLOYEE_TYPE":
                    employee.setEmploymentType(null);
                    break;
                case "WITH EMPTY EMAIL":
                    employee.setEmail(null);
                    break;
                case "WITH EMPTY PHONES":
                    employee.setPhones(null);
                    break;
                case "WITH EXISTING ID":
                    employee.setId(getExistingEmployee().getId());
                    break;
                default:
                    fail("Undefined test condition [%s]", testContext);
            }

            // AbstractSteps class makes the POST call and stores response in TestContext
            executePost(createEmployeeUrl);
        });

        Then("the response {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();

            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201, 202);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

        When("get all existing employees", () -> {
            String getEmployeesUrl = "/v1/employees";
            executeGet(getEmployeesUrl);
            List<Employee> employees = Arrays.asList(testContext().getResponse().as(Employee[].class));
            testContext().set(ACTUAL_RESULT, employees);
        });

        Then("validate received employee", () -> {
            Employee expectedEmployee = testContext().getPayload(Employee.class);
            List<Employee> listOfEmployees = (List<Employee>) testContext().get(ACTUAL_RESULT);
            Employee actualEmployee = listOfEmployees.stream()
                    .filter(e -> e.getId() == expectedEmployee.getId())
                    .findFirst()
                    .orElseThrow(() ->
                            new NullPointerException(format("Could not find employee [%s]", expectedEmployee.getId())));

            assertThat(actualEmployee.getFirstName())
                    .withFailMessage("Employee first name should be [%s]", expectedEmployee.getFirstName())
                    .isEqualTo(expectedEmployee.getFirstName());

            assertThat(actualEmployee.getLastName())
                    .withFailMessage("Employee last name should be [%s]", expectedEmployee.getLastName())
                    .isEqualTo(expectedEmployee.getLastName());

            assertThat(actualEmployee.getDateOfBirth())
                    .withFailMessage("Employee date of birth should be [%s]", expectedEmployee.getDateOfBirth())
                    .isEqualTo(expectedEmployee.getDateOfBirth());

            assertThat(actualEmployee.getStartDate())
                    .withFailMessage("Employee start date should be [%s]", expectedEmployee.getStartDate())
                    .isEqualTo(expectedEmployee.getStartDate());

            assertThat(actualEmployee.getEmail())
                    .withFailMessage("Employee email should be [%s]", expectedEmployee.getEmail())
                    .isEqualTo(expectedEmployee.getEmail());

            assertThat(actualEmployee.getEndDate())
                    .withFailMessage("Employee end date should be [%s]", expectedEmployee.getEndDate())
                    .isEqualTo(expectedEmployee.getEndDate());

            assertThat(actualEmployee.getEmploymentType())
                    .withFailMessage("Employee employment type should be [%s]", expectedEmployee.getEmploymentType())
                    .isEqualTo(expectedEmployee.getEmploymentType());

            assertThat(actualEmployee.getPhones().size())
                    .withFailMessage("Employee phones amount should be [%s]", expectedEmployee.getPhones().size())
                    .isEqualTo(expectedEmployee.getPhones().size());

            List<Phone> expectedPhones = expectedEmployee.getPhones();
            List<Phone> actualPhones = actualEmployee.getPhones();

            for (Phone expectedPhone : expectedPhones) {
                Phone matchingPhone = null;

                for (Phone actualPhone : actualPhones) {

                    if (actualPhone.getId().equals(expectedPhone.getId())) {
                        matchingPhone = actualPhone;
                    }
                }

                if (matchingPhone == null) {
                    fail("Could not find: " + expectedPhone.toString());
                } else {
                    assertThat(matchingPhone.getExtension())
                            .withFailMessage("Phone extension should be [%s]", expectedPhone.getExtension())
                            .isEqualTo(expectedPhone.getExtension());

                    assertThat(matchingPhone.getIsdCode())
                            .withFailMessage("Phone extension should be [%s]", expectedPhone.getIsdCode())
                            .isEqualTo(expectedPhone.getIsdCode());

                    assertThat(matchingPhone.getPhoneNumber())
                            .withFailMessage("Phone number should be [%s]", expectedPhone.getPhoneNumber())
                            .isEqualTo(expectedPhone.getPhoneNumber());

                    assertThat(matchingPhone.getType())
                            .withFailMessage("Phone type should be [%s]", expectedPhone.getType())
                            .isEqualTo(expectedPhone.getType());
                }
            }
        });
    }
    @SneakyThrows
    private Employee getExistingEmployee() {
        executeGet("/v1/employees");
        Employee[] employees = testContext().getResponse().as(Employee[].class);

        if (employees.length < 1) {
            fail("No existing employees");
        }
        return employees[0];
    }

}
