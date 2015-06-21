package co.edu.unal.client.services;

import java.util.List;

import co.edu.unal.shared.LoginInfo;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginGmail")
public interface LoginGmailService extends RemoteService {
  /*Usuario login(Usuario usuario);
  List<Usuario> listarUsuarios();
  boolean registrar(Usuario usuario);
  boolean eliminarUsuario(long id);
  boolean actualizar(long id, boolean admin, boolean activo);*/
	// TODO #09: start create login helper methods in service interface	
		String getUserEmail(String token);	

		LoginInfo login(String requestUri);	

		LoginInfo loginDetails(String token);
		// TODO #09:> end
}