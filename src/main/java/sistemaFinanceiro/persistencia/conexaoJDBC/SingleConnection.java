package sistemaFinanceiro.persistencia.conexaoJDBC;

import java.sql.*;

public class SingleConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/sistema_financeiro";
    private static final String user = "postgres";
    private static final String password = System.getenv("DB_PASSWORD");

    private static Connection connection = null;
    private SingleConnection() {}

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            if(password == null || password.isBlank())
                throw new SQLException("A variavel DB_PASSWORD nao foi configurada.");

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public static void fecharConexao() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
}