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

    //agora preciso tirar esses métodos e substituilos pelos filtros que criei com a interface. Cada filtro tem seu proprio metodo
    
    public List<Lancamento> filtrarLancamentos(int ano, int mes, int dia){
        LocalDate data = LocalDate.of(ano, mes, dia);
        return carteira.FiltrarLancamentos(data); //variável que aponta pra lista já existente (tecnicamente nao cria outra lista)
    }

    public List<Lancamento> filtrarLancamentosMes(int ano, int mes, int dia){
        LocalDate data = LocalDate.of(ano, mes, dia);
        return carteira.FiltrarLancamentos_mes(data);
    }
}
