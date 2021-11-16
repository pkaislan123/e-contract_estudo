package model;

import java.util.ArrayList;

public class Contrato {
	
	
	private int id, unidadeMedida;
	private ArrayList<Contratante> compradores;
	private ArrayList<Contratante> vendedores;
	private Contratante corretor;
	private Safra safra;
	private double quantidadeContratada, valorPorUnidade, quantidadeAtendida;
	private ArrayList<Carga> listCarga = new ArrayList<>();
	private ArrayList<Pagamento> listPagamentos = new ArrayList<>();
	
	public Contrato(int id, int unidadeMedida,ArrayList<Contratante>compradores, ArrayList<Contratante> vendedores,
			Contratante corretor, Safra safra, double quantidadeContratada, double valorPorUnidade,
			double quantidadeAtendida) {
		this.id = id;
		this.unidadeMedida = unidadeMedida;
		this.compradores = compradores;
		this.vendedores = vendedores;
		this.corretor = corretor;
		this.safra = safra;
		this.quantidadeContratada = quantidadeContratada;
		this.valorPorUnidade = valorPorUnidade;
		this.quantidadeAtendida = quantidadeAtendida;
	}

	
	public Contrato(int unidadeMedida,ArrayList<Contratante>compradores, ArrayList<Contratante> vendedores,
			Contratante corretor, Safra safra, double quantidadeContratada, double valorPorUnidade,
			double quantidadeAtendida) {
		this.unidadeMedida = unidadeMedida;
		this.compradores = compradores;
		this.vendedores = vendedores;
		this.corretor = corretor;
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


	


	public ArrayList<Contratante> getCompradores() {
		return compradores;
	}


	public void setCompradores(ArrayList<Contratante> compradores) {
		this.compradores = compradores;
	}


	public ArrayList<Contratante> getVendedores() {
		return vendedores;
	}


	public void setVendedores(ArrayList<Contratante> vendedores) {
		this.vendedores = vendedores;
	}




	public Contratante getCorretor() {
		return corretor;
	}


	public void setCorretor(Contratante corretor) {
		this.corretor = corretor;
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


	public ArrayList<Carga> getListCarga() {
		return listCarga;
	}


	public void setListCarga(ArrayList<Carga> listCarga) {
		this.listCarga = listCarga;
	}


	public ArrayList<Pagamento> getListPagamentos() {
		return listPagamentos;
	}


	public void setListPagamentos(ArrayList<Pagamento> listPagamentos) {
		this.listPagamentos = listPagamentos;
	}
	
	
	
	
	
	

}
