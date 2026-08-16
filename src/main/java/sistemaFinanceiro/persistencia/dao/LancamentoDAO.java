package sistemaFinanceiro.persistencia.dao;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            statement.setBigDecimal(2, BigDecimal.valueOf(Math.abs(lancamento.getValor())));
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

}
