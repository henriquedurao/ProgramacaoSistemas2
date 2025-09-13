import java.sql.*;
public class FabricaConexoes {
    private FabricaConexoes (){

    }
    public  static Connection getConnection(String url){
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão!");
            return null;
        }
    }
}