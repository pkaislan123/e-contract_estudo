
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
	
	
	public void alterar() {
		listarContratos();
		int pos, op;
		Contrato p;
		int id_contratante, id_safra, unidadeMedida;
		String tipos_unidades[] = { "Sacos", "Kgs" };
		double quantidadeContratada, quantidadeAtendida, valorPorUnidade;
		Safra safra;
		ArrayList<Contratante> compradores = new ArrayList<>();
		ArrayList<Contratante> vendedores = new ArrayList<>();
		Contratante corretor = null;
		if (listContratos.isEmpty()) {
			System.out.println("\n Não há Contratos até o momento!\n Operação cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Alterar cadastro ]==--");
		System.out.println("\n Digite a posição do cadastro que deseja alterar: ");
		pos = num.nextInt();

		if (isPosInvalida( pos)) {
			System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
			return;
		}
		
			p = listContratos.get(pos);
		
		imprimir(p.getId());

		System.out.println("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
			return;
		}
		System.out.println("\n Digite os novos dados");
		System.out.print(" Atualizar Compradores: \n");
	
		
		System.out.print(" Safra do Novo Contrato: \n");
		System.out.print(" Selecione o Id da Safra: ");
		do {
			gSafra.listarSafras();
			id_safra = num.nextInt();
		} while (id_safra < 0 || id_safra > listSafras.size());

		safra = listSafras.get(id_safra);
		p.setSafra(safra);
		
		System.out.print("\n Unidade de Medida ->  1 - Sacos | 2 - Kgs");
		unidadeMedida = num.nextInt();
		p.setUnidadeMedida(unidadeMedida);

		System.out.print("\n Quantidade de " + tipos_unidades[unidadeMedida - 1] + ":\n");
		quantidadeContratada = num.nextDouble();
		p.setQuantidadeContratada(quantidadeContratada);

		System.out.print("\n Valor por " + tipos_unidades[unidadeMedida - 1] + ":\n");
		valorPorUnidade = num.nextDouble();
		p.setValorPorUnidade(valorPorUnidade);

		
		System.out.println(" Cadastro alterado com sucesso!\n");

	}
	
	
	public boolean isPosInvalida( int pos) {
		boolean result = false;

			if (pos >= listContratos.size() || pos < 0) {
				result = true;
			}
		
		return result;
	}
	
	public void listarContratos() {
	
		for (Contrato ct : listContratos) {
			if (ct instanceof Contrato) {
				imprimir(listContratos.indexOf(ct));
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
			listContratos.remove(posConsulta);
			System.out.println("\n Contrato excluído com sucesso!\n");
		} else {
			System.out.println(" Exclusão cancelada");
		}
	}
	
	
	public void imprimir(int posConsulta) {
		String tipos_unidades[] = { "Sacos", "Kgs" };
		NumberFormat z = NumberFormat.getNumberInstance();
		Contrato ct = listContratos.get(posConsulta);
		ArrayList<Contratante> compradores = ct.getCompradores();
		ArrayList<Contratante> vendedores = ct.getVendedores();
		Contratante corretor = ct.getCorretor();

		String nome_compradores = "";
		for(Contratante comp : compradores) {
			nome_compradores +=  (comp.getNome());
			if(compradores.indexOf(comp) == compradores.size() -1) {
				
			}else
				nome_compradores += ", ";
		}
		String nome_vendedores = "";
		for(Contratante vend : vendedores) {
			nome_vendedores +=  (vend.getNome() );
			if(vendedores.indexOf(vend) == vendedores.size() -1) {
				
			}
			else {
				nome_vendedores += ", ";

			}
		}
		
		System.out.println("POSIÇÃO:" + listContratos.indexOf(ct) + " Descrição: " + z.format(ct.getQuantidadeContratada()) + " " +
				tipos_unidades[ct.getUnidadeMedida() ] + " de " + ct.getSafra().getProduto().getNome()
				+ " da safra " + ct.getSafra().getAnoPlantio() + "/" + ct.getSafra().getAnoColheita() +
				" no valor de " +  NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade()) +
				" por " + tipos_unidades[ct.getUnidadeMedida() ] + ", perfazendo um total de " +
				 NumberFormat.getCurrencyInstance(ptBr).format(ct.getValorPorUnidade() * ct.getQuantidadeContratada())
				 + ", entre " + nome_compradores + " X " + nome_vendedores + "; corretor: " + corretor.getNome()
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
		
		GerenciaCarga gCarga = new GerenciaCarga(listClientes, listContratos.get(posConsulta));
		gCarga.relatorio();
		
		GerenciaPagamento gPagamento = new GerenciaPagamento(listContratos.get(posConsulta));
		gPagamento.relatorio();
	}
	
	public void relatorio() {
		if (listContratos.isEmpty()) {
			System.out.println("\n Não há Contratos cadastradas!\n");
			return;
		}

		listarContratos();
		
		somatoriaGeral();
	}
	
	
	public void somatoriaGeral() {
		
		
		double total_quantidade_contratada = 0;
		double total_quantidade_recebida = 0;
		double total_quantidade_carregada = 0;
		String tipos_unidades[] = { "Sacos", "Kgs" };
		String unidade = "";
		double total_pago = 0;
		
		for (Contrato ct : listContratos) {
			unidade =  tipos_unidades[ct.getUnidadeMedida()];

			
			GerenciaCarga gCarga = new GerenciaCarga(listClientes, ct);
			
			GerenciaPagamento gPagamento = new GerenciaPagamento(ct);
				
			total_quantidade_contratada += ct.getQuantidadeContratada();
			total_quantidade_recebida += gCarga.getTotalRecebido();
			total_quantidade_carregada += gCarga.getTotalCarregado();
			total_pago += gPagamento.getTotalPago();
			
		
			
		}
		System.out.println("\n------------ Somatórias---------");

		System.out.println("\nQuantidade Total a Receber: "  + total_quantidade_contratada + " " + unidade );
		System.out.println("Quantidade Total Recebida: "  + total_quantidade_recebida + " " + unidade);
		
		
		double diferenca_recebimento = total_quantidade_contratada - total_quantidade_recebida;

		if (diferenca_recebimento == 0) {
			System.out.println("\nRecebimento Concluído");
		} else if (diferenca_recebimento < 0) {
			System.out.println("\nRecebimento Excedeu em " + diferenca_recebimento + " " + unidade);

		} else if (diferenca_recebimento > 0) {
			System.out.println("\nRecebimento Incompleto, falta " + diferenca_recebimento + " " + unidade);
		}
			
		System.out.println("\nQuantidade Total a Carregar: "  + total_quantidade_recebida+ " " + unidade );
		System.out.println("Quantidade Total Carregada: "  + total_quantidade_carregada  + " " + unidade);
		
		
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
