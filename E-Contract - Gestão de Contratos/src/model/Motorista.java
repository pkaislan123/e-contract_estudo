package model;

import java.util.ArrayList;

public class Motorista extends Cliente{

	
	private int rntrc;
	private ArrayList<Veiculo> veiculos;
	private String cpf;
	
	public Motorista(int id, int tipo, String nome, String ie, Endereco[] endereco,String _cpf, int rntrc, ArrayList<Veiculo> veiculos) {
		
		super(id, tipo, nome, ie, endereco);
		this.rntrc = rntrc;
		this.veiculos = veiculos;
		this.cpf = _cpf;
		
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
