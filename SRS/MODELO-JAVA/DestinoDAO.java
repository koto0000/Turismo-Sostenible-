package modelo;

import java.util.ArrayList;
import java.util.List;

public class DestinoDAO {

    /** 
     * Devuelve la lista completa de destinos predefinidos por departamento.
     * Puedes ampliar/editar costos y días según tu requerimiento.
     */
    public List<Destino> cargarDestinosPredefinidos() {
        List<Destino> destinos = new ArrayList<>();

        // Cusco
        destinos.add(new Destino(1, "Machu Picchu", "Cusco", 150.0, 3));
        destinos.add(new Destino(2, "Sacsayhuamán", "Cusco", 50.0, 1));
        destinos.add(new Destino(3, "Valle Sagrado de los Incas", "Cusco", 120.0, 2));

        // Puno
        destinos.add(new Destino(4, "Lago Titicaca", "Puno", 100.0, 2));
        destinos.add(new Destino(5, "Islas Uros", "Puno", 80.0, 1));

        // Arequipa
        destinos.add(new Destino(6, "Monasterio de Santa Catalina", "Arequipa", 60.0, 1));
        destinos.add(new Destino(7, "Cañón del Colca", "Arequipa", 140.0, 2));

        // Lima
        destinos.add(new Destino(8, "Centro Histórico de Lima", "Lima", 40.0, 1));
        destinos.add(new Destino(9, "Circuito Mágico del Agua", "Lima", 30.0, 1));
        destinos.add(new Destino(10, "Museo Larco", "Lima", 50.0, 1));

        // Ica
        destinos.add(new Destino(11, "Huacachina", "Ica", 90.0, 1));
        destinos.add(new Destino(12, "Islas Ballestas", "Ica", 120.0, 1));
        destinos.add(new Destino(13, "Líneas de Nazca", "Ica", 200.0, 2));

        // Amazonas
        destinos.add(new Destino(14, "Fortaleza de Kuélap", "Amazonas", 130.0, 2));
        destinos.add(new Destino(15, "Catarata de Gocta", "Amazonas", 100.0, 1));

        // Loreto
        destinos.add(new Destino(16, "Río Amazonas", "Loreto", 250.0, 3));
        destinos.add(new Destino(17, "Reserva Pacaya Samiria", "Loreto", 200.0, 3));

        // La Libertad
        destinos.add(new Destino(18, "Ciudadela de Chan Chan", "La Libertad", 85.0, 1));
        destinos.add(new Destino(19, "Huacas del Sol y de la Luna", "La Libertad", 70.0, 1));

        // Áncash
        destinos.add(new Destino(20, "Parque Nacional Huascarán", "Áncash", 60.0, 2));
        destinos.add(new Destino(21, "Chavín de Huántar", "Áncash", 45.0, 1));

        // Piura
        destinos.add(new Destino(22, "Máncora", "Piura", 70.0, 2));
        destinos.add(new Destino(23, "Vichayito", "Piura", 65.0, 2));

        // San Martín
        destinos.add(new Destino(24, "Cataratas de Ahuashiyacu", "San Martín", 40.0, 1));
        destinos.add(new Destino(25, "Laguna Azul (Sauce)", "San Martín", 55.0, 1));

        return destinos;
    }

    /**
     * Busca por lugar/departamento o por nombre del destino (contiene, case-insensitive).
     * Si el texto es nulo o vacío, devuelve todos los destinos.
     */
    public List<Destino> buscarPorLugar(String texto) {
        List<Destino> base = cargarDestinosPredefinidos();
        if (texto == null) texto = "";
        final String q = texto.trim().toLowerCase();
        if (q.isEmpty()) {
            return base;
        }
        List<Destino> filtrados = new ArrayList<>();
        for (Destino d : base) {
            String nombre = d.getNombre() != null ? d.getNombre().toLowerCase() : "";
            String lugar = d.getLugar() != null ? d.getLugar().toLowerCase() : "";
            if (nombre.contains(q) || lugar.contains(q)) {
                filtrados.add(d);
            }
        }
        return filtrados;
    }
}
