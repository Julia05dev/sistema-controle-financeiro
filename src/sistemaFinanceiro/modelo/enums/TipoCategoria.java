package sistemaFinanceiro.modelo.enums;

public enum TipoCategoria {
   //receita
    EMPREGO,
    FREELANCE,
    PRESENTE,
    //despesa
    MERCADO,
    CONTAS,
    BELEZA,
    LAZER,
    FARMACIA;

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
            case 4 -> {return FARMACIA;}
            default -> {throw new IllegalArgumentException("Opcao invalida!");}
        }
    }
}
