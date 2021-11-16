package model;

import java.util.ArrayList;

public class Contratante extends Cliente {

	private String cnpj;


	
	public Contratante(int id, String nome, String ie, ArrayList<Endereco> enderecos, String cnpj) {
		super(id, nome, ie, enderecos);
		this.cnpj = cnpj;
		// TODO Auto-generated constructor stub
	}



	public Contratante(String nome, String ie, ArrayList<Endereco> enderecos, String cnpj) {
		super(nome, ie, enderecos);
		this.cnpj = cnpj;
		// TODO Auto-generated constructor stub
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}





	

	
	
	
}
