
package controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import model.Cliente;
import model.Contratante;
import model.Contrato;
import model.Endereco;
import model.Produto;
import model.Safra;

public class GerenciaContratos {

	private Scanner num, str;
	private ArrayList<Safra> listSafras;
	private ArrayList<Cliente> listClientes;
	private ArrayList<Contrato> listContratos;
	private ArrayList<Produto> listProdutos;
	private Locale ptBr = new Locale("pt", "BR");

	private GerenciaSafra gSafra;
	private GerenciaPessoas gClientes;

	public GerenciaContratos(ArrayList<Contrato> listContratos, ArrayList<Cliente> listClientes,
			ArrayList<Safra> listSafras, ArrayList<Produto> listProdutos) {
		this.listSafras = listSafras;
		this.listContratos = listContratos;
		this.listClientes = listClientes;
		str = new Scanner(System.in);
		num = new Scanner(System.in);
		this.listProdutos = listProdutos;
		gSafra = new GerenciaSafra(listSafras, listProdutos);
		gClientes = new GerenciaPessoas(listClientes);
	}

	public void incluir() {
		int id_contratante, id_safra, unidadeMedida;
		String tipos_unidades[] = { "Sacos", "Kgs" };
		double quantidadeContratada, quantidadeAtendida, valorPorUnidade;
		Safra safra;
		ArrayList<Contratante> compradores = new ArrayList<>();
		ArrayList<Contratante> vendedores = new ArrayList<>();
		Contratante corretor = null;

		System.out.println("\n --==[ Cadastro de Contratos ]==--");

		System.out.print(" Compradores do Novo Contrato: \n");
		int op = 0;
		do {
			System.out.print(" Selecione o Id do(s) Contratante(s) deste Contrato: \n");
			boolean isValido = false;
			do {
				gClientes.listarContratantes();
				id_contratante = num.nextInt();
				if (gClientes.isPosInvalida(1, id_contratante)) {
					System.out.println("Posição Inválida!");
				} else
					isValido = true;
			} while (id_contratante < 0 || id_contratante > listClientes.size() || !isValido);

			compradores.add((Contratante) listClientes.get(id_contratante));
			System.out.println(" Deseja estipular outro Comprador ao contrato? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op != 2);

		op = 0;
		System.out.print(" Vendedores do Novo Contrato: \n");
		do {
			System.out.print(" Selecione o Id do(s) Vendedores(s) deste Contrato: \n");
			boolean isValido = false;
			do {
				gClientes.listarContratantes();
				id_contratante = num.nextInt();
				if (gClientes.isPosInvalida(1, id_contratante)) {
					System.out.println("Posição Inválida!");
				} else
					isValido = true;
			} while (id_contratante < 0 || id_contratante > listClientes.size() || !isValido);

			vendedores.add((Contratante) listClientes.get(id_contratante));
			System.out.println(" Deseja estipular outro Vendedor ao contrato? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op != 2);

		boolean isValido = false;
		System.out.print(" Corretor do Novo Contrato: \n");

		System.out.print(" Selecione o Id do Corretor deste Contrato: \n");
		do {

			gClientes.listarContratantes();
			id_contratante = num.nextInt();
			if (gClientes.isPosInvalida(1, id_contratante)) {
				System.out.println("Posição Inválida!");
			} else {
				isValido = true;

				corretor = (Contratante) listClientes.get(id_contratante);

			}
		} while (id_contratante < 0 || id_contratante > listClientes.size() || !isValido);

		System.out.print(" Safra do Novo Contrato: \n");
		System.out.print(" Selecione o Id da Safra: ");
		do {
			gSafra.listarSafras();
			id_safra = num.nextInt();
		} while (id_safra < 0 || id_safra > listSafras.size());

		safra = listSafras.get(id_safra);

		System.out.print("\n Unidade de Medida ->  1 - Sacos | 2 - Kgs");
		unidadeMedida = num.nextInt();

		System.out.print("\n Quantidade de " + tipos_unidades[unidadeMedida - 1] + ":\n");
		quantidadeContratada = num.nextDouble();

		System.out.print("\n Valor por " + tipos_unidades[unidadeMedida - 1] + ":\n");
		valorPorUnidade = num.nextDouble();

		listContratos.add(new Contrato(listContratos.size() + 1, unidadeMedida, compradores, vendedores, corretor,
				safra, quantidadeContratada, valorPorUnidade, 0));
		System.out.println(" Contrato cadastrado com sucesso!\n");

	}
	
	public void listarContratos() {
	
		for (Contrato ct : listContratos) {
			if (ct instanceof Contrato) {
				imprimir(ct.getId());
			}
		}
	}
	
	
	public void excluir() {
		int posConsulta, op;
		listarContratos();

		if (listContratos.isEmpty()) {
			System.out.println("\n Não há Contratos cadastrados!\n");
			return;
		}
		System.out.println("\n --==[ Exclusão de Contrato ]==--");
		System.out.print(" Informe a posição do Contrato que deseja EXCLUIR: ");
		posConsulta = num.nextInt() ;

		if (posConsulta > listContratos.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);

		System.out.println("\n Deseja EXCLUIR o Contrato listado? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			listContratos.remove(posConsulta-1);
			System.out.println("\n Contrato excluído com sucesso!\n");
		} else {
			System.out.println(" Exclusão cancelada");
		}
	}
	
	
	public void imprimir(int posConsulta) {
		String tipos_unidades[] = { "Sacos", "Kgs" };
		NumberFormat z = NumberFormat.getNumberInstance();
		Contrato ct = listContratos.get(posConsulta-1);
		ArrayList<Contratante> compradores = ct.getCompradores();
		ArrayList<Contratante> vendedores = ct.getCompradores();
		Contratante corretor = ct.getCorretor();

		String nome_compradores = "";
		for(Contratante comp : compradores) {
			nome_compradores +=  (comp.getNome());
			if(compradores.indexOf(comp) == compradores.size())
				nome_compradores += ", ";
		}
		String nome_vendedores = "";
		for(Contratante vend : vendedores) {
			nome_vendedores +=  (vend.getNome() );
			if(vendedores.indexOf(vend) == vendedores.size())
				nome_vendedores += ", ";
		}
		
		System.out.println("ID: " + ct.getId() + " Descrição: " + z.format(ct.getQuantidadeContratada()) + " " +
				tipos_unidades[ct.getUnidadeMedida() ] + " de " + ct.getSafra().getProduto().getNome()
				+ " da safra " + ct.getSafra().getAnoPlantio() + "/" + ct.getSafra().getAnoColheita() +
				" no valor de " +  NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade()) +
				" por " + tipos_unidades[ct.getUnidadeMedida() ] + ", perfazendo um total de " +
				 NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade() * ct.getQuantidadeContratada())
				 + ", entre " + nome_compradores + " X " + nome_vendedores + ", corretor: " + corretor.getNome()
				);
	}
	
	public void consultar() {
		int posConsulta = 0;
		listarContratos();

		if (listContratos.isEmpty()) {
			System.out.println("\n Não há Contratos cadastradas!\n");
			return;
		}

		System.out.println("\n --==[ Pesquisa Contratos ]==--");
		System.out.print(" Informe a posição da consulta: ");
		posConsulta = num.nextInt();

		if (posConsulta >= listContratos.size() || posConsulta < 0) {
			System.out.println("\n Posição inválida!\n");
			return;
		}
		imprimir(posConsulta);
	}

}
