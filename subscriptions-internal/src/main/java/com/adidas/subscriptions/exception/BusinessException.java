package com.adidas.subscriptions.exception;

import java.util.List;

/**
 * This exception is thrown whenever a business exception occurs.
 */
public class BusinessException extends AdidasException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorCode
	 *            the error code of the exception
	 * @param message
	 *            the description of the exception
	 */
	public BusinessException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorCode
	 *            the error code of the exception
	 * @param message
	 *            the description of the exception
	 * @param errors
	 *            the errors of the exception
	 */
	public BusinessException(ErrorCode errorCode, String message, List<String> errors) {
		super(errorCode, message, errors);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorCode
	 *            the error code of the exception
	 * @param message
	 *            the description of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorCode
	 *            the error code of the exception
	 * @param message
	 *            the description of the exception
	 * @param errors
	 *            the errors of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public BusinessException(ErrorCode errorCode, String message, List<String> errors, Throwable cause) {
		super(errorCode, message, errors, cause);
	}

}
