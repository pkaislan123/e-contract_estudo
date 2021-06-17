package model;

public class Transportadora extends Cliente{


	private String cnpj;
	private Veiculo veiculos[];
	
	
	
	
	public Transportadora(int id, int tipo, String nome, String ie, Endereco[] endereco, String cnpj, Veiculo veiculos[]) {
		super(id, tipo, nome, ie, endereco);
		this.cnpj = cnpj;
		this.veiculos = veiculos;
		// TODO Auto-generated constructor stub
	}




	public String getCnpj() {
		return cnpj;
	}




	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}




	public Veiculo[] getVeiculos() {
		return veiculos;
	}




	public void setVeiculos(Veiculo[] veiculos) {
		this.veiculos = veiculos;
	}
	
	
	
	
}
