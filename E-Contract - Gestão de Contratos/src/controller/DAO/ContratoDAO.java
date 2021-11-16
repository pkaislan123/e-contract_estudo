
package controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.conexao.ConexaoBanco;
import model.Contratante;
import model.Contrato;
import model.Produto;
import model.Safra;

public class ContratoDAO {

	private Connection conn = null;

	public ContratoDAO() {
		conn = ConexaoBanco.getConexao();
	}

	public int incluir(Contrato contrato, int id_corretor) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into contrato ( unidade_medida, id_safra, quantidade_contratada, valor_por_unidade, quantidade_atendida, id_corretor) values (?,?,?,?,?, ?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, contrato.getUnidadeMedida());
			ps.setInt(2, contrato.getSafra().getId());
			ps.setDouble(3, contrato.getQuantidadeContratada());
			ps.setDouble(4, contrato.getValorPorUnidade());
			ps.setDouble(5, contrato.getQuantidadeAtendida());
			ps.setInt(6, id_corretor);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println("Erro ao Inserir Contrato na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirContratoComprador(int id_contrato, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into contrato_comprador ( id_contrato, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out
					.println("Erro ao Inserir um Novo Comprador ao contrato na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirContratoCVendedor(int id_contrato, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into contrato_vendedor ( id_contrato, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out
					.println("Erro ao Inserir um Novo Vendedor ao contrato na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public boolean alterar(Contrato contrato) {
		String sql;
		PreparedStatement ps = null;
		sql = "update contrato\r\n" + "set unidade_medida = ?,\r\n" + "id_safra = ?,\r\n"
				+ "quantidade_contratada = ?,\r\n" + "valor_por_unidade = ?,\r\n"
				+ "quantidade_atendida = ? where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, contrato.getUnidadeMedida());
			ps.setInt(2, contrato.getSafra().getId());
			ps.setDouble(3, contrato.getQuantidadeContratada());
			ps.setDouble(4, contrato.getValorPorUnidade());
			ps.setDouble(5, contrato.getQuantidadeAtendida());
			ps.setDouble(6, contrato.getId());

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Contrato na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public int numCompradores(int id_contrato) {
		int num_registro = 0;
		String sql;
		Contrato contrato = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_compradores\r\n" + "from contrato_comprador ct_cp\r\n"
				+ "left join contrato ct \r\n" + "on ct.id = ct_cp.id_contrato\r\n" + "where ct.id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_compradores");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Número de Compradores do Contrato da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

	public int numVendedores(int id_contrato) {
		int num_registro = 0;
		String sql;
		Contrato contrato = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_vendedores\r\n" + "from contrato_vendedor ct_cp\r\n"
				+ "left join contrato ct \r\n" + "on ct.id = ct_cp.id_contrato\r\n" + "where ct.id = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_vendedores");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Número de Vendedores do Contrato da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

	public boolean alterarCorretor(int id_contrato, int id_corretor) {
		String sql;
		PreparedStatement ps = null;
		sql = "update contrato\r\n" + "set id_corretor = ?\r\n" + "where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_corretor);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Alterar o Corretor do Contrato na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public int incluirComprador(int id_contrato, int id_comprador) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into contrato_comprador ( id_contrato, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_comprador);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println(
					"Erro ao inserir a relação entre Contrato e Comprador na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public int incluirVendedor(int id_contrato, int id_vendedor) {
		String sql;
		PreparedStatement ps = null;
		int result = -1;
		sql = "insert into contrato_vendedor ( id_contrato, id_cliente) values (?,?)";

		try {

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_vendedor);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}

			ConexaoBanco.fechaConexao(conn, ps, rs);

		} catch (Exception e) {
			System.out.println(
					"Erro ao inserir a relação entre Contrato e Comprador na base de dados, erro: " + e.getMessage());
		}

		return result;

	}

	public ArrayList<Contratante> getCompradores(int id_contrato) {
		ArrayList<Contratante> listContratantes = new ArrayList<Contratante>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select \r\n" + "cli.id, cli.nome, cli.ie ,\r\n" + "ct.cnpj\r\n" + "from contrato_comprador ct_cp\r\n"
				+ "left join cliente cli \r\n" + "on cli.id = ct_cp.id_cliente\r\n" + "left join contratante ct\r\n"
				+ "on ct.id_cliente = cli.id\r\n" + "where ct_cp.id_contrato = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
			rs = pstm.executeQuery();
			while (rs.next()) {

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

	public ArrayList<Contratante> getVendedores(int id_contrato) {
		ArrayList<Contratante> listContratantes = new ArrayList<Contratante>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select \r\n" + "cli.id, cli.nome, cli.ie ,\r\n" + "ct.cnpj\r\n" + "from contrato_vendedor ct_cp\r\n"
				+ "left join cliente cli \r\n" + "on cli.id = ct_cp.id_cliente\r\n" + "left join contratante ct\r\n"
				+ "on ct.id_cliente = cli.id\r\n" + "where ct_cp.id_contrato = ?";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_contrato);
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

	public boolean excluir(int id) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from contrato where id = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir Contrato na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean excluirRelacaoContratoComprador(int id_contrato, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from contrato_comprador where id_contrato = ? and id_cliente = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out
					.println("Erro ao Excluir a relação contrato comprador na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public boolean excluirRelacaoContratoVendedor(int id_contrato, int id_cliente) {
		String sql;
		PreparedStatement ps = null;
		sql = "delete from contrato_vendedor where id_contrato = ? and id_cliente = ?";

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_contrato);
			ps.setInt(2, id_cliente);

			ps.executeUpdate();

			ConexaoBanco.fechaConexao(conn, ps);

			return true;

		} catch (Exception e) {
			System.out.println("Erro ao Excluir a relação contrato vendedor na base de dados, erro: " + e.getMessage());
			return false;
		}

	}

	public ArrayList<Contrato> relatorio() {

		ArrayList<Contrato> listContratos = new ArrayList<Contrato>();
		String sql;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		/*
		 * select ct.id, ct.unidade_medida, ct.quantidade_contratada,
		 * ct.valor_por_unidade, ct.quantidade_atendida, sf.ano_plantio, sf.ano_colheita
		 * , pd.nome as nome_produto, ct.id_corretor from contrato ct left join safra sf
		 * on sf.id = ct.id_safra left join produto pd on pd.id = sf.id_produto
		 * 
		 * order by ct.id
		 */
		sql = "select ct.id, ct.unidade_medida,\r\n"
				+ "ct.quantidade_contratada, ct.valor_por_unidade, ct.quantidade_atendida,\r\n"
				+ "sf.ano_plantio, sf.ano_colheita , pd.nome as nome_produto,\r\n" + "ct.id_corretor\r\n"
				+ "from contrato ct\r\n" + "left join safra sf\r\n" + "on sf.id = ct.id_safra\r\n"
				+ "left join produto pd\r\n" + "on pd.id = sf.id_produto\r\n" + "\r\n" + "order by ct.id";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto(rs.getString("nome_produto"), null, null);
				Contratante corretor = new Contratante(rs.getInt("id_corretor"), null, null, null, null);
				Safra safra = new Safra(rs.getInt("ano_plantio"), rs.getInt("ano_colheita"), produto);
				Contrato ct = new Contrato(rs.getInt("id"), rs.getInt("unidade_medida"), null, null, corretor, safra,
						rs.getDouble("quantidade_contratada"), rs.getDouble("valor_por_unidade"),
						rs.getDouble("quantidade_atendida"));

				listContratos.add(ct);
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return listContratos;
		} catch (Exception e) {
			System.out.println("Erro ao Listar Contratos da base de dados, erro: " + e.getMessage());
			return null;
		}
	}

	public int isVazio() {
		int num_registro = 0;
		String sql;
		Contrato contrato = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		sql = "select count(*) as num_registros from contrato";

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();

			num_registro = rs.getInt("num_registros");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println(
					"Erro ao Verificar Núemro de Registros de Contrato da base de dados, erro: " + e.getMessage());
		}

		return num_registro;

	}

}
