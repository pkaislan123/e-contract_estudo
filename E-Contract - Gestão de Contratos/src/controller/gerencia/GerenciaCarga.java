
package controller.gerencia;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import controller.DAO.CargaDAO;
import model.Carga;
import model.Cliente;
import model.Contrato;
import model.Produto;
import model.Veiculo;

public class GerenciaCarga {

	private Scanner num, str;
	private Contrato contrato;
	private GerenciaClientes gClientes;
	Locale ptBr = new Locale("pt", "BR");

	public GerenciaCarga(GerenciaClientes gClientes, Contrato contrato) {
		this.contrato = contrato;
		str = new Scanner(System.in);
		num = new Scanner(System.in);
		this.gClientes = gClientes;

	}

	public void incluir(int id_contrato) {

		double pesoCarga;
		int id_veiculo;
		LocalDate data_rec = null;
		System.out.println("\n --==[ Cadastro de Carga ]==--");

		System.out.print(" Peso da Carga: ");
		pesoCarga = num.nextDouble();
		System.out.print(" Veículo: ");
		System.out.print(" Selecione o Id do Veículo: \n");
		Veiculo veiculo = null;
		do {
			gClientes.listarVeiculos();
			id_veiculo = num.nextInt();
			veiculo = gClientes.posicaoVeiculoValida(id_veiculo);
			if (veiculo == null)
				System.out.println("Posição Inválida!");

		} while (veiculo == null);

		boolean data_valida = false;
		do {
			System.out.print(" Data do Recebimento: Ex: 19/06/2021\n");
			try {
				data_rec = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				data_valida = true;
			} catch (Exception e) {
				System.out.println("Data Invalida!");
			}

		} while (!data_valida);

		Carga carga = new Carga(pesoCarga, veiculo, data_rec, null);
		int inserir = new CargaDAO().incluir(carga, id_contrato);
		if (inserir > 0) {
			System.out.println(" Carga cadastrado com sucesso!\n");

		}

	}

