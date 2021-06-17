package model;

public class Motorista extends Cliente{

	
	private int rntrc;
	private Veiculo veiculos[];
	
	public Motorista(int id, int tipo, String nome, String ie, Endereco[] endereco, int rntrc, Veiculo veiculos[]) {
		
		super(id, tipo, nome, ie, endereco);
		this.rntrc = rntrc;
		this.veiculos = veiculos;
		
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
