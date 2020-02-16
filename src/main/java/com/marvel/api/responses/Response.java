package com.marvel.api.responses;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	
	private T data;
	private List<String> messages;
	
	public Response(T data) {
		this.data = data;
	}
	
	public Response(List<String> messages) {
		this.messages = messages;
	}

	public Response(String string) {
		List<String> messages = new ArrayList<String>();
		messages.add(string);
		this.messages = messages;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getmessages() {
		return messages;
	}

	public void setmessages(List<String> messages) {
		this.messages = messages;
	}

}
