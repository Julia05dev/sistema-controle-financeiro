package sistemaFinanceiro.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;
import sistemaFinanceiro.modelo.filtros.FiltroPorTipo;

class CarteiraTest {

    private Lancamento criarReceita(double valor) {
        return new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                valor);
    }

    private Lancamento criarDespesa(double valor) {
        return new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                valor);
    }

    @Test
    void deveAdicionarLancamento() {
        Carteira carteira = new Carteira();
        Lancamento lancamento = criarReceita(1000.0);

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

        Lancamento receita = criarReceita(1000.0);
        Lancamento despesa = criarDespesa(250.0);

        carteira.addListaLancamentos(List.of(receita, despesa));

        assertEquals(2, carteira.mostraLancamentos().size());
        assertSame(receita, carteira.buscaPorId(receita.getId()));
        assertSame(despesa, carteira.buscaPorId(despesa.getId()));
    }

    @Test
    void deveBuscarLancamentoPorId() {
        Carteira carteira = new Carteira();
        Lancamento lancamento = criarReceita(1000.0);

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
        Lancamento lancamento = criarReceita(1000.0);

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

        carteira.addLancamento(criarReceita(2000.0));
        carteira.addLancamento(criarDespesa(300.0));
        carteira.addLancamento(criarDespesa(200.0));

        assertEquals(
                1500.0,
                carteira.calculaSaldo(),
                0.0001);
    }

    @Test
    void carteiraVaziaDeveTerSaldoZero() {
        Carteira carteira = new Carteira();

        assertEquals(
                0.0,
                carteira.calculaSaldo(),
                0.0001);
    }

    @Test
    void mostraLancamentosDeveRetornarCopiaDaLista() {
        Carteira carteira = new Carteira();

        Lancamento lancamento = criarReceita(1000.0);
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

        Lancamento receita = criarReceita(1000.0);
        Lancamento despesa = criarDespesa(250.0);

        carteira.addLancamento(receita);
        carteira.addLancamento(despesa);

        List<Lancamento> resultado =
                carteira.filtrarLancamentos(
                        new FiltroPorTipo(
                                TipoLancamento.RECEITA));

        assertEquals(1, resultado.size());
        assertSame(receita, resultado.get(0));
    }
}