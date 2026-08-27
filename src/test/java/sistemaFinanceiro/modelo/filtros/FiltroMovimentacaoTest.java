package sistemaFinanceiro.modelo.filtros;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

class FiltroMovimentacaoTest {

    @Test
    void deveAceitarQuandoTipoEMovimentacaoCorrespondem() {
        Lancamento receita = new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                new BigDecimal("1000.00"));

        FiltroPorMovimentacao filtro =
                new FiltroPorMovimentacao(
                        TipoLancamento.RECEITA,
                        TipoMovimentacao.PIX);

        assertTrue(filtro.filtrar(receita));
    }

    @Test
    void deveRejeitarQuandoMovimentacaoDiferente() {
        Lancamento receita = new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DINHEIRO,
                TipoLancamento.RECEITA,
                new BigDecimal("1000.00"));

        FiltroPorMovimentacao filtro =
                new FiltroPorMovimentacao(
                        TipoLancamento.RECEITA,
                        TipoMovimentacao.PIX);

        assertFalse(filtro.filtrar(receita));
    }

    @Test
    void deveRejeitarQuandoTipoDiferenteMesmoComMesmaMovimentacao() {
        Lancamento despesa = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.DESPESA,
                new BigDecimal("100.00"));

        FiltroPorMovimentacao filtro =
                new FiltroPorMovimentacao(
                        TipoLancamento.RECEITA,
                        TipoMovimentacao.PIX);

        assertFalse(filtro.filtrar(despesa));
    }
}