package model;
import java.time.LocalDate;

public class Carga {

	private int id;
	private double pesoCarga;
	private Veiculo veiculo;
	private LocalDate dataRecebimento;
	private LocalDate dataCarregamento;
	
	
	
	
	public Carga(int id, double pesoCarga, Veiculo veiculo, LocalDate dataRecebimento, LocalDate dataCarregamento) {
		this.id = id;
		this.pesoCarga = pesoCarga;
		this.veiculo = veiculo;
		this.dataRecebimento = dataRecebimento;
		this.dataCarregamento = dataCarregamento;
	}
	
	
	public Carga(double pesoCarga, Veiculo veiculo, LocalDate dataRecebimento, LocalDate dataCarregamento) {
		this.pesoCarga = pesoCarga;
		this.veiculo = veiculo;
		this.dataRecebimento = dataRecebimento;
		this.dataCarregamento = dataCarregamento;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPesoCarga() {
		return pesoCarga;
	}
	public void setPesoCarga(double pesoCarga) {
		this.pesoCarga = pesoCarga;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public LocalDate getDataCarregamento() {
		return dataCarregamento;
	}
	public void setDataCarregamento(LocalDate dataCarregamento) {
		this.dataCarregamento = dataCarregamento;
	}
	
	
	
	
	
}
