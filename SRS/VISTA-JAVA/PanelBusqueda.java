package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelBusqueda extends JPanel {
    private JTextFieldRedondeado campoBusqueda;
    private RegionSeleccionListener regionListener;
    private PanelMapaSVG panelMapa;


    public PanelBusqueda() {
        setLayout(new BorderLayout());
        setLayout(null);
        setBackground(Color.WHITE);
        initComponents();
    }
 
    private void initComponents() {
        // Título
        JLabel lblTitulo = new JLabel("¿Adónde irás?");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBounds(350, 120, 350, 60);
        add(lblTitulo);
        
        // Campo de búsqueda
        campoBusqueda = new JTextFieldRedondeado(20);
        campoBusqueda.setBounds(325, 200, 400, 40);
        add(campoBusqueda);
        
        campoBusqueda.addActionListener(e -> {
            if (regionListener != null) {
                String texto = campoBusqueda.getText().trim();
                if (!texto.isEmpty()) {
                    regionListener.onLugarBuscado(texto); // ✅ Ahora llama al método correcto
                }
            }
        });
        panelMapa = new PanelMapaSVG(); // << GUARDADO COMO ATRIBUTO
        panelMapa.setBounds(60, 255, 860, 300);
        add(panelMapa);

        // Si ya hay un listener seteado antes de crear el mapa, lo aplicamos
        if (regionListener != null) {
            panelMapa.setRegionSeleccionListener(regionListener);
        }
        
    }
    
    public void setRegionSeleccionListener(RegionSeleccionListener listener) {
        this.regionListener = listener;

        // Si el panelMapa ya está creado, se lo pasamos
        if (panelMapa != null) {
            panelMapa.setRegionSeleccionListener(listener);
        }
    }

    public String getTextoBusqueda() {
        return campoBusqueda.getText();
    }
    
}