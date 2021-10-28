package trabajoPrograII;

public abstract class Vacuna {

	private Fecha fechaIngreso;

	public Vacuna(Fecha fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	public abstract String getNombre();
	
	public abstract boolean getATP();
	
	public abstract int getTemperaturaAlmacenamiento();
	
	protected boolean estaVencida() {
		return false;
	}
	
	public boolean aplica(Persona persona) {		
		return persona.esMayor60() || this.getATP();
	}
	

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Tipo de Vacuna ").append(this.getNombre()).append(System.lineSeparator());
		string.append("Temperatura de almacenamiento: ").append(this.getTemperaturaAlmacenamiento())
				.append(System.lineSeparator());
		string.append("Es apta para mayores: ").append(this.getATP()).append(System.lineSeparator());
		string.append("Fecha de Ingreso: ").append(this.fechaIngreso.toString()).append(System.lineSeparator());
		return string.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaIngreso == null) ? 0 : fechaIngreso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacuna other = (Vacuna) obj;
		if (fechaIngreso == null) {
			if (other.fechaIngreso != null)
				return false;
		} else if (!fechaIngreso.equals(other.fechaIngreso))
			return false;
		return true;
	}
}
