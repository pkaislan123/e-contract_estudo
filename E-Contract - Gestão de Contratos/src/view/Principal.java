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

		listProdutos.add(new Produto(listProdutos.size() + 1, "SOJA", "AMARELA", "CONVENCIONAL"));
		listProdutos.add(new Produto(listProdutos.size() + 1, "SORGO", "EM GRÃOS", "RR"));
		listProdutos.add(new Produto(listProdutos.size() + 1, "MILHO", "TIPO II", "RR"));

		listSafra.add(new Safra(listSafra.size() + 1, 2020, 2021, listProdutos.get(0)));
		listSafra.add(new Safra(listSafra.size() + 1, 2021, 2021, listProdutos.get(1)));
		listSafra.add(new Safra(listSafra.size() + 1, 2021, 2021, listProdutos.get(2)));

		// enderecos
		Endereco end1 = new Endereco("Rua dos Bobos", "0", "J.K", "Patos de Minas", "MG", "38580-000");
		Endereco end2 = new Endereco("Rua dos Bobos", "1", "J.K", "Patos de Minas", "MG", "38580-000");
		Endereco end3 = new Endereco("Rua dos Bobos", "2", "J.K", "Patos de Minas", "MG", "38580-000");
		Endereco end4 = new Endereco("Rua dos Bobos", "3", "J.K", "Patos de Minas", "MG", "38580-000");
		Endereco end5 = new Endereco("Rua dos Bobos", "4", "J.K", "Patos de Minas", "MG", "38580-000");

		Endereco[] ends1 = new Endereco[2];
		ends1[0] = end1;
		ends1[1] = end2;

		Endereco[] ends2 = new Endereco[2];
		ends2[0] = end3;
		ends2[1] = end4;

		Endereco[] ends3 = new Endereco[2];
		ends3[0] = end5;

		// veiculos
		Veiculo v1 = new Veiculo(1, 1, "RMI-0000");
		Veiculo v2 = new Veiculo(2, 2, "RMX-0000");
		Veiculo v3 = new Veiculo(3, 3, "RMZ-0000");
		Veiculo v4 = new Veiculo(4, 3, "RMG-0000");
		Veiculo v5 = new Veiculo(5, 2, "RMY-0000");
		Veiculo v6 = new Veiculo(6, 5, "RMH-0000");

		ArrayList<Veiculo> vs1 = new ArrayList<>();
		ArrayList<Veiculo> vs2 = new ArrayList<>();
		ArrayList<Veiculo> vs3 = new ArrayList<>();
		ArrayList<Veiculo> vs4 = new ArrayList<>();
		ArrayList<Veiculo> vs5 = new ArrayList<>();
		ArrayList<Veiculo> vs6 = new ArrayList<>();

		vs1.add(v1);
		vs1.add(v2);

		vs3.add(v3);
		vs3.add(v4);

		vs3.add(v5);
		vs4.add(v6);

		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Aislan", "0055451", ends1, "12092798600"));
		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Carlos", "0055451", ends2, "12092798600"));
		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Vitor", "0055451", ends3, "12092798600"));
		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Marcos", "0055451", ends3, "12092798600"));
		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Fernando", "0055451", ends3, "12092798600"));
		listClientes.add(new Contratante(listClientes.size() + 1, 1, "Cassio", "0055451", ends3, "12092798600"));

		listClientes
				.add(new Motorista(listClientes.size() + 1, 2, "Paulo", "0055451", ends3, "12092798600", 5414, vs1));
		listClientes
				.add(new Motorista(listClientes.size() + 1, 2, "Marcio", "0055451", ends3, "12092798600", 4541, vs2));
		listClientes
				.add(new Transportadora(listClientes.size() + 1, 2, "Ranilton", "0055451", ends3, "12092798600", vs3));

		ArrayList<Contratante> compradores = new ArrayList<>();
		ArrayList<Contratante> vendedores = new ArrayList<>();

		compradores.add((Contratante) listClientes.get(0));

		vendedores.add((Contratante) listClientes.get(1));
		vendedores.add((Contratante) listClientes.get(2));

		listContratos.add(new Contrato(listContratos.size() + 1, 0, compradores, vendedores,
				(Contratante) listClientes.get(4), listSafra.get(0), 1000, 80, 0));
		listContratos.add(new Contrato(listContratos.size() + 1, 0, compradores, vendedores,
				(Contratante) listClientes.get(4), listSafra.get(0), 2000, 60, 0));
		listContratos.add(new Contrato(listContratos.size() + 1, 0, compradores, vendedores,
				(Contratante) listClientes.get(5), listSafra.get(0), 3000, 70, 0));

		// cargas

		Carga car1 = new Carga(1, 200, v1, LocalDate.parse("20/06/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				null);
		Carga car2 = new Carga(2, 200, v2, LocalDate.parse("21/06/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				null);
		Carga car3 = new Carga(3, 200, v3, LocalDate.parse("22/06/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				null);
		Carga car4 = new Carga(4, 200, v4, LocalDate.parse("23/06/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				null);
		
		ArrayList<Carga> cargs1 = new ArrayList<>();
		cargs1.add(car1);
		cargs1.add(car2);

		listContratos.get(0).setListCarga(cargs1);
		

		GerenciaProduto gProduto = new GerenciaProduto(listProdutos);
		GerenciaPessoas gPessoas = new GerenciaPessoas(listClientes);
		GerenciaSafra gSafra = new GerenciaSafra(listSafra, listProdutos);
		GerenciaContratos gContratos = new GerenciaContratos(listContratos, listClientes, listSafra, listProdutos);
		GerenciaCarga gCarga;
		GerenciaPagamento gPagamento;

		do {
			System.out.println(" --==[ E-CONTRACT - Gestão de Contratos ]==--");
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
							System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
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
						System.out.println("\n Opção inválida!\n Por gentileza, tente novamente!\n");
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

						}
							break;
						case 3: {
							if (listContratos.isEmpty()) {
								System.out.println("Não há Contratos para Gerenciar");
							} else {
								int id_contrato = -1;
								do {
									System.out.println(" -Selecione o ID do Contrato que deseja Gerenciar: ");
									gContratos.listarContratos();
									id_contrato = sc.nextInt() - 1;

								} while (id_contrato < 0 && id_contrato > listContratos.size());

								imprimeSubMenuGerenciarContrato();
								op2 = sc.nextInt();
								switch (op2) {
								case 1: {

									do {
										imprimeSubMenu();
										op2 = sc.nextInt();
										gCarga = new GerenciaCarga(listClientes, listContratos.get(id_contrato));

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
							// relatorio
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
		System.out.println("\t| 3 ---------------- Gerencar       |");
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
