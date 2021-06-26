
package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Cliente;
import model.Contratante;
import model.Produto;
import model.Safra;


public class GerenciaSafra {

	private ArrayList<Safra> listSafra;
	private Scanner num, str;
	private ArrayList<Produto> listProduto;

	private GerenciaProduto gProduto;
	
	public GerenciaSafra(ArrayList<Safra> listSafra, ArrayList<Produto> listProduto) {
		this.listSafra = listSafra;
		this.listProduto = listProduto;
		str = new Scanner(System.in);
		num = new Scanner(System.in);
		gProduto = new GerenciaProduto(listProduto);

	}
	
	public void incluir() {
        int anoPlantio, anoColheita, id_safra;
        Produto p;
		

        System.out.println("\n --==[ Cadastro de Safras ]==--");
     
        System.out.print(" Ano Plantio: ");
        anoPlantio = num.nextInt();
        System.out.print(" Ano Colheita: ");
        anoColheita = num.nextInt();
        System.out.print(" Selecione o Id do Produto: ");
    	do {
    		gProduto.relatorio();
 			id_safra = num.nextInt();
		} while (id_safra < 0 || id_safra > listProduto.size());

       
        listSafra.add(new Safra(listSafra.size() + 1,anoPlantio, anoColheita, listProduto.get(id_safra)));
        System.out.println(" Safra cadastrado com sucesso!\n");
    }
	
	public void excluir() {
		listarSafras();

		int posConsulta, op;

		if (listSafra.isEmpty()) {
			System.out.println("\n Não há Safras cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclusão de Safra ]==--");
		System.out.print(" Informe a posição do safra que deseja EXCLUIR: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listSafra.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o safra listado? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			listSafra.remove(posConsulta);
			System.out.println("\n Safra excluído com sucesso!\n");
		} else {
			System.out.println(" Exclusão cancelada");
		}
	}
	
	public void relatorio() {
		if (listSafra.isEmpty()) {
			System.out.println("\n Não há safras cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relatório de consultas ]==--");
		for (int i = 0; i < listSafra.size(); i++) {
			imprimir(i);
		}
	}
	
	public void imprimir(int posConsulta) {

		System.out.println("\n --== Consulta posição: " + posConsulta + " ==--");
		System.out.println(" Ano Plantio: " + listSafra.get(posConsulta).getAnoPlantio());
		System.out.println(" Ano Colheita: " + listSafra.get(posConsulta).getAnoColheita());
		System.out.println(" Produto: " + listSafra.get(posConsulta).getProduto().getNome());
	
	}

	
	private void mostrar(Safra p) {


		System.out.println(" Ano Plantio: " + p.getAnoPlantio());
		System.out.println(" Ano Colheita: " + p.getAnoColheita());
		System.out.println(" Produto: " + p.getProduto().getNome());
	
		
	}
	
	public void alterar() {
		listarSafras();

		int pos, op, id_safra;
		Safra p;

		if (listSafra.isEmpty()) {
			System.out.println("\n Não há cadastros até o momento!\n Operação cancelada.\n");
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
		
			p = listSafra.get(pos);
		
		mostrar(p);

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
	
		
		  System.out.println("Alterar Ano do Plantio: ?(Atual: " + p.getAnoPlantio() + ")");
			System.out.println("\n1.Sim | 2.Não");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Ano Plantio: ");
		        p.setAnoPlantio(num.nextInt());
		    }
		
		    System.out.println("Alterar Ano Colheita: ?(Atual: " + p.getAnoColheita() + ")");
			System.out.println("\n1.Sim | 2.Não");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Ano Colheita: ");
		        p.setAnoColheita(num.nextInt());
		    }
		
		    
		    System.out.println("Alterar o Produto: ?(Atual: " + p.getProduto().getNome() + ")");
		 			System.out.println("\n1.Sim | 2.Não");
		 		    op = num.nextInt();
		 		    if(op  == 1) {
		 			  System.out.print(" Selecione o Id do Produto: ");
		 				do {
		 		    		gProduto.listarProdutos();
		 		 			id_safra = num.nextInt();
		 				} while (id_safra <= 0 || id_safra > listProduto.size());
		 	            p.setProduto(listProduto.get(id_safra));		 		    }
		
	    
	       
		
		System.out.println(" Cadastro alterado com sucesso!\n");
	}
	
	
	public boolean isPosInvalida( int pos) {
		boolean result = false;

			if (pos >= listSafra.size() || pos < 0) {
				result = true;
			}
		
		return result;
	}
	
	public void consultar() {
		listarSafras();
		int posConsulta = 0;

		if (listSafra.isEmpty()) {
			System.out.println("\n Não há consultas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa consultas ]==--");
		System.out.print(" Informe a posição da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listSafra.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);
	}

	public void listarSafras() {
		for (Safra sf : listSafra) {
				System.out.println("POSIÇÃO: " + listSafra.indexOf(sf) + " - Ano Plantio: " + sf.getAnoPlantio() + " - Ano Colheita: " + sf.getAnoColheita() + " - Produto: " + sf.getProduto().getNome());
			
		}
	}
	

	
}
