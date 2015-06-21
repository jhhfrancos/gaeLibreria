package co.edu.unal.shared;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id private long isbn;

    private String titulo;
    private String nombreAutor;
    private String categoria;
    private String editorial;
    private double costo;

    public Libro () {
        isbn = -1;
    }

    public Libro(Long isbn, String titulo, String nombreAutor, String categoria, String editorial, double costo){
        this.isbn = isbn;
        this.titulo = titulo;
        this.nombreAutor = nombreAutor;
        this.categoria = categoria;
        this.editorial = editorial;
        this.costo = costo;
    }
 
    public String getCategoria () {
        return categoria;
    }

    public void setCategoria (String val) {
        this.categoria = val;
    }

    public double getCosto () {
        return costo;
    }

    public void setCosto (double val) {
        this.costo = val;
    }

    public String getEditorial () {
        return editorial;
    }

    public void setEditorial (String val) {
        this.editorial = val;
    }

    public long getIsbn () {
        return isbn;
    }

    public void setIsbn (long val) {
        this.isbn = val;
    }

    public String getNombreAutor () {
        return nombreAutor;
    }

    public void setNombreAutor (String val) {
        this.nombreAutor = val;
    }

    public String getTitulo () {
        return titulo;
    }

    public void setTitulo (String val) {
        this.titulo = val;
    }
    
    public boolean equals(Libro libro)
    {
    	return this.isbn == libro.isbn;
    }
    
    public Libro clone(){
    	return new Libro(this.isbn, this.titulo, this.nombreAutor, this.categoria, this.editorial, this.costo);
    }
}