
package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Veiculo;

public class GerenciaVeiculo {

	private ArrayList<Veiculo> listVeiculo;
	private Scanner num, str;
	private String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "", "Bi-Trem", "Trimião" };

	public GerenciaVeiculo(ArrayList<Veiculo> listVeiculo) {
		this.listVeiculo = listVeiculo;
		str = new Scanner(System.in);
		num = new Scanner(System.in);

	}

	public void incluir() {

		int tipo;
		String placa;

		System.out.println("\n --==[ Cadastro de Veiculos ]==--");

		do {
			System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
			tipo = num.nextInt();
		} while (tipo <= 0 || tipo > 5);
		System.out.print(" Placa: ");
		placa = str.next();

		listVeiculo.add(new Veiculo(listVeiculo.size() + 1, tipo, placa));
		System.out.println(" Veiculo cadastrado com sucesso!\n");
	}

	public void excluir() {
		int posConsulta, op;

		if (listVeiculo.isEmpty()) {
			System.out.println("\n Não há veículos cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclusão de Veiculo ]==--");
		System.out.print(" Informe a posição do veículo que deseja EXCLUIR: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listVeiculo.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o veículo listado? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			listVeiculo.remove(posConsulta);
			System.out.println("\n Veiculo excluído com sucesso!\n");
		} else {
			System.out.println(" Exclusão cancelada");
		}
	}

	public void relatorio() {
		if (listVeiculo.isEmpty()) {
			System.out.println("\n Não há consultas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relatório de consultas ]==--");
		for (int i = 0; i < listVeiculo.size(); i++) {
			imprimir(i);
		}
	}

	public void imprimir(int posConsulta) {

		System.out.println("\n --== Consulta posição: " + posConsulta + " ==--");
		System.out.println(" Tipo: " + tipo_veiculo[listVeiculo.get(posConsulta).getTipo() + 1]);
		System.out.println(" Placa: " + listVeiculo.get(posConsulta).getPlaca());

	}

	private void mostrar(Veiculo v) {

		System.out.println("\n Tipo: " + tipo_veiculo[v.getTipo() + 1]);
		System.out.println(" Placa: " + v.getPlaca());

	}

	public void alterar() {

		String placa;
		int pos, op, tipo;
		Veiculo p;

		if (listVeiculo.isEmpty()) {
			System.out.println("\n Não há cadastros até o momento!\n Operação cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Alterar cadastro ]==--");
		System.out.println(" Selecione a opção correspondente ao que deseja alterar");
		System.out.println("\n Digite a posição do cadastro que deseja alterar: ");
		pos = num.nextInt();

		if (isPosInvalida(pos)) {
			System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
			return;
		}

		p = listVeiculo.get(pos);

		mostrar(p);

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
		System.out.println("\n Tipo: ");
		do {
			System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
			tipo = num.nextInt();
		} while (tipo <= 0 || tipo > 5);
		System.out.print(" Placa: ");
		placa = str.next();

		System.out.println(" Cadastro alterado com sucesso!\n");
	}

	public boolean isPosInvalida(int pos) {
		boolean result = false;

		if (pos >= listVeiculo.size() || pos < 0) {
			result = true;
		}

		return result;
	}

	public void consultar() {
		int posConsulta = 0;

		if (listVeiculo.isEmpty()) {
			System.out.println("\n Não há consultas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa consultas ]==--");
		System.out.print(" Informe a posição da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listVeiculo.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);
	}

}
