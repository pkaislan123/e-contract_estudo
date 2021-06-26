package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Endereco;
import model.Motorista;
import model.Contratante;
import model.Transportadora;
import model.Veiculo;
import model.Cliente;
import model.Contratante;

public class GerenciaPessoas {

	private ArrayList<Cliente> listClientes;
	private Scanner num, str;

	public GerenciaPessoas(ArrayList<Cliente> listClientes) {

		this.listClientes = listClientes;
		num = new Scanner(System.in);
		str = new Scanner(System.in);

	}

	public void incluir() {
		int tipoCliente, rntrc;
		Endereco[] end;
		ArrayList<Veiculo> veiculos;

		String nome, cpf, cnpj, ie;

		System.out.println("\n --==[ Cadastro ]==--");
		System.out.println(" Selecione a opção correspondente ao tipo de cadastro que deseja realizar");
		tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
			return;
		}

		System.out.println(" \n Informe os dados:");
		System.out.println(" Nome: ");
		nome = str.nextLine();
		System.out.println(" Inscrição Estadual: ");
		ie = str.nextLine();
		if (tipoCliente == 1) {
			System.out.println(" CNPJ: ");
			cnpj = str.nextLine();

			end = cadastrarEnderecos();

			listClientes.add(new Contratante(listClientes.size() + 1, tipoCliente, nome, ie, end, cnpj));
		} else if (tipoCliente == 2) {
			System.out.println(" CPF: ");
			cpf = str.nextLine();
			System.out.println(" RNTRC: ");
			rntrc = num.nextInt();

			end = cadastrarEnderecos();
			veiculos = cadastrarVeiculos();

			listClientes.add(new Motorista(listClientes.size() + 1, tipoCliente, nome, ie, end, cpf, rntrc, veiculos));
		} else if (tipoCliente == 3) {
			System.out.println(" CNPJ: ");
			cnpj = str.nextLine();
			end = cadastrarEnderecos();

			veiculos = cadastrarVeiculos();

			listClientes.add(new Transportadora(listClientes.size() + 1, tipoCliente, nome, ie, end, cnpj, veiculos));
		}

