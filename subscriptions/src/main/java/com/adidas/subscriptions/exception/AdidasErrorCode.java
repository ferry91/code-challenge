package com.adidas.subscriptions.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of error codes.
 */
@Getter
@AllArgsConstructor
public enum AdidasErrorCode implements ErrorCode {


	TECH_001("Http message not readable"),
	TECH_002("Method argument not valid"),
	TECH_003("Missing servlet request part"),
	TECH_004("Missing servlet request parameter"),
	TECH_005("Type mismatch"),
	TECH_006("No handler found"),
	TECH_007("No such element"),
	TECH_008("Http request method not supported"),
	TECH_009("Http media type not supported"),
	TECH_010("Internal server error"),
	BUSI_001("You do not meet the requirements"),
	BUSI_002("Business process failure");

	/** The reason phrase. */
	private final String reasonPhrase;

}
