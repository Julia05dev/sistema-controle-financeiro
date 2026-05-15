package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

public class FiltroPorMovimentacao implements FiltroLancamento{
    private final TipoLancamento tipo;
    public final TipoMovimentacao movimentacao;
    public FiltroPorMovimentacao(TipoLancamento tipo, TipoMovimentacao movimentacao){
        this.tipo = tipo;
        this.movimentacao = movimentacao;
    }

    @Override
    public boolean filtrar(Lancamento lancamento){
        if(lancamento.getTipo() != tipo)
            return false;
        return lancamento.getmeioDeMovimentacao() == movimentacao;
    }
}
