
package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Carga;
import model.Cliente;
import model.Contrato;
import model.Veiculo;




public class GerenciaCarga {

	private ArrayList<Carga> listCarga;
	private Scanner num, str;
	private ArrayList<Cliente> listClientes;
	private Contrato contrato;
	private GerenciaPessoas gClientes;
	
	public GerenciaCarga(ArrayList<Cliente> listClientes,  Contrato contrato) {
		this.listCarga = contrato.getListCarga();
		this.contrato = contrato;
		this.listClientes = listClientes;
		str = new Scanner(System.in);
		num = new Scanner(System.in);
		gClientes = new GerenciaPessoas(listClientes);

	}
	
public void incluir() {
        
		double pesoCarga;
		int id_veiculo;
		LocalDate data_rec = null;
        System.out.println("\n --==[ Cadastro de Carga ]==--");
     
        System.out.print(" Peso da Carga: ");
        pesoCarga = num.nextDouble();
        System.out.print(" Veículo: ");
        System.out.print(" Selecione o Id do Veículo: \n");
    	do {
    		gClientes.listarVeiculos();
    		id_veiculo = num.nextInt();
    		
		} while (id_veiculo < 0 || id_veiculo > gClientes.numVeiculos());

    	Veiculo v = gClientes.returnVeiculo(id_veiculo - 1);
    	boolean data_valida = false;
    	do {
        System.out.print(" Data do Recebimento: Ex: 19/06/2021\n");
        try {
         data_rec  = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        data_valida = true;
    	}catch(Exception e) {
    		System.out.println("Data Invalida!");
    	}
        
    	}while(!data_valida);
       
    	listCarga.add(new Carga(listCarga.size() + 1,pesoCarga,v, data_rec, data_rec.minusDays(1)));
    	contrato.setListCarga(listCarga);
        System.out.println(" Carga cadastrado com sucesso!\n");
    }
	
	
	

public void relatorio() {
	if (listCarga.isEmpty()) {
		System.out.println("\n Não há Cargas cadastradas!\n");
		return;
	}

	System.out.println("\n --==[ Relatório de Cargas ]==--");
	for (int i = 0; i < listCarga.size(); i++) {
		imprimir(i);
	}
}

public void listarCargas() {
	for(Carga ca : listCarga) {
		System.out.println("ID: " + ca.getId() + " Peso: " +ca.getPesoCarga() + " Placa: " + ca.getVeiculo().getPlaca() +
		" Data Recebimento: " + formatarData( ca.getDataRecebimento() ) + " Data Carregamento: " +formatarData( ca.getDataCarregamento()));
	}
}

public void imprimir(int posConsulta) {

	System.out.println("\n --== Consulta posição: " + posConsulta + " ==--");
	System.out.println(" Peso: " + listCarga.get(posConsulta).getPesoCarga());
	System.out.println(" Veiculo: " + listCarga.get(posConsulta).getVeiculo().getPlaca());
	System.out.println(" Data Recebimento: " + formatarData( listCarga.get(posConsulta).getDataRecebimento()));
	System.out.println(" Data Carregamento: " + formatarData (listCarga.get(posConsulta).getDataCarregamento()));

}

public void consultar() {
	listarCargas();
	int posConsulta = 0;

	if (listCarga.isEmpty()) {
		System.out.println("\n Não há Cargas cadastradas!\n");
		return;
	}

	System.out.println("\n --==[ Pesquisa produtos ]==--");
	System.out.print(" Informe a posição da consulta: ");
	posConsulta = num.nextInt();
	posConsulta -= 1;
	if (posConsulta >= listCarga.size() || posConsulta < 0) {
		System.out.println("\n Posição inválida!\n");
		return;
	}
	imprimir(posConsulta);
}

public void excluir() {
	int posConsulta, op;
	listarCargas();

	if (listCarga.isEmpty()) {
		System.out.println("\n Não há Cargas cadastrados!\n");
		return;
	}
	System.out.println("\n --==[ Exclusão de Carga ]==--");
	System.out.print(" Informe a posição do Carga que deseja EXCLUIR: ");
	posConsulta = num.nextInt() - 1;

	if (posConsulta >= listCarga.size() || posConsulta < 0) {
		System.out.println("\n Posição inválida!\n");
		return;
	}
	imprimir(posConsulta);

	System.out.println("\n Deseja EXCLUIR o produto listado? 1.Sim | 2.Não");
	op = num.nextInt();
	if (op == 1) {
		listCarga.remove(posConsulta);
		System.out.println("\n Carga excluído com sucesso!\n");
	} else {
		System.out.println(" Exclusão cancelada");
	}
}

	

	public String formatarData(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
