package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Endereco;

public class EnderecoDAO {

	private Connection conn = null;

	public EnderecoDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Endereco endereco, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into endereco ( logradouro, numero, bairro,municipio, estado ,cep, id_cliente) values (?,?,?, ?, ?,?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, endereco.getLogradouro());
			ps.setString(2, endereco.getNumero());
			ps.setString(3, endereco.getBairro());
			ps.setString(4, endereco.getMunicipio());
			ps.setString(5, endereco.getEstado());
			ps.setString(6, endereco.getCep());
			ps.setInt(7, id_cliente);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Endereco na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from endereco where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Endereco na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Endereco endereco) {
		String sql;
		PreparedStatement ps = null;
		sql = "update endereco set logradouro = ?, numero = ?, bairro = ?,municipio = ?, estado =? , cep = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, endereco.getLogradouro());
			ps.setString(2, endereco.getNumero());
			ps.setString(3, endereco.getBairro());
			ps.setString(4, endereco.getMunicipio());
			ps.setString(5, endereco.getEstado());
			ps.setString(6, endereco.getCep());
			ps.setInt(7, endereco.getId());
			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Endereco na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Endereco> relatorio(int id_cliente) {

		ArrayList<Endereco> listEnderecos = new ArrayList<Endereco>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select id, logradouro, numero, bairro, municipio, estado, cep from endereco where id_cliente = ? order by id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_cliente);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("logradouro"), rs.getString("numero"),
						rs.getString("bairro"), rs.getString("municipio"), rs.getString("estado"), rs.getString("cep"));

				listEnderecos.add(endereco);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listEnderecos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Enderecos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public Endereco consultar(int id) {

		String sql;
		Endereco endereco = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select logradouro, numero, bairro, municipio, estado, cep from endereco where id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				endereco = new Endereco(rs.getInt("id"), rs.getString("logradouro"), rs.getString("numero"),
						rs.getString("bairro"), rs.getString("municipio"), rs.getString("estado"), rs.getString("cep"));

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return endereco;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Enderecos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio(int id_cliente) {
		int num_registro = 0;
		String sql;
		Endereco endereco = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from endereco where id_cliente = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_cliente);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Número de Registros de Endereco da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
