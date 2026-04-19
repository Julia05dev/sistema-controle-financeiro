package sistemaFinanceiro.modelo;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private LocalDate data;
    private static int contador = 0;
    private int id;
    private double valor;
    private String tipo; //receita ou despesa
    private String categoria;
    private String meioDeMovimentacao;

    public Lancamento(String categoria, LocalDate data, String meioDeMovimentacao, String tipo, double valor) {
        if(categoria == null || data == null || meioDeMovimentacao == null || tipo == null || valor == 0.0)
            throw new IllegalArgumentException();
        
        if(categoria.isBlank() || tipo.isBlank() || meioDeMovimentacao.isBlank())
            throw new IllegalArgumentException("favor nao deixar campos em branco");

        if(tipo.equalsIgnoreCase("receita"))
            this.valor = valor;
        else if(tipo.equalsIgnoreCase("despesa"))
            this.valor = -valor;
        else{
            throw new IllegalArgumentException("favor escolher apenas entre receita e despesa");
        }

        this.categoria = categoria;
        this.data = data;
        this.meioDeMovimentacao = meioDeMovimentacao;
        this.tipo = tipo;

        contador++;
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

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getmeioDeMovimentacao() {
        return meioDeMovimentacao;
    } 

}
