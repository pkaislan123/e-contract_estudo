
package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Pagamento;

public class PagamentoDAO {

	private Connection conn = null;

	public PagamentoDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Pagamento pagamento, int id_contrato) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into pagamento ( valor, data_pagamento, id_contrato) values (?,?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, pagamento.getValor());
			ps.setString(2, pagamento.getDataPagamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			ps.setInt(3, id_contrato);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Pagamento na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from pagamento where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Pagamento na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Pagamento pagamento) {
		String sql;
		PreparedStatement ps = null;
		sql = "update pagamento set valor = ?, data_pagamento = ?  where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setDouble(1, pagamento.getValor());
			ps.setString(2, pagamento.getDataPagamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			ps.setInt(3, pagamento.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Pagamento na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Pagamento> relatorio() {

		ArrayList<Pagamento> listPagamentos = new ArrayList<Pagamento>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select pag.id, pag.valor, pag.data_pagamento, pag.id_contrato\r\n" + "from pagamento pag\r\n"
				+ "order by pag.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				LocalDate data_pagamento = LocalDate.parse(rs.getString("data_pagamento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				Pagamento pagamento = new Pagamento(rs.getInt("id"), rs.getDouble("valor"), data_pagamento);

				listPagamentos.add(pagamento);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listPagamentos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Pagamentos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Pagamento> relatorio(int id_contrato) {

		ArrayList<Pagamento> listPagamentos = new ArrayList<Pagamento>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select pag.id, pag.valor, pag.data_pagamento, pag.id_contrato\r\n" + "from pagamento pag\r\n"
				+ "where pag.id_contrato = ?\r\n" + "order by pag.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			while (rs.next()) {
				LocalDate data_pagamento = LocalDate.parse(rs.getString("data_pagamento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				Pagamento pagamento = new Pagamento(rs.getInt("id"), rs.getDouble("valor"), data_pagamento);

				listPagamentos.add(pagamento);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listPagamentos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Pagamentos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public Pagamento consultar(int id) {

		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Pagamento pagamento = null;
		sql = "select nome, descricao, transgenia from pagamento where id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				LocalDate data_pagamento = LocalDate.parse(rs.getString("data_pagamento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				pagamento = new Pagamento(rs.getInt("id"), rs.getDouble("valor"), data_pagamento);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return pagamento;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Pagamentos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Pagamento pagamento = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from pagamento";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Núemro de Registros de Pagamento da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
