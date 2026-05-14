package sistemaFinanceiro.modelo;
import java.util.*;
import sistemaFinanceiro.modelo.filtros.*;
import sistemaFinanceiro.modelo.enums.*;

public class Carteira {
    private final List<Lancamento> lancamentos = new ArrayList<>(); //final deixa claro que a carteira sempre trabalha com a mesma lista

    public void addLancamento(Lancamento lancam){
        if(lancam == null)
            throw new IllegalArgumentException("o lançamento não pode ser nulo!");
        lancamentos.add(lancam);
    }

    public boolean removeLancamento(int id){
        if(id < 0)
            throw new IllegalArgumentException();
        for (int i = 0; i<lancamentos.size(); i++) {
            if(lancamentos.get(i).getId() == id){
                lancamentos.remove(i);
                return true;
            }       
        }
        return false;   
    }

    public double calculaSaldo(){
        double saldo = 0.0;
        for (Lancamento l : lancamentos) {
            saldo += l.getValor(); 
    }
        return saldo;
    }

    public List<Lancamento> mostraLancamentos(){
        List<Lancamento> resultado = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            resultado.add(l);
        }  

        return resultado;
    }
    
    //------------FILTROS------------

    //data
    public List<Lancamento> filtrarPorData(int dia, int mes, int ano){
        List<Lancamento> aux = new ArrayList<>();
        FiltroPorData filtro = new FiltroPorData(dia, mes, ano);

        for (Lancamento l : lancamentos) {
            if(filtro.filtrar(l))
                aux.add(l);
        }
        return aux;
    }

    //tipo
    public List<Lancamento> filtrarPorTipo(TipoLancamento tipo){
        List<Lancamento> aux = new ArrayList<>();
        FiltroPorTipo filtro = new FiltroPorTipo(tipo);
        
        for (Lancamento l : lancamentos) {
            if(filtro.filtrar(l))
                aux.add(l);
        }

        return aux;
    }
    
    //categoria
    public List<Lancamento> filtrarPorCategoria(TipoCategoria categoria){
        List<Lancamento> aux = new ArrayList<>();
        FiltroPorCategoria filtro = new FiltroPorCategoria(categoria);

        for(Lancamento l : lancamentos){
            if(filtro.filtrar(l))
                aux.add(l);
        }

        return aux;
    }

    //movimentacao
    public List<Lancamento> filtrarPorMovimentacao(TipoMovimentacao movimentacao){
        List<Lancamento> aux = new ArrayList<>();
        FiltroPorMovimentacao filtro = new FiltroPorMovimentacao(movimentacao);

        for(Lancamento l : lancamentos){
            if(filtro.filtrar(l))
                aux.add(l);
        }

        return aux;
    } 
}
