package sistemaFinanceiro.modelo.filtros;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

class FiltroDataTest {

    private Lancamento criarLancamentoEm(LocalDate data) {
        return new Lancamento(
                TipoCategoria.MERCADO,
                data,
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                new BigDecimal("100.00"));
    }

    @Test
    void deveAceitarDataCompletaCorrespondente() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        FiltroPorData filtro =
                new FiltroPorData(14, 8, 2026);

        assertTrue(filtro.filtrar(lancamento));
    }

    @Test
    void deveRejeitarDataCompletaDiferente() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        FiltroPorData filtro =
                new FiltroPorData(13, 8, 2026);

        assertFalse(filtro.filtrar(lancamento));
    }

    @Test
    void deveRejeitarDataCompletaInvalida() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 2, 28));

        FiltroPorData filtro =
                new FiltroPorData(31, 2, 2026);

        assertFalse(filtro.filtrar(lancamento));
    }

    @Test
    void deveFiltrarSomentePorDia() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        assertTrue(
                new FiltroPorData(
                        14, 0, 0)
                        .filtrar(lancamento));

        assertFalse(
                new FiltroPorData(
                        13, 0, 0)
                        .filtrar(lancamento));
    }

    @Test
    void deveFiltrarSomentePorMes() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        assertTrue(
                new FiltroPorData(
                        0, 8, 0)
                        .filtrar(lancamento));

        assertFalse(
                new FiltroPorData(
                        0, 7, 0)
                        .filtrar(lancamento));
    }

    @Test
    void deveFiltrarSomentePorAno() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        assertTrue(
                new FiltroPorData(
                        0, 0, 2026)
                        .filtrar(lancamento));

        assertFalse(
                new FiltroPorData(
                        0, 0, 2025)
                        .filtrar(lancamento));
    }

    @Test
    void deveFiltrarPorMesEAno() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        assertTrue(
                new FiltroPorData(
                        0, 8, 2026)
                        .filtrar(lancamento));

        assertFalse(
                new FiltroPorData(
                        0, 8, 2025)
                        .filtrar(lancamento));
    }

    @Test
    void filtroSemCriterioDeDataDeveAceitarLancamento() {
        Lancamento lancamento =
                criarLancamentoEm(
                        LocalDate.of(2026, 8, 14));

        assertTrue(
                new FiltroPorData(
                        0, 0, 0)
                        .filtrar(lancamento));
    }
}