package trabajoPrograII;

public abstract class ConVencimiento extends Vacuna {

	public ConVencimiento(Fecha fechaIngreso, int diasParaVencimiento) {
		super(fechaIngreso);
		this.fechaVencimiento = new Fecha(fechaIngreso);
		for (int i = 0; i < diasParaVencimiento; i++) {
			fechaVencimiento.avanzarUnDia();
		}
	}
	protected Fecha fechaVencimiento;
	
	@Override
	public int getTemperaturaAlmacenamiento() {
		return -18;
	}

	@Override
	public boolean estaVencida() {
		return fechaVencimiento.anterior(Fecha.hoy());
	}
}
