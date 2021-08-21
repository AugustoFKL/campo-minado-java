package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Log;

public class FabricaConexao {

	private static final String CONEXA_PROPERTIES = "/jdbc/conexa.properties";
	private static final Logger logger            = Logger.getAnonymousLogger();
	private static final String SCHEMA            = "campo_minado_java";

	private FabricaConexao() {
	}

	public static Connection getConnection() {
		Properties properties = null;
		Connection connection = null;
		try {
			properties = getProperties();
			final String url      = properties.getProperty("banco.url");
			final String root     = properties.getProperty("banco.usuario");
			final String password = properties.getProperty("banco.senha");
			connection = DriverManager.getConnection(url, root, password);
			logger.log(Level.INFO, "Conexão efetuada com sucesso!");

			// TODO otimizar essa checagem para 1x por execução do aplicativo.
			checkSchema(connection);
			connection.setAutoCommit(true);
			connection.setSchema(SCHEMA);

		} catch (IOException e1) {
			Log.logAnon().logSevere("Não foi possível acessar as propriedades do banco.");
			e1.printStackTrace();
		} catch (SQLException e2) {
			Log.logAnon().logSevere("Não foi possível realizar a conexão.");
			e2.printStackTrace();
		}

		return connection;
	}

	private static Properties getProperties() throws IOException {
		final Properties properties = new Properties();
		final String     path       = CONEXA_PROPERTIES;
		properties.load(FabricaConexao.class.getResourceAsStream(path));
		return properties;
	}

	protected static void checkSchema(final Connection connection) {
		final List<String> schemas = getSchemas(connection);

		if (schemas.stream().noneMatch(i -> i.equals(SCHEMA))) {
			addSchema(connection);
		}
	}

	private static List<String> getSchemas(final Connection connection) {
		final List<String> schemas       = new ArrayList<>();
		final String       verifySchemas = "SHOW SCHEMAS";
		Statement          statement     = null;
		try {
			statement = connection.createStatement();

			final ResultSet resultado = statement.executeQuery(verifySchemas);
			while (resultado.next()) {
				schemas.add(resultado.getString(1));
			}
			return schemas;
		} catch (final SQLException sqlException) {
			Log.logAnon().logSevere("Não foi possível executar a requisição dos SCHEMAS.");
		} finally {
			try {
				if (null != statement) {
					statement.close();
				}
			} catch (SQLException e) {
				Log.logAnon().logSevere("Não foi possível fechar o statement.");

			}

		}
		return schemas;
	}

	private static void addSchema(final Connection connection) {
		Statement    statement    = null;
		final String createSchema = "CREATE SCHEMA " + SCHEMA + ";";

		try {
			statement = connection.createStatement();

			Log.logAnon().logInfo("Executando comando: " + createSchema);
			statement.executeUpdate(createSchema);

			Log.logAnon().logInfo("Statement executado com sucesso!");
		} catch (final SQLException sqlException) {
			Log.logAnon().logSevere("Não foi possível executar a criação do SCHEMA.");
			sqlException.printStackTrace();

		} finally {
			try {
				if (null != statement) {
					statement.close();
				}
			} catch (final SQLException sqlException2) {
				Log.logAnon().logSevere("Não foi possível fechar o preparedStatement.");
			}
		}
	}

	protected static void checkTableGames(final Connection connection) {
		final List<String> tables = getTables(connection);

		if (tables.stream().noneMatch(i -> i.equals("tb_games"))) {
			addTableGames(connection);
		}
	}

	private static List<String> getTables(Connection connection) {
		final List<String> tables        = new ArrayList<>();
		final String       verifySchemas = "SHOW TABLES";
		Statement          statement     = null;
		try {
			statement = connection.createStatement();

			final ResultSet resultado = statement.executeQuery(verifySchemas);
			while (resultado.next()) {
				tables.add(resultado.getString(1));
			}
			return tables;
		} catch (final SQLException sqlException) {
			Log.logAnon().logSevere("Não foi possível executar a requisição das TABLES.");
		} finally {
			try {
				if (null != statement) {
					statement.close();
				}
			} catch (SQLException e) {
				Log.logAnon().logSevere("Não foi possível fechar o statement.");

			}

		}
		return tables;
	}

	private static void addTableGames(final Connection connection) {
		Statement    statement   = null;
		final String createTable = "CREATE TABLE IF NOT EXISTS tb_games (codigo INT AUTO_INCREMENT PRIMARY KEY, resultado VARCHAR(80) NOT NULL, linhas INT, colunas INT, minas INT);";

		try {
			statement = connection.createStatement();

			Log.logAnon().logInfo("Executando comando: " + createTable);
			statement.executeUpdate(createTable);

			Log.logAnon().logInfo("Statement executado com sucesso!");
		} catch (final SQLException sqlException) {
			Log.logAnon().logSevere("Não foi possível executar a criação da TABLE.");
			sqlException.printStackTrace();

		} finally {
			try {
				if (null != statement) {
					statement.close();
				}
			} catch (final SQLException sqlException2) {
				Log.logAnon().logSevere("Não foi possível fechar o preparedStatement.");
			}
		}
	}
}
