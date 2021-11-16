package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Produto;

public class ProdutoDAO {

	private Connection conn = null;

	public ProdutoDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Produto produto) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into produto ( nome, descricao, transgenia) values (?,?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setString(3, produto.getTransgenia());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Produto na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from produto where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Produto na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Produto produto) {
		String sql;
		PreparedStatement ps = null;
		sql = "update produto set nome = ?, transgenia = ?, descricao = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getTransgenia());
			ps.setString(3, produto.getDescricao());
			ps.setInt(4, produto.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Produto na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Produto> relatorio() {

		ArrayList<Produto> listProdutos = new ArrayList<Produto>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select id, nome, descricao, transgenia from produto order by id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
						rs.getString("transgenia"));

				listProdutos.add(produto);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listProdutos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Produtos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public Produto consultar(int id) {

		String sql;
		Produto produto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select nome, descricao, transgenia from produto where id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
						rs.getString("transgenia"));

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return produto;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Produtos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Produto produto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from produto";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Núemro de Registros de Produto da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
