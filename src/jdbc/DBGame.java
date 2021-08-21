package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Tabuleiro;
import util.Log;

public class DBGame {

	private DBGame() {
	}

	public static void saveGame(final Tabuleiro tabuleiro, final String resultado) {
		final Connection connection = FabricaConexao.getConnection();

		FabricaConexao.checkTableGames(connection);

		insertData(connection, tabuleiro, resultado);
	}

	private static void insertData(final Connection connection, final Tabuleiro tabuleiro, final String resultado) {
		Statement    statement   = null;
		final String createTable = "INSERT INTO tb_games (resultado, linhas, colunas, minas) VALUES (\"" + resultado + "\"," + tabuleiro.getLines() + "," + tabuleiro.getColumns()
				+ "," + tabuleiro.getMines() + ");";

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
