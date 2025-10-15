package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL {
    
    private static final String URL      = "jdbc:mysql://127.0.0.1:3306/sistema_gestao?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO  = "root";
    private static final String SENHA = "1234"; 

    public static Connection getConexaoSQL() {
        try {
            // Tenta carregar o driver (necessário em versões antigas do Java, mas boa prática)
            // Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            Connection conn = DriverManager.getConnection(
                URL,
                USUARIO,
                SENHA
            );
            System.out.println("Conexão realizada com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no Banco de Dados: " + e.getMessage());
            e.printStackTrace(); // Mostra detalhes no console
            return null;
        }
    }
}