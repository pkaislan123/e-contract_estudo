package model;
import java.time.LocalDate;


public class Pagamento {

	private int id;
	private Contrato contrato;
	private LocalDate dataPagamento;
	
	public Pagamento(int id, Contrato contrato, LocalDate dataPagamento) {
		this.id = id;
		this.contrato = contrato;
		this.dataPagamento = dataPagamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
	
	
	
	
	
}
