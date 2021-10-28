package trabajoPrograII;

public class Pfizer extends ConVencimiento {

	public Pfizer(Fecha fechaIngreso) {
		super(fechaIngreso, 30);
		
	}

	@Override
	public String getNombre() {
		return "Pfizer";
	}

	@Override
	public boolean getATP() {
		return false;
	}

}
