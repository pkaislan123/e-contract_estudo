package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


import model.Produto;


public class GerenciaProduto {

	private ArrayList<Produto> listProduto;
	private Scanner num, str;

	public GerenciaProduto(ArrayList<Produto> listProduto) {
		this.listProduto = listProduto;
		str = new Scanner(System.in);
		num = new Scanner(System.in);

	}
	
	public void incluir() {
        
		String nome, descricao, transgenia;
		

        System.out.println("\n --==[ Cadastro de Produtos ]==--");
     
        System.out.print(" Nome: ");
        nome = str.nextLine();
        System.out.print(" Descrição: ");
        descricao = str.nextLine();
        System.out.print(" Transgênia: ");
        transgenia = str.nextLine();

       
        listProduto.add(new Produto(listProduto.size() + 1,nome, descricao, transgenia));
        System.out.println(" Produto cadastrado com sucesso!\n");
    }
	
	public void excluir() {
		listarProdutos();

		int posConsulta, op;

		if (listProduto.isEmpty()) {
			System.out.println("\n Não há produtos cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclusão de Produto ]==--");
		System.out.print(" Informe a posição do produto que deseja EXCLUIR: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listProduto.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o produto listado? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			listProduto.remove(posConsulta);
			System.out.println("\n Produto excluído com sucesso!\n");
		} else {
			System.out.println(" Exclusão cancelada");
		}
	}
	
	public void relatorio() {
		if (listProduto.isEmpty()) {
			System.out.println("\n Não há produtos cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relatório de produtos ]==--");
		for (int i = 0; i < listProduto.size(); i++) {
			imprimir(i);
		}
	}
	
	public void imprimir(int posConsulta) {

		System.out.println("\n --== Consulta posição: " + posConsulta + " ==--");
		System.out.println(" Nome: " + listProduto.get(posConsulta).getNome());
		System.out.println(" Descrição: " + listProduto.get(posConsulta).getDescricao());
		System.out.println(" Transgênia: " + listProduto.get(posConsulta).getTransgenia());
	
	}

	
	private void mostrar(Produto p) {

		System.out.println("\n Nome: " + p.getNome());
		System.out.println(" Descrição: " + p.getDescricao());
		System.out.println(" Transgênia: " + p.getTransgenia());
		
	}
	
	public void alterar() {
		listarProdutos();
		int pos, op;
		Produto p;

		if (listProduto.isEmpty()) {
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
		
			p = listProduto.get(pos);
		
		mostrar(p);

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
		  System.out.println("Alterar Nome: ?(Atual: " + p.getNome() + ")");
			System.out.println("\n1.Sim | 2.Não");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Nome: ");
				p.setNome(str.nextLine());

		    }
		    
		    
		    System.out.println("Alterar Descrição: ?(Atual: " + p.getDescricao() + ")");
			System.out.println("\n1.Sim | 2.Não");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Descrição: ");
				p.setDescricao(str.nextLine());
		    }
		    
		    System.out.println("Alterar Transgenia: ?(Atual: " + p.getTransgenia() + ")");
			System.out.println("\n1.Sim | 2.Não");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Trangenia: ");
				p.setTransgenia(str.nextLine());
		    }
	

		
		System.out.println(" Cadastro alterado com sucesso!\n");
	}
	
	
	public boolean isPosInvalida( int pos) {
		boolean result = false;

			if (pos >= listProduto.size() || pos < 0) {
				result = true;
			}
		
		return result;
	}
	
	public void consultar() {
		listarProdutos();
		int posConsulta = 0;

		if (listProduto.isEmpty()) {
			System.out.println("\n Não há produtos cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa produtos ]==--");
		System.out.print(" Informe a posição da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listProduto.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);
	}

	public void listarProdutos() {
		for(Produto p : listProduto) {
			System.out.println("POSIÇÃO: " + listProduto.indexOf(p) + " Nome: " + p.getNome() + " Descrição: " + p.getDescricao() + " Transgenia: " + p.getTransgenia());
		}
	}

	
}
