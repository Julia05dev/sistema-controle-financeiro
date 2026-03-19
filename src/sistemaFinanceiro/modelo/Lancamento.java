package sistemaFinanceiro.modelo;

public class Lancamento {
    private String data;
    private int id;
    private double valor;
    private String tipo;
    private String categoria;
    private String descricao;   //n sei se faz sentido manter
    private String meioDePagamento;

    @Override
    public String toString(){
        String str = "["+id+"]" + " | " + tipo + " | " + categoria + " | " + "R$"+valor + " | " + data + " | " + meioDePagamento;
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

    public String getCateoria() {
        return categoria;
    }
    public void setCateoria(String cateoria) {
        this.categoria = cateoria;
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

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    

}
