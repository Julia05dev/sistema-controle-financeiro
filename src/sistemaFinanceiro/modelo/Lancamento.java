package sistemaFinanceiro.modelo;

public class Lancamento {
    //data
    private int id;
    private double valor;
    private String tipo;
    private String cateoria;
    private String descricao;
    private String meioDePagamento;

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

    public String getCateoria() {
        return cateoria;
    }
    public void setCateoria(String cateoria) {
        this.cateoria = cateoria;
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
