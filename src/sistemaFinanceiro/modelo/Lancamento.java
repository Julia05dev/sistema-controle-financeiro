package sistemaFinanceiro.modelo;
//import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private LocalDate data;
    private static int contador = 0;
    private int id;
    private double valor;
    private String tipo;    //receita ou despesa
    private String categoria;
    private String meioDePagamento;

    public Lancamento(String categoria, LocalDate data, String meioDePagamento, String tipo, double valor) {
        if(categoria == null || data == null || meioDePagamento == null || tipo == null || valor == 0.0)
            throw new IllegalArgumentException();
        contador++;
        this.categoria = categoria;
        this.data = data;
        this.meioDePagamento = meioDePagamento;
        this.tipo = tipo;
        if(tipo.equalsIgnoreCase("receita"))
            this.valor = valor;
        else if(tipo.equalsIgnoreCase("despesa"))
            this.valor = -valor;
        this.id = contador;
    }
    
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada;
        if(data != null)
            dataFormatada = data.format(formatter);
        else 
            dataFormatada = "-";

        String str = "["+id+"]" + " | " + tipo + " | " + categoria + " | " + "R$"+valor + " | " + dataFormatada + " | " + meioDePagamento;
        return str;
    }

    public LocalDate getData(){
        return data;
    }

    public int getId() {
        return id;
    }
    //como o ID é definido pelo sistema, não é seguro ter um setId que permite qualquer lugar do código altera-lo

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMeioDePagamento() {
        return meioDePagamento;
    }
    public void setMeioDePagamento(String meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }    

}
