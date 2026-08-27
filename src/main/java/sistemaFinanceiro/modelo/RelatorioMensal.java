package sistemaFinanceiro.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import sistemaFinanceiro.modelo.enums.TipoCategoria;

public class RelatorioMensal {

    private final int mes;
    private final int ano;
    private final BigDecimal totalReceitas;
    private final BigDecimal totalDespesas;
    private final Map<TipoCategoria, BigDecimal> receitasPorCategoria;
    private final Map<TipoCategoria, BigDecimal> despesasPorCategoria;
    private final List<Lancamento> lancamentos;

    public RelatorioMensal(
        int mes,
        int ano,
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        Map<TipoCategoria, BigDecimal> receitasPorCategoria,
        Map<TipoCategoria, BigDecimal> despesasPorCategoria,
        List<Lancamento> lancamentos) {
            this.mes = mes;
            this.ano = ano;
            this.totalReceitas = totalReceitas;
            this.totalDespesas = totalDespesas;
            this.receitasPorCategoria = new LinkedHashMap<>(receitasPorCategoria);
            this.despesasPorCategoria = new LinkedHashMap<>(despesasPorCategoria);
            this.lancamentos = new ArrayList<>(lancamentos);
        }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public BigDecimal getSaldo() {
        return totalReceitas.subtract(totalDespesas);
    }

    public Map<TipoCategoria, BigDecimal>
    getReceitasPorCategoria() {
        return new LinkedHashMap<>(receitasPorCategoria);
    }

    public Map<TipoCategoria, BigDecimal>
    getDespesasPorCategoria() {
        return new LinkedHashMap<>(despesasPorCategoria);
    }

    public List<Lancamento> getLancamentos() {
        return new ArrayList<>(lancamentos);
    }
}
