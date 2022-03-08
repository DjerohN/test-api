package io.github.djerohn.hooks;

import io.cucumber.java.After;
import io.github.djerohn.manager.SessionManager;
import io.github.djerohn.session.Session;
import io.restassured.RestAssured;

public class DeleteUser {
	private Session session;

	public DeleteUser(Session session) {
		this.session = session;
	}

	@After("@create-user or @update-user")
	public void deleteUser() {
		RestAssured.given()
				.baseUri("https://gorest.co.in")
				.basePath(SessionManager.setSessionVariables(session, "/public/v2/users/{{user-id}}"))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer c2470f2767479d7990a171bfa9761efaff664618401db77ee7f0d40579246993")
				.when()
				.redirects().follow(false)
				.delete()
				.then()
				.statusCode(204);
	}
}
