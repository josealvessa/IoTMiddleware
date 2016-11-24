package br.org.cin.ufpe.middleware.pojo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Marshaller<T> {

	public byte[] marshall(T entity) throws IOException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(entity);

		return stream.toByteArray();
	}

	public T unmarshall(byte[] entityAsByteArray) throws IOException, ClassNotFoundException {

		ByteArrayInputStream stream = new ByteArrayInputStream(entityAsByteArray);
		ObjectInputStream objectStream = new ObjectInputStream(stream);

		return (T) objectStream.readObject();
	}

}
