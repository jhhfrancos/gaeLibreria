package co.edu.unal.client.services;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.shared.Inventario;
import co.edu.unal.shared.Libro;
import co.edu.unal.shared.Movimiento;
import co.edu.unal.shared.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("library")
public interface LibraryService extends RemoteService {

//---------------------------------------------------------------------------------------------------------
	List<Libro> listarLibros();
	List<Libro> listarLibrosN(String autor) throws IllegalArgumentException;
	List<Libro> listarLibrosA(String autor) throws IllegalArgumentException;
	List<Libro> listarLibrosC(String categoria) throws IllegalArgumentException;
	List<Libro> listarLibrosE(String editorial) throws IllegalArgumentException;
	List<Inventario> listarInventario();
	
	List<Movimiento> listarMovimientos();
	List<Usuario> listarUsuarios();
	
	List<String> getDatosInventario(long isbn);
	List<ArrayList<String>> getDatosReporte();
//---------------------------------------------------------------------------------------------------------
	Libro loadLibro(long isbn);
	Libro loadLibro(String name);
	Libro loadLibro(String name, long isbn);
	
	Movimiento loadMovimiento(long idMovimiento) throws IllegalArgumentException;
	
	Usuario loadUsuario(int idUsuario) throws IllegalArgumentException;
	Usuario loadUsuario(String name) throws IllegalArgumentException;
	
	Inventario loadInventario(long isbn);
//---------------------------------------------------------------------------------------------------------
	boolean guardarLibro(Libro libro, int cantidad);
	boolean guardarMovimiento(Movimiento movimiento);
	boolean guardarUsuario(Usuario usuario);
	
	boolean borrarLibro(Libro libro) throws IllegalArgumentException;
	boolean borrarMovimiento(long id) throws IllegalArgumentException;
	boolean borrarUsuario(Usuario usuario) throws IllegalArgumentException;
	boolean borrarInventario(long id) throws IllegalArgumentException;
//---------------------------------------------------------------------------------------------------------
}

