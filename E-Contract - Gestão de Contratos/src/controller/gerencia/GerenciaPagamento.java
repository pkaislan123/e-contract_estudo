
package controller.gerencia;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import controller.DAO.PagamentoDAO;
import model.Pagamento;
import model.Produto;
import model.Cliente;
import model.Contrato;
import model.Veiculo;

public class GerenciaPagamento {

	private Scanner num, str;
	private Contrato contrato;
	Locale ptBr = new Locale("pt", "BR");

	public GerenciaPagamento(Contrato contrato) {
		this.contrato = contrato;
		str = new Scanner(System.in);
		num = new Scanner(System.in);

	}

	public void incluir(int id_contrato) {

		double valorPagamento;
		LocalDate data_pag = null;
		System.out.println("\n --==[ Cadastro de Pagamento ]==--");

		System.out.print(" Valor: ");
		valorPagamento = num.nextDouble();
		boolean data_valida = false;
		do {
			System.out.print(" Data do Pagamento: Ex: 19/06/2021\n");
			try {
				data_pag = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				data_valida = true;
			} catch (Exception e) {
				System.out.println("Data Invalida!");
			}

		} while (!data_valida);

		Pagamento pagamento = new Pagamento(valorPagamento, data_pag);
		int inserir = new PagamentoDAO().incluir(pagamento, id_contrato);
		if (inserir > 0)
			System.out.println(" Pagamento cadastrado com sucesso!\n");
	}

	public Pagamento posicaoValida(ArrayList<Pagamento> pagamentos, int id) {

		Pagamento pagamento = null;
		boolean contem = false;
		Iterator<Pagamento> i = pagamentos.iterator();
		while (!contem && i.hasNext()) {
			pagamento = i.next();

			if (pagamento.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return pagamento;
		else
			return null;

	}

	public void excluir(int id_contrato) {
		ArrayList<Pagamento> listPagamentos = new PagamentoDAO().relatorio(id_contrato);

		if (listPagamentos != null && listPagamentos.size() > 0) {
			int id;

			listarPagamentos(listPagamentos);
			System.out.println("-Digite o ID do Pagamento para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listPagamentos.size()) {
				Pagamento pagamento = posicaoValida(listPagamentos, id);
				if (pagamento != null) {
					imprimir(pagamento);

					System.out.println("Deseja excluir o pagamento selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new PagamentoDAO().excluir(id)) {
							System.out.println(" Pagamento Excluído!");

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
			System.out.println(" Não há Pagamentos Cadastrados!");
		}

	}

	public void consultar(int id_contrato) {
		PagamentoDAO gerenciar = new PagamentoDAO();
		ArrayList<Pagamento> listPagamentos = gerenciar.relatorio(id_contrato);

		if (listPagamentos != null && listPagamentos.size() > 0) {
			listarPagamentos(listPagamentos);

			System.out.println("-Digite o ID do Pagamento para Detalhar:");
			int id = num.nextInt();

			Pagamento pagamento = posicaoValida(listPagamentos, id);
			if (pagamento != null) {

				imprimir(pagamento);

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há produtos Cadastrados!");

		}
	}

	public void alterar(int id_contrato) {
		ArrayList<Pagamento> listPagamentos = new PagamentoDAO().relatorio();

		if (listPagamentos != null && listPagamentos.size() > 0) {
			int id;

			listarPagamentos(listPagamentos);
			System.out.println("-Digite o ID do pagamento para Alterar:");
			id = num.nextInt();

			Pagamento pagamento = posicaoValida(listPagamentos, id);
			if (pagamento != null) {

				System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");

				int op = num.nextInt();
				if (op != 1) {
					System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
					return;
				}

				System.out.println("Alterar Valor do Pagamento: ? Valor atual: " + pagamento.getValor());
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				LocalDate data_rec = null;
				if (op == 1) {
					System.out.print(" Valor: ");
					pagamento.setValor(num.nextDouble());
				}

				System.out.println(
						"Alterar Data do Pagamento: ? Valor atual: " + formatarData(pagamento.getDataPagamento()));
				System.out.println("\n1.Sim | 2.Não");
				op = num.nextInt();
				if (op == 1) {
					LocalDate data_pag = null;

					boolean data_valida = false;
					do {
						System.out.print(" Data do Pagamento: Ex: 19/06/2021\n");
						try {
							data_pag = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
							data_valida = true;
							pagamento.setDataPagamento(data_pag);
						} catch (Exception e) {
							System.out.println("Data Invalida!");
						}

					} while (!data_valida);

				}

				boolean alterar = new PagamentoDAO().alterar(pagamento);
				if (alterar) {
					System.out.println("Pagamento Alterado!");
				}

			} else {
				System.out.println(" ID Inválido!");
			}
		} else {
			System.out.println(" Não há Pagamentos Cadastrados!");
		}
	}

	public void relatorio(int id_contrato) {
		ArrayList<Pagamento> listPagamento = new PagamentoDAO().relatorio(id_contrato);

		if (listPagamento.isEmpty()) {
			System.out.println("\n Não há Pagamentos cadastradas!\n");
			return;
		}
		double valorTotalPagamentos = 0;

		System.out.println("\n --==[ Relatório de Pagamentos ]==--");
		for (Pagamento pag : listPagamento) {
			valorTotalPagamentos += pag.getValor();
			imprimir(pag);
		}

		double valorTotalPagar = contrato.getValorPorUnidade() * contrato.getQuantidadeContratada();
		System.out.println("\nValor Total a Pagar: " + NumberFormat.getCurrencyInstance(ptBr).format(valorTotalPagar));
		System.out.println("Valor Total Pago: " + NumberFormat.getCurrencyInstance(ptBr).format(valorTotalPagamentos));
		double diferenca = valorTotalPagar - valorTotalPagamentos;

		if (diferenca == 0) {
			System.out.println("Pagamento Concluído");
		} else if (diferenca < 0) {
			System.out.println("Excedeu em " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

		} else if (diferenca > 0) {
			System.out.println("Incompleto, falta " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

		}

	}

	public void listarPagamentos(ArrayList<Pagamento> listPagamento) {
		for (Pagamento ca : listPagamento) {
			System.out.println("ID: " + ca.getId() + " Valor: " + ca.getValor() + " Data Pagamento: "
					+ formatarData(ca.getDataPagamento()));
		}
	}

	public void imprimir(Pagamento pag) {

		System.out.println("\n --== Consulta posição: " + pag.getId() + " ==--");
		System.out.println(" Valor: " + NumberFormat.getCurrencyInstance(ptBr).format(pag.getValor()));
		System.out.println(" Data Pagamento: " + formatarData(pag.getDataPagamento()));

	}

	public double getTotalPago() {
		ArrayList<Pagamento> listPagamento = new PagamentoDAO().relatorio();

		double total_pago = 0;

		for (int i = 0; i < listPagamento.size(); i++) {
			total_pago += listPagamento.get(i).getValor();

		}
		return total_pago;
	}

	public String formatarData(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}
