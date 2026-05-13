package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.enums.*;

public class FiltroPorTipo implements FiltroLancamento {

    private final TipoLancamento tipo;

    public FiltroPorTipo(TipoLancamento tipo) {     //construtor que armazena o tipo que o usuário quer filtrar
        this.tipo = tipo;
    }

    @Override
    public boolean filtrar(Lancamento lancamento) {     //daí chama o método da interface
        return lancamento.getTipo() == tipo;
    }

    //classe carteira vai chamar esse método e passar lançamento por lançamento, tendo ao fim um arraylist com todos os 
        //lançamentos que atendem ao filtro selecionado
}

//como a lógica está sendo feita com interface, em breve vou conseguir implementar uma combinaçao entre os filtros!