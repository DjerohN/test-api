package io.github.djerohn.exception;

public class ApiTestException extends RuntimeException {

	public ApiTestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiTestException(String message) {
		super(message);
	}

	public ApiTestException(Throwable cause) {
		super(cause);
	}
}