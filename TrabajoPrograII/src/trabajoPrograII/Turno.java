package trabajoPrograII;

public class Turno {
	private Fecha fecha;
	private Persona persona;
	private Vacuna vacuna;

	public Turno(Fecha fecha, Persona persona, Vacuna vacuna) {
		this.fecha = fecha;
		this.persona = persona;
		this.vacuna = vacuna;
	}

	public Vacuna getVacunaAplicada() {
		return this.vacuna;
	}

	public Fecha getFecha() {
		return fecha;

	}

	public Persona getPersona() {
		return this.persona;
	}

	public int dniPersona() {
		return persona.getDNI();
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		string.append("Fecha de aplicacion: ").append(this.getFecha()).append(System.lineSeparator());
		string.append("Vacuna a ser aplicada: ").append(this.getVacunaAplicada()).append(System.lineSeparator());
		string.append("Paciente: ").append(persona.toString()).append(System.lineSeparator());

		return string.toString();
	}
}
