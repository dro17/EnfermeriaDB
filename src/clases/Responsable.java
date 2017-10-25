package clases;

public class Responsable {
	private String nombre;
	private String telefono;
	
	Responsable(){
		this.nombre = "";
		this.telefono = "";
	}
	
	Responsable(String nombre, String telefono){
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
}