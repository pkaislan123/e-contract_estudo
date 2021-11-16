package controller.conexao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexaoBanco {

	private static String url;

	public static Connection getConexao() {

		try {

			Class.forName("org.postgresql.Driver");
			Properties props = new Properties();
			props.put("user", "postgres");
			props.put("password", "1234");
			props.put("charset", "UTF-8");

			url = "jdbc:postgresql://localhost:5432/bd_biblioteca";

			return DriverManager.getConnection(url, props);

		} catch (Exception e) {
			System.out.println(
					"Erro ao conectar com a base de dados: \n->Erro" + e.getMessage() + "\n->Causa: " + e.getCause());
			return null;
		}

	}

	public static void fechaConexao(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			} else {
				System.out.println("Não foi possível fechar a conexão com o banco de dados!");

			}

		} catch (Exception e) {
			System.out.println("Não foi possível fechar  a conexão com o banco de dados\n->Erro: " + e.getMessage()
					+ "\n->Causa: " + e.getCause());
		}
	}

	public static void fechaConexao(Connection conn, PreparedStatement stmt) {

		try {
			if (conn != null) {
				fechaConexao(conn);
			}
			if (stmt != null) {
				stmt.close();
			} else {
				System.out.println("Erro ao fechar Statement");
			}

		} catch (Exception e) {
			System.out.println("[ERRO] Não foi possivel fechar o statement\nErro:->" + e.getMessage() + "\n->Causa: "
					+ e.getCause());
		}
	}

	public static void fechaConexao(Connection conn, Statement stmt) {

		try {
			if (conn != null) {
				fechaConexao(conn);
			}
			if (stmt != null) {
				stmt.close();
			} else {
				System.out.println("Erro ao fechar Statement");
			}

		} catch (Exception e) {
			System.out.println("[ERRO] Não foi possivel fechar o statement\nErro:->" + e.getMessage() + "\n->Causa: "
					+ e.getCause());
		}
	}

	public static void fechaConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {

		try {
			if (conn != null || stmt != null) {
				fechaConexao(conn, stmt);
			}
			if (rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			System.out.println("Não foi possivel fechar o ResultSet " + e.getMessage());
		}
	}

	public static void fechaConexao(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (conn != null || stmt != null) {
				fechaConexao(conn, stmt);
			}
			if (rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			System.out.println("Não foi possivel fechar o ResultSet " + e.getMessage());
		}
	}
}
