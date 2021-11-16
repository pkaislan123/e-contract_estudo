package controller.gerencia;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import controller.DAO.ProdutoDAO;
import model.Produto;

public class GerenciaProduto {

	private Scanner num, str;

	public GerenciaProduto() {
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

		Produto produto = new Produto(nome, descricao, transgenia);

		if (new ProdutoDAO().incluir(produto) > 0) {
			System.out.println("\n Cadastro realizado com sucesso!\n");
		}
	}

	public void imprimeDetalhesProduto(Produto produto) {

		System.out.println(" ID: " + produto.getId());
		System.out.println(" Nome: " + produto.getNome());
		System.out.println(" Descrição: " + produto.getDescricao());
		System.out.println(" Transgênia: " + produto.getTransgenia());

	}

	public void relatorio() {
		ProdutoDAO gerenciar = new ProdutoDAO();
		ArrayList<Produto> listProdutos = gerenciar.relatorio();
		if (listProdutos != null && listProdutos.size() > 0) {
			imprimeProdutos(listProdutos);
		} else {
			System.out.println(" Não há Produtos Cadastrados!");

		}
	}

	public void alterar() {
		ArrayList<Produto> listProdutos = new ProdutoDAO().relatorio();

		if (listProdutos != null && listProdutos.size() > 0) {
			int id;

			imprimeProdutos(listProdutos);
			System.out.println("-Digite o ID do produto para Alterar:");
			id = num.nextInt();

			Produto produto = posicaoValida(listProdutos, id);
			if (produto != null) {

				System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");

				int op = num.nextInt();
				if (op != 1) {
					System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
					return;
				}
				System.out.println("\n Digite os novos dados");
				System.out.println("Alterar Nome: ?(Atual: " + produto.getNome() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Nome: ");
					produto.setNome(str.nextLine());

				}

				System.out.println("Alterar Descrição: ?(Atual: " + produto.getDescricao() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Descrição: ");
					produto.setDescricao(str.nextLine());
				}

				System.out.println("Alterar Transgenia: ?(Atual: " + produto.getTransgenia() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Trangenia: ");
					produto.setTransgenia(str.nextLine());
				}

				if (new ProdutoDAO().alterar(produto)) {
					System.out.println("\n Cadastro alterado com sucesso!\n");
				}

			} else {
				System.out.println(" ID Inválido!");
			}
		} else {
			System.out.println(" Não há Produtos Cadastrados!");
		}
	}

	public Produto posicaoValida(ArrayList<Produto> listProdutos, int id) {

		Produto produto = null;
		boolean contem = false;
		Iterator<Produto> i = listProdutos.iterator();
		while (!contem && i.hasNext()) {
			produto = i.next();

			if (produto.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return produto;
		else
			return null;

	}

	public Produto posicaoValida(int id) {
		ArrayList<Produto> listProdutos = new ProdutoDAO().relatorio();

		Produto produto = null;
		boolean contem = false;
		Iterator<Produto> i = listProdutos.iterator();
		while (!contem && i.hasNext()) {
			produto = i.next();

			if (produto.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return produto;
		else
			return null;

	}

	public void excluir() {
		ArrayList<Produto> listProdutos = new ProdutoDAO().relatorio();

		if (listProdutos != null && listProdutos.size() > 0) {
			int id;

			imprimeProdutos(listProdutos);
			System.out.println("-Digite o ID do produto para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listProdutos.size()) {
				Produto produto = posicaoValida(listProdutos, id);
				if (produto != null) {
					imprimeDetalhesProduto(produto);

					System.out.println("Deseja excluir o produto selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new ProdutoDAO().excluir(id)) {
							System.out.println(" Produto Excluído!");

						}
					} else {
						System.out.println(" Exclusão Cancelada!");

					}

				} else {
					System.out.println(" ID Inválido!");

				}

			} else {
				System.out.println(" ID Inválido!");

			}

		} else {
			System.out.println(" Não há Produtos Cadastrados!");
		}

	}

	public void consultar() {
		ProdutoDAO gerenciar = new ProdutoDAO();
		ArrayList<Produto> listprodutos = gerenciar.relatorio();

		if (listprodutos != null && listprodutos.size() > 0) {
			imprimeProdutos(listprodutos);

			System.out.println("-Digite o ID do produto para Detalhar:");
			int id = num.nextInt();

			Produto produto = posicaoValida(listprodutos, id);
			if (produto != null) {

				imprimeDetalhesProduto(produto);

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há produtos Cadastrados!");

		}
	}

	public void imprimeProdutos(ArrayList<Produto> listProdutos) {
		Iterator<Produto> i = listProdutos.iterator();
		System.out.println("-Lista de Produtos]-");
		while (i.hasNext()) {
			Produto produto = i.next();
			System.out.println("ID: " + produto.getId() + " Nome: " + produto.getNome() + " Descrição: "
					+ produto.getDescricao() + " Transgenia: " + produto.getTransgenia());
		}
	}

	public int contemProdutos() {
		return new ProdutoDAO().isVazio();

	}

}
