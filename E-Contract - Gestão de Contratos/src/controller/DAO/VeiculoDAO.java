
package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Veiculo;

public class VeiculoDAO {

	private Connection conn = null;

	public VeiculoDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Veiculo veiculo, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into veiculo ( tipo, placa, id_cliente) values (?,?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, veiculo.getTipo());
			ps.setString(2, veiculo.getPlaca());
			ps.setInt(3, id_cliente);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Veiculo na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from veiculo where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Veiculo na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Veiculo veiculo) {
		String sql;
		PreparedStatement ps = null;
		sql = "update veiculo set tipo = ?, placa = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, veiculo.getTipo());
			ps.setString(2, veiculo.getPlaca());
			ps.setInt(3, veiculo.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Veiculo na base de dados, erro: " + e.getMessage());
			return false;
		}

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

	public ArrayList<Veiculo> relatorio(int id_cliente) {

		ArrayList<Veiculo> listVeiculos = new ArrayList<Veiculo>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select id, tipo, placa from veiculo where id_cliente = ? order by id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_cliente);
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

	public Veiculo consultar(int id) {

		String sql;
		Veiculo veiculo = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select id, tipo, placa from veiculo where id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				veiculo = new Veiculo(rs.getInt("id"), rs.getInt("tipo"), rs.getString("placa"));

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return veiculo;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Veiculos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Veiculo veiculo = null;
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
			System.out.println(
					"Erro ao Verificar Número de Registros de Veiculo da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
