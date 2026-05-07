package sistemaFinanceiro.modelo.enums;

public enum TipoLancamento {
    RECEITA("Receita"),
    DESPESA("Despesa");
    private final String descricao;

    private TipoLancamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return descricao;
    }

    public static TipoLancamento fromInt (int opcao){
        switch(opcao){
            case 1 -> {return RECEITA;}
            case 2 -> {return DESPESA;}
            default -> {throw new IllegalArgumentException("Opcao invalida");}
        }
    }

    public String getDescricao() {
        return descricao;
    }
}