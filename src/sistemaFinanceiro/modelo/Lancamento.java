package sistemaFinanceiro.modelo;
//import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    private LocalDate data;
    private int id;
    private double valor;
    private String tipo;
    private String categoria;
    private String descricao;   //n sei se faz sentido manter
    private String meioDePagamento;

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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMeioDePagamento() {
        return meioDePagamento;
    }
    public void setMeioDePagamento(String meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }    

}
