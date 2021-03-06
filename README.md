#  QA Automation (Java) Assignment

You're provided with a preconfigured Spring Boot + Cucumber Maven project.
It represents a simple API for creating employees. 
The project contains an example of Cucumber test feature: _create-employee.feature_

Add additional Cucumber tests for the API.

Considerations:

* we expect you to write new features and steps
* not mandatory but would be great if you could extend the API with 1 additional end-point and write test for it

Submission:

* Push your code to a GitHub/GitLab/Bitbucket repository and send us the link
* 3rd party libraries may be used

Requirements:

* Java 8
* Maven 3.6


Building the project:

```
mvn clean install
```

Results:

* Added 1 endpoint (GET employees)
* Additional Test scenario with new endpoint that verifies the presence and structure of recently created employee;
* 4 new negative test scenarios related to create(POST employees) endpoint;
* Some small additional verifications regarding POST request;
* Additional options for existing cucumber steps;



