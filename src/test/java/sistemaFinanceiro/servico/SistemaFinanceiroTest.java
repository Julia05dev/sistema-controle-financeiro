package sistemaFinanceiro.servico;

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
import sistemaFinanceiro.persistencia.dao.LancamentoDAO;

class SistemaFinanceiroTest {

    private Connection connection;
    private LancamentoDAO dao;
    private SistemaFinanceiro sistema;

    @BeforeEach
    void prepararTeste() throws SQLException {
        connection = SingleConnection.getConnection();
        dao = new LancamentoDAO(connection);

        limparTabela();

        sistema = new SistemaFinanceiro();
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
    void deveCarregarLancamentosDoBancoAoIniciar()
            throws SQLException {

        Lancamento lancamento = new Lancamento(
            TipoCategoria.EMPREGO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.PIX,
            TipoLancamento.RECEITA,
            2500.00
        );

        int idGerado = dao.inserir(lancamento);

        SistemaFinanceiro novoSistema = new SistemaFinanceiro();

        List<Lancamento> resultado =
            novoSistema.mostraLancamentos();

        assertEquals(1, resultado.size());
        assertEquals(idGerado, resultado.get(0).getId());
        assertEquals(2500.00, resultado.get(0).getValor(), 0.001);
    }

    @Test
    void deveCriarLancamentoNoBancoENaCarteira()
            throws SQLException {

        sistema.criaLancamento(
            TipoCategoria.MERCADO,
            LocalDate.of(2026, 8, 18),
            TipoMovimentacao.DEBITO,
            TipoLancamento.DESPESA,
            150.00
        );

        List<Lancamento> naCarteira =
            sistema.mostraLancamentos();

        List<Lancamento> noBanco =
            dao.listarTodos();

        assertAll(
            () -> assertEquals(1, naCarteira.size()),
            () -> assertEquals(1, noBanco.size()),

            () -> assertEquals(
                noBanco.get(0).getId(),
                naCarteira.get(0).getId()
            ),

            () -> assertEquals(
                -150.00,
                naCarteira.get(0).getValor(),
                0.001
            ),

            () -> assertEquals(
                -150.00,
                noBanco.get(0).getValor(),
                0.001
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
            500.00
        );

        int id = sistema
            .mostraLancamentos()
            .get(0)
            .getId();

        boolean removeu = sistema.removeLancamento(id);

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
            100.00
        );

        boolean removeu =
            sistema.removeLancamento(Integer.MAX_VALUE);

        assertFalse(removeu);
        assertEquals(
            1,
            sistema.mostraLancamentos().size()
        );
    }
}