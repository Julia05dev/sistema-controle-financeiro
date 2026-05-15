package sistemaFinanceiro.servico;
import java.time.LocalDate;
import java.util.List;

import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.filtros.*;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

public class SistemaFinanceiro{
    Carteira carteira = new Carteira();

    public List<Lancamento> mostraLancamentos(){
        return carteira.mostraLancamentos();
    }

    public boolean removeLancamento(int id){
        return carteira.removeLancamento(id);
    }

    public double calculaSaldo(){
        return carteira.calculaSaldo();
    }

    public void criaLancamento(TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, double valor){
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);
        carteira.addLancamento(novoLancamento);
    }

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
