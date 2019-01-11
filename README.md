# Sales taxes
Java11 application solving the problem at https://github.com/xpeppers/sales-taxes-problem

## Specs to run
 - Java 11 JDK
 - Maven
 
 To run just use ```mvn test```
 
 **IMPORTANT:** There is a typo in the INPUT provided, I fixed it in my code, wanting to pass the tests. However 2 tests do not pass, as 2 of the output values are different. Unknown if a bug or a typo.
 
 ## Implementation
 It is a Spring-Boot app where the input data has been provided as mocked data to some tests (Junit,Mockito). Given the scope of the exercise, no work has been done to allow any other kind of user input, but the implementation permits a command line or a simple web app for that.
 While some hard-coded data is saved in classes or in properties file, the rules for the goods with no basic tax, have been implemented as if they were in two database tables.
 The access to the database has been mocked too for the tests.
 The business logic, parsing and formatting is all done by a service class.
