package model;

public class Cliente {

	
	private int id, tipo;
	private String nome, ie;
	private Endereco endereco[];
	
	
	
	public Cliente(int id, int tipo, String nome, String ie, Endereco[] endereco) {
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.ie = ie;
		this.endereco = endereco;
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
	public Endereco[] getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco[] endereco) {
		this.endereco = endereco;
	}
	
	
	
}
