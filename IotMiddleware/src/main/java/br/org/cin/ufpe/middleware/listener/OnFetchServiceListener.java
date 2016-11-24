package br.org.cin.ufpe.middleware.listener;

import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;

public interface OnFetchServiceListener {

	void onFetchSuccess(ServiceAddress service);

	void onFetchError(String description);
}
