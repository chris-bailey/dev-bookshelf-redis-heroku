package com.chrisbaileydeveloper.bookshelf.web.util;

public class Message {
	private String type;
	private String message;

	public Message() {
	}

	public Message(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
