package sistemaFinanceiro.modelo.enums;

public enum TipoMovimentacao {
    DEBITO("Debito"),
    CREDITO("Credito"),
    PIX("Pix"),
    DINHEIRO("Dinheiro"),
    TRANSFERENCIA("Transferencia");
    private final String descricao;

    private TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return descricao;
    }

    public static TipoMovimentacao fromIntReceita(int opcao){
        switch(opcao){
            case 1 -> {return PIX;}
            case 2-> {return DINHEIRO;}
            case 3 -> {return TRANSFERENCIA;}
            default -> {throw new IllegalArgumentException("Opcao invalida!");}
        }
    }

    public static TipoMovimentacao fromIntDespesa(int opcao){
        switch(opcao){
            case 1 -> {return DEBITO;}
            case 2-> {return CREDITO;}
            case 3 -> {return PIX;}
            case 4 -> {return DINHEIRO;}
            case 5 -> {return TRANSFERENCIA;}
            default -> {throw new IllegalArgumentException("Opcao invalida!");}
        }
    }

    public String getDescricao() {
        return descricao;
    }
}
