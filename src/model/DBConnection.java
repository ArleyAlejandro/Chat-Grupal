package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    
//    private String servidor = "jdbc:mysql://192.168.1.100:3306/";
//    private String servidor = "jdbc:mysql://192.168.103.58:3306/";
//    private String servidor = "jdbc:mysql://m3act.ssanchez.dev:3308/chat";
      private String servidor = "jdbc:mysql://192.168.103.50:3306/chat";
      private String user = "appuser";
//    private String password = "Sergiazo31@Sergio10";
      private String password = "TiC.123456";
    
    private DBConnection() {
        try {
            connection = DriverManager.getConnection(servidor, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
