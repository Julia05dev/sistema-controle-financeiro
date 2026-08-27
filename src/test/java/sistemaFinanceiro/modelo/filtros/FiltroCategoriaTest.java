package sistemaFinanceiro.modelo.filtros;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

class FiltroCategoriaTest {

    @Test
    void deveAceitarLancamentoDaCategoriaInformada() {
        Lancamento lancamento = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                new BigDecimal("100.00"));

        FiltroPorCategoria filtro =
                new FiltroPorCategoria(
                        TipoCategoria.MERCADO);

        assertTrue(filtro.filtrar(lancamento));
    }

    @Test
    void deveRejeitarLancamentoDeCategoriaDiferente() {
        Lancamento lancamento = new Lancamento(
                TipoCategoria.CONTAS,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                new BigDecimal("100.00"));

        FiltroPorCategoria filtro =
                new FiltroPorCategoria(
                        TipoCategoria.MERCADO);

        assertFalse(filtro.filtrar(lancamento));
    }
}