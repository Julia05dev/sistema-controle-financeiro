package sistemaFinanceiro.persistencia.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
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

class LancamentoDAOTest {

    private static final String NOME_BANCO_TESTE =
        "teste";

    private static final String URL_TESTE =
        "jdbc:postgresql://localhost:5432/" + NOME_BANCO_TESTE;

    private static final String USUARIO = "postgres";
    private static final String SENHA =
        System.getenv("DB_PASSWORD");

    private Connection connection;
    private LancamentoDAO dao;

    @BeforeEach
    void prepararTeste() throws SQLException {
        connection = DriverManager.getConnection(
            URL_TESTE,
            USUARIO,
            SENHA
        );

        connection.setAutoCommit(false);

        limparTabela();

        dao = new LancamentoDAO(connection);
    }

    @AfterEach
    void finalizarTeste() throws SQLException {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    limparTabela();
                }
            } finally {
                connection.close();
            }
        }
    }

    private void confirmarBancoDeTeste()
            throws SQLException {

        String bancoAtual = connection.getCatalog();

        if (!NOME_BANCO_TESTE.equals(bancoAtual)) {
            throw new IllegalStateException(
                "Os testes so podem usar o banco "
                    + NOME_BANCO_TESTE
            );
        }
    }

    private void limparTabela() throws SQLException {
        confirmarBancoDeTeste();

        try (Statement statement =
                connection.createStatement()) {

            statement.executeUpdate(
                "DELETE FROM lancamento"
            );

            connection.commit();
        }
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremLancamentos()
            throws SQLException {

        List<Lancamento> resultado =
            dao.listarTodos();

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
            new BigDecimal("1500.00")
        );

        Lancamento despesa = new Lancamento(
            TipoCategoria.MERCADO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.DEBITO,
            TipoLancamento.DESPESA,
            new BigDecimal("200.00")
        );

        int idReceita = dao.inserir(receita);
        int idDespesa = dao.inserir(despesa);

        List<Lancamento> resultado =
            dao.listarTodos();

        assertEquals(2, resultado.size());

        Lancamento receitaRecuperada =
            resultado.get(0);

        Lancamento despesaRecuperada =
            resultado.get(1);

        assertAll(
            () -> assertEquals(
                idReceita,
                receitaRecuperada.getId()
            ),

            () -> assertEquals(
                LocalDate.of(2026, 8, 17),
                receitaRecuperada.getData()
            ),

            () -> assertEquals(
                0,
                new BigDecimal("1500.00")
                    .compareTo(
                        receitaRecuperada.getValor()
                    )
            ),

            () -> assertEquals(
                TipoLancamento.RECEITA,
                receitaRecuperada.getTipo()
            ),

            () -> assertEquals(
                TipoCategoria.EMPREGO,
                receitaRecuperada.getCategoria()
            ),

            () -> assertEquals(
                TipoMovimentacao.PIX,
                receitaRecuperada
                    .getmeioDeMovimentacao()
            ),

            () -> assertEquals(
                idDespesa,
                despesaRecuperada.getId()
            ),

            () -> assertEquals(
                LocalDate.of(2026, 8, 18),
                despesaRecuperada.getData()
            ),

            () -> assertEquals(
                0,
                new BigDecimal("200.00")
                    .compareTo(
                        despesaRecuperada.getValor()
                    )
            ),

            () -> assertEquals(
                TipoLancamento.DESPESA,
                despesaRecuperada.getTipo()
            ),

            () -> assertEquals(
                TipoCategoria.MERCADO,
                despesaRecuperada.getCategoria()
            ),

            () -> assertEquals(
                TipoMovimentacao.DEBITO,
                despesaRecuperada
                    .getmeioDeMovimentacao()
            )
        );
    }

    @Test
    void deveExcluirLancamentoExistente()
            throws SQLException {

        Lancamento lancamento = new Lancamento(
            TipoCategoria.MERCADO,
            LocalDate.of(2026, 8, 17),
            TipoMovimentacao.DEBITO,
            TipoLancamento.DESPESA,
            new BigDecimal("100.00")
        );

        int idGerado = dao.inserir(lancamento);

        boolean excluiu =
            dao.excluirPorId(idGerado);

        assertTrue(excluiu);
        assertTrue(dao.listarTodos().isEmpty());
    }

    @Test
    void deveRetornarFalsoQuandoIdNaoExistir()
            throws SQLException {

        boolean excluiu =
            dao.excluirPorId(Integer.MAX_VALUE);

        assertFalse(excluiu);
    }

    @Test
    void deveRejeitarIdInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> dao.excluirPorId(0)
        );
    }
}