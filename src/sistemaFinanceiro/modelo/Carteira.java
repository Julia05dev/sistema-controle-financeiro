package sistemaFinanceiro.modelo;
import java.util.*;
import java.time.*;

public class Carteira {
    private String instituicao;
    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

    //metodos : add / rmv lançamentos, mostrar lançamentos, calcular saldo

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
        System.out.println("id inexistente");
        return false;
    }

    public double calculaSaldo(){
        double saldo = 0.0;
        for (Lancamento l : lancamentos) {
            saldo += l.getValor();  //tem que garantir que os valores de despesa são AUTOMATICAMENTE registrados com valor negativo
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
        
    }
}
