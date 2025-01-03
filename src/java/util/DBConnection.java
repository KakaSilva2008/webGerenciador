package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gerenciador_tarefas";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456"; 

    public static Connection getConnection() throws SQLException {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }

        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

