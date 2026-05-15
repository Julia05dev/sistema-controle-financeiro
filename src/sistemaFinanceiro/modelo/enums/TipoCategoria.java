package sistemaFinanceiro.modelo.enums;

public enum TipoCategoria {
    //receita
    EMPREGO("Emprego", TipoLancamento.RECEITA),
    FREELANCE("Freelance", TipoLancamento.RECEITA),
    PRESENTE("Presente", TipoLancamento.RECEITA),
    //despesa
    MERCADO("Mercado", TipoLancamento.DESPESA),
    CONTAS("Contas", TipoLancamento.DESPESA),
    BELEZA("Beleza", TipoLancamento.DESPESA),
    LAZER("Lazer", TipoLancamento.DESPESA),
    FARMACIA("Farmácia", TipoLancamento.DESPESA);

    private final TipoLancamento tipoLancamento;
    private final String descricao;

    private TipoCategoria(String descricao, TipoLancamento tipoLancamento) {
        this.descricao = descricao;
        this.tipoLancamento = tipoLancamento;
    }

    @Override
    public String toString(){
        return descricao;
    }

    public static TipoCategoria fromIntReceita(int opcao){
        switch(opcao){
            case 1 -> {return EMPREGO;}
            case 2-> {return FREELANCE;}
            case 3 -> {return PRESENTE;}
            default -> {throw new IllegalArgumentException("Opcao invalida!");}
        }
    }

    public static TipoCategoria fromIntDespesa(int opcao){
        switch(opcao){
            case 1 -> {return MERCADO;}
            case 2-> {return CONTAS;}
            case 3 -> {return BELEZA;}
            case 4 -> {return LAZER;}
            case 5 -> {return FARMACIA;}
            default -> {throw new IllegalArgumentException("Opcao invalida!");}
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean aceitaTipo(TipoLancamento tipo) {
        return this.tipoLancamento == tipo;
    }
}
