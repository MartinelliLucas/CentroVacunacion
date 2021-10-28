package trabajoPrograII;

public class AstraZeneca extends Vacuna {

	public AstraZeneca(Fecha fechaIngreso) {
		super(fechaIngreso);
	}

	@Override
	public String getNombre() {
		return "AstraZeneca";
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
