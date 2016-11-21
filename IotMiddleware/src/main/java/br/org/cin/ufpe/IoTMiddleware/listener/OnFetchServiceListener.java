package br.org.cin.ufpe.IoTMiddleware.listener;

import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;

public interface OnFetchServiceListener {

	void onFetchSuccess(ServiceAddress service);

	void onFetchError(String description);
}
