package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.*;

public class FiltroPorTipo implements FiltroLancamento {

    private final TipoLancamento tipo;

    public FiltroPorTipo(TipoLancamento tipo) {     
        this.tipo = tipo;
    }

    @Override
    public boolean filtrar(Lancamento lancamento) {     
        return lancamento.getTipo() == tipo;
    }
}
