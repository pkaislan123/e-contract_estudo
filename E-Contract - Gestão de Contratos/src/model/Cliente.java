package model;

import java.util.ArrayList;

public abstract class Cliente {

	
	private int id;
	private String nome, ie;
	private ArrayList<Endereco> enderecos;
	
	
	public Cliente(int id, String nome, String ie, ArrayList<Endereco> enderecos) {
		super();
		this.id = id;
		this.nome = nome;
		this.ie = ie;
		this.enderecos = enderecos;
	}
	
	public Cliente(String nome, String ie, ArrayList<Endereco> enderecos) {
		super();
		this.nome = nome;
		this.ie = ie;
		this.enderecos = enderecos;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	

	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getIe() {
		return ie;
	}
	
	public void setIe(String ie) {
		this.ie = ie;
	}

	public ArrayList<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(ArrayList<Endereco> enderecos) {
		this.enderecos = enderecos;
	}


	
	
	
}
