package sistemaFinanceiro.modelo;
import java.util.*;
import java.time.*;

public class Carteira {
    //private String instituicao;
    private final List<Lancamento> lancamentos = new ArrayList<>(); //final deixa claro que a carteira sempre trabalha com a mesma lista

    //metodos : add / rmv lançamentos, mostrar lançamentos, calcular saldo

    public void addLancamento(Lancamento lancam){
        if(lancam == null)
            throw new IllegalArgumentException("o lançamento não pode ser nulo!");
        lancamentos.add(lancam);
    }

    public void criaLancamento(String categoria, LocalDate data, String meioDePagamento, String tipo, double valor){
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDePagamento, tipo, valor);
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
        return false;   //melhor retornar boolean e deixar o print pra main (não foi possivel loclaizar o lançamento)
    }

    public double calculaSaldo(){
        double saldo = 0.0;
        for (Lancamento l : lancamentos) {
            saldo += l.getValor(); 
    }
        return saldo;
    }

    public void mostraLancamentos(){
        if(lancamentos.isEmpty()){
            System.out.println("nenhum lançamento cadastrado!");
            return;
        }
        for (Lancamento l : lancamentos) {
            System.out.println(l);
        }
    }

    public void filtraLancamentos(int ano, int mes, int dia){
        LocalDate data = LocalDate.of(ano, mes, dia);

        for (Lancamento l : lancamentos) {
            if(data.equals(l.getData()))
                System.out.println(l);
        }
    }

    public void filtrarLancamentosMes(int ano, int mes){
        for (Lancamento l : lancamentos) {
            if((l.getData().getMonthValue() == mes && l.getData().getYear() == ano))
                System.out.println(l);
        }
    }
}