	public void alterar(int id_contrato) {
		ArrayList<Carga> listCarga = new CargaDAO().relatorio(id_contrato);

		if (listCarga != null && listCarga.size() > 0) {
			int id;

			listarCargas(listCarga);
			System.out.println("---Digite o ID do Carga para Alterar:");
			id = num.nextInt();

			Carga carga = posicaoValida(listCarga, id);
			if (carga != null) {

				System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");

				int op = num.nextInt();
				if (op != 1) {
					System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
					return;
				}

				System.out.println("\n Digite os novos dados");

				System.out.println("Alterar Peso da Carga: ? Peso atual: " + carga.getPesoCarga());
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					System.out.print(" Peso da Carga: ");
					carga.setPesoCarga(num.nextDouble());
				}

				System.out.println("Alterar Veiculo da Carga: ? Veiculo atual: " + carga.getVeiculo().getPlaca());
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					Veiculo veiculo = null;
					do {
						gClientes.listarVeiculos();
						int id_veiculo = num.nextInt();
						veiculo = gClientes.posicaoVeiculoValida(id_veiculo);
						if (veiculo == null)
							System.out.println("Posição Inválida!");

					} while (veiculo == null);

				}

				System.out.println("Alterar Data de Recebimento da Carga: ? Data atual: "
						+ formatarData(carga.getDataRecebimento()));
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				LocalDate data_rec = null;
				if (op == 1) {
					boolean data_valida = false;
					do {
						System.out.print(" Data do Recebimento: Ex: 19/06/2021\n");
						try {
							data_rec = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
							data_valida = true;
						} catch (Exception e) {
							System.out.println("Data Invalida!");
						}

					} while (!data_valida);
					carga.setDataRecebimento(data_rec);
				}

				System.out.println("Alterar Data de Carregamento da Carga: ? Data atual: "
						+ formatarData(carga.getDataCarregamento()));
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				LocalDate data_carg = null;

				if (op == 1) {
					boolean data_valida = false;
					do {
						System.out.print(" Data do Carregamento: Ex: 19/06/2021\n");
						try {
							data_carg = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
							data_valida = true;
						} catch (Exception e) {
							System.out.println("Data Invalida!");
						}

					} while (!data_valida);
					carga.setDataCarregamento(data_carg);
				}

				boolean alterar = new CargaDAO().alterar(carga);
				if (alterar) {
					System.out.println("Carga Alterada");
				}

			} else {
				System.out.println(" ID Inválido!");
			}
		} else {
			System.out.println(" Não há Cargas Cadastrados!");
		}
	}

	public void relatorio(int id_contrato) {
		String tipos_unidades[] = { "Sacos", "Kgs" };
		String unidade = tipos_unidades[contrato.getUnidadeMedida()];

		double quantidade_recebida = 0;
		double quantidade_carregada = 0;
		double quantidade_total = contrato.getQuantidadeContratada();
		if (new CargaDAO().isVazio() <= 0) {
			System.out.println("\n Não há Cargas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relatório de Cargas ]==--");
		ArrayList<Carga> cargas = new CargaDAO().relatorio(id_contrato);
		for (Carga c : cargas) {
			detalhar(c);
			quantidade_recebida += c.getPesoCarga();
			if (c.getDataCarregamento() != null) {
				quantidade_carregada += quantidade_recebida;

			}
		}

		System.out.println("\n------------ Somatórias---------");

		System.out.println("\nQuantidade Total a Receber: " + quantidade_total + " " + unidade);
		System.out.println("Quantidade Total Recebida: " + quantidade_recebida + " " + unidade);

		double diferenca_recebimento = quantidade_total - quantidade_recebida;

		if (diferenca_recebimento == 0) {
			System.out.println("Recebimento Concluído");
		} else if (diferenca_recebimento < 0) {
			System.out.println("Recebimento Excedeu em " + diferenca_recebimento + " " + unidade);

		} else if (diferenca_recebimento > 0) {
			System.out.println("Recebimento Incompleto, falta " + diferenca_recebimento + " " + unidade);
		}

		System.out.println("\nQuantidade Total a Carregar: " + quantidade_recebida + " " + unidade);
		System.out.println("Quantidade Total Carregada: " + quantidade_carregada + " " + unidade);

		double diferenca_carregamento = quantidade_recebida - quantidade_carregada;

		if (diferenca_carregamento == 0) {
			System.out.println("Carregamento Concluído");
		} else if (diferenca_carregamento < 0) {
			System.out.println("Carregamento Excedeu em " + diferenca_carregamento + " " + unidade);

		} else if (diferenca_carregamento > 0) {
			System.out.println("Carregamento Incompleto, falta " + diferenca_carregamento + " " + unidade);
		}

	}

	public void listarCargas(ArrayList<Carga> listCarga) {
		for (Carga ca : listCarga) {
			System.out.println("ID: " + ca.getId() + " Peso: " + ca.getPesoCarga() + " Placa: "
					+ ca.getVeiculo().getPlaca() + " Data Recebimento: " + formatarData(ca.getDataRecebimento())
					+ " Data Carregamento: " + formatarData(ca.getDataCarregamento()));
		}
	}

	public void detalhar(Carga c) {

		System.out.println("\n --== ID: " + c.getId() + " ==--");
		System.out.println(" Peso: " + c.getPesoCarga());
		System.out.println(" Veiculo: " + c.getVeiculo().getPlaca());
		System.out.println(" Data Recebimento: " + formatarData(c.getDataRecebimento()));
		System.out.println(" Data Carregamento: " + formatarData(c.getDataCarregamento()));

	}

	public Carga posicaoValida(ArrayList<Carga> cargas, int id) {

		Carga carga = null;
		boolean contem = false;
		Iterator<Carga> i = cargas.iterator();
		while (!contem && i.hasNext()) {
			carga = i.next();

			if (carga.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return carga;
		else
			return null;

	}

	public void excluir(int id_contrato) {
		ArrayList<Carga> listCargas = new CargaDAO().relatorio(id_contrato);

		if (listCargas != null && listCargas.size() > 0) {
			int id;

			listarCargas(listCargas);
			System.out.println("-Digite o ID do Carga para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listCargas.size()) {
				Carga carga = posicaoValida(listCargas, id);
				if (carga != null) {
					detalhar(carga);

					System.out.println("Deseja excluir a Carga selecionada: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new CargaDAO().excluir(id)) {
							System.out.println(" Carga Excluída!");

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

	public void consultar(int id_contrato) {
		CargaDAO gerenciar = new CargaDAO();
		ArrayList<Carga> listCargas = gerenciar.relatorio(id_contrato);

		if (listCargas != null && listCargas.size() > 0) {
			listarCargas(listCargas);

			System.out.println("-Digite o ID da Carga para Detalhar:");
			int id = num.nextInt();

			Carga carga = posicaoValida(listCargas, id);
			if (carga != null) {

				detalhar(carga);

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há Cargas Cadastradas!");

		}
	}

	public String formatarData(LocalDate data) {
		if (data != null)
			return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		else
			return "";
	}

	public double getTotalCarregado() {
		double quantidade_carregada = 0;
		double quantidade_recebida = 0;
		for (Carga c : new CargaDAO().relatorio()) {
			quantidade_recebida += c.getPesoCarga();
			if (c.getDataCarregamento() != null) {
				quantidade_carregada += quantidade_recebida;

			}
		}
		return quantidade_carregada;
	}

	public double getTotalRecebido() {
		double quantidade_recebida = 0;
		ArrayList<Carga> listCarga = new CargaDAO().relatorio();
		for (Carga c : listCarga) {
			quantidade_recebida += c.getPesoCarga();

		}
		return quantidade_recebida;
	}

	public double getTotalRecebido(int id_contrato) {
		return new CargaDAO().quantidadeRecebida(id_contrato);
	}
}
