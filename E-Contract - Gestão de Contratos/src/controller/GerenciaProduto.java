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
        System.out.print(" Descri��o: ");
        descricao = str.nextLine();
        System.out.print(" Transg�nia: ");
        transgenia = str.nextLine();

       
        listProduto.add(new Produto(listProduto.size() + 1,nome, descricao, transgenia));
        System.out.println(" Produto cadastrado com sucesso!\n");
    }
	
	public void excluir() {
		listarProdutos();

		int posConsulta, op;

		if (listProduto.isEmpty()) {
			System.out.println("\n N�o h� produtos cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclus�o de Produto ]==--");
		System.out.print(" Informe a posi��o do produto que deseja EXCLUIR: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listProduto.size() || posConsulta < 0) {
			System.out.println("\n Posi��o inv�lida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o produto listado? 1.Sim | 2.N�o");
		op = num.nextInt();
		if (op == 1) {
			listProduto.remove(posConsulta);
			System.out.println("\n Produto exclu�do com sucesso!\n");
		} else {
			System.out.println(" Exclus�o cancelada");
		}
	}
	
	public void relatorio() {
		if (listProduto.isEmpty()) {
			System.out.println("\n N�o h� produtos cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relat�rio de produtos ]==--");
		for (int i = 0; i < listProduto.size(); i++) {
			imprimir(i);
		}
	}
	
	public void imprimir(int posConsulta) {

		System.out.println("\n --== Consulta posi��o: " + posConsulta + " ==--");
		System.out.println(" Nome: " + listProduto.get(posConsulta).getNome());
		System.out.println(" Descri��o: " + listProduto.get(posConsulta).getDescricao());
		System.out.println(" Transg�nia: " + listProduto.get(posConsulta).getTransgenia());
	
	}

	
	private void mostrar(Produto p) {

		System.out.println("\n Nome: " + p.getNome());
		System.out.println(" Descri��o: " + p.getDescricao());
		System.out.println(" Transg�nia: " + p.getTransgenia());
		
	}
	
	public void alterar() {
		listarProdutos();
		int pos, op;
		Produto p;

		if (listProduto.isEmpty()) {
			System.out.println("\n N�o h� cadastros at� o momento!\n Opera��o cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Alterar cadastro ]==--");
		System.out.println(" Selecione a op��o correspondente ao que deseja alterar");
		System.out.println("\n Digite a posi��o do cadastro que deseja alterar: ");
		pos = num.nextInt();

		if (isPosInvalida( pos)) {
			System.out.println("\n Posi��o inexistente!\n Opera��o cancelada.\n");
			return;
		}
		
			p = listProduto.get(pos);
		
		mostrar(p);

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.N�o");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Opera��o cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
		  System.out.println("Alterar Nome: ?(Atual: " + p.getNome() + ")");
			System.out.println("\n1.Sim | 2.N�o");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Nome: ");
				p.setNome(str.nextLine());

		    }
		    
		    
		    System.out.println("Alterar Descri��o: ?(Atual: " + p.getDescricao() + ")");
			System.out.println("\n1.Sim | 2.N�o");
		    op = num.nextInt();
		    if(op  == 1) {
			  System.out.print(" Descri��o: ");
				p.setDescricao(str.nextLine());
		    }
		    
		    System.out.println("Alterar Transgenia: ?(Atual: " + p.getTransgenia() + ")");
			System.out.println("\n1.Sim | 2.N�o");
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
			System.out.println("\n N�o h� produtos cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa produtos ]==--");
		System.out.print(" Informe a posi��o da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listProduto.size() || posConsulta < 0) {
			System.out.println("\n Posi��o inv�lida!\n");
			return;
		}
		imprimir(posConsulta);
	}

	public void listarProdutos() {
		for(Produto p : listProduto) {
			System.out.println("POSI��O: " + listProduto.indexOf(p) + " Nome: " + p.getNome() + " Descri��o: " + p.getDescricao() + " Transgenia: " + p.getTransgenia());
		}
	}

	
}
