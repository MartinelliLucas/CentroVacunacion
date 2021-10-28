package trabajoPrograII;

public class Sputnik extends Vacuna {

	public Sputnik(Fecha fechaIngreso) {
		super(fechaIngreso);
	}

	@Override
	public String getNombre() {
		return "Sputnik";
	}

	@Override
	public boolean getATP() {
		return false;
	}

	@Override
	public int getTemperaturaAlmacenamiento() {
		return 3;
	}
}
