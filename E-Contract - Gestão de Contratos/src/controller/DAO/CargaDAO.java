
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
import model.Carga;
import model.Veiculo;

public class CargaDAO {

	private Connection conn = null;

	public CargaDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Carga carga, int id_contrato) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into carga ( peso_carga, id_veiculo, data_recebimento, data_carregamento, id_contrato) values (?,?,?, ?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, carga.getPesoCarga());
			ps.setInt(2, carga.getVeiculo().getId());
			ps.setString(3, carga.getDataRecebimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			ps.setString(4, "");
			ps.setInt(5, id_contrato);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Carga na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from carga where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Carga na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean alterar(Carga carga) {
		String sql;
		PreparedStatement ps = null;
		sql = "update carga set peso_carga = ?, id_veiculo = ?, data_recebimento = ?, data_carregamento = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setDouble(1, carga.getPesoCarga());
			ps.setInt(2, carga.getVeiculo().getId());
			ps.setString(3, carga.getDataRecebimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			ps.setString(4, carga.getDataCarregamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			ps.setInt(5, carga.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Carga na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Carga> relatorio(int id_contrato) {

		ArrayList<Carga> listCargas = new ArrayList<Carga>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select c.id as id_carga, c.peso_carga, c.data_recebimento, c.data_carregamento,\r\n"
				+ "v.id as id_veiculo, v.tipo, v.placa\r\n" + "from carga c\r\n" + "left join veiculo v\r\n"
				+ "on v.id = c.id_veiculo\r\n" + "where c.id_contrato = ?\r\n" + "order by c.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			while (rs.next()) {
				LocalDate data_carregamento = null;
				Veiculo veiculo = new Veiculo(rs.getInt("id_veiculo"), rs.getInt("tipo"), rs.getString("placa"));
				LocalDate data_recebimento = LocalDate.parse(rs.getString("data_recebimento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				try {
					data_carregamento = LocalDate.parse(rs.getString("data_carregamento"),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				} catch (Exception e) {
					data_carregamento = null;
				}

				Carga carga = new Carga(rs.getInt("id_carga"), rs.getDouble("peso_carga"), veiculo, data_recebimento,
						data_carregamento);

				listCargas.add(carga);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listCargas;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Cargas da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Carga> relatorio() {

		ArrayList<Carga> listCargas = new ArrayList<Carga>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select c.id as id_carga, c.peso_carga, c.data_recebimento, c.data_carregamento,\r\n"
				+ "v.id as id_veiculo, v.tipo, v.placa\r\n" + "from carga c\r\n" + "left join veiculo v\r\n"
				+ "on v.id = c.id_veiculo\r\n" + "order by c.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {

				Veiculo veiculo = new Veiculo(rs.getInt("id_veiculo"), rs.getInt("tipo"), rs.getString("placa"));
				LocalDate data_recebimento = LocalDate.parse(rs.getString("data_recebimento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate data_carregamento = LocalDate.parse(rs.getString("data_carregamento"),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				Carga carga = new Carga(rs.getInt("id_carga"), rs.getDouble("peso_carga"), veiculo, data_recebimento,
						data_carregamento);

				listCargas.add(carga);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listCargas;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Cargas da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public double quantidadeRecebida(int id_contrato) {
		double num_registro = 0;
		String sql;
		Carga carga = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select sum (c.peso_carga) as quantidade_recebida\r\n" + "	from carga c where\r\n"
				+ "	c.id_contrato = ?;";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("quantidade_recebida");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar a quantidade recebida de Carga da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Carga carga = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from carga";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Núemro de Registros de Carga da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
