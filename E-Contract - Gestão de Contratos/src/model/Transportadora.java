package model;

import java.util.ArrayList;

public class Transportadora extends Cliente{


	private String cnpj;
	private  ArrayList<Veiculo> veiculos;
	
	

	public Transportadora(int id, String nome, String ie, ArrayList<Endereco> enderecos, String cnpj ) {
		super(id, nome, ie, enderecos);
		this.cnpj = cnpj;
		// TODO Auto-generated constructor stub
	}




	public Transportadora(String nome, String ie, ArrayList<Endereco> enderecos, String cnpj) {
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




	public ArrayList<Veiculo> getVeiculos() {
		return veiculos;
	}




	public void setVeiculos(ArrayList<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}




	
	
}
