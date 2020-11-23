package com.demo.es.client;

public class ESConnectionException extends RuntimeException {

	private static final long serialVersionUID = -1893567882276551259L;

	public ESConnectionException() {
		super();
	}

	public ESConnectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ESConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ESConnectionException(String message) {
		super(message);
	}

	public ESConnectionException(Throwable cause) {
		super(cause);
	}

	
}
