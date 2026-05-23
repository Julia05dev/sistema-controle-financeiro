package sistemaFinanceiro.servico;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.*;
import sistemaFinanceiro.persistencia.*;
import sistemaFinanceiro.modelo.filtros.*;

public class SistemaFinanceiro{
    private Carteira carteira;
    private PersistenciaCSV persistencia;

    public SistemaFinanceiro() throws IOException{
        this.carteira = new Carteira();
        this.persistencia = new PersistenciaCSV();
        carteira.addListaLancamentos(persistencia.carregarCsvPraLista());
    }
    //Carteira carteira = new Carteira();
    //PersistenciaCSV persistencia = new PersistenciaCSV();

    public List<Lancamento> mostraLancamentos() throws IOException{
        return carteira.mostraLancamentos();
    }

    public boolean removeLancamento(int id) throws IOException{
        boolean remove = carteira.removeLancamento(id);
        if(remove)
            persistencia.salvar(carteira.mostraLancamentos());
        return remove;
    }

    public double calculaSaldo(){
        return carteira.calculaSaldo();
    }

    public void criaLancamento(TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, double valor) throws IOException{
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);
        carteira.addLancamento(novoLancamento);
        persistencia.salvar(carteira.mostraLancamentos());
    }

    //criaLancamentoAPartirDeCSV(List<lancamento>) --> faz um loop e chama buscaPorID. Se nao existir, add na carteira. Se existir pula

    //------------FILTROS------------

    //data
    public List<Lancamento> filtrarPorData(int dia, int mes, int ano){
        FiltroLancamento filtro = new FiltroPorData(dia, mes, ano);
        return carteira.filtrarLancamentos(filtro);
    }

    //tipo
    public List<Lancamento> filtrarPorTipo(TipoLancamento tipo){
        FiltroLancamento filtro = new FiltroPorTipo(tipo);
        return carteira.filtrarLancamentos(filtro);
    }
    
    //categoria
    public List<Lancamento> filtrarPorCategoria(TipoCategoria categoria){
        FiltroLancamento filtro = new FiltroPorCategoria(categoria);
        return carteira.filtrarLancamentos(filtro);
    }

    //movimentacao
    public List<Lancamento> filtrarPorMovimentacao(TipoLancamento tipo, TipoMovimentacao movimentacao){
        FiltroLancamento filtro = new FiltroPorMovimentacao(tipo, movimentacao);
        return carteira.filtrarLancamentos(filtro);
    } 
}
