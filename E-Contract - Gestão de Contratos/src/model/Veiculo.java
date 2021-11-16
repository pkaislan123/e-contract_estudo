package model;

public class Veiculo {

	
	private int id, tipo;
	private String placa;
	
	public Veiculo(int id, int tipo, String placa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.placa = placa;
	}
	
	
	public Veiculo(int tipo, String placa) {
		super();
		this.tipo = tipo;
		this.placa = placa;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	
	
}
