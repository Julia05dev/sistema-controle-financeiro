package sistemaFinanceiro.persistencia.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;
import sistemaFinanceiro.persistencia.conexaoJDBC.SingleConnection;

class LancamentoDAOTest {

    @Test
    void deveInserirLancamentoNoBanco() throws SQLException {
        Connection connection = SingleConnection.getConnection();

        LancamentoDAO dao = new LancamentoDAO(connection);

        Lancamento lancamento = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 16),
                TipoMovimentacao.PIX,
                TipoLancamento.DESPESA,
                125.70
        );

        int idGerado = dao.inserir(lancamento);

        assertTrue(
                idGerado > 0,
                "O PostgreSQL deveria gerar um ID maior que zero."
        );
    }
}