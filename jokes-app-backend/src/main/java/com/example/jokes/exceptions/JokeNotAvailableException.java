package com.example.jokes.exceptions;

public class JokeNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = -6255293520119048713L;

	public JokeNotAvailableException(String message) {
        super(message);
    }
}
