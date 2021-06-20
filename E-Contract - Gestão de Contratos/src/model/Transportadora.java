package model;

import java.util.ArrayList;

public class Transportadora extends Cliente{


	private String cnpj;
	private  ArrayList<Veiculo> veiculos;
	
	
	
	
	public Transportadora(int id, int tipo, String nome, String ie, Endereco[] endereco, String cnpj, ArrayList<Veiculo> veiculos) {
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




	public ArrayList<Veiculo> getVeiculos() {
		return veiculos;
	}




	public void setVeiculos(ArrayList<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}




	
	
}
