package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL {

    public static Connection getConexaoSQL() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/sistema_gestao?useTimezone=true&serverTimezone=UTC",
                "root",
                "1234"
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
