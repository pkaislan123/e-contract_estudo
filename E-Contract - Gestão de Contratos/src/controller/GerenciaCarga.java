
package controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import model.Carga;
import model.Cliente;
import model.Contrato;
import model.Produto;
import model.Veiculo;




public class GerenciaCarga {

	private ArrayList<Carga> listCarga;
	private Scanner num, str;
	private ArrayList<Cliente> listClientes;
	private Contrato contrato;
	private GerenciaPessoas gClientes;
	Locale ptBr = new Locale("pt", "BR");

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
       
    	listCarga.add(new Carga(listCarga.size() + 1,pesoCarga,v, data_rec, null));
    	contrato.setListCarga(listCarga);
        System.out.println(" Carga cadastrado com sucesso!\n");
    }
	
	
public void alterar() {
	listarCargas();
	int pos, op;
	Carga p;
	int id_veiculo;
	LocalDate data_rec = null;
	LocalDate data_carg = null;

	if (listCarga.isEmpty()) {
		System.out.println("\n Não há Cargas até o momento!\n Operação cancelada.\n");
		return;
	}

	System.out.println("\n --==[ Alterar cadastro ]==--");
	System.out.println(" Selecione a opção correspondente ao que deseja alterar");
	System.out.println("\n Digite a posição do cadastro que deseja alterar: ");
	pos = num.nextInt();

	if (isPosInvalida( pos)) {
		System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
		return;
	}
	
		p = listCarga.get(pos);
	
	imprimir(p.getId());

	System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
	op = num.nextInt();
	if (op != 1) {
		System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
		return;
	}
	System.out.println("\n Digite os novos dados");
    System.out.println("Alterar Peso da Carga: ? Peso atual: " + p.getPesoCarga());
	System.out.println("\n1.Sim | 2.Não");
    op = num.nextInt();
    if(op  == 1) {
	  System.out.print(" Peso da Carga: ");
      p.setPesoCarga(num.nextDouble());
    }
    
    System.out.println("Alterar Veiculo da Carga: ? Veiculo atual: " + p.getVeiculo().getPlaca());
    System.out.println("\n1.Sim | 2.Não");
    op = num.nextInt();
    if(op  == 1) {
    	 System.out.print(" Veículo: ");
         System.out.print(" Selecione o Id do Veículo: \n");
     	do {
     		gClientes.listarVeiculos();
     		id_veiculo = num.nextInt();
     		
   		} while (id_veiculo < 0 || id_veiculo > gClientes.numVeiculos());

     	Veiculo v = gClientes.returnVeiculo(id_veiculo - 1);
     	p.setVeiculo(v);
    }
     
    
    
    
    System.out.println("Alterar Data de Recebimento da Carga: ? Data atual: " + formatarData(p.getDataRecebimento()));
    System.out.println("\n1.Sim | 2.Não");
    op = num.nextInt();
    if(op  == 1) {
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
      	p.setDataRecebimento(data_rec);
    }
    
    System.out.println("Alterar Data de Carregamento da Carga: ? Data atual: " + formatarData(p.getDataCarregamento()));
    System.out.println("\n1.Sim | 2.Não");
    op = num.nextInt();
    if(op  == 1) {
    	boolean data_valida = false;
      	do {
          System.out.print(" Data do Carregamento: Ex: 19/06/2021\n");
          try {
           data_carg  = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
          data_valida = true;
      	}catch(Exception e) {
      		System.out.println("Data Invalida!");
      	}
          
      	}while(!data_valida);
      	p.setDataCarregamento(data_carg);
    }
    
  
     
	
	System.out.println(" Cadastro alterado com sucesso!\n");
}


public void relatorio() {
	String tipos_unidades[] = { "Sacos", "Kgs" };
	String unidade  =  tipos_unidades[contrato.getUnidadeMedida()];
	
	double quantidade_recebida  = 0;
	double quantidade_carregada = 0;
	double quantidade_total = contrato.getQuantidadeContratada();
	if (listCarga.isEmpty()) {
		System.out.println("\n Não há Cargas cadastradas!\n");
		return;
	}

	System.out.println("\n --==[ Relatório de Cargas ]==--");
	for (int i = 0; i < listCarga.size(); i++) {
		imprimir(i);
		quantidade_recebida += listCarga.get(i).getPesoCarga();
		if(listCarga.get(i).getDataCarregamento() != null) {
			quantidade_carregada += quantidade_recebida;

		}
	}
	
	System.out.println("\n------------ Somatórias---------");

	System.out.println("\nQuantidade Total a Receber: "  + quantidade_total + " " + unidade );
	System.out.println("Quantidade Total Recebida: "  + quantidade_recebida + " " + unidade);
	
	double diferenca_recebimento = quantidade_total - quantidade_recebida;

	if (diferenca_recebimento == 0) {
		System.out.println("Recebimento Concluído");
	} else if (diferenca_recebimento < 0) {
		System.out.println("Recebimento Excedeu em " + diferenca_recebimento + " " + unidade);

	} else if (diferenca_recebimento > 0) {
		System.out.println("Recebimento Incompleto, falta " + diferenca_recebimento + " " + unidade);
	}
		
	System.out.println("\nQuantidade Total a Carregar: "  + quantidade_recebida+ " " + unidade );
	System.out.println("Quantidade Total Carregada: "  + quantidade_carregada  + " " + unidade);
	
	
	double diferenca_carregamento = quantidade_recebida - quantidade_carregada;

	if (diferenca_carregamento == 0) {
		System.out.println("Carregamento Concluído");
	} else if (diferenca_carregamento < 0) {
		System.out.println("Carregamento Excedeu em " + diferenca_carregamento + " " + unidade);

	} else if (diferenca_carregamento > 0) {
		System.out.println("Carregamento Incompleto, falta " + diferenca_carregamento + " " + unidade);
	}
	
}

public void listarCargas() {
	for(Carga ca : listCarga) {
		System.out.println("POSIÇÃO: " + listCarga.indexOf(ca) + " Peso: " +ca.getPesoCarga() + " Placa: " + ca.getVeiculo().getPlaca() +
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

public boolean isPosInvalida( int pos) {
	boolean result = false;

		if (pos >= listCarga.size() || pos < 0) {
			result = true;
		}
	
	return result;
}


	public String formatarData(LocalDate data) {
		if(data!= null)
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		else
			return "";
	}
	
	
	public double getTotalCarregado() {
		double quantidade_carregada = 0;
		double quantidade_recebida = 0;
		for (int i = 0; i < listCarga.size(); i++) {
			quantidade_recebida += listCarga.get(i).getPesoCarga();
			if(listCarga.get(i).getDataCarregamento() != null) {
				quantidade_carregada += quantidade_recebida;

			}
		}
		return quantidade_carregada;
	}
	
	public double getTotalRecebido() {
		double quantidade_carregada = 0;
		double quantidade_recebida = 0;
		for (int i = 0; i < listCarga.size(); i++) {
			quantidade_recebida += listCarga.get(i).getPesoCarga();
			
		}
		return quantidade_recebida;
	}
}
