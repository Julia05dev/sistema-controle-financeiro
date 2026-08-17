package sistemaFinanceiro.persistencia.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;
import sistemaFinanceiro.persistencia.conexaoJDBC.SingleConnection;

class LancamentoDAOTest {

    private Connection connection;
    private LancamentoDAO dao;

    @BeforeEach
    void prepararTeste() throws SQLException {
        connection = SingleConnection.getConnection();
        dao = new LancamentoDAO(connection);
        limparTabela();
    }

    @AfterEach
    void finalizarTeste() throws SQLException {
        limparTabela();
    }

    private void limparTabela() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM lancamento");
            connection.commit();
        }
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremLancamentos()
            throws SQLException {

        List<Lancamento> resultado = dao.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveListarTodosOsLancamentos()
            throws SQLException {

        Lancamento receita = new Lancamento(
            TipoCategoria.EMPREGO,
            LocalDate.of(2026, 8, 17),
            TipoMovimentacao.PIX,
            TipoLancamento.RECEITA,
            1500.00
        );

        Lancamento despesa = new Lancamento(
            TipoCategoria.MERCADO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.DEBITO,
            TipoLancamento.DESPESA,
            200.00
        );

        int idReceita = dao.inserir(receita);
        int idDespesa = dao.inserir(despesa);

        List<Lancamento> resultado = dao.listarTodos();

        assertEquals(2, resultado.size());

        Lancamento receitaRecuperada = resultado.get(0);
        Lancamento despesaRecuperada = resultado.get(1);

        assertAll(
            () -> assertEquals(idReceita, receitaRecuperada.getId()),
            () -> assertEquals(LocalDate.of(2026, 8, 17),
                               receitaRecuperada.getData()),
            () -> assertEquals(1500.00,
                               receitaRecuperada.getValor(), 0.001),
            () -> assertEquals(TipoLancamento.RECEITA,
                               receitaRecuperada.getTipo()),
            () -> assertEquals(TipoCategoria.EMPREGO,
                               receitaRecuperada.getCategoria()),
            () -> assertEquals(TipoMovimentacao.PIX,
                               receitaRecuperada.getmeioDeMovimentacao()),

            () -> assertEquals(idDespesa, despesaRecuperada.getId()),
            () -> assertEquals(LocalDate.of(2026, 8, 18),
                               despesaRecuperada.getData()),
            () -> assertEquals(-200.00,
                               despesaRecuperada.getValor(), 0.001),
            () -> assertEquals(TipoLancamento.DESPESA,
                               despesaRecuperada.getTipo()),
            () -> assertEquals(TipoCategoria.MERCADO,
                               despesaRecuperada.getCategoria()),
            () -> assertEquals(TipoMovimentacao.DEBITO,
                               despesaRecuperada.getmeioDeMovimentacao())
        );
    }
}