package model;

import java.util.ArrayList;

public class Motorista extends Cliente{

	
	private int rntrc;
	private ArrayList<Veiculo> veiculos;
	private String cpf;
	
	


	public Motorista(int id, String nome, String ie, ArrayList<Endereco> enderecos, String cpf, int rntrc) {
		super(id, nome, ie, enderecos);
		this.cpf = cpf;
		this.rntrc = rntrc;
		// TODO Auto-generated constructor stub
	}



	public Motorista(String nome, String ie, ArrayList<Endereco> enderecos, String cpf, int rntrc) {
		super(nome, ie, enderecos);
		this.cpf = cpf;
		this.rntrc = rntrc;
		// TODO Auto-generated constructor stub
	}



	public String getCpf() {
		return cpf;
	}



	public void setCpf(String cpf) {
		this.cpf = cpf;
	}



	public int getRntrc() {
		return rntrc;
	}

	public void setRntrc(int rntrc) {
		this.rntrc = rntrc;
	}



	public ArrayList<Veiculo> getVeiculos() {
		return veiculos;
	}



	public void setVeiculos(ArrayList<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}



	
}
