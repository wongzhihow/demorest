package com.example.demorest.exception;

import java.time.LocalDateTime;

public class ErrorDetail {
	private LocalDateTime timestamp;
	private String message;

	public ErrorDetail(LocalDateTime timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
