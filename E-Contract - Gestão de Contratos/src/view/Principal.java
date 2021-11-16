package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.gerencia.GerenciaCarga;
import controller.gerencia.GerenciaContratos;
import controller.gerencia.GerenciaPagamento;
import controller.gerencia.GerenciaClientes;
import controller.gerencia.GerenciaProduto;
import controller.gerencia.GerenciaSafra;
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

		GerenciaProduto gProduto = new GerenciaProduto();
		GerenciaSafra gSafra = new GerenciaSafra(gProduto);
		GerenciaClientes gCliente = new GerenciaClientes();
		GerenciaContratos gContratos = new GerenciaContratos(gSafra, gCliente);
		GerenciaCarga gCarga = null;
		GerenciaPagamento gPagamento = null;

		do {
			System.out.println("\n\t --==[ E-CONTRACT - Gestão de Contratos ]==--");
			System.out.println("\t+===================================+");
			System.out.println("\t| 1 ---------------- PRODUTOS       |");
			System.out.println("\t| 2 ---------------- SAFRAS         |");
			System.out.println("\t| 3 ---------------- CLIENTES       |");
			System.out.println("\t| 4 ---------------- CONTRATOS      |");
			System.out.println("\t| 0 ---------------- SAIR           |");
			System.out.println("\t+===================================+");
			System.out.println(" Opção: ");
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
						System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
					}

				} while (op2 != 0);
			}
				break;
			case 2: {
				if (gProduto.contemProdutos() <= 0)
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
							System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
						}

					} while (op2 != 0);
				}
			}
				break;
			case 3: {
				do {

					System.out.println("\t --==[  E-CONTRACT - Clientes ]==--");
					imprimeSubMenu();
					op2 = sc.nextInt();

					switch (op2) {
					case 1:
						gCliente.incluir();
						break;
					case 2:
						gCliente.alterar();
						break;
					case 3:
						gCliente.consultar();
						break;
					case 4:
						gCliente.relatorio();
						break;
					case 5:
						gCliente.excluir();
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
			case 4: {
				if (gProduto.contemProdutos() <= 0) {
					System.out.println("Cadastre um produto antes de continuar...");

				} else if (gSafra.contemSafras() <= 0) {
					System.out.println("Cadastre uma safra antes de continuar...");

				} else if (gCliente.contem(0) <= 0 && gCliente.contem(1) <= 0 && gCliente.contem(2) <= 0) {
					System.out.println("Cadastre Contratantes, Motoristas/Transportadores antes de continuar...");

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
							if (gContratos.contem() <= 0) {
								System.out.println("Não há Contratos para Gerenciar");
							} else {
								int id_contrato = -1;
								Contrato contrato = null;
								do {
									System.out.println(" -Selecione o ID do Contrato que deseja Gerenciar: ");
									gContratos.relatorio();
									id_contrato = sc.nextInt();
									contrato = gContratos.posicaoValida(id_contrato);
									if (contrato == null) {
										System.out.println("Posição Inválida!");

									} else {
									}
								} while (contrato == null);

								imprimeSubMenuGerenciarContrato();
								op2 = sc.nextInt();
								switch (op2) {
								case 1: {

									do {
										if (gCliente.numVeiculos() <= 0) {
											System.out.println(
													"Cadastre Motorista Autonomos ou Transportadora para Gerenciar Cargas!");
											op2 = 0;
										} else {

											imprimeSubMenu();
											op2 = sc.nextInt();

											gCarga = new GerenciaCarga(gCliente, contrato);

											switch (op2) {
											case 1: {
												gCarga.incluir(contrato.getId());
											}
												break;
											case 2: {
												if (gCarga.getTotalRecebido(contrato.getId()) > 0) {
													gCarga.alterar(contrato.getId());

												} else {
													System.out.println("Nenhuma Carga Registrada!");

												}
											}
												break;
											case 3: {
												gCarga.consultar(contrato.getId());
											}
												break;
											case 4: {
												gCarga.relatorio(contrato.getId());
											}
												break;
											case 5: {
												gCarga.excluir(contrato.getId());

											}
												break;
											}

										}
									} while (op2 != 0);
								}
									break;
								case 2: {

									do {
										imprimeSubMenu();
										op2 = sc.nextInt();
										gPagamento = new GerenciaPagamento(contrato);

										switch (op2) {

										case 1: {
											gPagamento.incluir(contrato.getId());
										}
											break;
										case 2: {
											gPagamento.alterar(contrato.getId());
										}
											break;
										case 3: {
											gPagamento.consultar(contrato.getId());
										}
											break;
										case 4: {
											gPagamento.relatorio(contrato.getId());
										}
											break;
										case 5: {
											gPagamento.excluir(contrato.getId());

										}
											break;
										}

									} while (op2 != 0);

								}
									break;
								case 0:
									System.out.println("\n Até mais!\n");
									break;
								default:
									System.out.println("\n Opção inválida!\n Tente novamente.\n");
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
							System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
						}

					} while (op2 != 0);

				}
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
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- Cadastrar      |");
		System.out.println("\t| 2 ---------------- Alterar        |");
		System.out.println("\t| 3 ---------------- Consultar      |");
		System.out.println("\t| 4 ---------------- Relatório      |");
		System.out.println("\t| 5 ---------------- Excluir        |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Opção: ");

	}

	public static void imprimeSubMenuContratos() {
		System.out.println("Escolha uma das seguintes opções disponíveis");
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- Cadastrar      |");
		System.out.println("\t| 2 ---------------- Alterar        |");
		System.out.println("\t| 3 ---------------- Gerenciar      |");
		System.out.println("\t| 4 ---------------- Consultar      |");
		System.out.println("\t| 5 ---------------- Relatório      |");
		System.out.println("\t| 6 ---------------- Excluir        |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Opção: ");

	}

	public static void imprimeSubMenuGerenciarContrato() {
		System.out.println("\n Escolha uma das seguintes opções disponíveis");
		System.out.println("\t --==[  GERENCIAR CONTRATO ]==--");
		System.out.println("\t+===================================+");
		System.out.println("\t| 1 ---------------- CARGAS         |");
		System.out.println("\t| 2 ---------------- PAGAMENTOS     |");
		System.out.println("\t| 0 ---------------- Retornar       |");
		System.out.println("\t+===================================+");
		System.out.print("\t Opção: ");

	}
}
