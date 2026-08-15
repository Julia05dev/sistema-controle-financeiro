package sistemaFinanceiro.modelo;
import java.time.*;
import java.time.format.DateTimeFormatter;

import sistemaFinanceiro.modelo.enums.*;

public class Lancamento {
    private final LocalDate data;
    private static int contador = 0;
    private final int id;
    private final double valor;
    private final TipoLancamento tipo; //receita ou despesa
    private final TipoCategoria categoria;
    private final TipoMovimentacao meioDeMovimentacao;

    public Lancamento(TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, double valor) {
        if(categoria == null || data == null || meioDeMovimentacao == null || tipo == null || valor <= 0.0)
            throw new IllegalArgumentException();

        if (!categoria.aceitaTipo(tipo)) {
            throw new IllegalArgumentException("categoria incompatível com o tipo de lançamento.");
        }

        if (!meioDeMovimentacao.aceitaTipo(tipo)) {
            throw new IllegalArgumentException("meio de movimentação incompatível com o tipo de lançamento.");
        }

        switch(tipo){
            case RECEITA -> this.valor = valor;
            case DESPESA -> this.valor = -valor;
            default -> throw new IllegalArgumentException("favor selecionar entre receita ou despesa");
        }

        this.categoria = categoria;
        this.data = data;
        this.meioDeMovimentacao = meioDeMovimentacao;
        this.tipo = tipo;

        contador++;
        this.id = contador;
    }
    public Lancamento(int id, TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, double valor) {
    if(categoria == null || data == null || meioDeMovimentacao == null || tipo == null || valor <= 0.0)
        throw new IllegalArgumentException();

    if (!categoria.aceitaTipo(tipo)) {
        throw new IllegalArgumentException("categoria incompatível com o tipo de lançamento.");
    }

    if (!meioDeMovimentacao.aceitaTipo(tipo)) {
        throw new IllegalArgumentException("meio de movimentação incompatível com o tipo de lançamento.");
    }

    switch(tipo){
        case RECEITA -> this.valor = valor;
        case DESPESA -> this.valor = -valor;
        default -> throw new IllegalArgumentException("favor selecionar entre receita ou despesa");
    }

    this.categoria = categoria;
    this.data = data;
    this.meioDeMovimentacao = meioDeMovimentacao;
    this.tipo = tipo;
    
    this.id = id;
    if(id > contador)
        contador = id;
    }
    
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada;
        if(data != null)
            dataFormatada = data.format(formatter);
        else 
            dataFormatada = "-";

        String str = "["+id+"]" + " | " + tipo + " | " + categoria + " | " + "R$"+valor + " | " + dataFormatada + " | " + meioDeMovimentacao;
        return str;
    }

    public LocalDate getData(){
        return data;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public TipoCategoria getCategoria() {
        return categoria;
    }

    public TipoMovimentacao getmeioDeMovimentacao() {
        return meioDeMovimentacao;
    } 

}
