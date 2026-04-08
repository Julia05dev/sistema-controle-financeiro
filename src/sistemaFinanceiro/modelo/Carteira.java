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

    public void criaLancamento(String categoria, LocalDate data, String meioDeMovimentacao, String tipo, double valor){
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);
        addLancamento(novoLancamento);
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

    public void filtraLancamentos(int ano, int mes, int dia){
        LocalDate data = LocalDate.of(ano, mes, dia);
        int cont = 0;

        for (Lancamento l : lancamentos) {
            if(data.equals(l.getData())){
                System.out.println(l);
                cont ++;
            }
                
        }
        if(cont == 0)   
            System.out.println("Nenhum lancamento nessa data!");
    }

    public void filtrarLancamentosMes(int ano, int mes){
        int cont = 0;

        for (Lancamento l : lancamentos) {
            if((l.getData().getMonthValue() == mes && l.getData().getYear() == ano)){
                System.out.println(l);
                cont++;
            }
        }
        if(cont == 0)
            System.out.println("Nenhum lancamento nesse mes!");
    }
}
