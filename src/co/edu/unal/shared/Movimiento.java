package co.edu.unal.shared;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	long idMovimiento;
	private int entradas = 0;
	private int salidas = 0;

	public Movimiento() {
	}

	public int getEntradas() {
		return entradas;
	}

	public void setEntradas(int val) {
		this.entradas = val;
	}

	public Movimiento(Libro libro) {
		this.idMovimiento = libro.getIsbn();
	}

	public long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(int val) {
		this.idMovimiento = val;
	}

	public int getSalidas() {
		return salidas;
	}

	public void setSalidas(int val) {
		this.salidas = val;
	}
}