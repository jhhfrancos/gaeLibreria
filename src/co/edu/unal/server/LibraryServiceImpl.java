package co.edu.unal.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import co.edu.unal.client.services.LibraryService;
import co.edu.unal.shared.Inventario;
import co.edu.unal.shared.Libro;
import co.edu.unal.shared.Movimiento;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class LibraryServiceImpl extends RemoteServiceServlet implements
		LibraryService {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig sc) {
		try {
			super.init(sc);

			ObjectifyService.register(Inventario.class);
			ObjectifyService.register(Libro.class);
			ObjectifyService.register(Movimiento.class);

			ofy = ObjectifyService.factory().begin();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private String escapeHtml(String html) {
		return (html != null ? html.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;").replaceAll(">", "&gt;") : null);
	}

	// ----------------------------------------------------------------------------------------------------------
	@Override
	public List<Libro> listarLibros() {
		List<Libro> listaLibros = ofy.load().type(Libro.class).list();

		ArrayList<Libro> AListaLibros = new ArrayList<Libro>();

		for (Libro l : listaLibros) {
			AListaLibros.add(l);
		}

		return AListaLibros;
	}
	
	@Override
	public List<ArrayList<String>> getDatosReporte() {
		List<Libro> listaLibros = ofy.load().type(Libro.class).list();

		List<ArrayList<String>> informacionReporte = new ArrayList<ArrayList<String>>();
		
		for (Libro l : listaLibros) {
			ArrayList<String> temporal = new ArrayList<String>();
			
			temporal.add(l.getIsbn()+"");									//0
			temporal.add(l.getTitulo());									//1
			temporal.add(l.getNombreAutor());								//2
			temporal.add(loadMovimiento(l.getIsbn()).getEntradas()+"");		//3
			temporal.add(loadMovimiento(l.getIsbn()).getSalidas()+"");		//4
			temporal.add(l.getCosto()+"");									//5
			
			informacionReporte.add(temporal);
		}

		return informacionReporte;
	}

	@Override
	public List<Movimiento> listarMovimientos() {
		List<Movimiento> listaMovimientos = ofy.load().type(Movimiento.class)
				.list();

		ArrayList<Movimiento> AListaMovimientos = new ArrayList<Movimiento>();

		for (Movimiento m : listaMovimientos) {
			AListaMovimientos.add(m);
		}

		return AListaMovimientos;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		List<Usuario> listaUsuarios = ofy.load().type(Usuario.class).list();

		ArrayList<Usuario> AListaUsuarios = new ArrayList<Usuario>();

		for (Usuario u : listaUsuarios) {
			AListaUsuarios.add(u);
		}

		return AListaUsuarios;
	}

	@Override
	public List<Libro> listarLibrosN(String nombre)
			throws IllegalArgumentException {
		List<Libro> librosTemp = listarLibros();
		List<Libro> librosFin = new ArrayList<Libro>();

		nombre = escapeHtml(nombre);

		for (Libro libroR : librosTemp) {
			if (libroR.getTitulo().matches("(.*)" + nombre + "(.*)"))
				librosFin.add(libroR);
		}
		return librosFin;
	}

	@Override
	public List<Libro> listarLibrosA(String autor)
			throws IllegalArgumentException {
		List<Libro> librosTemp = listarLibros();
		List<Libro> librosFin = new ArrayList<Libro>();

		autor = escapeHtml(autor);

		for (Libro libroR : librosTemp) {
			if (libroR.getNombreAutor().matches("(.*)" + autor + "(.*)"))
				librosFin.add(libroR);
		}
		return librosFin;
	}

	@Override
	public List<Libro> listarLibrosC(String categoria)
			throws IllegalArgumentException {
		List<Libro> librosTemp = listarLibros();
		List<Libro> librosFin = new ArrayList<Libro>();

		categoria = escapeHtml(categoria);

		for (Libro libroR : librosTemp) {
			if (libroR.getCategoria().matches("(.*)" + categoria + "(.*)")) {
				librosFin.add(libroR);
			}
		}
		return librosFin;
	}

	@Override
	public List<Libro> listarLibrosE(String editorial)
			throws IllegalArgumentException {
		List<Libro> librosTemp = listarLibros();
		List<Libro> librosFin = new ArrayList<Libro>();

		editorial = escapeHtml(editorial);

		for (Libro libroR : librosTemp) {
			if (libroR.getEditorial().matches("(.*)" + editorial + "(.*)")) {
				librosFin.add(libroR);
			}
		}
		return librosFin;
	}

	// ----------------------------------------------------------------------------------------------------------
	@Override
	public boolean guardarLibro(Libro libro, int cantidad) {
		int cantidad_inicial = 0, cantidad_final = 0;
		long isbn = libro.getIsbn();
		Libro temp = loadLibro(isbn);
		Inventario invLibro = new Inventario();
		Movimiento movLibro;

		if (temp == null) {
			movLibro = new Movimiento(libro);
			
			movLibro.setEntradas(cantidad);
			
			invLibro.setID(libro.getIsbn());
			invLibro.setCantidad(cantidad);

			try {
				
				ofy.save().entity(libro).now();
				ofy.save().entity(invLibro).now();
				ofy.save().entity(movLibro).now();
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			movLibro = loadMovimiento(libro.getIsbn());
			
			invLibro = loadInventario(libro.getIsbn());
			
			cantidad_inicial = invLibro.getCantidad();
			cantidad_final = cantidad;
			
			invLibro.setCantidad(cantidad);
			
			try {
				int val = cantidad_final-cantidad_inicial;
				
				if((val) < 0)
					movLibro.setSalidas(movLibro.getSalidas()-val);
				else
					movLibro.setEntradas(movLibro.getEntradas()+val);
				
				ofy.save().entity(movLibro).now();
				ofy.save().entity(libro).now();
				ofy.save().entity(invLibro).now();
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public boolean guardarMovimiento(Movimiento movimiento)
			throws IllegalArgumentException {
		try {
			ofy.save().entity(movimiento).now();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean guardarUsuario(Usuario usuario)
			throws IllegalArgumentException {
		try {
			ofy.save().entity(usuario).now();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// ----------------------------------------------------------------------------------------------------------
	@Override
	public Libro loadLibro(long isbn) throws IllegalArgumentException {
		LoadResult<Libro> l = ofy.load().type(Libro.class).id(isbn);

		return l.now();
	}

	@Override
	public Libro loadLibro(String name, long isbn)
			throws IllegalArgumentException {
		Libro resultado = null;
		if(isbn > 0){
			LoadResult<Libro> l = ofy.load().type(Libro.class).id(isbn);
			resultado = l.now();
		} 
		if (resultado == null)
			resultado = loadLibro(name);

		return resultado;
	}

	@Override
	public Movimiento loadMovimiento(long idMovimiento)
			throws IllegalArgumentException {
		LoadResult<Movimiento> m = ofy.load().type(Movimiento.class)
				.id(idMovimiento);
		return m.now();
	}

	@Override
	public Usuario loadUsuario(int idUsuario) throws IllegalArgumentException {
		LoadResult<Usuario> u = ofy.load().type(Usuario.class).id(idUsuario);
		return u.now();
	}

	@Override
	public Libro loadLibro(String name) throws IllegalArgumentException {
		List<Libro> listaLibros = listarLibros();
		for (Libro l : listaLibros)
			if (l.getTitulo().equals(name))
				return l;
		return null;
	}

	@Override
	public Usuario loadUsuario(String name) throws IllegalArgumentException {
		List<Usuario> listaUsuarios = listarUsuarios();
		for (Usuario u : listaUsuarios)
			if (u.getNombre().equals(u.getNombre()))
				return u;

		return null;
	}

	// ----------------------------------------------------------------------------------------------------------
	@Override
	public boolean borrarLibro(Libro libro) throws IllegalArgumentException {
		try {
			ofy.delete().type(Libro.class).id(libro.getIsbn()).now();
			borrarInventario(libro.getIsbn());
			borrarMovimiento(libro.getIsbn());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean borrarInventario(long id) throws IllegalArgumentException {
		try {
			ofy.delete().type(Inventario.class).id(id).now();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean borrarMovimiento(long id)
			throws IllegalArgumentException {
		try {
			ofy.delete().type(Movimiento.class).id(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean borrarUsuario(Usuario usuario)
			throws IllegalArgumentException {
		try {
			ofy.delete().type(Usuario.class).id(usuario.getId());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// ----------------------------------------------------------------------------------------------------------
	private Objectify ofy;

	@Override
	public Inventario loadInventario(long isbn) {
		LoadResult<Inventario> u = ofy.load().type(Inventario.class).id(isbn);
		return u.now();
	}

	@Override
	public List<Inventario> listarInventario() {
		List<Inventario> listaInventarios = ofy.load().type(Inventario.class)
				.list();

		ArrayList<Inventario> AListaInventarios = new ArrayList<Inventario>();

		for (Inventario i : listaInventarios) {
			AListaInventarios.add(i);
		}

		return AListaInventarios;
	}
	
	@Override
	public List<String> getDatosInventario(long isbn) {
		Inventario inventario = loadInventario(isbn);
		Libro libro = loadLibro(isbn);
		Movimiento movimiento = loadMovimiento(isbn);

		ArrayList<String> listaInventarios = new ArrayList<String>();
		listaInventarios.add(isbn + "");
		listaInventarios.add(libro.getTitulo());
		listaInventarios.add(inventario.getCantidad() + "");
		listaInventarios.add(movimiento.getSalidas() + "");
		listaInventarios.add(movimiento.getEntradas() + "");
		listaInventarios.add(libro.getCosto() + "");

		return listaInventarios;
	}
}
