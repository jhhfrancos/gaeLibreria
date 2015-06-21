package co.edu.unal.client.services;

import java.util.List;

import co.edu.unal.shared.LoginInfo;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginGmailServiceAsync {
	

	// TODO #10: create login helper methods in service asynchronous interface	
	void getUserEmail(String token, AsyncCallback<String> callback);

	void login(String requestUri, AsyncCallback<LoginInfo> asyncCallback);

	void loginDetails(String token, AsyncCallback<LoginInfo> asyncCallback);
	// TODO #10:> end
	
  /*public void login(Usuario usuario, AsyncCallback<Usuario> async);
  public void listarUsuarios(AsyncCallback<List<Usuario>> async);
  public void registrar(Usuario usuario, AsyncCallback<Boolean> async);
  void eliminarUsuario(long id, AsyncCallback<Boolean> callback);
  void actualizar(long id, boolean admin, boolean activo,
		AsyncCallback<Boolean> callback);*/
}