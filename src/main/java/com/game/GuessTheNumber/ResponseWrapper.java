package com.game.GuessTheNumber;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {
	private String userMsg;
	private String errorMsg;
	private Object result;

	public ResponseWrapper() {
	}

	public ResponseWrapper(Object result) {
		this.result = result;
	}

	public ResponseWrapper(Exception e) {
		this.result = null;
		this.errorMsg = e.getMessage();
	}
	
	public String getUserMsg() {
		return userMsg;
	}

	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String error) {
		this.errorMsg = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}