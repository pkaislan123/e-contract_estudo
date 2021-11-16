package controller.gerencia;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import controller.DAO.ClienteDAO;
import controller.DAO.EnderecoDAO;
import controller.DAO.ProdutoDAO;
import controller.DAO.VeiculoDAO;
import model.Endereco;
import model.Motorista;
import model.Produto;
import model.Contratante;
import model.Transportadora;
import model.Veiculo;
import model.Cliente;
import model.Contratante;

public class GerenciaClientes {

	private Scanner num, str;

	public GerenciaClientes() {

		num = new Scanner(System.in);
		str = new Scanner(System.in);

	}

	public void incluir() {
		int tipoCliente, rntrc;
		ArrayList<Endereco> enderecos;
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

			Contratante contratante = new Contratante(nome, ie, null, cnpj);
			int id_cliente = new ClienteDAO().incluirCliente(contratante);

			if (new ClienteDAO().incluirContratante(contratante, id_cliente) > 0) {
				if (id_cliente > 0) {
					cadastrarEnderecos(id_cliente);
				}
			}
		} else if (tipoCliente == 2) {
			System.out.println(" CPF: ");
			cpf = str.nextLine();
			System.out.println(" RNTRC: ");
			rntrc = num.nextInt();

			Motorista motorista = new Motorista(nome, ie, null, cpf, rntrc);
			int id_cliente = new ClienteDAO().incluirCliente(motorista);

			if (new ClienteDAO().incluirMotorista(motorista, id_cliente) > 0) {
				if (id_cliente > 0) {
					cadastrarEnderecos(id_cliente);
					cadastrarVeiculos(id_cliente);
				}
			}

			// veiculos = cadastrarVeiculos();

		} else if (tipoCliente == 3) {
			System.out.println(" CNPJ: ");
			cnpj = str.nextLine();
			Transportadora transportadora = new Transportadora(nome, ie, null, cnpj);
			int id_cliente = new ClienteDAO().incluirCliente(transportadora);

			if (new ClienteDAO().incluirTransportadora(transportadora, id_cliente) > 0) {
				if (id_cliente > 0) {
					cadastrarEnderecos(id_cliente);
					cadastrarVeiculos(id_cliente);

				}
			}

		}

	}

	public void excluir() {
		System.out.println(" Selecione a opção correspondente ao tipo de cliente que deseja gerar o relatorio");
		int tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
			return;
		}

		if (tipoCliente == 1) {
			excluirContratante();
		} else if (tipoCliente == 2) {
			excluirMotorista();
		} else {
			excluirTransportadora();
		}

	}

	public void cadastrarEnderecos(int id_cliente) {

		String logradouro, estado, bairro, numero, municipio, cep;
		int i = 0, op = 0;

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
			Endereco end = new Endereco(logradouro, numero, bairro, municipio, estado, cep);

			if (new EnderecoDAO().incluir(end, id_cliente) > 0) {
				System.out.println("Endereço Cadastrado!");
			}

			System.out.println(" Deseja cadastrar outro endereço? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op == 1);

	}

	public void cadastrarVeiculos(int id_cliente) {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		String placa;
		int i = 0, op = 0, tipo;
		do {
			System.out.println(" -Veiculo");
			do {
				System.out.print(" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
				tipo = num.nextInt();
			} while (tipo <= 0 || tipo > 5);
			System.out.print(" Placa: ");
			placa = str.next();

			Veiculo veiculo = new Veiculo(tipo, placa);

			if (new VeiculoDAO().incluir(veiculo, id_cliente) > 0) {
				System.out.println("Veículo Cadastrado!");
			}

			System.out.println(" Deseja cadastrar outro Veículo? 1.Sim | 2.Não");
			op = num.nextInt();

		} while (op == 1);

	}

	public void consultar() {
		System.out.println(" Selecione a opção correspondente ao tipo de cliente que deseja gerar o relatorio");
		int tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
			return;
		}

		if (tipoCliente == 1) {
			consultarContratante();
		} else if (tipoCliente == 2) {
			consultarMotorista();
		} else {
			consultarTransportadora();
		}
	}

	public void consultarContratante() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Contratante> listContratantes = gerenciar.relatorioContratantes();

		if (listContratantes != null && listContratantes.size() > 0) {
			imprimeContratantes(listContratantes);

			System.out.println("-Digite o ID do Contratante para Detalhar:");
			int id = num.nextInt();

			Contratante contratante = posicaoContratanteValida(listContratantes, id);
			if (listContratantes != null) {

				imprimeDetalhesContratante(contratante, ((Cliente) contratante).getId());

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há Contratantes Cadastrados!");

		}
	}

	public void consultarMotorista() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Motorista> listMotoristas = gerenciar.relatorioMotoristas();

		if (listMotoristas != null && listMotoristas.size() > 0) {
			imprimeMotoristas(listMotoristas);

			System.out.println("-Digite o ID do Motorista para Detalhar:");
			int id = num.nextInt();

			Motorista motorista = posicaoMotoristaValida(listMotoristas, id);
			if (listMotoristas != null) {

				imprimeDetalhesMotorista(motorista, ((Cliente) motorista).getId());

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há Motoristas Cadastrados!");

		}
	}

	public void consultarTransportadora() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Transportadora> listTransportadora = gerenciar.relatorioTransportadora();

		if (listTransportadora != null && listTransportadora.size() > 0) {
			imprimeTransportadoras(listTransportadora);

			System.out.println("-Digite o ID da listTransportadora para Detalhar:");
			int id = num.nextInt();

			Transportadora tranpostadora = posicaoTransportadoraValida(listTransportadora, id);
			if (listTransportadora != null) {

				imprimeDetalhesTransportadora(tranpostadora, ((Cliente) tranpostadora).getId());

			} else {
				System.out.println(" ID Inválido!");
			}

		} else {
			System.out.println(" Não há Transportadora Cadastradas!");

		}
	}

	public void alterarContratante() {
		ArrayList<Contratante> listContratantes = new ClienteDAO().relatorioContratantes();
		int op;
		if (listContratantes != null && listContratantes.size() > 0) {
			int id;

			imprimeContratantes(listContratantes);
			System.out.println("-Digite o ID do Contratante para alterar:");
			id = num.nextInt();
			if (id > 0 || id > listContratantes.size()) {
				Contratante contratante = posicaoContratanteValida(listContratantes, id);
				if (contratante != null) {
					imprimeContratante(contratante);

					System.out.println("Deseja Alterar o Contratante selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {

						int opcaoMenu = menuInternoAlteracao();

						if (opcaoMenu == 0) {
							System.out.println("\n Retonando ao menu anterior...\n");
							return;
						}

						if (opcaoMenu != 1 && opcaoMenu != 2 && opcaoMenu != 3) {
							System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
							return;
						}

						if (opcaoMenu == 1) {

							System.out.println("Alterar Nome: ?(Atual: " + contratante.getNome() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Nome:");
								contratante.setNome(str.nextLine());
							}

							System.out.println("Alterar IE: ?(Atual: " + contratante.getIe() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Inscrição Estadual:");
								contratante.setIe(str.nextLine());
							}

							System.out.println("Alterar CNPJ: ?(Atual: " + contratante.getCnpj() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n CNPJ:");
								contratante.setCnpj(str.nextLine());
							}

							boolean alterado = new ClienteDAO().alterarContratante(contratante, id);
							if (alterado)
								System.out.println(" Contratante Alterado!");

						} else if (opcaoMenu == 2) {
							editarEndereco(new EnderecoDAO().relatorio(id));

						} else if (opcaoMenu == 3) {
							cadastrarEnderecos(((Cliente) contratante).getId());
						}

					} else {
						System.out.println(" Alteração Cancelada!");

					}

				} else {
					System.out.println(" ID Inválido!");

				}

			} else {
				System.out.println(" ID Inválido!");

			}

		} else {
			System.out.println(" Não há Contratantes Cadastrados!");
		}
	}

	public void alterarMotorista() {
		ArrayList<Motorista> listMotoristas = new ClienteDAO().relatorioMotoristas();
		int op;
		if (listMotoristas != null && listMotoristas.size() > 0) {
			int id;

			imprimeMotoristas(listMotoristas);
			System.out.println("-Digite o ID do Motorista para alterar:");
			id = num.nextInt();
			if (id > 0 || id > listMotoristas.size()) {
				Motorista motorista = posicaoMotoristaValida(listMotoristas, id);
				if (motorista != null) {
					imprimeMotorista(motorista);

					System.out.println("Deseja Alterar o Motorista selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {

						int opcaoMenu = menuInternoAlteracaoVeiculo();

						if (opcaoMenu == 0) {
							System.out.println("\n Retonando ao menu anterior...\n");
							return;
						}

						if (opcaoMenu != 1 && opcaoMenu != 2 && opcaoMenu != 3 && opcaoMenu != 4 && opcaoMenu != 5) {
							System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
							return;
						}

						if (opcaoMenu == 1) {
							System.out.println("Alterar Nome: ?(Atual: " + motorista.getNome() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Nome:");
								motorista.setNome(str.nextLine());
							}

							System.out.println("Alterar IE: ?(Atual: " + motorista.getIe() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Inscrição Estadual:");
								motorista.setIe(str.nextLine());
							}

							System.out.println("Alterar CPF: ?(Atual: " + motorista.getCpf() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n CPF:");
								motorista.setCpf(str.nextLine());
							}

							System.out.println("Alterar RNTRC: ?(Atual: " + motorista.getRntrc() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n RNTRC:");
								motorista.setRntrc(num.nextInt());
							}

							boolean alterado = new ClienteDAO().alterarMotorista(motorista, id);
							if (alterado) {
								System.out.println(" Motorista Alterado!");

							}
						} else if (opcaoMenu == 2) {
							editarEndereco(new EnderecoDAO().relatorio(id));

						} else if (opcaoMenu == 3) {
							cadastrarEnderecos(((Cliente) motorista).getId());
						} else if (opcaoMenu == 4) {
							editarVeiculos(new VeiculoDAO().relatorio(id));
						} else if (opcaoMenu == 5) {
							cadastrarVeiculos(((Cliente) motorista).getId());

						}

					} else {
						System.out.println(" Alteração Cancelada!");

					}

				} else {
					System.out.println(" ID Inválido!");

				}

			} else {
				System.out.println(" ID Inválido!");

			}

		} else {
			System.out.println(" Não há Contratantes Cadastrados!");
		}
	}

	public void alterarTransportadora() {
		ArrayList<Transportadora> listTransportadoras = new ClienteDAO().relatorioTransportadora();
		int op;
		if (listTransportadoras != null && listTransportadoras.size() > 0) {
			int id;

			imprimeTransportadoras(listTransportadoras);
			System.out.println("-Digite o ID da Transportadora para alterar:");
			id = num.nextInt();
			if (id > 0 || id > listTransportadoras.size()) {
				Transportadora transportadora = posicaoTransportadoraValida(listTransportadoras, id);
				if (transportadora != null) {
					imprimeTransportadora(transportadora);

					System.out.println("Deseja Alterar a Transportadora selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {

						int opcaoMenu = menuInternoAlteracaoVeiculo();

						if (opcaoMenu == 0) {
							System.out.println("\n Retonando ao menu anterior...\n");
							return;
						}

						if (opcaoMenu != 1 && opcaoMenu != 2 && opcaoMenu != 3 && opcaoMenu != 4 && opcaoMenu != 5) {
							System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
							return;
						}

						if (opcaoMenu == 1) {
							System.out.println("Alterar Nome: ?(Atual: " + transportadora.getNome() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Nome:");
								transportadora.setNome(str.nextLine());
							}

							System.out.println("Alterar IE: ?(Atual: " + transportadora.getIe() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Inscrição Estadual:");
								transportadora.setIe(str.nextLine());
							}

							System.out.println("Alterar CNPJ: ?(Atual: " + transportadora.getCnpj() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n CNPJ:");
								transportadora.setCnpj(str.nextLine());
							}

							boolean alterado = new ClienteDAO().alterarTransportadora(transportadora, id);
							if (alterado) {
								System.out.println(" Transportadora Alterada!");

							}
						} else if (opcaoMenu == 2) {
							editarEndereco(new EnderecoDAO().relatorio(id));

						} else if (opcaoMenu == 3) {
							cadastrarEnderecos(((Cliente) transportadora).getId());
						} else if (opcaoMenu == 4) {
							editarVeiculos(new VeiculoDAO().relatorio(id));
						} else if (opcaoMenu == 5) {
							cadastrarVeiculos(((Cliente) transportadora).getId());

						}

					} else {
						System.out.println(" Alteração Cancelada!");

					}

				} else {
					System.out.println(" ID Inválido!");

				}

			} else {
				System.out.println(" ID Inválido!");

			}

		} else {
			System.out.println(" Não há Transportadoras Cadastradass!");
		}
	}

	public Contratante posicaoContratanteValida(ArrayList<Contratante> contratantes, int id) {

		Contratante contratante = null;
		boolean contem = false;
		Iterator<Contratante> i = contratantes.iterator();
		while (!contem && i.hasNext()) {
			contratante = i.next();

			if (contratante.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return contratante;
		else
			return null;

	}

	public void listarVeiculos() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Veiculo> listVeiculos = gerenciar.relatorioVeiculos();
		for (Veiculo v : listVeiculos) {
			detalharVeiculo(v);
		}

	}

	public int numVeiculos() {
		return new ClienteDAO().numVeiculos();
	}

	public Contratante posicaoContratanteValida(int id) {
		ArrayList<Contratante> listContratantes = new ClienteDAO().relatorioContratantes();

		Contratante contratante = null;
		boolean contem = false;
		Iterator<Contratante> i = listContratantes.iterator();
		while (!contem && i.hasNext()) {
			contratante = i.next();

			if (contratante.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return contratante;
		else
			return null;

	}

	public Endereco posicaoEnderecoValida(ArrayList<Endereco> enderecos, int id) {

		Endereco end = null;
		boolean contem = false;
		Iterator<Endereco> i = enderecos.iterator();
		while (!contem && i.hasNext()) {
			end = i.next();

			if (end.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return end;
		else
			return null;

	}

	public Veiculo posicaoVeiculoValida(ArrayList<Veiculo> veiculos, int id) {

		Veiculo v = null;
		boolean contem = false;
		Iterator<Veiculo> i = veiculos.iterator();
		while (!contem && i.hasNext()) {
			v = i.next();

			if (v.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return v;
		else
			return null;

	}

	public Veiculo posicaoVeiculoValida(int id) {
		ArrayList<Veiculo> veiculos = new ClienteDAO().relatorioVeiculos();

		Veiculo v = null;
		boolean contem = false;
		Iterator<Veiculo> i = veiculos.iterator();
		while (!contem && i.hasNext()) {
			v = i.next();

			if (v.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return v;
		else
			return null;

	}

	public Transportadora posicaoTransportadoraValida(int id) {
		ArrayList<Transportadora> transportadoras = new ClienteDAO().relatorioTransportadora();

		Transportadora transportadora = null;
		boolean contem = false;
		Iterator<Transportadora> i = transportadoras.iterator();
		while (!contem && i.hasNext()) {
			transportadora = i.next();

			if (transportadora.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return transportadora;
		else
			return null;

	}

	public Transportadora posicaoTransportadoraValida(ArrayList<Transportadora> transportadoras, int id) {

		Transportadora transportadora = null;
		boolean contem = false;
		Iterator<Transportadora> i = transportadoras.iterator();
		while (!contem && i.hasNext()) {
			transportadora = i.next();

			if (transportadora.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return transportadora;
		else
			return null;

	}

	public Motorista posicaoMotoristaValida(int id) {
		ArrayList<Motorista> motoristas = new ClienteDAO().relatorioMotoristas();

		Motorista motorista = null;
		boolean contem = false;
		Iterator<Motorista> i = motoristas.iterator();
		while (!contem && i.hasNext()) {
			motorista = i.next();

			if (motorista.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return motorista;
		else
			return null;

	}

	public Motorista posicaoMotoristaValida(ArrayList<Motorista> motoristas, int id) {

		Motorista motorista = null;
		boolean contem = false;
		Iterator<Motorista> i = motoristas.iterator();
		while (!contem && i.hasNext()) {
			motorista = i.next();

			if (motorista.getId() == id) {
				contem = true;
				break;
			}
		}

		if (contem)
			return motorista;
		else
			return null;

	}

	public void excluirContratante() {
		ArrayList<Contratante> listContratantes = new ClienteDAO().relatorioContratantes();

		if (listContratantes != null && listContratantes.size() > 0) {
			int id;

			imprimeContratantes(listContratantes);
			System.out.println("-Digite o ID do Contratante para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listContratantes.size()) {
				Contratante contratante = posicaoContratanteValida(listContratantes, id);
				if (contratante != null) {
					imprimeContratante(contratante);

					System.out.println("Deseja excluir o Contratante selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new ClienteDAO().excluir(id)) {
							System.out.println(" Contratante Excluído!");

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
			System.out.println(" Não há Contratantes Cadastrados!");
		}
	}

	public void excluirMotorista() {
		ArrayList<Motorista> listMotoristas = new ClienteDAO().relatorioMotoristas();

		if (listMotoristas != null && listMotoristas.size() > 0) {
			int id;

			imprimeMotoristas(listMotoristas);
			System.out.println("-Digite o ID do Motorista para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listMotoristas.size()) {
				Motorista motorista = posicaoMotoristaValida(listMotoristas, id);
				if (motorista != null) {
					imprimeMotorista(motorista);

					System.out.println("Deseja excluir o Motorista selecionado: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new ClienteDAO().excluir(id)) {
							System.out.println(" Motorista Excluído!");

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
			System.out.println(" Não há Motoristas Cadastrados!");
		}
	}

	public void excluirTransportadora() {
		ArrayList<Transportadora> listTransportadoras = new ClienteDAO().relatorioTransportadora();

		if (listTransportadoras != null && listTransportadoras.size() > 0) {
			int id;

			imprimeTransportadoras(listTransportadoras);
			System.out.println("-Digite o ID da Transportadora para excluir:");
			id = num.nextInt();
			if (id > 0 || id > listTransportadoras.size()) {
				Transportadora motorista = posicaoTransportadoraValida(listTransportadoras, id);
				if (motorista != null) {
					imprimeTransportadora(motorista);

					System.out.println("Deseja excluir o Transportadora selecionada: | 1(SIM) | 2(NÃO)");
					int opcao = num.nextInt();
					if (opcao == 1) {
						if (new ClienteDAO().excluir(id)) {
							System.out.println(" Transportadora Excluída!");

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
			System.out.println(" Não há Transportadoras Cadastradas!");
		}
	}

	public void alterar() {
		System.out.println(" Selecione a opção correspondente ao tipo de cliente que deseja gerar o relatorio");
		int tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
			return;
		}

		if (tipoCliente == 1) {
			alterarContratante();
		} else if (tipoCliente == 2) {
			alterarMotorista();
		} else {
			alterarTransportadora();

		}
	}

	public void relatorio() {
		System.out.println(" Selecione a opção correspondente ao tipo de cliente que deseja gerar o relatorio");
		int tipoCliente = tipoCliente();

		if (tipoCliente == 0) {
			System.out.println("\n Retonando ao menu anterior...\n");
			return;
		}

		if (tipoCliente != 1 && tipoCliente != 2 && tipoCliente != 3) {
			System.out.println("\n Opção Inválida\nOperação Cancelada.\n");
			return;
		}

		if (tipoCliente == 1) {
			relatorioContratantes();
		} else if (tipoCliente == 2) {
			relatorioMotoristas();
		} else {
			relatorioTransportadoras();
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

	private int menuInternoAlteracao() {

		System.out.println(" 1 - Editar Cadastro");
		System.out.println(" 2 - Editar Endereço");
		System.out.println(" 3 - Cadastrar Endereço");
		System.out.println(" 0 - Sair");
		System.out.println(" Opção:");
		return num.nextInt();

	}

	private int menuInternoAlteracaoVeiculo() {

		System.out.println(" 1 - Editar Cadastro");
		System.out.println(" 2 - Editar Endereço");
		System.out.println(" 3 - Cadastrar Endereço");
		System.out.println(" 4 - Editar Veículo");
		System.out.println(" 5 - Cadastrar Veículo");
		System.out.println(" 0 - Sair");
		System.out.println(" Opção:");
		return num.nextInt();

	}

	public void imprimeContratantes(ArrayList<Contratante> listClientes) {
		Iterator<Contratante> i = listClientes.iterator();
		System.out.println("-Lista de Contratantes]-");
		while (i.hasNext()) {
			Contratante cli = i.next();
			System.out.println("ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: "
					+ cli.getCnpj());
		}
	}

	public void imprimeContratante(Contratante cli) {
		System.out.println(
				"ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: " + cli.getCnpj());
	}

	public void imprimeDetalhesContratante(Contratante cli, int id_cliente) {
		ArrayList<Endereco> enderecos = new EnderecoDAO().relatorio(id_cliente);
		System.out.println(
				"ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: " + cli.getCnpj());
		imprimeEnderecos(enderecos);
	}

	public void imprimeDetalhesMotorista(Motorista cli, int id_cliente) {
		ArrayList<Endereco> enderecos = new EnderecoDAO().relatorio(id_cliente);
		ArrayList<Veiculo> veiculos = new VeiculoDAO().relatorio(id_cliente);
		System.out.println("ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CPF: "
				+ cli.getCpf() + " RTNTRC: " + cli.getRntrc());
		imprimeEnderecos(enderecos);
		imprimeVeiculos(veiculos);

	}

	public void imprimeDetalhesTransportadora(Transportadora cli, int id_cliente) {
		ArrayList<Endereco> enderecos = new EnderecoDAO().relatorio(id_cliente);
		ArrayList<Veiculo> veiculos = new VeiculoDAO().relatorio(id_cliente);
		System.out.println(
				"ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: " + cli.getCnpj());
		imprimeEnderecos(enderecos);
		imprimeVeiculos(veiculos);
	}

	public void editarEndereco(ArrayList<Endereco> enderecos) {

		int op = 1;
		int finalizar = 0;

		if (enderecos.size() > 0) {

			do {

				int opcao_endereco = -1;

				imprimeEnderecos(enderecos);
				System.out.print("Selecione o id do endereço:");
				opcao_endereco = num.nextInt();
				Endereco end_selecionado = posicaoEnderecoValida(enderecos, opcao_endereco);
				if (end_selecionado != null) {

					int opcaoMenu = -1;
					do {
						System.out.println("1 - Editar");
						System.out.println("2 - Excluir");
						System.out.println("0 - Sair");
						opcaoMenu = num.nextInt();

						if (opcaoMenu == 1) {
							System.out.println("Alterar Logradouro: ?(Atual: " + end_selecionado.getLogradouro() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Logradouro:");
								end_selecionado.setLogradouro(str.nextLine());
							}

							System.out.println("Alterar Número: ?(Atual: " + end_selecionado.getNumero() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Número:");
								end_selecionado.setNumero(str.nextLine());
							}

							System.out.println("Alterar Bairro: ?(Atual: " + end_selecionado.getBairro() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Bairro:");
								end_selecionado.setBairro(str.nextLine());
							}

							System.out.println("Alterar Município: ?(Atual: " + end_selecionado.getMunicipio() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Município:");
								end_selecionado.setMunicipio(str.nextLine());
							}

							System.out.println("Alterar CEP: ?(Atual: " + end_selecionado.getCep() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n CEP:");
								end_selecionado.setCep(str.nextLine());
							}

							System.out.println("Alterar Estado: ?(Atual: " + end_selecionado.getEstado() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println("\n Estado:");
								end_selecionado.setEstado(str.nextLine());
							}

							boolean alterado = new EnderecoDAO().alterar(end_selecionado);
							if (alterado) {
								System.out.println("--Endereço Alterado");
							}

							finalizar = 2;
						} else if (opcaoMenu == 2) {
							System.out.println("Excluir o Endereço Selecionado?:");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								boolean exclusao = new EnderecoDAO().excluir(end_selecionado.getId());
								if (exclusao) {
									System.out.println("Endereço Excluido!");
								}
							}

							finalizar = 2;
						}

					} while (opcaoMenu != 0);

				} else {
					System.out.println(" ID Inválido!\nTentar Outro Endereço?: 1.Sim | 2.Não");
					opcao_endereco = num.nextInt();
					if (opcao_endereco == 2)
						finalizar = 2;
				}

			} while (finalizar != 2);

		} else {
			System.out.println(" -- Não há endereços Cadastrados!");
		}
	}

	public void editarVeiculos(ArrayList<Veiculo> veiculos) {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		int op = 1;
		int finalizar = 0;

		if (veiculos.size() > 0) {

			do {

				int opcao_veiculo = -1;

				imprimeVeiculos(veiculos);
				System.out.print("Selecione o id do veículo:");
				opcao_veiculo = num.nextInt();
				Veiculo veiculo_selecionado = posicaoVeiculoValida(veiculos, opcao_veiculo);
				if (veiculo_selecionado != null) {

					int opcaoMenu = -1;
					do {
						System.out.println("1 - Editar");
						System.out.println("2 - Excluir");
						System.out.println("0 - Sair");
						opcaoMenu = num.nextInt();

						if (opcaoMenu == 1) {
							System.out.println("Alterar o Tipo: ?(Atual: "
									+ tipo_veiculo[veiculo_selecionado.getTipo() - 1] + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								int tipo = -1;
								do {
									System.out.print(
											" Tipo: 1 -> Truco | 2 -> Bi-Truco | 3 -> LS | 4 -> Bi-Trem | 5 -> Trimião");
									tipo = num.nextInt();
								} while (tipo <= 0 || tipo > 5);
								veiculo_selecionado.setTipo(tipo);
							}

							System.out.println("Alterar a Placa: ?(Atual: " + veiculo_selecionado.getPlaca() + ")");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								System.out.println(" Placa: ");
								veiculo_selecionado.setPlaca(str.nextLine());

							}

							boolean alterado = new VeiculoDAO().alterar(veiculo_selecionado);
							if (alterado) {
								System.out.println("--Veículo Alterado");
							}

							finalizar = 2;
						} else if (opcaoMenu == 2) {
							System.out.println("Excluir o Veículo Selecionado?:");
							System.out.println("\n1.Sim | 2.Não");
							op = num.nextInt();
							if (op == 1) {
								boolean exclusao = new VeiculoDAO().excluir(veiculo_selecionado.getId());
								if (exclusao) {
									System.out.println("Veículo Excluido!");
								}
							}

							finalizar = 2;
						}

					} while (opcaoMenu != 0);

				} else {
					System.out.println(" ID Inválido!\nTentar Outro Veículo?: 1.Sim | 2.Não");
					opcao_veiculo = num.nextInt();
					if (opcao_veiculo == 2)
						finalizar = 2;
				}

			} while (finalizar != 2);

		} else {
			System.out.println(" -- Não há veículos Cadastrados!");
		}
	}

	private void imprimeEnderecos(ArrayList<Endereco> enderecos) {
		for (Endereco endereco : enderecos) {
			if (endereco != null) {
				System.out.println("\n ID: " + endereco.getId());
				System.out.println(" Logradouro: " + endereco.getLogradouro());
				System.out.println(" Número: " + endereco.getNumero());
				System.out.println(" Bairro: " + endereco.getBairro());
				System.out.println(" Municipio: " + endereco.getMunicipio());
				System.out.println(" Estado: " + endereco.getEstado());
				System.out.println(" CEP: " + endereco.getCep());
			}
		}
	}

	public void detalharEndereco(Endereco endereco) {
		if (endereco != null) {
			System.out.println("\n ID: " + endereco.getId());
			System.out.println(" Logradouro: " + endereco.getLogradouro());
			System.out.println(" Número: " + endereco.getNumero());
			System.out.println(" Bairro: " + endereco.getBairro());
			System.out.println(" Municipio: " + endereco.getMunicipio());
			System.out.println(" Estado: " + endereco.getEstado());
			System.out.println(" CEP: " + endereco.getCep());
		}
	}

	public void imprimeVeiculos(ArrayList<Veiculo> veiculos) {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		for (Veiculo v : veiculos) {
			if (v != null) {
				System.out.println("\n ID: " + v.getId());
				System.out.println(" Tipo: " + tipo_veiculo[(v.getTipo() - 1)]);
				System.out.println(" Placa: " + v.getPlaca());

			}
		}

	}

	public void detalharVeiculo(Veiculo v) {
		String[] tipo_veiculo = { "Truco", "Bi-Truco", "LS", "Bi-Trem", "Trimião" };

		if (v != null) {
			System.out.println("\n ID: " + v.getId());
			System.out.println(" Tipo: " + tipo_veiculo[(v.getTipo() - 1)]);
			System.out.println(" Placa: " + v.getPlaca());

		}
	}

	public void imprimeMotoristas(ArrayList<Motorista> listClientes) {
		Iterator<Motorista> i = listClientes.iterator();
		System.out.println("-Lista de Motoristas]-");
		while (i.hasNext()) {
			Motorista cli = i.next();
			System.out.println("ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CPF: "
					+ cli.getCpf() + " RNTRC: " + cli.getRntrc());
		}
	}

	public void imprimeMotorista(Motorista cli) {
		System.out.println(
				"ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CPF: " + cli.getCpf());
	}

	public void imprimeTransportadoras(ArrayList<Transportadora> listClientes) {
		Iterator<Transportadora> i = listClientes.iterator();
		System.out.println("-Lista de Transportadoras]-");
		while (i.hasNext()) {
			Transportadora cli = i.next();
			System.out.println("ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: "
					+ cli.getCnpj());
		}
	}

	public void imprimeTransportadora(Transportadora cli) {
		System.out.println(
				"ID: " + cli.getId() + " Nome: " + cli.getNome() + " IE: " + cli.getIe() + " CNPJ: " + cli.getCnpj());

	}

	public void relatorioContratantes() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Contratante> listContratantes = gerenciar.relatorioContratantes();
		if (listContratantes != null && listContratantes.size() > 0) {
			imprimeContratantes(listContratantes);
		} else {
			System.out.println(" Não há Contratantes Cadastrados!");

		}
	}

	public void relatorioMotoristas() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Motorista> listContratantes = gerenciar.relatorioMotoristas();
		if (listContratantes != null && listContratantes.size() > 0) {
			imprimeMotoristas(listContratantes);
		} else {
			System.out.println(" Não há Motoristas Cadastrados!");

		}
	}

	public void relatorioTransportadoras() {
		ClienteDAO gerenciar = new ClienteDAO();
		ArrayList<Transportadora> listTransportadoras = gerenciar.relatorioTransportadora();
		if (listTransportadoras != null && listTransportadoras.size() > 0) {
			imprimeTransportadoras(listTransportadoras);
		} else {
			System.out.println(" Não há Transportadoras Cadastrados!");

		}
	}

	public int contem(int flag_tipo_cliente) {
		return new ClienteDAO().isVazio(flag_tipo_cliente);

	}

}
