package com.enrolle.enrolles_service.domain;

/**
 * A generic response domain that must be used by all endpoints. The idea is
 * that the restful response should should include Http status code and message.
 * If the response is 200 then it contains data in data object else it contains
 * error message if any.
 */

public class Response {
	/**
	 * Http Response Code
	 */
	private int code;
	/**
	 * Http Response Message
	 */
	private String message;
	/**
	 * Actual data to be send in the response can be null
	 */
	private Object data;

	public Response() {

	}

	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Response(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
