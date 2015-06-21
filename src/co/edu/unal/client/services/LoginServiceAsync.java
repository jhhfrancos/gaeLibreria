package co.edu.unal.client.services;

import java.util.List;

import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  public void login(Usuario usuario, AsyncCallback<Usuario> async);
  public void listarUsuarios(AsyncCallback<List<Usuario>> async);
  public void registrar(Usuario usuario, AsyncCallback<Boolean> async);
  void eliminarUsuario(long id, AsyncCallback<Boolean> callback);
  void actualizar(long id, boolean admin, boolean activo,
		AsyncCallback<Boolean> callback);
}