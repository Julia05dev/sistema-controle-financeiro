package sistemaFinanceiro.persistencia.dao;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import sistemaFinanceiro.modelo.enums.*;
import sistemaFinanceiro.modelo.Lancamento;

public class LancamentoDAO {
    private final Connection connection;

    public LancamentoDAO(Connection connection){
        if(connection == null)
            throw new IllegalArgumentException("a conexao nao pode ser nula.");
        this.connection = connection;
    }

    private static final String SQL_INSERIR =
        "INSERT INTO lancamento " +
        "(data_lancamento, valor, tipo, categoria, meio_de_movimentacao) " +
        "VALUES (?, ?, ?, ?, ?) " +
        "RETURNING id";

    public int inserir(Lancamento lancamento) throws SQLException{
        if(lancamento == null)
            throw new IllegalArgumentException("O lancamento nao pode ser nulo.");

        try(PreparedStatement statement = connection.prepareStatement(SQL_INSERIR)){
            statement.setObject(1, lancamento.getData());
            statement.setBigDecimal(2, lancamento.getValor());
            statement.setString(3, lancamento.getTipo().name());
            statement.setString(4, lancamento.getCategoria().name());
            statement.setString(5, lancamento.getmeioDeMovimentacao().name());

            try(ResultSet resultado = statement.executeQuery()){
                if(!resultado.next())
                    throw new SQLException("o banco nao devolveu o ID do lancamento.");

                int idGerado = resultado.getInt("id");
                connection.commit();    //operaçao deu certo, pode tornar a inserçao permanente
                return idGerado;    //o bd que gera o id e manda pro codigo
            }

        }catch(SQLException e){
            connection.rollback();
            throw e;
        }
    }

    private static final String SQL_ATUALIZAR =
    "UPDATE lancamento " +
    "SET data_lancamento = ?, valor = ?, tipo = ?, " +
    "categoria = ?, meio_de_movimentacao = ? " +
    "WHERE id = ?";

    public boolean atualizar(Lancamento lancamento) throws SQLException {
        if (lancamento == null) throw new IllegalArgumentException("O lancamento nao pode ser nulo.");

        try (PreparedStatement statement = connection.prepareStatement(SQL_ATUALIZAR)) {
            statement.setObject(1, lancamento.getData());
            statement.setBigDecimal(2, lancamento.getValor());
            statement.setString(3, lancamento.getTipo().name());
            statement.setString(4, lancamento.getCategoria().name());
            statement.setString(5, lancamento .getmeioDeMovimentacao() .name());
            statement.setInt(6, lancamento.getId());

            int linhasAfetadas = statement.executeUpdate();
            connection.commit();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    private static final String SQL_LISTAR_TODOS = 
        "SELECT id, data_lancamento, valor, tipo, categoria, meio_de_movimentacao " +
        "FROM LANCAMENTO " +
        "ORDER BY id";

    public List<Lancamento> listarTodos() throws SQLException{
        List<Lancamento> lancamentos = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(SQL_LISTAR_TODOS)){
            try(ResultSet resultado = statement.executeQuery()){
                while(resultado.next()){
                    int id = resultado.getInt("id");
                    LocalDate data = resultado.getObject("data_lancamento", LocalDate.class);
                    BigDecimal valor = resultado.getBigDecimal("valor");
                    TipoLancamento tipo = TipoLancamento.valueOf(resultado.getString("tipo"));
                    TipoCategoria categoria = TipoCategoria.valueOf(resultado.getString("categoria"));
                    TipoMovimentacao movimentacao = TipoMovimentacao.valueOf(resultado.getString("meio_de_movimentacao"));

                    Lancamento lancamento = new Lancamento(id, categoria, data, movimentacao, tipo, valor);
                    lancamentos.add(lancamento);
                }
            }
            connection.commit();
            return lancamentos;
        }catch(SQLException e){
            connection.rollback();
            throw e;
        }
    }

    private static final String SQL_TOTAL_POR_TIPO_NO_PERIODO =
    "SELECT tipo, SUM(valor) AS total " +
    "FROM lancamento " +
    "WHERE data_lancamento >= ? " +
    "AND data_lancamento < ? " +
    "GROUP BY tipo";

    public Map<TipoLancamento, BigDecimal> totalPorTipoNoPeriodo(LocalDate inicio, LocalDate fim) throws SQLException {
        validarPeriodo(inicio, fim);
        Map<TipoLancamento, BigDecimal> totais = new EnumMap<>(TipoLancamento.class);

        totais.put(TipoLancamento.RECEITA, BigDecimal.ZERO);

        totais.put(TipoLancamento.DESPESA, BigDecimal.ZERO);

        try (PreparedStatement statement = connection.prepareStatement(SQL_TOTAL_POR_TIPO_NO_PERIODO)){
            statement.setObject(1, inicio);
            statement.setObject(2, fim);
            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    TipoLancamento tipo = TipoLancamento.valueOf(resultado.getString("tipo"));
                    BigDecimal total = resultado.getBigDecimal("total");
                    totais.put(tipo, total);
                }
            }
            connection.commit();
            return totais;
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }


