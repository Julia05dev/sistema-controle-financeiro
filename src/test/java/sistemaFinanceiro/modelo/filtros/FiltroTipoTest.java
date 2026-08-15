package sistemaFinanceiro.modelo.filtros;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

class FiltroTipoTest {

    @Test
    void deveAceitarLancamentoDoTipoInformado() {
        Lancamento receita = new Lancamento(
                TipoCategoria.EMPREGO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.PIX,
                TipoLancamento.RECEITA,
                1000.0);

        FiltroPorTipo filtro =
                new FiltroPorTipo(TipoLancamento.RECEITA);

        assertTrue(filtro.filtrar(receita));
    }

    @Test
    void deveRejeitarLancamentoDeTipoDiferente() {
        Lancamento despesa = new Lancamento(
                TipoCategoria.MERCADO,
                LocalDate.of(2026, 8, 14),
                TipoMovimentacao.DEBITO,
                TipoLancamento.DESPESA,
                100.0);

        FiltroPorTipo filtro =
                new FiltroPorTipo(TipoLancamento.RECEITA);

        assertFalse(filtro.filtrar(despesa));
    }
}