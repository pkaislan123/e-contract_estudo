package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.GerenciaCarga;
import controller.GerenciaContratos;
import controller.GerenciaPagamento;
import controller.GerenciaPessoas;
import controller.GerenciaProduto;
import controller.GerenciaSafra;
import model.Carga;
import model.Cliente;
import model.Contratante;
import model.Contrato;
import model.Endereco;
import model.Motorista;
import model.Pagamento;
import model.Produto;
import model.Safra;
import model.Transportadora;
import model.Veiculo;

public class Principal {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		int op = 0, op2;
		
		ArrayList<Produto> listProdutos = new ArrayList();
		ArrayList<Cliente> listClientes = new ArrayList();
		ArrayList<Safra> listSafra = new ArrayList();
		ArrayList<Contrato> listContratos = new ArrayList();
		ArrayList<Carga> listCarga = new ArrayList();
		ArrayList<Veiculo> listVeiculo = new ArrayList();
		
		

		GerenciaProduto gProduto = new GerenciaProduto(listProdutos);
		GerenciaPessoas gPessoas = new GerenciaPessoas(listClientes);
		GerenciaSafra gSafra = new GerenciaSafra(listSafra, listProdutos);
		GerenciaContratos gContratos = new GerenciaContratos(listContratos, listClientes, listSafra, listProdutos);
		GerenciaCarga gCarga;
		GerenciaPagamento gPagamento;

