package model;

public class Contrato {
	
	
	private int id, unidadeMedida;
	private Contratante[] compradores;
	private Contratante[] vendedores;
	private Contratante[] corretores;
	private Safra safra;
	private double quantidadeContratada, valorPorUnidade, quantidadeAtendida;
	
	
	public Contrato(int id, int unidadeMedida, Contratante[] compradores, Contratante[] vendedores,
			Contratante[] corretores, Safra safra, double quantidadeContratada, double valorPorUnidade,
			double quantidadeAtendida) {
		this.id = id;
		this.unidadeMedida = unidadeMedida;
		this.compradores = compradores;
		this.vendedores = vendedores;
		this.corretores = corretores;
		this.safra = safra;
		this.quantidadeContratada = quantidadeContratada;
		this.valorPorUnidade = valorPorUnidade;
		this.quantidadeAtendida = quantidadeAtendida;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUnidadeMedida() {
		return unidadeMedida;
	}


	public void setUnidadeMedida(int unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}


	public Contratante[] getCompradores() {
		return compradores;
	}


	public void setCompradores(Contratante[] compradores) {
		this.compradores = compradores;
	}


	public Contratante[] getVendedores() {
		return vendedores;
	}


	public void setVendedores(Contratante[] vendedores) {
		this.vendedores = vendedores;
	}


	public Contratante[] getCorretores() {
		return corretores;
	}


	public void setCorretores(Contratante[] corretores) {
		this.corretores = corretores;
	}


	public Safra getSafra() {
		return safra;
	}


	public void setSafra(Safra safra) {
		this.safra = safra;
	}


	public double getQuantidadeContratada() {
		return quantidadeContratada;
	}


	public void setQuantidadeContratada(double quantidadeContratada) {
		this.quantidadeContratada = quantidadeContratada;
	}


	public double getValorPorUnidade() {
		return valorPorUnidade;
	}


	public void setValorPorUnidade(double valorPorUnidade) {
		this.valorPorUnidade = valorPorUnidade;
	}


	public double getQuantidadeAtendida() {
		return quantidadeAtendida;
	}


	public void setQuantidadeAtendida(double quantidadeAtendida) {
		this.quantidadeAtendida = quantidadeAtendida;
	}
	
	
	
	
	
	

}
