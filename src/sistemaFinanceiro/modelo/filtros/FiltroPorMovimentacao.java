package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

public class FiltroPorMovimentacao implements FiltroLancamento{
    public final TipoMovimentacao movimentacao;
    public FiltroPorMovimentacao(TipoMovimentacao movimentacao){
        this.movimentacao = movimentacao;
    }

    @Override
    public boolean filtrar(Lancamento lancamento){
        return lancamento.getmeioDeMovimentacao() == movimentacao;
    }
}
