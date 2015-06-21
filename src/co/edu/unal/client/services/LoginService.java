package co.edu.unal.client.services;

import java.util.List;

import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  Usuario login(Usuario usuario);
  List<Usuario> listarUsuarios();
  boolean registrar(Usuario usuario);
  boolean eliminarUsuario(long id);
  boolean actualizar(long id, boolean admin, boolean activo);
}