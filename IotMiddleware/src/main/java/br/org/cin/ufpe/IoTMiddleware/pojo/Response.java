package br.org.cin.ufpe.IoTMiddleware.pojo;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 3766221263633246881L;
	private int status;
	private String message;
	private byte[] payload;

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

	public static Response getSuccessResponse() {
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Success");

		return response;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + "]";
	}

}
