# test-api
Sample project for testing API.

Used:
* Java 17
* Maven
* JUnit 4
* Cucumber 7
* RestAssured
* Allure Report

Tests written for https://gorest.co.in/ public API

To run tests

`mvn clean verify` - report will be generated tо directory: `target/site/allure-maven/index.html`

To run tests with report in browser

`mvn clean verify -P local-report` - report will be generated into temp folder. Web server with results will start.
