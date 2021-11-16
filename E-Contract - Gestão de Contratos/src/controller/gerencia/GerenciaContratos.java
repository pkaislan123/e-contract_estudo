
package controller.gerencia;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;

import controller.DAO.ClienteDAO;
import controller.DAO.ContratoDAO;
import model.Cliente;
import model.Contratante;
import model.Contrato;
import model.Endereco;
import model.Motorista;
import model.Produto;
import model.Safra;
import model.Transportadora;

public class GerenciaContratos {

	private Scanner num, str;

	private Locale ptBr = new Locale("pt", "BR");

	private GerenciaSafra gSafra;
	private GerenciaClientes gClientes;

	public GerenciaContratos(GerenciaSafra gSafra, GerenciaClientes gClientes) {

		str = new Scanner(System.in);
		num = new Scanner(System.in);

		this.gSafra = gSafra;
		this.gClientes = gClientes;
	}

	public void incluir() {
		int id_contratante, id_safra, unidadeMedida;
		String tipos_unidades[] = { "Sacos", "Kgs" };
		double quantidadeContratada, quantidadeAtendida, valorPorUnidade;
		ArrayList<Contratante> compradores = new ArrayList<>();
		ArrayList<Contratante> vendedores = new ArrayList<>();
		Contratante corretor = null;

		System.out.println("\n --==[ Cadastro de Contratos ]==--");

		System.out.print(" Compradores do Novo Contrato: \n");
		int op = 0;
		do {
			Contratante cont_selecionado = null;
			System.out.print(" Selecione o Id do(s) Contratante(s) deste Contrato: \n");
			boolean isValido = false;
			do {
				gClientes.relatorioContratantes();
				;
				id_contratante = num.nextInt();
				cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
				if (cont_selecionado == null) {
					System.out.println("Posição Inválida!");
				} else
					isValido = true;
			} while (!isValido);

			compradores.add(cont_selecionado);
			System.out.println(" Deseja estipular outro Comprador ao contrato? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op != 2);

		op = 0;
		System.out.print(" Vendedores do Novo Contrato: \n");
		do {
			Contratante cont_selecionado = null;
			System.out.print(" Selecione o Id do(s) Vendedores(s) deste Contrato: \n");
			boolean isValido = false;
			do {
				gClientes.relatorioContratantes();
				;
				id_contratante = num.nextInt();
				cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
				if (cont_selecionado == null) {
					System.out.println("Posição Inválida!");
				} else
					isValido = true;
			} while (!isValido);

			vendedores.add(cont_selecionado);
			System.out.println(" Deseja estipular outro Vendedor ao contrato? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op != 2);

		boolean isValido = false;
		System.out.print(" Corretor do Novo Contrato: \n");
		Contratante cont_selecionado = null;
		System.out.print(" Selecione o Id do Corretor deste Contrato: \n");
		do {
			gClientes.relatorioContratantes();
			;
			id_contratante = num.nextInt();
			cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
			if (cont_selecionado == null) {
				System.out.println("Posição Inválida!");
			} else {
				isValido = true;
				corretor = cont_selecionado;
			}
		} while (!isValido);

		System.out.print(" Safra do Novo Contrato: \n");
		System.out.print(" Selecione o Id da Safra: ");
		Safra safra = null;
		boolean safraValida = false;
		do {
			gSafra.relatorio();
			;
			id_safra = num.nextInt();
			safra = gSafra.posicaoValida(id_safra);
			if (safra != null) {
				safraValida = true;
			} else {
				System.out.println("Posição Inválida!");

			}
		} while (!safraValida);

		System.out.print("\n Unidade de Medida ->  0 - Sacos | 1 - Kgs");
		unidadeMedida = num.nextInt();

		System.out.print("\n Quantidade de " + tipos_unidades[unidadeMedida] + ":\n");
		quantidadeContratada = num.nextDouble();

		System.out.print("\n Valor por " + tipos_unidades[unidadeMedida] + ":\n");
		valorPorUnidade = num.nextDouble();

		Contrato contrato = new Contrato(unidadeMedida, compradores, vendedores, corretor, safra, quantidadeContratada,
				valorPorUnidade, 0);
		int id_inserido = new ContratoDAO().incluir(contrato, ((Cliente) corretor).getId());
		if (id_inserido > 0) {
			System.out.println(" Contrato cadastrado com sucesso!\n");
			boolean prosseguir = true;
			for (Contratante comprador : compradores) {
				if (new ContratoDAO().incluirComprador(id_inserido, ((Cliente) comprador).getId()) > 0) {

				} else {
					System.out.println(" Erro ao cadastrar relação contrato e comprador, banco corrompido!\n");
					prosseguir = false;
					break;
				}
			}
			if (prosseguir) {
				for (Contratante vendedor : vendedores) {
					if (new ContratoDAO().incluirVendedor(id_inserido, ((Cliente) vendedor).getId()) > 0) {

					} else {
						System.out.println(" Erro ao cadastrar relação contrato e vendedor, banco corrompido!\n");
						prosseguir = false;
						break;
					}
				}
			}
		}

	}

	public void detalharContrato(Contrato ct) {
		ContratoDAO gerenciar = new ContratoDAO();

		String tipos_unidades[] = { "Sacos", "Kgs" };
		NumberFormat z = NumberFormat.getNumberInstance();

		ArrayList<Contratante> compradores = gerenciar.getCompradores(ct.getId());

		ArrayList<Contratante> vendedores = gerenciar.getVendedores(ct.getId());

		System.out.println("---Contrato----");
		System.out.println("ID: " + ct.getId());
		System.out.println("Safra: " + ct.getSafra().getAnoPlantio() + "/" + ct.getSafra().getAnoColheita());
		System.out.println("Produto: " + ct.getSafra().getProduto().getNome());
		System.out.println("Unidade: " + tipos_unidades[ct.getUnidadeMedida()]);
		System.out.println(
				"Valor por Unidade: " + NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade()));
		System.out.println("Quantidade Contratada: " + z.format(ct.getQuantidadeContratada()));
		System.out.println("Valor Total: " + NumberFormat.getCurrencyInstance(ptBr)
				.format(ct.getValorPorUnidade() * ct.getQuantidadeContratada()));

		System.out.println("----Compradores:");
		for (Contratante comprador : compradores) {
			System.out.println("Nome: " + comprador.getNome() + " CNPJ: " + comprador.getCnpj());
		}

		System.out.println("----Vendedores:");
		for (Contratante vendedor : vendedores) {
			System.out.println("Nome: " + vendedor.getNome() + " CNPJ: " + vendedor.getCnpj());
		}

	}

	public void imprimirContratos(ArrayList<Contrato> listContratos) {
		ContratoDAO gerenciar = new ContratoDAO();

		String tipos_unidades[] = { "Sacos", "Kgs" };
		NumberFormat z = NumberFormat.getNumberInstance();

		for (Contrato ct : listContratos) {

			System.out.println("---Contrato----");
			System.out.println("ID: " + ct.getId());
			System.out.println("Safra: " + ct.getSafra().getAnoPlantio() + "/" + ct.getSafra().getAnoColheita());
			System.out.println("Produto: " + ct.getSafra().getProduto().getNome());
			System.out.println("Unidade: " + tipos_unidades[ct.getUnidadeMedida()]);
			System.out.println(
					"Valor por Unidade: " + NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade()));
			System.out.println("Quantidade Contratada: " + z.format(ct.getQuantidadeContratada()));
			System.out.println("Valor Total: " + NumberFormat.getCurrencyInstance(ptBr)
					.format(ct.getValorPorUnidade() * ct.getQuantidadeContratada()));
		}

	}

	public void relatorio() {
		ContratoDAO gerenciar = new ContratoDAO();
		ArrayList<Contrato> listContratos = gerenciar.relatorio();
		if (listContratos != null && listContratos.size() > 0) {
			imprimirContratos(listContratos);
		} else {
			System.out.println(" Não há Contratos Cadastrados!");

		}
	}

	public int contem() {
		return new ContratoDAO().isVazio();

	}

	public Contrato posicaoValida(ArrayList<Contrato> contratos, int id) {

		Contrato contrato = null;
		boolean contem = false;
		Iterator<Contrato> i = contratos.iterator();
		while (!contem && i.hasNext()) {
			contrato = i.next();

			if (contrato.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return contrato;
		else
			return null;

	}

	public Contrato posicaoValida(int id) {
		ArrayList<Contrato> contratos = new ContratoDAO().relatorio();
		Contrato contrato = null;
		boolean contem = false;
		Iterator<Contrato> i = contratos.iterator();
		while (!contem && i.hasNext()) {
			contrato = i.next();

			if (contrato.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return contrato;
		else
			return null;

	}

	public void excluir() {
		ArrayList<Contrato> listContratos = new ContratoDAO().relatorio();

		if (listContratos != null && listContratos.size() > 0) {
			int id;

			imprimirContratos(listContratos);
			System.out.println("-Digite o ID do Contrato para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listContratos.size()) {
				Contrato contrato = posicaoValida(listContratos, id);
				if (contrato != null) {
					detalharContrato(contrato);

					System.out.println("Deseja excluir o Contrato selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new ContratoDAO().excluir(id)) {
							System.out.println(" Contrato Excluído!");

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

	public void alterar() {
		String tipos_unidades[] = { "Sacos", "Kgs" };

		ArrayList<Contrato> listContratos = new ContratoDAO().relatorio();

		if (listContratos != null && listContratos.size() > 0) {
			int id;
			int op = 0;
			imprimirContratos(listContratos);
			System.out.println("-Digite o ID do Contrato para Alterar:");
			id = num.nextInt();

			Contrato contrato = posicaoValida(listContratos, id);
			if (contrato != null) {

				int finalizar = -1;
				do {
					System.out.println("1 - Editar o Contrato ");
					System.out.println("2 - Editar o Corretor ");
					System.out.println("3 - Editar Compradores ");
					System.out.println("4 - Editar Vendedores ");
					System.out.println("0 - Sair");
					int opcaoMenu = num.nextInt();

					if (opcaoMenu == 1) {
						System.out.println("Alterar Safra?(Safra; " + contrato.getSafra().getProduto().getNome() + " "
								+ contrato.getSafra().getAnoPlantio() + "/" + contrato.getSafra().getAnoColheita()
								+ ")");
						System.out.println("\n1.Sim | 2.Não");
						op = num.nextInt();
						if (op == 1) {
							int id_safra = -1;
							Safra safra = null;
							boolean safraValida = false;
							do {
								gSafra.relatorio();

								id_safra = num.nextInt();
								safra = gSafra.posicaoValida(id_safra);
								if (safra != null) {
									safraValida = true;
									contrato.setSafra(safra);
								} else {
									System.out.println("Posição Inválida!");

								}
							} while (!safraValida);

						}

						System.out.println("Alterar Unidade de Medida: ?(Unidade de Medida: "
								+ tipos_unidades[contrato.getUnidadeMedida()] + ")");
						System.out.println("\n1.Sim | 2.Não");
						op = num.nextInt();
						if (op == 1) {
							System.out.print("\n Unidade de Medida ->  0 - Sacos | 1 - Kgs");
							int unidadeMedida = num.nextInt();
							contrato.setUnidadeMedida(unidadeMedida);

						}

						System.out.println("Alterar Quantidade: ?( " + contrato.getQuantidadeContratada() + " "
								+ tipos_unidades[contrato.getUnidadeMedida()] + ")");
						System.out.println("\n1.Sim | 2.Não");
						op = num.nextInt();
						if (op == 1) {
							System.out.print("\n Quantidade de " + tipos_unidades[contrato.getUnidadeMedida()] + ":\n");
							double quantidadeContratada = num.nextDouble();
							contrato.setQuantidadeContratada(quantidadeContratada);

						}

						System.out.println("Alterar Valor: ?( " + contrato.getValorPorUnidade() + " por "
								+ tipos_unidades[contrato.getUnidadeMedida()] + ")");
						System.out.println("\n1.Sim | 2.Não");
						op = num.nextInt();
						if (op == 1) {
							System.out.print("Valor: ");
							double valorPorUnidade = num.nextDouble();
							contrato.setValorPorUnidade(valorPorUnidade);

						}

						boolean alterar = new ContratoDAO().alterar(contrato);
						if (alterar) {
							System.out.println("Contrato Alterado!");
						}

					} else if (opcaoMenu == 2) {
						Contratante corretor_atual = new ClienteDAO().getContratante(contrato.getCorretor().getId());
						System.out.println(
								"Corretor Atual: " + corretor_atual.getNome() + " CNPJ: " + corretor_atual.getCnpj());
						System.out.println(" Mudar Corretor?: 1.(SIM)");
						int opcao = num.nextInt();
						if (opcao == 1) {
							Contratante cont_selecionado = null;
							System.out.print(" Selecione o Id do Corretor deste Contrato: \n");
							boolean isValido = false;
							Contratante corretor = null;
							do {
								gClientes.relatorioContratantes();

								int id_contratante = num.nextInt();
								cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
								if (cont_selecionado == null) {
									System.out.println("Posição Inválida!");
								} else {
									isValido = true;
									corretor = cont_selecionado;
								}
							} while (!isValido);

							boolean alterar_corretor = new ContratoDAO().alterarCorretor(contrato.getId(),
									((Cliente) corretor).getId());
							if (alterar_corretor) {
								System.out.println("--Corretor do Contrato Alterado");
							}
						}
					} else if (opcaoMenu == 3) {
						ContratoDAO gerenciar = new ContratoDAO();
						ArrayList<Contratante> compradores = gerenciar.getCompradores(contrato.getId());

						System.out.println("1 - Novo Comprador");
						System.out.println("2 - Excluir Comprador");
						opcaoMenu = num.nextInt();
						if (opcaoMenu == 1) {

							System.out.print(" Comprador para o Contrato: \n");
							finalizar = -1;
							do {
								Contratante cont_selecionado = null;
								System.out.print(" Selecione o Id do(s) Contratante(s) deste Contrato: \n");
								boolean isValido = false;
								do {
									gClientes.relatorioContratantes();

									int id_contratante = num.nextInt();
									cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
									if (cont_selecionado == null) {
										System.out.println("Posição Inválida!");
									} else
										isValido = true;
								} while (!isValido);

								System.out.println(" Confirmar Inclusão: 1. Sim | 2.Não");
								opcaoMenu = num.nextInt();
								if (opcaoMenu == 1) {
									int inserir = new ContratoDAO().incluirComprador(contrato.getId(),
											((Cliente) cont_selecionado).getId());
									if (inserir > 0) {
										System.out.println("Novo comprador incluso no contrato");
										finalizar = 0;
									} else {
										finalizar = 0;
									}

								} else {
									finalizar = 0;
								}

							} while (finalizar != 0);

						} else if (opcaoMenu == 2) {

							int id_selecionado = -1;
							finalizar = -1;
							do {
								System.out.println("----Compradores:");
								for (Contratante comprador : compradores) {
									System.out.println("ID " + ((Cliente) comprador).getId() + " Nome: "
											+ comprador.getNome() + " CNPJ: " + comprador.getCnpj());

								}
								System.out.println("Digite o id do Comprador: ");
								int posicao = num.nextInt();
								Contratante comprador_selecionado = posicaoContratanteValida(compradores, posicao);
								if (comprador_selecionado == null) {
									System.out.println("Posição Inválida!");
								} else {
									System.out.println("1 - Excluir Comprador");
									System.out.println("0 - Sair");

									opcaoMenu = num.nextInt();

									if (opcaoMenu == 1) {
										int num_compradores = new ContratoDAO().numCompradores(contrato.getId());
										if (num_compradores <= 1) {
											System.out.println(" --É necessário ao menos um Comprador no Contrato!");
											finalizar = 0;
										} else {
											System.out.println("Confirmar Exclusão?: 1 - Sim | 2 - Não");
											opcaoMenu = num.nextInt();
											if (opcaoMenu == 1) {
												boolean excluir_relacao = new ContratoDAO()
														.excluirRelacaoContratoComprador(contrato.getId(), posicao);
												if (excluir_relacao) {
													System.out
															.print("O comprador selecionado foi removido do contrato!");
													finalizar = 0;
												}
											} else {
												finalizar = 0;
											}
										}
									} else if (opcaoMenu == 0) {
										finalizar = 0;
									} else {
										System.out.println("Opção Inválida!");
									}

								}
							} while (finalizar != 0);

						}

					} else if (opcaoMenu == 4) {
						ContratoDAO gerenciar = new ContratoDAO();
						ArrayList<Contratante> vendedores = gerenciar.getVendedores(contrato.getId());

						System.out.println("1 - Novo Vendedor");
						System.out.println("2 - Excluir Vendedor");
						opcaoMenu = num.nextInt();
						if (opcaoMenu == 1) {

							System.out.print(" Vendedor para o Contrato: \n");
							finalizar = -1;
							do {
								Contratante cont_selecionado = null;
								System.out.print(" Selecione o Id do(s) Contratante(s) deste Contrato: \n");
								boolean isValido = false;
								do {
									gClientes.relatorioContratantes();

									int id_contratante = num.nextInt();
									cont_selecionado = gClientes.posicaoContratanteValida(id_contratante);
									if (cont_selecionado == null) {
										System.out.println("Posição Inválida!");
									} else
										isValido = true;
								} while (!isValido);

								System.out.println(" Confirmar Inclusão: 1. Sim | 2.Não");
								opcaoMenu = num.nextInt();
								if (opcaoMenu == 1) {
									int inserir = new ContratoDAO().incluirContratoCVendedor(contrato.getId(),
											((Cliente) cont_selecionado).getId());
									if (inserir > 0) {
										System.out.println("Novo vendedor incluso no contrato");
										finalizar = 0;
									} else {
										finalizar = 0;
									}

								} else {
									finalizar = 0;
								}

							} while (finalizar != 0);

						} else if (opcaoMenu == 2) {

							int id_selecionado = -1;
							finalizar = -1;
							do {
								System.out.println("----Vendedores:");
								for (Contratante vendedor : vendedores) {
									System.out.println("ID " + ((Cliente) vendedor).getId() + " Nome: "
											+ vendedor.getNome() + " CNPJ: " + vendedor.getCnpj());

								}
								System.out.println("Digite o id do vendedor: ");
								int posicao = num.nextInt();
								Contratante vendedor_selecionado = posicaoContratanteValida(vendedores, posicao);
								if (vendedor_selecionado == null) {
									System.out.println("Posição Inválida!");
								} else {
									System.out.println("1 - Excluir Vendedor");
									System.out.println("0 - Sair");

									opcaoMenu = num.nextInt();

									if (opcaoMenu == 1) {
										int num_vendedores = new ContratoDAO().numVendedores(contrato.getId());
										if (num_vendedores <= 1) {
											System.out.println(" --É necessário ao menos um Vendedor no Contrato!");
											finalizar = 0;
										} else {
											System.out.println("Confirmar Exclusão?: 1 - Sim | 2 - Não");
											opcaoMenu = num.nextInt();
											if (opcaoMenu == 1) {
												boolean excluir_relacao = new ContratoDAO()
														.excluirRelacaoContratoVendedor(contrato.getId(), posicao);
												if (excluir_relacao) {
													System.out
															.print("O vendedor selecionado foi removido do contrato!");
													finalizar = 0;
												}
											} else {
												finalizar = 0;
											}
										}
									} else if (opcaoMenu == 0) {
										finalizar = 0;
									} else {
										System.out.println("Opção Inválida!");
									}

								}
							} while (finalizar != 0);

						}

					} else if (opcaoMenu == 0) {
						finalizar = 0;
					} else {
						System.out.println(" Opção Inválida!");

					}

				} while (finalizar != 0);

			} else {
				System.out.println(" ID Inválido!");
			}
		} else {
			System.out.println(" Não há Contratos Cadastrados!");
		}
	}

	public void consultar() {
		ContratoDAO gerenciar = new ContratoDAO();
		ArrayList<Contrato> listContratos = gerenciar.relatorio();

		if (listContratos != null && listContratos.size() > 0) {
			imprimirContratos(listContratos);

			System.out.println("-Digite o ID do Contrato para Detalhar:");
			int id = num.nextInt();

			Contrato contrato = posicaoValida(listContratos, id);
			if (contrato != null) {

				detalharContrato(contrato);

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há Contratos Cadastrados!");

		}
	}

	public Contratante posicaoContratanteValida(ArrayList<Contratante> contratantes, int id) {

		Contratante contratante = null;
		boolean contem = false;
		Iterator<Contratante> i = contratantes.iterator();
		while (!contem && i.hasNext()) {
			contratante = i.next();

			if (((Cliente) contratante).getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return contratante;
		else
			return null;

	}

	public void somatoriaGeral(ArrayList<Contrato> listContratos) {

		double total_quantidade_contratada = 0;
		double total_quantidade_recebida = 0;
		double total_quantidade_carregada = 0;
		String tipos_unidades[] = { "Sacos", "Kgs" };
		String unidade = "";
		double total_pago = 0;

		for (Contrato ct : listContratos) {
			unidade = tipos_unidades[ct.getUnidadeMedida()];

			GerenciaCarga gCarga = new GerenciaCarga(gClientes, ct);

			GerenciaPagamento gPagamento = new GerenciaPagamento(ct);

			total_quantidade_contratada += ct.getQuantidadeContratada();
			total_quantidade_recebida += gCarga.getTotalRecebido();
			total_quantidade_carregada += gCarga.getTotalCarregado();
			total_pago += gPagamento.getTotalPago();

		}
		System.out.println("\n------------ Somatórias---------");

		System.out.println("\nQuantidade Total a Receber: " + total_quantidade_contratada + " " + unidade);
		System.out.println("Quantidade Total Recebida: " + total_quantidade_recebida + " " + unidade);

		double diferenca_recebimento = total_quantidade_contratada - total_quantidade_recebida;

		if (diferenca_recebimento == 0) {
			System.out.println("\nRecebimento Concluído");
		} else if (diferenca_recebimento < 0) {
			System.out.println("\nRecebimento Excedeu em " + diferenca_recebimento + " " + unidade);

		} else if (diferenca_recebimento > 0) {
			System.out.println("\nRecebimento Incompleto, falta " + diferenca_recebimento + " " + unidade);
		}

		System.out.println("\nQuantidade Total a Carregar: " + total_quantidade_recebida + " " + unidade);
		System.out.println("Quantidade Total Carregada: " + total_quantidade_carregada + " " + unidade);

		double diferenca_carregamento = total_quantidade_recebida - total_quantidade_carregada;

		if (diferenca_carregamento == 0) {
			System.out.println("Carregamento Concluído");
		} else if (diferenca_carregamento < 0) {
			System.out.println("Carregamento Excedeu em " + diferenca_carregamento + " " + unidade);

		} else if (diferenca_carregamento > 0) {
			System.out.println("Carregamento Incompleto, falta " + diferenca_carregamento + " " + unidade);
		}
	}

}
