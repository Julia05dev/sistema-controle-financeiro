package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.*;

public class FiltroPorCategoria implements FiltroLancamento{
    private final TipoCategoria categoria;

    public FiltroPorCategoria(TipoCategoria categoria){
        this.categoria = categoria;
    }

    @Override
    public boolean filtrar(Lancamento lancamento){
        return lancamento.getCategoria() == categoria;
    }
}
