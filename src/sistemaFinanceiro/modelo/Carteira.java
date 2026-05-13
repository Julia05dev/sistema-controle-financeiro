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

    public List<Lancamento> mostraLancamentos(){
        List<Lancamento> resultado = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            resultado.add(l);
        }  

        return resultado;
    }

    public List<Lancamento> FiltrarLancamentos(LocalDate data){
        List<Lancamento> aux = new ArrayList<>();

        for (Lancamento l : lancamentos) {
            if(data.equals(l.getData())){
                aux.add(l);
            }
        }
        return aux;
    }

    public List<Lancamento> FiltrarLancamentos_mes(LocalDate data){
        List<Lancamento> aux = new ArrayList<>();
        
        for (Lancamento l : lancamentos) {
            if((l.getData().getMonthValue() == data.getMonthValue() && l.getData().getYear() == data.getYear())){
                aux.add(l);
            }
        }

        return aux;
    }

    
    
}
