package sistemaFinanceiro.modelo;
import java.util.*;
import java.time.*;

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

    public boolean mostraLancamentos(){
        if(lancamentos.isEmpty()){
            return false;
        }else{
            for (Lancamento l : lancamentos) {
                System.out.println(l);
            }
            return true;    
        }   
    }

    public List<Lancamento> loopFiltrarLancamentos(LocalDate data){
        List<Lancamento> aux = new ArrayList<>();

        for (Lancamento l : lancamentos) {
            if(data.equals(l.getData())){
                aux.add(l);
            }
        }
        return aux;
    }

    public List<Lancamento> loopFiltrarLancamentos_mes(int ano, int mes){
        List<Lancamento> aux = new ArrayList<>();
        
        for (Lancamento l : lancamentos) {
            if((l.getData().getMonthValue() == mes && l.getData().getYear() == ano)){
                aux.add(l);
            }
        }

        return aux;
    }
    
}
