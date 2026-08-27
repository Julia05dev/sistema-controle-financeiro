package sistemaFinanceiro.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;
import sistemaFinanceiro.modelo.filtros.FiltroPorTipo;

class CarteiraTest {

        private Lancamento criarReceita(int id, BigDecimal valor) {
        return new Lancamento(
                id,
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                valor);
        }

        private Lancamento criarDespesa(int id, BigDecimal valor) {
        return new Lancamento(
                id,
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                valor);
        }

    @Test
    void deveAdicionarLancamento() {
        Carteira carteira = new Carteira();
        Lancamento lancamento = criarReceita(1, new BigDecimal("1000.00"));

        carteira.addLancamento(lancamento);

        assertEquals(1, carteira.mostraLancamentos().size());
        assertSame(lancamento, carteira.mostraLancamentos().get(0));
        assertSame(lancamento, carteira.buscaPorId(lancamento.getId()));
    }

    @Test
    void deveRejeitarLancamentoNulo() {
        Carteira carteira = new Carteira();

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.addLancamento(null));
    }

    @Test
    void deveAdicionarListaDeLancamentos() {
        Carteira carteira = new Carteira();

        Lancamento receita = criarReceita(1, new BigDecimal("1000.00"));
        Lancamento despesa = criarDespesa(2, new BigDecimal("250.00"));

        carteira.addListaLancamentos(List.of(receita, despesa));

        assertEquals(2, carteira.mostraLancamentos().size());
        assertSame(receita, carteira.buscaPorId(receita.getId()));
        assertSame(despesa, carteira.buscaPorId(despesa.getId()));
    }

    @Test
    void deveBuscarLancamentoPorId() {
        Carteira carteira = new Carteira();
        Lancamento lancamento = criarReceita(1, new BigDecimal("1000.00"));

        carteira.addLancamento(lancamento);

        Lancamento encontrado =
                carteira.buscaPorId(lancamento.getId());

        assertSame(lancamento, encontrado);
    }

    @Test
    void deveRejeitarBuscaComIdInvalido() {
        Carteira carteira = new Carteira();

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.buscaPorId(0));

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.buscaPorId(-1));
    }

    @Test
    void deveRejeitarBuscaDeIdInexistente() {
        Carteira carteira = new Carteira();

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.buscaPorId(Integer.MAX_VALUE));
    }

    @Test
    void deveRemoverLancamentoExistente() {
        Carteira carteira = new Carteira();
        Lancamento lancamento = criarReceita(1, new BigDecimal("1000.00"));

        carteira.addLancamento(lancamento);

        boolean removido =
                carteira.removeLancamento(lancamento.getId());

        assertTrue(removido);
        assertTrue(carteira.mostraLancamentos().isEmpty());

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.buscaPorId(lancamento.getId()));
    }

    @Test
    void deveRetornarFalseAoRemoverIdInexistente() {
        Carteira carteira = new Carteira();

        assertFalse(
                carteira.removeLancamento(Integer.MAX_VALUE));
    }

    @Test
    void deveRejeitarRemocaoComIdInvalido() {
        Carteira carteira = new Carteira();

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.removeLancamento(0));

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.removeLancamento(-1));
    }

    @Test
    void deveCalcularSaldoCorretamente() {
        Carteira carteira = new Carteira();

        carteira.addLancamento(criarReceita(1, new BigDecimal("2000.00")));
        carteira.addLancamento(criarDespesa(2, new BigDecimal("300.00")));
        carteira.addLancamento(criarDespesa(3, new BigDecimal("200.00")));

        assertEquals(0, new BigDecimal("1500.00").compareTo(carteira.calculaSaldo()));
        }

    @Test
    void carteiraVaziaDeveTerSaldoZero() {
        Carteira carteira = new Carteira();
        assertEquals(0, BigDecimal.ZERO.compareTo(carteira.calculaSaldo()));
    }

    @Test
    void mostraLancamentosDeveRetornarCopiaDaLista() {
        Carteira carteira = new Carteira();

        Lancamento lancamento = criarReceita(1, new BigDecimal("1000.00"));
        carteira.addLancamento(lancamento);

        List<Lancamento> copia =
                carteira.mostraLancamentos();

        copia.clear();

        assertEquals(
                1,
                carteira.mostraLancamentos().size());

        assertSame(
                lancamento,
                carteira.mostraLancamentos().get(0));
    }

    @Test
    void deveFiltrarLancamentosUsandoFiltroInformado() {
        Carteira carteira = new Carteira();

        Lancamento receita = criarReceita(1, new BigDecimal("1000.00"));
        Lancamento despesa = criarDespesa(2, new BigDecimal("250.00"));

        carteira.addLancamento(receita);
        carteira.addLancamento(despesa);

        List<Lancamento> resultado =
                carteira.filtrarLancamentos(
                        new FiltroPorTipo(
                                TipoLancamento.RECEITA));

        assertEquals(1, resultado.size());
        assertSame(receita, resultado.get(0));
    }
    @Test
        void deveAlterarLancamentoExistente() {
        Carteira carteira = new Carteira();

        Lancamento original = criarReceita(1, new BigDecimal("1000.00") );

        Lancamento alterado = new Lancamento(1, TipoCategoria.MERCADO, LocalDate.of(2026, 8, 27), TipoMovimentacao.DEBITO, TipoLancamento.DESPESA, new BigDecimal("250.00") );

        carteira.addLancamento(original);

        boolean resultado = carteira.alteraLancamento(alterado);

        assertAll(
                () -> assertTrue(resultado),

                () -> assertEquals(1,carteira.mostraLancamentos().size()),
                () -> assertSame(alterado, carteira.buscaPorId(1)),

                () -> assertSame(alterado, carteira.mostraLancamentos().get(0))
        );
        }
}