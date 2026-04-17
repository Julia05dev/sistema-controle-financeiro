package sistemaFinanceiro.servico;
import java.time.LocalDate;
import java.util.List;

import sistemaFinanceiro.modelo.*;

public class SistemaFinanceiro{
    Carteira carteira = new Carteira();

    public boolean mostraLancamentos(){
        return carteira.mostraLancamentos();
    }

    public boolean removeLancamento(int id){
        return carteira.removeLancamento(id);
    }

    public double calculaSaldo(){
        return carteira.calculaSaldo();
    }

    public void criaLancamento(String categoria, LocalDate data, String meioDeMovimentacao, String tipo, double valor){
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);
        carteira.addLancamento(novoLancamento);
    }

    
    public void filtrarLancamentos(int ano, int mes, int dia){
        LocalDate data = LocalDate.of(ano, mes, dia);

        List<Lancamento> resultado = carteira.loopFiltrarLancamentos(data); //variável que aponta pra lista já existente (tecnicamente nao cria outra lista)

        if (resultado.isEmpty()) {
            System.out.println("Nenhum lancamento nessa data!");
        } else {
            for (Lancamento l : resultado) {
                System.out.println(l);
            }
        }
    }

    public void filtrarLancamentosMes(int ano, int mes){
        List<Lancamento> resultado = carteira.loopFiltrarLancamentos_mes(ano, mes);

        if(resultado.isEmpty()){
            System.out.println("Nenhum lancamento nessa data!");
        }else{
            for (Lancamento l : resultado) {
                System.out.println(l);
            }
        }
    }
}
