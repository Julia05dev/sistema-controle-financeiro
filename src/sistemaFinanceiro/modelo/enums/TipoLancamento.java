package sistemaFinanceiro.modelo.enums;


public enum TipoLancamento {
    RECEITA,
    DESPESA;

    public static TipoLancamento fromInt (int opcao){
        switch(opcao){
            case 1 -> {return RECEITA;}
            case 2 -> {return DESPESA;}
            default -> {throw new IllegalArgumentException("Opcao invalida");}
        }
    }
}