package modelo;

import modelo.Destino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinoDAO {
    public List<Destino> buscarPorLugar(String lugar) {
        List<Destino> lista = new ArrayList<>();
        String sql = "{call BuscarDestinoPorLugar(?)}"; // Llamada al SP

        try (Connection conn = ConexionBD.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, lugar); // Parametro del SP
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Destino d = new Destino();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setLugar(rs.getString("lugar"));
                d.setCosto(rs.getDouble("costo"));
                d.setDias(rs.getInt("dias"));
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
        public List<Destino> buscarPorNombre(String nombre) {
        List<Destino> lista = new ArrayList<>();
        String sql = "{call BuscarDestinoPorNombre(?)}";

        try (Connection conn = ConexionBD.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Destino d = new Destino();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setLugar(rs.getString("lugar"));
                d.setCosto(rs.getDouble("costo"));
                d.setDias(rs.getInt("dias"));
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}