
package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Cliente;
import model.Contratante;
import model.Endereco;
import model.Motorista;
import model.Produto;
import model.Transportadora;
import model.Veiculo;

public class ClienteDAO {

	private Connection conn = null;

	public ClienteDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluirCliente(Cliente cliente) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into cliente ( nome, ie) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getIe());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir o Cliente na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirContratante(Contratante contratante, int id_cliente) {
		int result = -1;
		PreparedStatement ps = null;

		String sql = "insert into contratante ( cnpj, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, contratante.getCnpj());
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir o Contratante na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirMotorista(Motorista motorista, int id_cliente) {
		int result = -1;
		PreparedStatement ps = null;

		String sql = "insert into motorista ( cpf, rntrc, id_cliente) values (?,?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, motorista.getCpf());
			ps.setInt(2, motorista.getRntrc());
			ps.setInt(3, id_cliente);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir o Motorista na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirTransportadora(Transportadora transportadora, int id_cliente) {
		int result = -1;
		PreparedStatement ps = null;

		String sql = "insert into transportadora ( cnpj, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, transportadora.getCnpj());
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir a Transportadora na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from cliente where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Cliente na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterarContratante(Contratante contratante, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		sql = "with cliente_atualizado as (\r\n" + "  update cliente set nome = ?, ie = ?\r\n" + "  where id = ? \r\n"
				+ "  returning *)\r\n" + "update contratante set cnpj = ? \r\n"
				+ "where id_cliente in (select id from cliente_atualizado);";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, contratante.getNome());
			ps.setString(2, contratante.getIe());
			ps.setInt(3, id_cliente);
			ps.setString(4, contratante.getCnpj());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Contratante na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterarMotorista(Motorista motorista, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		sql = "with cliente_atualizado as (\r\n" + "  update cliente set nome = ?, ie = ?\r\n" + "  where id = ? \r\n"
				+ "  returning *)\r\n" + "update motorista set cpf = ?, rntrc = ? \r\n"
				+ "where id_cliente in (select id from cliente_atualizado);";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, motorista.getNome());
			ps.setString(2, motorista.getIe());
			ps.setInt(3, id_cliente);
			ps.setString(4, motorista.getCpf());
			ps.setInt(5, motorista.getRntrc());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Motorista na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterarTransportadora(Transportadora transportadora, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		sql = "with cliente_atualizado as (\r\n" + "  update cliente set nome = ?, ie = ?\r\n" + "  where id = ? \r\n"
				+ "  returning *)\r\n" + "update transportadora set cnpj = ? \r\n"
				+ "where id_cliente in (select id from cliente_atualizado);";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, transportadora.getNome());
			ps.setString(2, transportadora.getIe());
			ps.setInt(3, id_cliente);
			ps.setString(4, transportadora.getCnpj());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar a Tranportadora na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Contratante> relatorioContratantes() {

		ArrayList<Contratante> listContratantes = new ArrayList<Contratante>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select cli.id, cli.nome, cli.ie ,\r\n" + "ct.cnpj\r\n" + "from contratante ct\r\n"
				+ "left join cliente cli on cli.id = ct.id_cliente order by cli.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {

				// public Contratante(int id String nome, String ie, ArrayList<Endereco>
				// enderecos) {

				Contratante contratante = new Contratante(rs.getInt("id"), rs.getString("nome"), rs.getString("ie"),
						null, rs.getString("cnpj")

				);

				listContratantes.add(contratante);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listContratantes;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Contratantes da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public Contratante getContratante(int id_cliente) {

		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Contratante contratante = null;

		sql = "select cli.id, cli.nome, cli.ie ,\r\n" + "ct.cnpj\r\n" + "from contratante ct\r\n"
				+ "left join cliente cli on cli.id = ct.id_cliente where cli.id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_cliente);
			rs = pstm.executeQuery();

			if (rs.next()) {

				// public Contratante(int id String nome, String ie, ArrayList<Endereco>
				// enderecos) {

				contratante = new Contratante(rs.getInt("id"), rs.getString("nome"), rs.getString("ie"), null,
						rs.getString("cnpj")

				);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return contratante;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Contratante da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Motorista> relatorioMotoristas() {

		ArrayList<Motorista> listMotoristas = new ArrayList<Motorista>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select cli.id, cli.nome, cli.ie ,\r\n" + "motor.cpf, motor.rntrc\r\n" + "from motorista motor\r\n"
				+ "left join cliente cli on cli.id = motor.id_cliente order by cli.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {

				// public Contratante(int id String nome, String ie, ArrayList<Endereco>
				// enderecos) {

				Motorista motor = new Motorista(rs.getInt("id"), rs.getString("nome"), rs.getString("ie"), null,
						rs.getString("cpf"), rs.getInt("rntrc")

				);

				listMotoristas.add(motor);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listMotoristas;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Motoristas da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Transportadora> relatorioTransportadora() {

		ArrayList<Transportadora> listTransportadora = new ArrayList<Transportadora>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select cli.id, cli.nome, cli.ie ,\r\n" + "transp.cnpj\r\n" + "from transportadora transp\r\n"
				+ "left join cliente cli on cli.id = transp.id_cliente order by cli.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {

				// public Contratante(int id String nome, String ie, ArrayList<Endereco>
				// enderecos) {

				Transportadora motor = new Transportadora(rs.getInt("id"), rs.getString("nome"), rs.getString("ie"),
						null, rs.getString("cnpj")

				);

				listTransportadora.add(motor);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listTransportadora;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Motoristas da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio(int flag_tipo_cliente) {
		int num_registro = 0;
		String sql = "";
		Produto produto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		if (flag_tipo_cliente == 0) {
			sql = "select count(*) as num_registros from contratante";
		} else if (flag_tipo_cliente == 1) {
			sql = "select count(*) as num_registros from motorista";

		} else if (flag_tipo_cliente == 2) {
			sql = "select count(*) as num_registros from transportadora";

		}
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println("Erro ao Verificar Número de Registros da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

	public ArrayList<Veiculo> relatorioVeiculos() {

		ArrayList<Veiculo> listVeiculos = new ArrayList<Veiculo>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select \r\n" + "v.id, v.tipo, v.placa, v.id_cliente\r\n" + "from veiculo v order by v.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {

				Veiculo veiculo = new Veiculo(rs.getInt("id"), rs.getInt("tipo"), rs.getString("placa"));

				listVeiculos.add(veiculo);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listVeiculos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Veiculos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int numVeiculos() {
		int num_registro = 0;
		String sql = "";
		Produto produto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from veiculo";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println("Erro ao Verificar Número de Veículos da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
