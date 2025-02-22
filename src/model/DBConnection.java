package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    
    private String servidor = "jdbc:mysql://192.168.1.100:3306/";
    private String bbdd = "chat";
    private String user = "appuser";
    private String password = "TiC.123456";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(servidor + bbdd, user, password);
            System.out.println("Conexión establecida con la base de datos.");
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

    // Método para ejecutar el procedimiento almacenado "connect"
    public boolean ejecutarConexion(String userName) {
        if (connection == null) {
            System.out.println("No hay conexión a la base de datos.");
            return false;
        }

        String query = "{ CALL connect(?) }"; 
        try (CallableStatement stmt = connection.prepareCall(query)) {
            stmt.setString(1, userName); 
            stmt.execute(); 
            System.out.println("Usuario conectado en la base de datos: " + userName);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el procedimiento 'connect'.");
            return false;
        }
    }
    
    public void desconectar() {
    	  String query = "{ CALL disconnect() }"; 
    	  try (CallableStatement stmt = connection.prepareCall(query)) {
              stmt.execute(); 
              System.out.println("Desconexión exitosa");
          } catch (SQLException e) {
              e.printStackTrace();
              System.out.println("Error al ejecutar el procedimiento 'disconnect'.");
          }
    }
    
    public List<String> obtenerUsuariosConectados() {
        List<String> usuarios = new ArrayList<>();
        String query = "{ CALL getConnectedUsers() }"; 
        try (CallableStatement stmt = connection.prepareCall(query);
             ResultSet rs = stmt.executeQuery()) { 

            while (rs.next()) {
                usuarios.add(rs.getString("nick"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el procedimiento 'getConnectedUsers'.");
        }
        return usuarios;
    }


}
