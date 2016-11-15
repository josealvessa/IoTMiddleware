package br.org.cin.ufpe.IoTCommonsProject.listener;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public interface OnFetchServiceListener {

	void onFetchSuccess(ServiceAddress service);

	void onFetchError(String description);
}
