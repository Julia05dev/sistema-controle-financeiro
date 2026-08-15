package sistemaFinanceiro.modelo;
import static org.junit.jupiter.api.Assertions.*;
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
                1000.0);

        assertEquals(1000.0, lancamento.getValor(), 0.0001);
        assertEquals(TipoLancamento.RECEITA, lancamento.getTipo());
        assertEquals(TipoCategoria.EMPREGO, lancamento.getCategoria());
        assertEquals(TipoMovimentacao.PIX, lancamento.getmeioDeMovimentacao());
        assertEquals(LocalDate.of(2026, 8, 14), lancamento.getData());
    }

    @Test
    void deveCriarDespesaComValorNegativo() {
        Lancamento lancamento = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                250.0);

        assertEquals(-250.0, lancamento.getValor(), 0.0001);
    }

    @Test
    void deveManterIdInformadoNoConstrutorComId() {
        Lancamento lancamento = new Lancamento(
                500,
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                100.0);

        assertEquals(500, lancamento.getId());
    }

    @Test
    void deveRejeitarValorZero() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                0.0));
    }

    @Test
    void deveRejeitarValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                -10.0));
    }

    @Test
    void deveRejeitarCategoriaIncompativelComTipo() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                100.0));
    }

    @Test
    void deveRejeitarMovimentacaoIncompativelComTipo() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.RECEITA,
                100.0));
    }

    @Test
    void deveRejeitarCategoriaNula() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                null,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                100.0));
    }

    @Test
    void deveRejeitarDataNula() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                null,
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                100.0));
    }

    @Test
    void deveRejeitarMovimentacaoNula() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                null,
                TipoLancamento.RECEITA,
                100.0));
    }

    @Test
    void deveRejeitarTipoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                null,
                100.0));
    }
}
