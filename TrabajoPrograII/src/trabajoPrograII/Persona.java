package trabajoPrograII;

public class Persona implements Comparable<Persona> {
	private int dni;
	private Fecha fechaNacimiento;
	private boolean tienePadecimientos;
	private boolean esEmpleadoSalud;
	private int prioridad;
	private boolean esMayor60;

	public Persona(int dni, Fecha fechaNacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.tienePadecimientos = tienePadecimientos;
		this.esEmpleadoSalud = esEmpleadoSalud;
		this.prioridad = calcularPrioridad();
		this.esMayor60 = calcularEsMayor60();
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public int getDNI() {
		return this.dni;
	}

	private boolean calcularEsMayor60() {
		Fecha hoy = Fecha.hoy();
		if (Fecha.diferenciaAnios(hoy, fechaNacimiento) >= 60) {
			return true;
		} else
			return false;
	}

	public boolean esMayor60() {
		return this.esMayor60;
	}

	private int calcularPrioridad() {
		if (esEmpleadoSalud)
			return 1;

		if (esMayor60)
			return 2;

		if (tienePadecimientos)
			return 3;

		else
			return 4;
	}

	@Override
	public int compareTo(Persona o) {
		if (this.equals(o))
			return 0;
		if (this.prioridad == o.prioridad)
			return this.dni - o.getDNI();
		return this.prioridad - o.prioridad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dni;
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
		Persona other = (Persona) obj;
		if (dni != other.dni)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("DNI: ").append(this.getDNI()).append(System.lineSeparator());
		string.append("Fecha de Nacimiento: ").append(this.getFechaNacimiento().toString())
				.append(System.lineSeparator());
		string.append("Nivel de prioridad: ").append(this.prioridad).append(System.lineSeparator());
		return string.toString();
	}

}
