package Vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaConexionSQL {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=TurismoBD;encrypt=true;trustServerCertificate=true";
        String usuario = "sa"; // Cambia esto si usas otro usuario
        String clave = "sotito3579"; // Cambia esto si tu contraseña es diferente

        try (Connection conn = DriverManager.getConnection(url, usuario, clave)) {
            System.out.println("✅ Conexión exitosa a SQL Server.");

            // Obtener metadatos
            var metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            System.out.println("📋 Tablas encontradas en la base de datos:");
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println("- " + tableName);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error de conexión:");
            e.printStackTrace();
        }
    }
}
