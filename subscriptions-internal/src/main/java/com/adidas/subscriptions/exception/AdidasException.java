package com.adidas.subscriptions.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base exception from which the other exceptions of the project
 * inherit.
 */
@Getter
public abstract class AdidasException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error code. */
	private final ErrorCode errorCode;

	/** The errors. */
	private final List<String> errors;

	/**
	 * Instantiates a new arch exception.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the description of the exception
	 */
	public AdidasException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

	/**
	 * Instantiates a new arch exception.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the description of the exception
	 * @param errors    the errors of the exception
	 */
	public AdidasException(ErrorCode errorCode, String message, List<String> errors) {
		super(message);
		this.errorCode = errorCode;
		this.errors = new ArrayList<>(errors);
	}

	/**
	 * Instantiates a new arch exception.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the description of the exception
	 * @param cause     the cause of the exception
	 */
	public AdidasException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

	/**
	 * Instantiates a new arch exception.
	 *
	 * @param errorCode the error code of the exception
	 * @param message   the description of the exception
	 * @param errors    the errors of the exception
	 * @param cause     the cause of the exception
	 */
	public AdidasException(ErrorCode errorCode, String message, List<String> errors, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errors = new ArrayList<>(errors);
	}

}
