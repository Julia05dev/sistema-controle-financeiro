package sistemaFinanceiro.servico;
import java.time.LocalDate;
import java.util.List;
import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.*;
import sistemaFinanceiro.modelo.filtros.*;

import java.sql.SQLException;
import sistemaFinanceiro.persistencia.dao.LancamentoDAO;
import sistemaFinanceiro.persistencia.conexaoJDBC.SingleConnection;

public class SistemaFinanceiro{
    private final Carteira carteira;
    private final LancamentoDAO lancamentoDAO;

    public SistemaFinanceiro() throws SQLException{
        this.carteira = new Carteira();
        this.lancamentoDAO = new LancamentoDAO(SingleConnection.getConnection());
        carteira.addListaLancamentos(lancamentoDAO.listarTodos());
    }

    public List<Lancamento> mostraLancamentos(){
        return carteira.mostraLancamentos();
    }

    public boolean removeLancamento(int id) throws SQLException{
        boolean removeNoBanco = lancamentoDAO.excluirPorId(id);
        if(removeNoBanco)
            carteira.removeLancamento(id);  //tirou do banco tem que tirar da carteira tb
        return removeNoBanco;
    }

    public double calculaSaldo(){
        return carteira.calculaSaldo();
    }

    public void criaLancamento(TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, double valor) throws SQLException{
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);   //joga os dados pro banco
        int idGerado = lancamentoDAO.inserir(novoLancamento);   //insert no banco e gera (recebe) o id do lancamento
        Lancamento lancamentoPersistido =  new Lancamento(idGerado, categoria, data, meioDeMovimentacao, tipo, valor);  //coloca na carteira
        carteira.addLancamento(lancamentoPersistido);   //só o lancamento com id vai pra carteira (nao fica duplicado)
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
