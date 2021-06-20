package model;
import java.time.LocalDate;


public class Pagamento {

	private int id;
	private double valor;
	private LocalDate dataPagamento;
	
	public Pagamento(int id, double valor,  LocalDate dataPagamento) {
		this.id = id;
		this.valor =valor;
		this.dataPagamento = dataPagamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
	
	
	
	
	
}
