package br.org.cin.ufpe.IoTCommonsProject.pojo;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 3766221263633246881L;
	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
