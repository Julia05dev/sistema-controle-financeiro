package sistemaFinanceiro.persistencia.conexaoJDBC;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class ConexaoBancoTest {

    private static final String URL_TESTE =
        "jdbc:postgresql://localhost:5432/teste";

    private static final String USUARIO = "postgres";
    private static final String SENHA = System.getenv("DB_PASSWORD");

    @Test
    void deveConectarAoBancoDeTeste() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                URL_TESTE, USUARIO, SENHA)) {

            assertNotNull(connection);
            assertFalse(connection.isClosed());
            assertEquals("teste", connection.getCatalog());
        }
    }
}