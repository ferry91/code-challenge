package com.adidas.subscriptions.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * The api response for handled errors.
 */
@Getter
public class ApiError {

	/** The version. */
	private final LocalDateTime timestamp;

	/** The error code. */
	private final ErrorCode errorCode;

	/** The message. */
	private final String message;

	/** The errors. */
	private final List<String> errors;

	/**
	 * Instantiates a new api error.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the message of the exception
	 *
	 */
	public ApiError(ErrorCode errorCode, String message) {
		super();
		this.timestamp = LocalDateTime.now();
		this.errorCode = errorCode;
		this.message = message;
		this.errors = new ArrayList<>();
	}

	/**
	 * Instantiates a new api error.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the message of the exception
	 * @param errors    the errors of the exception
	 */
	public ApiError(ErrorCode errorCode, String message, List<String> errors) {
		super();
		this.timestamp = LocalDateTime.now();
		this.errorCode = errorCode;
		this.message = message;
		this.errors = new ArrayList<>(errors);
	}

	/**
	 * Instantiates a new api error.
	 *
	 * @param message   the message of the exception
	 *
	 */
	public ApiError(String message) {
		super();
		this.timestamp = LocalDateTime.now();
		this.errorCode = null;
		this.message = message;
		this.errors = new ArrayList<>();
	}

	/**
	 * Instantiates a new api error.
	 *
	 * @param message   the message of the exception
	 * @param errors    the errors of the exception
	 */
	public ApiError(String message, List<String> errors) {
		super();
		this.timestamp = LocalDateTime.now();
		this.errorCode = null;
		this.message = message;
		this.errors = new ArrayList<>(errors);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiError [timestamp=" + timestamp + ", errorCode=" + errorCode + ", message=" + message + ", errors="
				+ errors + "]";
	}

}
