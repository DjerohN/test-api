@gorest-co-in
Feature: Delete user in https://gorest.co.in/

	Background: Create user first
		Given endpoint will be "/public/v2/users"
		And headers will be:
			| Content-Type | application/json |
		And Boris authorized with token "deleteuser/auth/token.txt"
		And request body will be "deleteuser/request/create-user-for-delete-request.json"
		When Boris sends POST request in application "gorest-co-in"
		Then response code is equal to "201"
		And variable "user-id" gets value of field "id" from response JSON

	@delete-user
	@id:3
	Scenario: Delete user
		Given endpoint will be "/public/v2/users/{{user-id}}"
		And headers will be:
			| Content-Type | application/json |
		And Boris authorized with token "deleteuser/auth/token.txt"
		When Boris sends DELETE request in application "gorest-co-in"
		Then response code is equal to "204"
		And response body is empty