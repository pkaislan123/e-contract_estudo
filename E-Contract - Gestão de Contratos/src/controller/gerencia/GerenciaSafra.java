
package controller.gerencia;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import controller.DAO.ProdutoDAO;
import controller.DAO.SafraDAO;
import model.Cliente;
import model.Contratante;
import model.Produto;
import model.Safra;

public class GerenciaSafra {

	private Scanner num, str;
	private GerenciaProduto gProduto;

	public GerenciaSafra(GerenciaProduto gProduto) {

		str = new Scanner(System.in);
		num = new Scanner(System.in);
		this.gProduto = gProduto;
	}

	public void incluir() {
		int anoPlantio, anoColheita, id_produto;
		Produto produto;

		System.out.println("\n --==[ Cadastro de Safras ]==--");

		System.out.print(" Ano Plantio: ");
		anoPlantio = num.nextInt();
		System.out.print(" Ano Colheita: ");
		anoColheita = num.nextInt();
		System.out.print(" Selecione o Id do Produto: ");
		do {
			gProduto.relatorio();
			id_produto = num.nextInt();
			produto = gProduto.posicaoValida(id_produto);
		} while (produto == null);

		Safra safra = new Safra(anoPlantio, anoColheita, produto);

		if (new SafraDAO().incluir(safra) > 0) {
			System.out.println("\n Cadastro realizado com sucesso!\n");
		}

	}

	public void excluir() {
		ArrayList<Safra> listSafras = new SafraDAO().relatorio();

		if (listSafras != null && listSafras.size() > 0) {
			int id;

			imprimeSafras(listSafras);
			System.out.println("-Digite o ID da Safra para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listSafras.size()) {
				Safra safra = posicaoValida(listSafras, id);
				if (safra != null) {
					imprimeDetalhesSafra(safra);

					System.out.println("Deseja excluir a safra selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new SafraDAO().excluir(safra.getId())) {
							System.out.println(" Safra Excluída!");

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
			System.out.println(" Não há Safras Cadastrados!");
		}

	}

	public void alterar() {
		ArrayList<Safra> listSafras = new SafraDAO().relatorio();

		if (listSafras != null && listSafras.size() > 0) {
			int id;

			imprimeSafras(listSafras);
			System.out.println("-Digite o ID da Safra para Alterar:");
			id = num.nextInt();

			Safra safra = posicaoValida(listSafras, id);
			if (safra != null) {

				System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
				int op = num.nextInt();
				if (op != 1) {
					System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
					return;
				}
				System.out.println("\n Digite os novos dados");

				System.out.println("Alterar Ano do Plantio: ?(Atual: " + safra.getAnoPlantio() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Ano Plantio: ");
					safra.setAnoPlantio(num.nextInt());
				}

				System.out.println("Alterar Ano Colheita: ?(Atual: " + safra.getAnoColheita() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Ano Colheita: ");
					safra.setAnoColheita(num.nextInt());
				}

				System.out.println("Alterar o Produto: ?(Atual: " + safra.getProduto().getNome() + ")");
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {

					System.out.print(" Selecione o Id do Produto: ");
					int id_produto = -1;
					Produto produto = null;
					do {
						gProduto.relatorio();
						id_produto = num.nextInt();
						produto = gProduto.posicaoValida(id_produto);
					} while (produto == null);

				}

				if (new SafraDAO().alterar(safra)) {
					System.out.println("\n Cadastro alterado com sucesso!\n");
				}

			} else {
				System.out.println(" ID Inválido!");
			}
		} else {
			System.out.println(" Não há Produtos Cadastrados!");
		}
	}

	public void imprimeSafras(ArrayList<Safra> listSafras) {
		Iterator<Safra> i = listSafras.iterator();
		System.out.println("-Lista de Safras]-");
		while (i.hasNext()) {
			Safra safra = i.next();
			System.out.println("ID: " + safra.getId() + " Ano Plantio: " + safra.getAnoPlantio() + " Ano Colheita: "
					+ safra.getAnoColheita() + " Produto: " + safra.getProduto().getNome());
		}
	}

	public void consultar() {
		SafraDAO gerenciar = new SafraDAO();
		ArrayList<Safra> listSafras = gerenciar.relatorio();

		if (listSafras != null && listSafras.size() > 0) {
			imprimeSafras(listSafras);

			System.out.println("-Digite o ID da Safra para Detalhar:");
			int id = num.nextInt();

			Safra safra = posicaoValida(listSafras, id);
			if (safra != null) {

				imprimeDetalhesSafra(safra);

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há produtos Cadastrados!");

		}
	}

	public void imprimeDetalhesSafra(Safra safra) {

		System.out.println(" ID: " + safra.getId());
		System.out.println(" Ano Plantio: " + safra.getAnoPlantio());
		System.out.println(" Ano Colheita: " + safra.getAnoColheita());
		System.out.println(" Produto: " + safra.getProduto().getNome());

	}

	public void relatorio() {
		SafraDAO gerenciar = new SafraDAO();
		ArrayList<Safra> listSafras = gerenciar.relatorio();
		if (listSafras != null && listSafras.size() > 0) {
			imprimeSafras(listSafras);
		} else {
			System.out.println(" Não há Safras Cadastradas!");

		}
	}

	public Safra posicaoValida(ArrayList<Safra> listSafras, int id) {

		Safra safra = null;
		boolean contem = false;
		Iterator<Safra> i = listSafras.iterator();
		while (!contem && i.hasNext()) {
			safra = i.next();

			if (safra.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return safra;
		else
			return null;

	}

	public Safra posicaoValida(int id) {
		ArrayList<Safra> listaSafras = new SafraDAO().relatorio();

		Safra safra = null;
		boolean contem = false;
		Iterator<Safra> i = listaSafras.iterator();
		while (!contem && i.hasNext()) {
			safra = i.next();

			if (safra.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return safra;
		else
			return null;

	}

	public int contemSafras() {
		return new SafraDAO().isVazio();

	}

}
