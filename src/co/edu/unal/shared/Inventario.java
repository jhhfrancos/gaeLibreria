package co.edu.unal.shared;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Inventario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id long id;
    private int cantidad;

    public Inventario ()
    {
        cantidad = 0;
    }
    public int getCantidad ()
    {
        return cantidad;
    } 
    public void setCantidad (int val)
    {
        this.cantidad = val;
    }
    public void setID (long val) {
        this.id = val;
    }
    public long getID () {
        return id;
    }
}