		System.out.println("\n Cadastro realizado com sucesso!\n");
	}

	public void consultar() {
		int tipoCliente, pos, op;
		Cliente p;

		if (isAllEmpty()) {
			System.out.println("\n Não há cadastros no momento\nOperação Cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Consultar cadastro ]==--");
		System.out.println(" Selecione a opção correspondente ao que deseja consultar");
		tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação cancelada.\n");
			return;
		}

		if (listPisEmpty(tipoCliente)) {
			return;
		}

		if (tipoCliente == 1)
			listarContratantes();
		else if (tipoCliente == 2)
			listarMotoristas();
		else if (tipoCliente == 3)
			listarTransportadora();

		System.out.println("\n Digite a posição do cadastro que deseja consultar: ");
		pos = num.nextInt();

		if (isPosInvalida(tipoCliente, pos)) {
			System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
			return;
		}

		if (tipoCliente == 1) {
			p = (Contratante) listClientes.get(pos);
		} else if (tipoCliente == 2) {
			p = (Motorista) listClientes.get(pos);
		} else {
			p = (Transportadora) listClientes.get(pos);
		}
		mostrar(p);
	}

	public void alterar() {

		int tipoPessoa, pos, op;
		Cliente cli;

		if (isAllEmpty()) {
			System.out.println("\n Não há cadastros até o momento!\n Operação cancelada.\n");
			return;
		}

		System.out.println("\n --==[ Alterar cadastro ]==--");
		System.out.println(" Selecione a opção correspondente ao que deseja alterar");
		tipoPessoa = tipoCliente();
		if (tipoPessoa == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoPessoa != 1 && tipoPessoa != 2 && tipoPessoa != 3) {
			System.out.println("\n Opção inválida!\n Operação cancelada.\n");
			return;
		}
		if (listPisEmpty(tipoPessoa)) {
			return;
		}

		if (tipoPessoa == 1)
			listarContratantes();
		else if (tipoPessoa == 2)
			listarMotoristas();
		else if (tipoPessoa == 3) {
			listarTransportadora();
		}
		System.out.println("\n Digite a posição do cadastro que deseja alterar: ");
		pos = num.nextInt();

		if (isPosInvalida(tipoPessoa, pos)) {
			System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
			return;
		}
		if (tipoPessoa == 1) {
			cli = (Contratante) listClientes.get(pos);
		} else if (tipoPessoa == 2) {
			cli = (Motorista) listClientes.get(pos);
		} else {
			cli = (Transportadora) listClientes.get(pos);

		}
		mostrar(cli);

		System.out.print("\n Deseja alterar este cadastro? 1.Sim | 2.Não");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Operação cancelada, retornando ao menu anterior...\n");
			return;
		}

		System.out.println("Alterar Nome: ?(Atual: " + cli.getNome() + ")");
		System.out.println("\n1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			System.out.println("\n Nome:");
			cli.setNome(str.nextLine());
		}

		System.out.println("Alterar IE: ?(Atual: " + cli.getIe() + ")");
		System.out.println("\n1.Sim | 2.Não");
		op = num.nextInt();
		if (op == 1) {
			System.out.println("\n Inscrição Estadual:");
			cli.setIe(str.nextLine());
		}

		if (cli instanceof Contratante) {

			System.out.println("Alterar CNPJ: ?(Atual: " + ((Contratante) listClientes.get(pos)).getCnpj() + ")");
			System.out.println("\n1.Sim | 2.Não");
			op = num.nextInt();
			if (op == 1) {
				System.out.println("\n CNPJ:");
				((Contratante) listClientes.get(pos)).setCnpj(str.nextLine());
			}
			System.out.println("\n -Endereço");
			System.out.print("Deseja alterar seus endereços? - 1 - SIM | 2 - NÃO");
			if (num.nextInt() == 1)
				editarEndereco(pos);

		} else if (cli instanceof Motorista) {

			System.out.println("Alterar CPF: ?(Atual: " + ((Motorista) listClientes.get(pos)).getCpf() + ")");
			System.out.println("\n1.Sim | 2.Não");
			op = num.nextInt();
			if (op == 1) {
				System.out.println("\n CPF:");
				((Motorista) listClientes.get(pos)).setCpf(str.nextLine());
			}

			System.out.println("Alterar RNTRC: ?(Atual: " + ((Motorista) listClientes.get(pos)).getRntrc() + ")");
			System.out.println("\n1.Sim | 2.Não");
			op = num.nextInt();
			if (op == 1) {
				System.out.println("\n RNTRC:");
				((Motorista) listClientes.get(pos)).setRntrc(num.nextInt());
			}

			System.out.println("\n -Endereço");
			System.out.println("Deseja alterar seus endereços? - 1 - SIM | 2 - NÃO");
			if (num.nextInt() == 1)
				editarEndereco(pos);
			System.out.println("\n -Veículos");
			System.out.println("Deseja alterar seus veículos? - 1 - SIM | 2 - NÃO");
			if (num.nextInt() == 1)
				editarVeiculoMotorista(pos);
		} else {

			System.out.println("Alterar CNPJ: ?(Atual: " + ((Transportadora) listClientes.get(pos)).getCnpj() + ")");
			System.out.println("\n1.Sim | 2.Não");
			op = num.nextInt();
			if (op == 1) {
				System.out.println("\n CNPJ:");
				((Transportadora) listClientes.get(pos)).setCnpj(str.nextLine());
			}

			System.out.println("\n -Endereço");
			System.out.println("Deseja alterar seus endereços? - 1 - SIM | 2 - NÃO");
			if (num.nextInt() == 1)
				editarEndereco(pos);
			System.out.println("\n -Veículos");
			System.out.println("Deseja alterar seus veículos? - 1 - SIM | 2 - NÃO");
			if (num.nextInt() == 1)
				editarVeiculoTransportadora(pos);
		}

		System.out.println("\n Cadastro alterado com sucesso!\n");
	}

	public void excluir() {
		int tipoCliente, pos, op;
		Cliente p;

		if (isAllEmpty()) {
			System.out.println("\n NÃo há cadastros\nOperação Cancelada\n");
			return;
		}

		System.out.println("\n --==[ Excluir cadastro ]==--");
		System.out.println(" Selecione a operação correspondente ao que deseja excluir");
		tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2) {
			System.out.println("\n Opção Inválida!\n Operação cancelada.\n");
			return;
		}
		if (listPisEmpty(tipoCliente)) {
			return;
		}

		if (tipoCliente == 1)
			listarContratantes();
		else if (tipoCliente == 2)
			listarMotoristas();
		else if (tipoCliente == 3) {
			listarTransportadora();
		}

		System.out.println("\n Digite a posição do cadastro que deseja excluir: ");
		pos = num.nextInt();

		if (isPosInvalida(tipoCliente, pos)) {
			System.out.println("\n Posição inexistente!\n Operação cancelada.\n");
			return;
		}
		if (tipoCliente == 1) {
			p = (Contratante) listClientes.get(pos);
		} else if (tipoCliente == 2) {
			p = (Motorista) listClientes.get(pos);
		} else {
			p = (Transportadora) listClientes.get(pos);

		}
		mostrar(p);

		System.out.println("\n Excluir o cadastro listado? 1.Sim | 2.NÃ£o");
		op = num.nextInt();
		if (op != 1) {
			System.out.println("\n Exclusãoo cancelada!\n");
			return;
		} else {
			listClientes.remove(pos);

		}
		System.out.println("\n Cadastro excluido com sucesso!\n");
	}

	public void relatorio() {
		int tipoCliente, pos, op;
		Cliente p;

		if (isAllEmpty()) {
			System.out.println("\n Não há cadastros até o momento\nOperação Cancelada\n");
			return;
		}

		System.out.println("\n --==[ Relatório ]==--");
		for (int i = 0; i < listClientes.size(); i++) {
			mostrar(listClientes.get(i));
		}

	}

	private void mostrar(Cliente c) {

		System.out.println("\n Nome: " + c.getNome());
		System.out.println(" IE: " + c.getIe());
		if (c instanceof Contratante) {
			Contratante contratante = (Contratante) c;
			System.out.println(" CNPJ: " + contratante.getCnpj());
			System.out.println(" --== Endereço(s) ==--");
			mostrarEndereco(contratante.getEndereco());
		} else if (c instanceof Motorista) {
			Motorista motorista = (Motorista) c;
			System.out.println(" CPF: " + motorista.getCpf());
			System.out.println(" RNTRC: " + motorista.getRntrc());
			mostrarEndereco(motorista.getEndereco());
			mostrarVeiculos(motorista.getVeiculos());

		} else if (c instanceof Transportadora) {
			Transportadora transportador = (Transportadora) c;
			System.out.println(" CNPJ: " + transportador.getCnpj());
			mostrarEndereco(transportador.getEndereco());
			mostrarVeiculos(transportador.getVeiculos());

		}

	}

	private void mostrarEndereco(Endereco[] endereco) {
		for (int i = 0; i < endereco.length; i++) {
			if (endereco[i] != null) {
				System.out.println("\n POSIÇÃO: " + i);
				System.out.println(" Logradouro: " + endereco[i].getLogradouro());
				System.out.println(" Número: " + endereco[i].getNumero());
				System.out.println(" Bairro: " + endereco[i].getBairro());
				System.out.println(" Municipio: " + endereco[i].getMunicipio());
				System.out.println(" Estado: " + endereco[i].getEstado());
				System.out.println(" CEP: " + endereco[i].getCep());
			}
		}
	}

	private int tipoCliente() {

		System.out.println(" 1 - Contratante");
		System.out.println(" 2 - Motorista");
		System.out.println(" 3 - Transportadora");
		System.out.println(" 0 - Sair");

		System.out.println(" Opção:");
		return num.nextInt();

	}

	public boolean isAllEmpty() {
		boolean result = false;

		if (listClientes.isEmpty() && listClientes.isEmpty()) {
			result = true;
		}
		return result;
	}

	public boolean listPisEmpty(int tipoP) {
		boolean result = false;
		if (listClientes.isEmpty() && tipoP == 1) {
			System.out.println(" Não há Contratantes cadastrados!\n");
			result = true;
		}
		if (listClientes.isEmpty() && tipoP == 2) {
			System.out.println(" Não há Motoristas cadastrads!\n");
			result = true;
		}
		if (listClientes.isEmpty() && tipoP == 3) {
			System.out.println(" Não há Tranportadoras cadastradas!\n");
			result = true;
		}

		return result;
	}

	public boolean isPosInvalida(int tipoP, int pos) {
		boolean result = false;

		if (tipoP == 1) {
			if (pos >= listClientes.size() || pos < 0) {
				result = true;
			}
		} else if (tipoP == 2) {
			if (pos >= listClientes.size() || pos < 0) {
				result = true;
			}
		} else if (tipoP == 3) {
			if (pos >= listClientes.size() || pos < 0) {
				result = true;
			}
		}
		return result;
	}

	public Endereco[] cadastrarEnderecos() {

		String logradouro, estado, bairro, numero, municipio, cep;
		int i = 0, op = 0;

		Endereco end[] = new Endereco[2];
		do {
			System.out.println(" -ENDEREÇO");
			System.out.println(" Logradouro: ");
			logradouro = str.nextLine();
			System.out.println(" Número");
			numero = str.nextLine();
			System.out.println(" Bairro: ");
			bairro = str.nextLine();
			System.out.println(" Municipio: ");
			municipio = str.nextLine();
			System.out.println(" Estado: ");
			estado = str.nextLine();
			System.out.println(" CEP: ");
			cep = str.nextLine();
			end[i] = new Endereco(logradouro, numero, bairro, municipio, estado, cep);
			i++;
			if (i < end.length) {
				System.out.println(" Deseja cadastrar outro endereço? 1.Sim | 2.Não");
				op = num.nextInt();
			}
		} while (i < end.length && op == 1);

		return end;
	}

	public ArrayList<Veiculo> cadastrarVeiculos() {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		String placa;
		int i = 0, op = 0, tipo;

		ArrayList<Veiculo> veiculos = new ArrayList<>();
		do {
			System.out.println(" -Veiculo");
			do {
				System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
				tipo = num.nextInt();
			} while (tipo <= 0 || tipo > 5);
			System.out.print(" Placa: ");
			placa = str.next();

			veiculos.add(new Veiculo(veiculos.size() + 1, tipo, placa));

			System.out.println(" Deseja cadastrar outro Veículo? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op == 1);

		return veiculos;
	}

	public void mostrarVeiculos(ArrayList<Veiculo> veiculos) {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		for (Veiculo v : veiculos) {
			if (v != null) {
				System.out.println("\n POSIÇÃO: " + v.getId());
				System.out.println(" Tipo: " + (v.getTipo() + 1));
				System.out.println(" Placa: " + v.getPlaca());

			}
		}

	}

	public void relatorioVeiculos() {
		for (Cliente cli : listClientes) {
			if (cli instanceof Motorista) {
				mostrarVeiculos(((Motorista) cli).getVeiculos());
			} else if (cli instanceof Transportadora) {
				mostrarVeiculos(((Transportadora) cli).getVeiculos());
			}
		}
	}

	public void editarEndereco(int pos) {

		int op = 1;
		int finalizar = 0;

		do {

			int opcao_endereco = -1;

			mostrarEndereco(((Cliente) listClientes.get(pos)).getEndereco());
			System.out.print("Selecione a posição do endereço para editar: -1 -> Sair");
			opcao_endereco = num.nextInt();
			if (opcao_endereco != -1) {
				System.out.println(" Logradouro: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setLogradouro(str.nextLine());
				System.out.println(" Número: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setNumero(str.nextLine());
				System.out.println(" Bairro: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setBairro(str.nextLine());
				System.out.println(" Município: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setMunicipio(str.nextLine());
				System.out.println(" CEP: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setCep(str.nextLine());
				System.out.println(" Estado: ");
				((Cliente) listClientes.get(pos)).getEndereco()[opcao_endereco].setEstado(str.nextLine());

				System.out.println(" Deseja laterar outro endereço? 1.Sim | 2.Não");
				finalizar = num.nextInt();

			} else
				finalizar = 2;
		} while (finalizar != 2);

	}

	public void editarVeiculoMotorista(int pos) {

		int op = 1;
		int finalizar = 0;

		do {

			int opcao_veiculo = -1;
			int tipo = 0;

			mostrarVeiculos(((Motorista) listClientes.get(pos)).getVeiculos());
			System.out.print("Selecione a posição do Veiculo para editar: -1 -> Sair");
			opcao_veiculo = num.nextInt();
			if (opcao_veiculo != -1) {
				do {
					System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
					tipo = num.nextInt();
				} while (tipo <= 0 || tipo > 5);
				((Motorista) listClientes.get(pos)).getVeiculos().get(opcao_veiculo).setTipo(tipo);

				System.out.println(" Placa: ");
				((Motorista) listClientes.get(pos)).getVeiculos().get(opcao_veiculo).setPlaca(str.nextLine());

				System.out.println(" Deseja alterar outro Veiculo? 1.Sim | 2.Não");

				finalizar = num.nextInt();

			} else
				finalizar = 2;
		} while (finalizar != 2);

	}

	public void editarVeiculoTransportadora(int pos) {

		int op = 1;
		int finalizar = 0;

		do {

			int opcao_veiculo = -1;
			int tipo = 0;

			mostrarVeiculos(((Transportadora) listClientes.get(pos)).getVeiculos());
			System.out.print("Selecione a posição do Veiculo para editar: -1 -> Sair");
			opcao_veiculo = num.nextInt();
			if (opcao_veiculo != -1) {
				do {
					System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
					tipo = num.nextInt();
				} while (tipo <= 0 || tipo > 5);
				((Transportadora) listClientes.get(pos)).getVeiculos().get(opcao_veiculo).setTipo(tipo);

				System.out.println(" Placa: ");
				((Transportadora) listClientes.get(pos)).getVeiculos().get(opcao_veiculo).setPlaca(str.nextLine());

				System.out.println(" Deseja alterar outro Veiculo? 1.Sim | 2.Não");

				finalizar = num.nextInt();

			} else
				finalizar = 2;
		} while (finalizar != 2);
		
		
		
	}

	public void listarContratantes() {
		for (Cliente c : listClientes) {
			if (c instanceof Contratante) {
				System.out.println(
						listClientes.indexOf(c) + " - Nome: " + c.getNome() + " CNPJ: " + ((Contratante) c).getCnpj());
			}
		}
	}

	public void listarMotoristas() {
		for (Cliente c : listClientes) {
			if (c instanceof Motorista) {
				System.out.println(listClientes.indexOf(c) + " - Nome: " + c.getNome() + " CPF: "
						+ ((Motorista) c).getCpf() + " RNTRC: " + ((Motorista) c).getRntrc());
			}
		}
	}

	public void listarTransportadora() {
		for (Cliente c : listClientes) {
			if (c instanceof Transportadora) {
				System.out.println(listClientes.indexOf(c) + " - Nome: " + c.getNome() + " CNPJ: "
						+ ((Transportadora) c).getCnpj());
			}
		}
	}

	public void listarVeiculos() {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		for (Cliente c : listClientes) {
			if (c instanceof Motorista) {
				for (Veiculo v : ((Motorista) c).getVeiculos()) {
					System.out.print("Id: " + v.getId() + " Placa: " + v.getPlaca() + " Tipo: "
							+ tipo_veiculo[v.getTipo() + 1] + " Motorista: " + c.getNome() + "\n");
				}
			} else if (c instanceof Transportadora) {
				for (Veiculo v : ((Transportadora) c).getVeiculos()) {
					System.out.print("Id: " + v.getId() + " Placa: " + v.getPlaca() + " Tipo: "
							+ tipo_veiculo[v.getTipo() + 1] + " Motorista: " + c.getNome() + "\n");
				}
			}
		}
	}

	public Veiculo returnVeiculo(int id_veiculo) {
		ArrayList<Veiculo> veiculos = new ArrayList<>();
		for (Cliente c : listClientes) {
			if (c instanceof Motorista) {
				veiculos.addAll(((Motorista) c).getVeiculos());
			} else if (c instanceof Transportadora) {
				veiculos.addAll(((Transportadora) c).getVeiculos());

			}
		}

		return veiculos.get(id_veiculo);
	}

	public int numVeiculos() {
		int num_veiculos = 0;
		for (Cliente c : listClientes) {
			if (c instanceof Motorista) {
				num_veiculos += ((Motorista) c).getVeiculos().size();
			} else if (c instanceof Transportadora) {
				num_veiculos += ((Transportadora) c).getVeiculos().size();

			}
		}
		return num_veiculos;
	}
}
