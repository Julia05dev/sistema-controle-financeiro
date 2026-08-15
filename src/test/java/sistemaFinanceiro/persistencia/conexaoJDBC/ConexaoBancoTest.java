package sistemaFinanceiro.persistencia.conexaoJDBC;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import org.junit.jupiter.api.Test;

public class ConexaoBancoTest {
    @Test
    void deveConectarAoBanco() {
        Connection connection = SingleConnection.getConnection();
        assertNotNull(connection);
    }
}
