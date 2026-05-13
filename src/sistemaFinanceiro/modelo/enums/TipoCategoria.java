package sistemaFinanceiro.modelo.enums;

public enum TipoCategoria {
   //receita
    EMPREGO("Emprego"),
    FREELANCE("Freelance"),
    PRESENTE("Presente"),
    //despesa
    MERCADO("Mercado"),
    CONTAS("Contas"),
    BELEZA("Beleza"),
    LAZER("Lazer"),
    FARMACIA("Farmácia");
    private final String descricao;

    private TipoCategoria(String descricao) {
        this.descricao = descricao;
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
}
