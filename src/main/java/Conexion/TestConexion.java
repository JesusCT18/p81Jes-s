package Conexion;

import java.sql.Connection;

public class TestConexion {

    public static void main(String[] args) {
        // Intentamos obtener la conexión utilizando el método getInstance()
        Connection connection = Conexion.getInstance();
        
        // Verificamos si la conexión es exitosa
        if (connection != null) {
            System.out.println("¡Conexión exitosa a la base de datos!");
        } else {
            System.out.println("Error al intentar conectar a la base de datos.");
        }
    }
}

