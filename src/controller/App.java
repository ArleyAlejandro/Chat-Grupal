package controller;

import model.DBConnection;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        // Obtener la instancia única de DBConnection
        DBConnection dbConnection = DBConnection.getInstance();

        // Obtener la conexión a la base de datos
        Connection connection = dbConnection.getConnection();
  
        if (connection != null) {
            System.out.println("Conexión obtenida correctamente.");
        } else {
            System.out.println("No se pudo obtener la conexión.");
        }

        // Cerrar la conexión cuando ya no la necesites
        dbConnection.closeConnection();
    }
}