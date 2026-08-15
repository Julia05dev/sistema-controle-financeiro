package sistemaFinanceiro.persistencia.conexaoJDBC;
import java.sql.*;

public class SingleConnection {
    private static String url = "jdbc:postgresql://localhost:5432/sistema_financeiro";
    private static String user = "postgres"; 
    private static Connection connection = null;

    static{
        conectar(); //sempre que chamar o SingleConnection ele vai chamar o conectar()
    }

    public SingleConnection(){
        conectar();
    }
    private static void conectar(){
        try{
            if(connection == null){
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false); //nao salva automaticamente
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
