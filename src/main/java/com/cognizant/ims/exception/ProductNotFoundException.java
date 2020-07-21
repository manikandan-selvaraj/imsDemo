package com.cognizant.ims.exception;

public class ProductNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 4180426920574423585L;

	public ProductNotFoundException() {
		super();
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}

}
