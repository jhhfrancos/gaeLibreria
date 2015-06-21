package co.edu.unal.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import co.edu.unal.client.services.LoginService;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig sc) {
		try {
			super.init(sc);

			ObjectifyService.register(Usuario.class);
			ofy = ObjectifyService.factory().begin();

		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Usuario login(Usuario usuario) {
		List<Usuario> listaUsuarios = ofy.load().type(Usuario.class).list();
		for (Usuario user : listaUsuarios) {
			if (usuario.equals(user)) {
				return user;
			}
		}
		return usuario;
	}

	public Usuario loadUsuario(long id) {
		List<Usuario> listaUsuarios = ofy.load().type(Usuario.class).list();
		for (Usuario user : listaUsuarios) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public boolean registrar(Usuario usuario) {
		List<Usuario> listaUsuarios = ofy.load().type(Usuario.class).list();
		usuario.setId(listaUsuarios.size() + 1);

		for (Usuario user : listaUsuarios) {
			if (usuario.equals(user))
				return false;
		}
		try {
			ofy.save().entity(usuario).now();
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	public List<Usuario> listarUsuarios() {
		List<Usuario> listaUsuarios = ofy.load().type(Usuario.class).list();
		List<Usuario> listaFinal = new ArrayList<Usuario>();
		for (Usuario u : listaUsuarios)
			listaFinal.add(u);
		return listaFinal;
	}

	private Objectify ofy;

	@Override
	public boolean eliminarUsuario(long id) {
		ofy.delete().type(Usuario.class).id(id).now();
		return true;
	}

	@Override
	public boolean actualizar(long id, boolean admin, boolean activo) {
		try {
			Usuario temporal = null;
			while(temporal == null)
			temporal = loadUsuario(id);

			temporal.activo = activo;
			temporal.admin = admin;

			registrarActualizacion(temporal);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean registrarActualizacion(Usuario usuario) {

		try {
			ofy.save().entity(usuario).now();
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	
}