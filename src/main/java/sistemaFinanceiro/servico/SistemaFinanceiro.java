package sistemaFinanceiro.servico;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.*;
import sistemaFinanceiro.modelo.filtros.*;

import java.math.BigDecimal;
import java.sql.Connection;
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
    public SistemaFinanceiro(Connection connection) throws SQLException{
        this. carteira = new Carteira();
        this.lancamentoDAO = new LancamentoDAO(connection);
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

    public BigDecimal calculaSaldo(){
        return carteira.calculaSaldo();
    }

    public void criaLancamento(TipoCategoria categoria, LocalDate data, TipoMovimentacao meioDeMovimentacao, TipoLancamento tipo, BigDecimal valor) throws SQLException{
        Lancamento novoLancamento = new Lancamento(categoria, data, meioDeMovimentacao, tipo, valor);   //joga os dados pro banco
        int idGerado = lancamentoDAO.inserir(novoLancamento);   //insert no banco e gera (recebe) o id do lancamento
        Lancamento lancamentoPersistido =  new Lancamento(idGerado, categoria, data, meioDeMovimentacao, tipo, valor);  //coloca na carteira
        carteira.addLancamento(lancamentoPersistido);   //só o lancamento com id vai pra carteira (nao fica duplicado)
    }

    public RelatorioMensal gerarRelatorioMensal(int mes, int ano) throws SQLException {
        if (mes < 1 || mes > 12) 
            throw new IllegalArgumentException("O mes deve estar entre 1 e 12.");

        if (ano < 1) 
            throw new IllegalArgumentException("O ano deve ser maior que zero.");

        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.plusMonths(1);

        Map<TipoLancamento, BigDecimal>
            totaisPorTipo = lancamentoDAO.totalPorTipoNoPeriodo(inicio, fim);

        Map<TipoCategoria, BigDecimal> receitasPorCategoria = lancamentoDAO.totalPorCategoriaNoPeriodo(inicio, fim, TipoLancamento.RECEITA);

        Map<TipoCategoria, BigDecimal> despesasPorCategoria = lancamentoDAO.totalPorCategoriaNoPeriodo(inicio, fim, TipoLancamento.DESPESA);

        List<Lancamento> lancamentos = lancamentoDAO.listarPorPeriodo(inicio, fim);

        BigDecimal totalReceitas = totaisPorTipo.get(TipoLancamento.RECEITA);

        BigDecimal totalDespesas = totaisPorTipo.get(TipoLancamento.DESPESA);

        return new RelatorioMensal(mes, ano, totalReceitas, totalDespesas, receitasPorCategoria, despesasPorCategoria, lancamentos);
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