    private static final String SQL_TOTAL_POR_CATEGORIA_NO_PERIODO =
        "SELECT categoria, SUM(valor) AS total " +
        "FROM lancamento " +
        "WHERE data_lancamento >= ? " +
        "AND data_lancamento < ? " +
        "AND tipo = ? " +
        "GROUP BY categoria " +
        "ORDER BY total DESC";

    public Map<TipoCategoria, BigDecimal>totalPorCategoriaNoPeriodo(LocalDate inicio, LocalDate fim, TipoLancamento tipo) throws SQLException {
        validarPeriodo(inicio, fim);

        if (tipo == null) {
            throw new IllegalArgumentException(
                "O tipo nao pode ser nulo."
            );
        }

        Map<TipoCategoria, BigDecimal> totais = new LinkedHashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_TOTAL_POR_CATEGORIA_NO_PERIODO)) {
            statement.setObject(1, inicio);
            statement.setObject(2, fim);
            statement.setString(3, tipo.name());

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    TipoCategoria categoria = TipoCategoria.valueOf(resultado.getString("categoria"));
                    BigDecimal total = resultado.getBigDecimal("total");
                    totais.put(categoria, total);
                }
            }
            connection.commit();
            return totais;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private static final String SQL_LISTAR_POR_PERIODO =
        "SELECT id, data_lancamento, valor, " +
        "tipo, categoria, meio_de_movimentacao " +
        "FROM lancamento " +
        "WHERE data_lancamento >= ? " +
        "AND data_lancamento < ? " +
        "ORDER BY data_lancamento, id";

    public List<Lancamento> listarPorPeriodo(LocalDate inicio, LocalDate fim) throws SQLException {
        validarPeriodo(inicio, fim);
        List<Lancamento> lancamentos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_LISTAR_POR_PERIODO)) {
            statement.setObject(1, inicio);
            statement.setObject(2, fim);
            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    LocalDate data = resultado.getObject("data_lancamento", LocalDate.class);
                    BigDecimal valor = resultado.getBigDecimal("valor");
                    TipoLancamento tipo = TipoLancamento.valueOf(resultado.getString("tipo"));
                    TipoCategoria categoria = TipoCategoria.valueOf(resultado.getString("categoria"));
                    TipoMovimentacao movimentacao = TipoMovimentacao.valueOf(resultado.getString("meio_de_movimentacao"));
                    Lancamento lancamento = new Lancamento(id, categoria, data, movimentacao, tipo, valor);
                    lancamentos.add(lancamento);
                }
            }
            connection.commit();
            return lancamentos;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null || !inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Periodo invalido.");
        }
    }

    private static final String SQL_EXCLUIR_POR_ID = 
        "DELETE FROM lancamento " +
        "WHERE id = ?";
    
    public boolean excluirPorId(int id) throws SQLException{
        if(id <=0)
            throw new IllegalArgumentException("id nao pode ser menor que 1.");

        try(PreparedStatement statement = connection.prepareStatement(SQL_EXCLUIR_POR_ID)){
            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();  //retorna um int informando quantas linhas foram afetadas
            connection.commit();

            return linhasAfetadas > 0;  //se nao afetou nenhuma retorna falso
        }catch(SQLException e){
            connection.rollback();
            throw e;
        }
    }
}
