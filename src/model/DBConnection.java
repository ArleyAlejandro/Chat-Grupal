package model;

import java.sql.*;

public class DBConnection {

    private static DBConnection instance; // Instancia única de la clase
    private Connection connexioDB;
    private String servidor = "jdbc:mysql://192.168.103.50:3306/";
    private String bbdd = "chat";
    private String user = "appuser";
    private String password = "TiC.123456";

    // Constructor privado para evitar instanciación directa
    private DBConnection() {
        try {
            this.connexioDB = DriverManager.getConnection(this.servidor + this.bbdd, this.user, this.password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Mensaje de Error: " + e);
        }
    }

    // Método estático para obtener la instancia única
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Método para obtener la conexión a la base de datos
    public Connection getConnection() {
        return this.connexioDB;
    }

    // Método para cerrar la conexión
    public void closeConnection() {
        if (this.connexioDB != null) {
            try {
                this.connexioDB.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e);
            }
        }
    }
}