		do {
			System.out.println(" --==[ E-CONTRACT - Gest�o de Contratos ]==--");
			System.out.println("\t+===================================+");
			System.out.println("\t| 1 ---------------- PRODUTOS       |");
			System.out.println("\t| 2 ---------------- SAFRAS         |");
			System.out.println("\t| 3 ---------------- CLIENTES       |");
			System.out.println("\t| 4 ---------------- CONTRATOS      |");
			System.out.println("\t| 0 ---------------- SAIR           |");
			System.out.println("\t+===================================+");
			System.out.println(" Op��o: ");
			op = sc.nextInt();

			switch (op) {
			case 1: {

				do {
					System.out.println("\t --==[  E-CONTRACT - PRODUTOS ]==--");
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
						System.out.println("\n Op��o inv�lida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);
			}
				break;
			case 2: {
				if (listProdutos.isEmpty())
					System.out.println("Cadastre um produto antes de continuar...");
				else {
					do {

						System.out.println("\t --==[  E-CONTRACT - SAFRAS ]==--");
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
							System.out.println("\n Op��o inv�lida!\n Por gentileza, tente novamente!\n");
						}

					} while (op2 != 0);
				}
			}
				break;
			case 3: {
				do {
					System.out.println("\t --==[  E-CONTRACT - CLIENTES ]==--");
					imprimeSubMenu();
					op2 = sc.nextInt();

					switch (op2) {
					case 1:
						gPessoas.incluir();
						break;
					case 2:
						gPessoas.alterar();
						break;
					case 3:
						gPessoas.consultar();
						break;
					case 4:
						gPessoas.relatorio();
						break;
					case 5:
						gPessoas.excluir();
						break;
					case 0:
						System.out.println("\n retornar...\n");
						break;
					default:
						System.out.println("\n Op��o inv�lida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);

			}
				break;
			case 4: {
				if (listProdutos.isEmpty()) {
					System.out.println("Cadastre um produto antes de continuar...");

				} else if (listSafra.isEmpty()) {
					System.out.println("Cadastre uma safra antes de continuar...");

				} else if (listClientes.isEmpty()) {
					System.out.println("Cadastre Contratantes antes de continuar...");

				} else {

					do {

						imprimeSubMenuContratos();
						op2 = sc.nextInt();

						switch (op2) {
						case 1:
							gContratos.incluir();
							break;
						case 2: {
							gContratos.alterar();
						}
							break;
						case 3: {
							if (listContratos.isEmpty()) {
								System.out.println("N�o h� Contratos para Gerenciar");
							} else {
								int id_contrato = -1;
								do {
									System.out.println(" -Selecione o ID do Contrato que deseja Gerenciar: ");
									gContratos.listarContratos();
									id_contrato = sc.nextInt();

								} while (id_contrato < 0 && id_contrato > listContratos.size());

								imprimeSubMenuGerenciarContrato();
								op2 = sc.nextInt();
								switch (op2) {
								case 1: {

									do {
										if(gPessoas.numVeiculos() <= 0) {
											System.out.println("Cadastre Motorista Autonomos ou Transportador para Gerenciar Cargas!");
										    op2= 0;
										}else {
											
										imprimeSubMenu();
										op2 = sc.nextInt();
										
									
										
										
										gCarga = new GerenciaCarga(listClientes, listContratos.get(id_contrato));

									

										if(gCarga.getTotalRecebido()  > 0) {
										
											switch (op2) {
										case 1: {
											gCarga.incluir();
										}
											break;
										case 2: {
											gCarga.alterar();
										}
											break;
										case 3: {
											gCarga.consultar();
										}
											break;
										case 4: {
											gCarga.relatorio();
										}
											break;
										case 5: {
											gCarga.excluir();

										}
											break;
										}
										
										}else {
											System.out.println("Nenhuma Carga Registrada!");
										}

										}
									} while (op2 != 0);
								}
									break;
								case 2: {

									do {
										imprimeSubMenu();
										op2 = sc.nextInt();
										gPagamento = new GerenciaPagamento(listContratos.get(id_contrato));

										switch (op2) {

										case 1: {
											gPagamento.incluir();
										}
											break;
										case 2: {
											gPagamento.alterar();
										}
											break;
										case 3: {
											gPagamento.consultar();
										}
											break;
										case 4: {
											gPagamento.relatorio();
										}
											break;
										case 5: {
											gPagamento.excluir();

										}
											break;
										}

									} while (op2 != 0);

								}
									break;
								case 0:
									System.out.println("\n At� mais!\n");
									break;
								default:
									System.out.println("\n Op��o inv�lida!\n Tente novamente.\n");
								}// fim siwtch
							} // fim else
						}
							break;
						case 4:
							gContratos.consultar();
							break;
						case 5:
							gContratos.relatorio();
							break;
						case 6:
							gContratos.excluir();
							break;
						case 0:
							System.out.println("\n retornar...\n");
							break;
						default:
							System.out.println("\n Op��o inv�lida!\n Por gentileza, tente novamente!\n");
						}

					} while (op2 != 0);

				}
			}
				break;
			case 0:
				System.out.println("\n At� mais!\n");
				break;
			default:
				System.out.println("\n Op��o inv�lida!\n Tente novamente.\n");
			}

		} while (op != 0);

	}

	public static void imprimeSubMenu() {
		System.out.println("\n Escolha uma das seguintes op��es dispon�veis");
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- Cadastrar      |");
		System.out.println("\t| 2 ---------------- Alterar        |");
		System.out.println("\t| 3 ---------------- Consultar      |");
		System.out.println("\t| 4 ---------------- Relat�rio      |");
		System.out.println("\t| 5 ---------------- Excluir        |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Op��o: ");

	}

	public static void imprimeSubMenuContratos() {
		System.out.println("Escolha uma das seguintes op��es dispon�veis");
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- Cadastrar      |");
		System.out.println("\t| 2 ---------------- Alterar        |");
		System.out.println("\t| 3 ---------------- Gerenciar      |");
		System.out.println("\t| 4 ---------------- Consultar      |");
		System.out.println("\t| 5 ---------------- Relat�rio      |");
		System.out.println("\t| 6 ---------------- Excluir        |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Op��o: ");

	}

	public static void imprimeSubMenuGerenciarContrato() {
		System.out.println("\n Escolha uma das seguintes op��es dispon�veis");
		System.out.println("\t --==[  GERENCIAR CONTRATO ]==--");
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- CARGAS         |");
		System.out.println("\t| 2 ---------------- PAGAMENTOS     |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Op��o: ");

	}
}
