package trabajoPrograII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CentroVacunacion {
	public String nombreCentro;
	public int capacidadVacunacionDiaria;
	public TreeSet<Persona> listaDeEspera;// treeset de personas en lista de espera
	public Map<Fecha, List<Turno>> calendario; // Mapa de fechas con listas de turno por fecha
	public Map<Integer, Turno> turnosConFecha; // mapa de turnos por dni
	public Map<Integer, String> reporteVacunacion; // mapa de personas vacunadas por dni cuyo valor es el tipo de vacuna
	public Map<String, Integer> reporteVacunasVencidas; // mapa de vacunas vencidas por nombre de vacuna
	public Map<String, List<Vacuna>> stockVacunas; // mapa cuya clave es el nombre del tipo de vacuna y su valor es
													// dicha vacuna

	/**
	 * Constructor. recibe el nombre del centro y la capacidad de vacunación diaria.
	 * Si la capacidad de vacunación no es positiva se debe generar una excepción.
	 * Si el nombre no está definido, se debe generar una excepción.
	 */
	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		if (nombreCentro == null || nombreCentro.isBlank())
			throw new IllegalArgumentException("El nombre del centro no es válido");

		else
			this.nombreCentro = nombreCentro;
		if (capacidadVacunacionDiaria <= 0)
			throw new IllegalArgumentException("La capacidad de vacunación no puede ser negativa o nula");
		else
			this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;

		this.listaDeEspera = new TreeSet<>();
		this.turnosConFecha = new HashMap<>();
		this.calendario = new HashMap<>();
		this.reporteVacunacion = new HashMap<>();
		this.reporteVacunasVencidas = new HashMap<>();
		this.stockVacunas = new HashMap<>();
		stockVacunas.put("Pfizer", new ArrayList<>());
		stockVacunas.put("Moderna", new ArrayList<>());
		stockVacunas.put("Sputnik", new ArrayList<>());
		stockVacunas.put("Sinopharm", new ArrayList<>());
		stockVacunas.put("AstraZeneca", new ArrayList<>());

	}

	/**
	 * Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte. Si
	 * el nombre de la vacuna no coincidiera con los especificados se debe generar
	 * una excepción. También se genera excepción si la cantidad es negativa. La
	 * cantidad se debe sumar al stock existente, tomando en cuenta las vacunas ya
	 * utilizadas.
	 */

	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		if (cantidad <= 0)
			throw new IllegalArgumentException("La cantidad de vacunas ingresadas no puede ser menor a 0");

		if (!stockVacunas.containsKey(nombreVacuna)) {
			throw new IllegalArgumentException("El nombre de la vacuna es invalido");
		}
		if (nombreVacuna.equals("Pfizer")) {
			for (int i = 0; i < cantidad; i++) {
				Pfizer vacunaNueva = new Pfizer(fechaIngreso);
				stockVacunas.get("Pfizer").add(vacunaNueva);
			}
		}
		if (nombreVacuna.equals("Moderna")) {
			for (int i = 0; i < cantidad; i++) {
				Moderna vacunaNueva = new Moderna(fechaIngreso);
				stockVacunas.get("Moderna").add(vacunaNueva);
			}
		}
		if (nombreVacuna.equals("Sputnik")) {
			for (int i = 0; i < cantidad; i++) {
				Sputnik vacunaNueva = new Sputnik(fechaIngreso);
				stockVacunas.get("Sputnik").add(vacunaNueva);
			}
		}
		if (nombreVacuna.equals("Sinopharm")) {
			for (int i = 0; i < cantidad; i++) {
				Sinopharm vacunaNueva = new Sinopharm(fechaIngreso);
				stockVacunas.get("Sinopharm").add(vacunaNueva);
			}
		}
		if (nombreVacuna.equals("AstraZeneca")) {
			for (int i = 0; i < cantidad; i++) {
				AstraZeneca vacunaNueva = new AstraZeneca(fechaIngreso);
				stockVacunas.get("AstraZeneca").add(vacunaNueva);
			}
		}

	}

	/**
	 * total de vacunas disponibles no vencidas sin distinción por tipo.
	 */

	public int vacunasDisponibles() {
		int totalVacunas = 0;

		for (String vacuna : stockVacunas.keySet()) {
			totalVacunas += stockVacunas.get(vacuna).size();
		}

		return totalVacunas;
	}

	/**
	 * total de vacunas disponibles no vencidas que coincida con el nombre de vacuna
	 * especificado.
	 */
	public int vacunasDisponibles(String nombreVacuna) {
		if (stockVacunas.containsKey(nombreVacuna))
			return stockVacunas.get(nombreVacuna).size();
		else
			throw new IllegalArgumentException("La vacuna ingresada no corresponde con las existentes en el sistema");

	}

	/**
	 * Se inscribe una persona en lista de espera. Si la persona ya se encuentra
	 * inscripta o es menor de 18 años, se debe generar una excepción. Si la persona
	 * ya fue vacunada, también debe generar una excepción.
	 */
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		Persona p = new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
		Fecha hoy = Fecha.hoy();
		if (listaDeEspera.contains(p)) {
			throw new IllegalArgumentException("La persona ya se encuentra en la lista de espera");
		}
		if (turnosConFecha.containsKey(p.getDNI())) {
			throw new IllegalArgumentException("La persona ya tiene un turno asignado");
		}
		if (reporteVacunacion.containsKey(p.getDNI())) {
			throw new IllegalArgumentException("La persona ya fue vacunada");
		}
		if (Fecha.diferenciaAnios(hoy, p.getFechaNacimiento()) <= 18) {
			throw new IllegalArgumentException("La persona es menor de edad, no puede ser ingresada");
		}
		listaDeEspera.add(p);
	}

	/**
	 * Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron y
	 * que no tienen turno asignado. Si no quedan inscriptos sin vacunas debe
	 * devolver una lista vacía.
	 */
	public List<Integer> listaDeEspera() {
		List<Integer> listaDeEsperaDni = new LinkedList<>();
		for (Persona persona : listaDeEspera) {
			if (!turnosConFecha.containsKey(persona.getDNI()) && !reporteVacunacion.containsKey(persona.getDNI()))
				listaDeEsperaDni.add(persona.getDNI());
		}
		return listaDeEsperaDni;
	}

	/**
	 * Remueve los turnos vencidos del sistema
	 */
	private void quitarTurnosVencidos(Fecha fechaInicial) {
		for (Fecha fechaTurno : calendario.keySet()) {
			if (fechaTurno.anterior(fechaInicial)) {
				for (Turno turno : calendario.get(fechaTurno)) {
					stockVacunas.get(turno.getVacunaAplicada().getNombre()).add(turno.getVacunaAplicada()); // devuelvo
																											// la vacuna
																											// al stock
					turnosConFecha.remove(turno.getPersona().getDNI()); // la persona pierde el turno debe volver a
																		// registrarse

				}
			}
		}
	}

	/**
	 * Remueve del stock las vacunas que vencieron al dia de hoy
	 */
	private void quitarVacunasVencidas() {
	
		List<Vacuna> vencidas = new ArrayList<>();
		for (String nombreVacuna : stockVacunas.keySet()) {
			for (Vacuna vacuna : stockVacunas.get(nombreVacuna)) {
				if (vacuna.estaVencida()) {
					vencidas.add(vacuna);
					if (reporteVacunasVencidas.containsKey(nombreVacuna)) {
						reporteVacunasVencidas.put(nombreVacuna, reporteVacunasVencidas.get(nombreVacuna) + 1);
					} else
						reporteVacunasVencidas.put(nombreVacuna, 1);
				}
			}
			stockVacunas.get(nombreVacuna).removeAll(vencidas);
		}
	}

	/**
	 * Devuelve true si hay vacunas disponibles y si hay gente en lista de espera
	 */
	private boolean sePuedeGenerarTurno() {
		return vacunasDisponibles() > 0 && !listaDeEspera.isEmpty();
	}

	/**
	 * Reserva una dosis de una vacuna especifica para ser aplicada a una persona en
	 * particular
	 */
	private Vacuna reservarVacuna(Persona persona) {
		if (!listaDeEspera.contains(persona)) {
			throw new IllegalArgumentException("La persona no se encuentra inscripta");
		}
		if (reporteVacunacion.containsKey(persona.getDNI())) {
			throw new IllegalArgumentException("La persona ya fue vacunada");
		}
		if (turnosConFecha.containsKey(persona.getDNI())) {
			throw new IllegalArgumentException("La persona ya tiene turno asignado");
		}
		for (String nombreVacuna : stockVacunas.keySet()) {
			for (Vacuna vacuna : stockVacunas.get(nombreVacuna)) {
				if (vacuna.aplica(persona)) {
					stockVacunas.get(nombreVacuna).remove(vacuna);
					return vacuna;
				}		
			}
		}	
		throw new RuntimeException("No quedan vacunas aptas");
	}

	/**
	 * Primero se verifica si hay turnos vencidos. En caso de haber turnos vencidos,
	 * la persona que no asistió al turno debe ser borrada del sistema y la vacuna
	 * reservada debe volver a estar disponible.
	 *
	 * Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	 *
	 * Por último, se procede a asignar los turnos a partir de la fecha inicial
	 * recibida según lo especificado en la 1ra parte. Cada vez que se registra un
	 * nuevo turno, la vacuna destinada a esa persona dejará de estar disponible.
	 * Dado que estará reservada para ser aplicada el día del turno.
	 *
	 *
	 */

	public void generarTurnos(Fecha fechaInicial) {
		if (fechaInicial.anterior(Fecha.hoy())) {
			throw new IllegalArgumentException("La fecha inicial es anterior a la fecha actual");
		}
		quitarTurnosVencidos(fechaInicial); //verifico si hay turnos vencidos
		quitarVacunasVencidas(); 			//quito las vacunas vencidas
		
		Fecha fecha = new Fecha(fechaInicial);
		int turnosDelDia = 0;
		Set<Persona> personasConTurno = new HashSet<>();
		
		for (Persona persona : listaDeEspera) {
			if (sePuedeGenerarTurno()) {
				Fecha fechaAux = new Fecha(fecha);
				Turno nuevoTurno = new Turno(fecha, persona, reservarVacuna(persona));
				turnosConFecha.put(persona.getDNI(), nuevoTurno);
				personasConTurno.add(persona);
				turnosDelDia++;
				if (!calendario.containsKey(fechaAux)) {
					calendario.put(fechaAux, new LinkedList<>());
				}
				calendario.get(fechaAux).add(nuevoTurno);
				if (turnosDelDia == capacidadVacunacionDiaria) {
					fecha.avanzarUnDia();
					turnosDelDia = 0;					
				}
			}
		}
		listaDeEspera.removeAll(personasConTurno);

	}

	/**
	 * Devuelve una lista con los dni de las personas que tienen turno asignado para
	 * la fecha pasada por parámetro. Si no hay turnos asignados para ese día, se
	 * debe devolver una lista vacía. La cantidad de turnos no puede exceder la
	 * capacidad por día de la ungs.
	 */
	public List<Integer> turnosConFecha(Fecha fecha) {
		List<Integer> turnosConFechaDni = new LinkedList<>();
		if (!calendario.containsKey(fecha)) {
			return turnosConFechaDni;
		}
		for (Turno turno : calendario.get(fecha)) {
			turnosConFechaDni.add(turno.dniPersona());
		}
		return turnosConFechaDni;
	}

	/**
	 * Dado el DNI de la persona y la fecha de vacunación se valida que esté
	 * inscripto y que tenga turno para ese dia. - Si tiene turno y está inscripto
	 * se debe registrar la persona como vacunada y la vacuna se quita del depósito.
	 * - Si no está inscripto o no tiene turno ese día, se genera una Excepcion.
	 */
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		if (!turnosConFecha.containsKey(dni)) {
			throw new IllegalArgumentException("La persona no tiene turno asignado");
		}
		if (reporteVacunacion().containsKey(dni)) {
			throw new IllegalArgumentException("La persona ya fue vacunada");
		}
		if (this.calendario.containsKey(fechaVacunacion)) {
			for (Turno turno : this.calendario.get(fechaVacunacion)) {
				if (turno.dniPersona() == dni) {
					reporteVacunacion.put(turno.dniPersona(), turno.getVacunaAplicada().getNombre());
					turnosConFecha.remove(turno.getPersona().getDNI());
					calendario.get(fechaVacunacion).remove(turno);
					return;
				}
			}
			throw new IllegalArgumentException("La persona no tiene turno para esta fecha");
		} else
			throw new IllegalArgumentException("La fecha ingresada es incorrecta");
	}

	/**
	 * Devuelve un Diccionario donde - la clave es el dni de las personas vacunadas
	 * - Y, el valor es el nombre de la vacuna aplicada.
	 */
	public Map<Integer, String> reporteVacunacion() {
		return reporteVacunacion;
	}
	/**
	 * Devuelve la cantidad de turnos asignados hasta el momento.
	 */
	public int cantidadTurnos() {
		return turnosConFecha.size();
	}

	/**
	 * Devuelve en O(1) un Diccionario: - clave: nombre de la vacuna - valor:
	 * cantidad de vacunas vencidas conocidas hasta el momento.
	 */
	public Map<String, Integer> reporteVacunasVencidas() {

		return reporteVacunasVencidas;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Centro: ").append(nombreCentro).append(System.lineSeparator());
		string.append("Capacidad de vacunacion diaria: ").append(capacidadVacunacionDiaria)
				.append(System.lineSeparator());
		string.append("Cantidad de vacunas disponibles: ").append(vacunasDisponibles()).append(System.lineSeparator());
		string.append("Cantidad de personas en lista de espera: ").append(this.listaDeEspera.size())
				.append(System.lineSeparator());
		string.append("Cantidad de turnos asignados hasta el momento: ").append(cantidadTurnos())
				.append(System.lineSeparator());
		string.append("Cantidad de vacunas aplicadas hasta el momento: ").append(this.reporteVacunacion.size())
				.append(System.lineSeparator());

		return string.toString();
	}
}
