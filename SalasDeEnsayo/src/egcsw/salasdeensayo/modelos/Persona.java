package egcsw.salasdeensayo.modelos;

public class Persona {
	
	private String nombre;
	private String dni;
	private String domicilio;

	public Persona(String nombre, String dni, String domicilio){
		this.nombre = nombre;
		this.dni = dni;
		this.domicilio = domicilio;
	}
	
	public String getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
}
