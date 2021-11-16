package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Produto;
import model.Safra;

public class SafraDAO {

	private Connection conn = null;

	public SafraDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Safra safra) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into safra ( ano_plantio, ano_colheita, id_produto) values (?,?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, safra.getAnoPlantio());
			ps.setInt(2, safra.getAnoColheita());
			ps.setInt(3, safra.getProduto().getId());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Safra na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from safra where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Safra na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Safra safra) {
		String sql;
		PreparedStatement ps = null;
		sql = "update safra set ano_plantio = ?, ano_colheita = ?, id_produto = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, safra.getAnoPlantio());
			ps.setInt(2, safra.getAnoColheita());
			ps.setInt(3, safra.getProduto().getId());
			ps.setInt(4, safra.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Safra na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Safra> relatorio() {

		ArrayList<Safra> listSafras = new ArrayList<Safra>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select sf.id, sf.ano_plantio, sf.ano_colheita,\r\n" + "p.id as produto_id, \r\n"
				+ "p.nome as produto_nome,\r\n" + "p.descricao as produto_descricao,\r\n"
				+ "p.transgenia as produto_transgenia\r\n" + "from safra sf\r\n"
				+ "left join produto p on p.id = sf.id_produto order by sf.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto(rs.getInt("produto_id"), rs.getString("produto_nome"),
						rs.getString("produto_descricao"), rs.getString("produto_transgenia"));

				Safra safra = new Safra(rs.getInt("id"), rs.getInt("ano_plantio"), rs.getInt("ano_colheita"), produto);

				listSafras.add(safra);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listSafras;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Safras da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public Safra consultar(int id) {

		String sql;
		Safra safra = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select sf.id, sf.ano_plantio, sf.ano_colheita,\r\n" + "p.id as produto_id, \r\n"
				+ "p.nome as produto_nome,\r\n" + "p.descricao as produto_descricao,\r\n"
				+ "p.transgenia as produto_transgenia\r\n" + "from safra sf\r\n"
				+ "left join produto p on p.id = sf.id_produto whre sf.id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
						rs.getString("transgenia"));

				safra = new Safra(rs.getInt("id"), rs.getInt("ano_plantio"), rs.getInt("ano_colheita"), produto);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return safra;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Safras da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Produto produto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from safra";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Núemro de Registros de Safra da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
