package model;

public class Contratante extends Cliente {

	public Contratante(int id, int tipo, String nome, String ie, Endereco[] endereco, String cnpj) {
		super(id, tipo, nome, ie, endereco);
		this.cnpj = cnpj;
		// TODO Auto-generated constructor stub
	}



	private String cnpj;

	
	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}





	

	
	
	
}
