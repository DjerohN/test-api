@gorest-co-in
Feature: Update user in https://gorest.co.in/

	Background: Create user first
		Given endpoint will be "/public/v2/users"
		And headers will be:
			| Content-Type | application/json |
		And Boris authorized with token "updateuser/auth/token.txt"
		And request body will be "updateuser/request/create-user-for-update-request.json"
		When Boris sends POST request in application "gorest-co-in"
		Then response code is equal to "201"
		And variable "user-id" gets value of field "id" from response JSON

	@update-user
	@id:2
	Scenario: Update user, make inactive
		Given endpoint will be "/public/v2/users/{{user-id}}"
		And headers will be:
			| Content-Type | application/json |
		And Boris authorized with token "updateuser/auth/token.txt"
		And request body will be "updateuser/request/update-user-request.json"
		When Boris sends PATCH request in application "gorest-co-in"
		Then response code is equal to "200"
		And variable "user-id" gets value of field "id" from response JSON
		And JSON response body is equal to "updateuser/response/update-user-response.json" by comparison rule "NON_EXTENSIBLE"