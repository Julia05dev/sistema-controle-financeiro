package sistemaFinanceiro.servico;

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
import sistemaFinanceiro.persistencia.dao.LancamentoDAO;

class SistemaFinanceiroTest {

    private static final String NOME_BANCO_TESTE =
        "teste";

    private static final String URL_TESTE =
        "jdbc:postgresql://localhost:5432/"
            + NOME_BANCO_TESTE;

    private static final String USUARIO =
        "postgres";

    private static final String SENHA =
        System.getenv("DB_PASSWORD");

    private Connection connection;
    private LancamentoDAO dao;
    private SistemaFinanceiro sistema;

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
        sistema = new SistemaFinanceiro(connection);
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
    void deveCarregarLancamentosDoBancoAoIniciar()
            throws SQLException {

        Lancamento lancamento = new Lancamento(
            TipoCategoria.EMPREGO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.PIX,
            TipoLancamento.RECEITA,
            new BigDecimal("2500.00")
        );

        int idGerado = dao.inserir(lancamento);

        SistemaFinanceiro novoSistema =
            new SistemaFinanceiro(connection);

        List<Lancamento> resultado =
            novoSistema.mostraLancamentos();

        assertEquals(1, resultado.size());

        assertEquals(
            idGerado,
            resultado.get(0).getId()
        );

        assertEquals(
            0,
            new BigDecimal("2500.00")
                .compareTo(resultado.get(0).getValor())
        );
    }

    @Test
    void deveCriarLancamentoNoBancoENaCarteira()
            throws SQLException {

        sistema.criaLancamento(
            TipoCategoria.MERCADO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.DEBITO,
            TipoLancamento.DESPESA,
            new BigDecimal("150.00")
        );

        List<Lancamento> naCarteira =
            sistema.mostraLancamentos();

        List<Lancamento> noBanco =
            dao.listarTodos();

        assertAll(
            () -> assertEquals(
                1,
                naCarteira.size()
            ),

            () -> assertEquals(
                1,
                noBanco.size()
            ),

            () -> assertEquals(
                noBanco.get(0).getId(),
                naCarteira.get(0).getId()
            ),

            () -> assertEquals(
                0,
                new BigDecimal("150.00")
                    .compareTo(
                        naCarteira.get(0).getValor()
                    )
            ),

            () -> assertEquals(
                0,
                new BigDecimal("150.00")
                    .compareTo(
                        noBanco.get(0).getValor()
                    )
            )
        );
    }

    @Test
    void deveRemoverLancamentoDoBancoEDaCarteira()
            throws SQLException {

        sistema.criaLancamento(
            TipoCategoria.FREELANCE,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.PIX,
            TipoLancamento.RECEITA,
            new BigDecimal("500.00")
        );

        int id = sistema
            .mostraLancamentos()
            .get(0)
            .getId();

        boolean removeu =
            sistema.removeLancamento(id);

        assertAll(
            () -> assertTrue(removeu),

            () -> assertTrue(
                sistema.mostraLancamentos().isEmpty()
            ),

            () -> assertTrue(
                dao.listarTodos().isEmpty()
            )
        );
    }

    @Test
    void naoDeveAlterarCarteiraQuandoIdNaoExistir()
            throws SQLException {

        sistema.criaLancamento(
            TipoCategoria.PRESENTE,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.DINHEIRO,
            TipoLancamento.RECEITA,
            new BigDecimal("100.00")
        );

        boolean removeu =
            sistema.removeLancamento(
                Integer.MAX_VALUE
            );

        assertFalse(removeu);

        assertEquals(
            1,
            sistema.mostraLancamentos().size()
        );
    }
}