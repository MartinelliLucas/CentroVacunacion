package trabajoPrograII;

public class Sinopharm extends Vacuna {

	public Sinopharm(Fecha fechaIngreso) {
		super(fechaIngreso);
	}

	@Override
	public String getNombre() {
		return "Sinopharm";
	}

	@Override
	public boolean getATP() {
		return true;
	}

	@Override
	public int getTemperaturaAlmacenamiento() {
		return 3;
	}
}
