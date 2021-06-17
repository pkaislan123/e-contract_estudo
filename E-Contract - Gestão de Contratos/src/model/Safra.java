package model;

public class Safra {
	
	private int id, anoPlantio, anoColheita;
	private Produto produto;


	public Safra(int id, int anoPlantio, int anoColheita, Produto p) {
		this.id = id;
		this.anoPlantio = anoPlantio;
		this.anoColheita = anoColheita;
		this.produto = p;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getAnoPlantio() {
		return anoPlantio;
	}


	public void setAnoPlantio(int anoPlantio) {
		this.anoPlantio = anoPlantio;
	}


	public int getAnoColheita() {
		return anoColheita;
	}


	public void setAnoColheita(int anoColheita) {
		this.anoColheita = anoColheita;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	

}
