@gorest-co-in
Feature: Create user in https://gorest.co.in/

	@create-user
	@id:1
	Scenario: Create user
		Given endpoint will be "/public/v2/users"
		And headers will be:
			| Content-Type | application/json |
		And Boris authorized with token "createuser/auth/token.txt"
		And request body will be "createuser/request/create-user-request.json"
		When Boris sends POST request in application "gorest-co-in"
		Then response code is equal to "201"
		And variable "user-id" gets value of field "id" from response JSON
		And JSON response body is equal to "createuser/response/create-user-response.json" by comparison rule "NON_EXTENSIBLE"