package co.edu.unal.shared;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
 
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private long idUsuario;
    private String nombre;
    private String password;
    public Boolean activo = false;
    public Boolean admin = false;
 
    public Usuario () {
    }
    
    public Usuario(int id, String nom, String pass, Boolean admin)
    {
        this.idUsuario = id;
        this.nombre = nom;
        this.password = pass;
        this.admin = admin;
    } 

    public long getId ()
    {
        return idUsuario;
    }
 
    public void setId (int val)
    {
        this.idUsuario = val;
    }
    
    public String getNombre () {
        return nombre;
    }
 
    public void setNombre (String val) {
        this.nombre = val;
    }
 
    public String getPassword () {
        return password;
    }
 
    public void setPassword (String val) {
        this.password = val;
    }
    
    public boolean esAdmin() {
        return admin;
    }
 
    public void setAdmin (boolean admin) {
        this.admin = admin;
    }
//    
//    public boolean esActivo() {
//        return activo;
//    }
// 
//    public void setActivo (boolean activo) {
//        this.activo = activo;
//    }
    
    public boolean equals(Usuario usuario){
    	if(this.getNombre().equals(usuario.getNombre()))
    		return true;
    	return false;
    }
    
    public String toString(){
    	return "Nombre: " + nombre + " - Password: " + password +
    			" - Activo: " + activo + " - Admin: " + admin;
    }
}

