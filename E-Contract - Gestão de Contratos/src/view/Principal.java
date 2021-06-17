package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GerenciaProduto;
import controller.GerenciaSafra;
import controller.GerenciaVeiculo;
import model.Produto;
import model.Safra;
import model.Veiculo;

public class Principal {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		int op = 0, op2;
		ArrayList<Produto> listProdutos = new ArrayList();
		ArrayList<Veiculo> listVeiculo = new ArrayList();
		ArrayList<Safra> listSafra = new ArrayList();

		GerenciaProduto gProduto = new GerenciaProduto(listProdutos);
		GerenciaVeiculo gVeiculo = new GerenciaVeiculo(listVeiculo);
		GerenciaSafra gSafra = new GerenciaSafra(listSafra, listProdutos);

		System.out.println(" --==[ Gestão de Contratos ]==--");
		do {
			System.out.println(" Selecione:");
			System.out.println(" 1 - Produtos");
			System.out.println(" 2 - Safras");

			System.out.println(" 3 - Veículos");
			System.out.println(" 0 - Sair");
			op = sc.nextInt();

			switch (op) {
			case 1: {

				do {

					imprimeSubMenu();
					op2 = sc.nextInt();

					switch (op2) {
					case 1:
						gProduto.incluir();
						break;
					case 2:
						gProduto.alterar();
						break;
					case 3:
						gProduto.consultar();
						break;
					case 4:
						gProduto.relatorio();
						break;
					case 5:
						gProduto.excluir();
						break;
					case 0:
						System.out.println("\n retornar...\n");
						break;
					default:
						System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);
			}
				break;
			case 2: {
				if(listProdutos.isEmpty())
				 System.out.println("Cadastre um produto antes de continuar...");
				else
				{
				do {

					imprimeSubMenu();
					op2 = sc.nextInt();

					switch (op2) {
					case 1:
						gSafra.incluir();
						break;
					case 2:
						gSafra.alterar();
						break;
					case 3:
						gSafra.consultar();
						break;
					case 4:
						gSafra.relatorio();
						break;
					case 5:
						gSafra.excluir();
						break;
					case 0:
						System.out.println("\n retornar...\n");
						break;
					default:
						System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);
				}
			}
				break;
			case 3: {
				do {

					imprimeSubMenu();
					op2 = sc.nextInt();

					switch (op2) {
					case 1:
						gVeiculo.incluir();
						break;
					case 2:
						gVeiculo.alterar();
						break;
					case 3:
						gVeiculo.consultar();
						break;
					case 4:
						gVeiculo.relatorio();
						break;
					case 5:
						gVeiculo.excluir();
						break;
					case 0:
						System.out.println("\n retornar...\n");
						break;
					default:
						System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);

			}
				break;
			case 0:
				System.out.println("\n Até mais!\n");
				break;
			default:
				System.out.println("\n Opção inválida!\n Tente novamente.\n");
			}

		} while (op != 0);

	}

	public static void imprimeSubMenu() {
		System.out.println("\n Escolha uma das seguintes opções disponíveis");
		System.out.println(" 1 - Cadastar");
		System.out.println(" 2 - Alterar");
		System.out.println(" 3 - Consultar");
		System.out.println(" 4 - Relatório");
		System.out.println(" 5 - Excluir");
		System.out.println(" 0 - Retornar");
		System.out.println(" Opção: ");
	}
}
