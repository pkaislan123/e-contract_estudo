
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
	private String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "", "Bi-Trem", "Trimi�o" };

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
			System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimi�o");
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
			System.out.println("\n N�o h� ve�culos cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclus�o de Veiculo ]==--");
		System.out.print(" Informe a posi��o do ve�culo que deseja EXCLUIR: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listVeiculo.size() || posConsulta < 0) {
			System.out.println("\n Posi��o inv�lida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o ve�culo listado? 1.Sim | 2.N�o");
		op = num.nextInt();
		if (op == 1) {
			listVeiculo.remove(posConsulta);
			System.out.println("\n Veiculo exclu�do com sucesso!\n");
		} else {
			System.out.println(" Exclus�o cancelada");
		}
	}

	public void relatorio() {
		if (listVeiculo.isEmpty()) {
			System.out.println("\n N�o h� consultas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Relat�rio de consultas ]==--");
		for (int i = 0; i < listVeiculo.size(); i++) {
			imprimir(i);
		}
	}

	public void imprimir(int posConsulta) {

		System.out.println("\n --== Consulta posi��o: " + posConsulta + " ==--");
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
			System.out.println("\n N�o h� cadastros at� o momento!\n Opera��o cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Alterar cadastro ]==--");
		System.out.println(" Selecione a op��o correspondente ao que deseja alterar");
		System.out.println("\n Digite a posi��o do cadastro que deseja alterar: ");
		pos = num.nextInt();

		if (isPosInvalida(pos)) {
			System.out.println("\n Posi��o inexistente!\n Opera��o cancelada.\n");
			return;
		}

		p = listVeiculo.get(pos);

		mostrar(p);

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.N�o");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Opera��o cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
		System.out.println("\n Tipo: ");
		do {
			System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimi�o");
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
			System.out.println("\n N�o h� consultas cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa consultas ]==--");
		System.out.print(" Informe a posi��o da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listVeiculo.size() || posConsulta < 0) {
			System.out.println("\n Posi��o inv�lida!\n");
			return;
		}
		imprimir(posConsulta);
	}

}
