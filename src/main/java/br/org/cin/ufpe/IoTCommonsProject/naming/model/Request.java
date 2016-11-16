package br.org.cin.ufpe.IoTCommonsProject.naming.model;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {

	private static final long serialVersionUID = -4103150184180373577L;
	private String operation;
	private byte[] payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Request [operation=" + operation + ", payload=" + Arrays.toString(payload) + "]";
	}

}