package trabajoPrograII;

public class Moderna extends ConVencimiento {

	public Moderna(Fecha fechaIngreso) {
		super(fechaIngreso, 60);
		

	}

	@Override
	public String getNombre() {
		return "Moderna";
	}

	@Override
	public boolean getATP() {
		return true;
	}

}
