package sistemaFinanceiro.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

public class LancamentoTest {

    @Test
    void deveCriarReceitaComValorPositivo() {
        Lancamento lancamento = new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                new BigDecimal("1000.00"));

        assertEquals(
                0,
                new BigDecimal("1000.00")
                        .compareTo(lancamento.getValor()));

        assertEquals(
                TipoLancamento.RECEITA,
                lancamento.getTipo());

        assertEquals(
                TipoCategoria.EMPREGO,
                lancamento.getCategoria());

        assertEquals(
                TipoMovimentacao.PIX,
                lancamento.getmeioDeMovimentacao());

        assertEquals(
                LocalDate.of(2026, 8, 14),
                lancamento.getData());
    }

    @Test
    void deveCriarDespesaComValorPositivo() {
        Lancamento lancamento = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                new BigDecimal("250.00"));

        assertEquals(
                0,
                new BigDecimal("250.00")
                        .compareTo(lancamento.getValor()));
    }

    @Test
    void deveManterIdInformadoNoConstrutorComId() {
        Lancamento lancamento = new Lancamento(
                500,
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                new BigDecimal("100.00"));

        assertEquals(500, lancamento.getId());
    }

    @Test
    void deveRejeitarValorZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.PIX,
                        TipoLancamento.RECEITA,
                        BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarValorNegativo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.PIX,
                        TipoLancamento.RECEITA,
                        new BigDecimal("-10.00")));
    }

    @Test
    void deveRejeitarCategoriaIncompativelComTipo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.MERCADO,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.PIX,
                        TipoLancamento.RECEITA,
                        new BigDecimal("100.00")));
    }

    @Test
    void deveRejeitarMovimentacaoIncompativelComTipo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.DEBITO,
                        TipoLancamento.RECEITA,
                        new BigDecimal("100.00")));
    }

    @Test
    void deveRejeitarCategoriaNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        null,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.PIX,
                        TipoLancamento.RECEITA,
                        new BigDecimal("100.00")));
    }

    @Test
    void deveRejeitarDataNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        null,
                        TipoMovimentacao.PIX,
                        TipoLancamento.RECEITA,
                        new BigDecimal("100.00")));
    }

    @Test
    void deveRejeitarMovimentacaoNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        LocalDate.of(2026, 8, 14),
                        null,
                        TipoLancamento.RECEITA,
                        new BigDecimal("100.00")));
    }

    @Test
    void deveRejeitarTipoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Lancamento(
                        TipoCategoria.EMPREGO,
                        LocalDate.of(2026, 8, 14),
                        TipoMovimentacao.PIX,
                        null,
                        new BigDecimal("100.00")));
    }
    
}