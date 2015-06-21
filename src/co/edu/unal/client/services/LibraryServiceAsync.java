package co.edu.unal.client.services;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.shared.Inventario;
import co.edu.unal.shared.Libro;
import co.edu.unal.shared.Movimiento;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LibraryServiceAsync {
//---------------------------------------------------------------------------------------------------------
	void listarLibros(AsyncCallback<List<Libro>> callback);
	void listarMovimientos(AsyncCallback<List<Movimiento>> callback);
	void listarUsuarios(AsyncCallback<List<Usuario>> callback);
	void listarInventario(AsyncCallback<List<Inventario>> callback);
	
	void getDatosInventario(long isbn, AsyncCallback<List<String>> callback);
	void getDatosReporte(AsyncCallback<List<ArrayList<String>>> callback);
	
	void listarLibrosN(String autor, AsyncCallback<List<Libro>> callback);
	void listarLibrosA(String autor, AsyncCallback<List<Libro>> callback)
			throws IllegalArgumentException;
	void listarLibrosC(String categoria, AsyncCallback<List<Libro>> callback)
			throws IllegalArgumentException;
	void listarLibrosE(String editorial, AsyncCallback<List<Libro>> callback)
			throws IllegalArgumentException;
//---------------------------------------------------------------------------------------------------------
	void guardarLibro(Libro libro, int cantidad, AsyncCallback<Boolean> callback);
	void guardarMovimiento(Movimiento movimiento,
			AsyncCallback<Boolean> callback);
	void guardarUsuario(Usuario usuario, AsyncCallback<Boolean> callback);
//---------------------------------------------------------------------------------------------------------
	void loadLibro(long isbn, AsyncCallback<Libro> callback)
			throws IllegalArgumentException;
	void loadLibro(String name, long isbn, AsyncCallback<Libro> callback)
			throws IllegalArgumentException;
	void loadMovimiento(long idMovimiento, AsyncCallback<Movimiento> callback)
			throws IllegalArgumentException;
	void loadUsuario(int i, AsyncCallback<Usuario> callback)
			throws IllegalArgumentException;
	void loadLibro(String name, AsyncCallback<Libro> callback)
			throws IllegalArgumentException;
	void loadUsuario(String name, AsyncCallback<Usuario> callback)
			throws IllegalArgumentException;
	void loadInventario(long isbn, AsyncCallback<Inventario> callback)
			throws IllegalArgumentException;
//---------------------------------------------------------------------------------------------------------
	void borrarLibro(Libro libro, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void borrarInventario(long id, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void borrarMovimiento(long id, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void borrarUsuario(Usuario usuario, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
//---------------------------------------------------------------------------------------------------------
}
