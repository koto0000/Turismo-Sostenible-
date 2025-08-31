package Vista;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.ShapeElement;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class PanelMapaSVG extends JPanel {
    private SVGPanel svgPanel;
    private SVGUniverse universe;
    private SVGDiagram diagram;
    private Map<String, ShapeElement> elementosMapa = new HashMap<>();
    private Map<String, JButton> botonesRegiones = new HashMap<>();
    private String hoveredRegionId = null;
    private final Color HOVER_COLOR = new Color(200, 200, 200, 200);
    private final Color SELECTED_COLOR = new Color(91, 250, 32, 150);
    private String selectedRegionId = null;
    private JLayeredPane layeredPane;
    private JPanel panelBotones;
    
    // Nuevo: listener para comunicar selección
    private RegionSeleccionListener regionListener;

    public void setRegionSeleccionListener(RegionSeleccionListener listener) {
        this.regionListener = listener;
    }
    
    public PanelMapaSVG() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 800));
        configurarPanelMapa();
        configurarBotonesRegiones();
        cargarMapaSVG();
    }

    private void configurarPanelMapa() {
        layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        layeredPane.setPreferredSize(new Dimension(1200, 800));

        svgPanel = new SVGPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (hoveredRegionId != null && elementosMapa.containsKey(hoveredRegionId)) {
                    g2d.setColor(HOVER_COLOR);
                    g2d.fill(elementosMapa.get(hoveredRegionId).getShape());
                }

                if (selectedRegionId != null && elementosMapa.containsKey(selectedRegionId)) {
                    g2d.setColor(SELECTED_COLOR);
                    g2d.fill(elementosMapa.get(selectedRegionId).getShape());
                    g2d.setColor(new Color(70, 130, 180));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.draw(elementosMapa.get(selectedRegionId).getShape());
                }
            }
        };
        svgPanel.setAntiAlias(true);
        svgPanel.setBackground(Color.WHITE);
        svgPanel.setOpaque(true);
        svgPanel.setBounds(0, 0, 800, 800);
        layeredPane.add(svgPanel, JLayeredPane.DEFAULT_LAYER);

        svgPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point mousePoint = e.getPoint();
                String nuevaRegion = detectarRegion(mousePoint);

                if (nuevaRegion != hoveredRegionId) {
                    if (hoveredRegionId != null && botonesRegiones.containsKey(hoveredRegionId)) {
                        if (!hoveredRegionId.equals(selectedRegionId)) {
                            botonesRegiones.get(hoveredRegionId).setBackground(new Color(240, 240, 240));
                            botonesRegiones.get(hoveredRegionId).setForeground(Color.BLACK);
                        }
                    }

                    hoveredRegionId = nuevaRegion;

                    if (hoveredRegionId != null && botonesRegiones.containsKey(hoveredRegionId)) {
                        if (!hoveredRegionId.equals(selectedRegionId)) {
                            botonesRegiones.get(hoveredRegionId).setBackground(new Color(220, 230, 240));
                            botonesRegiones.get(hoveredRegionId).setForeground(Color.ORANGE);
                        }
                    }

                    svgPanel.repaint();
                }
            }
        });

        svgPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (hoveredRegionId != null && botonesRegiones.containsKey(hoveredRegionId)) {
                    if (!hoveredRegionId.equals(selectedRegionId)) {
                        botonesRegiones.get(hoveredRegionId).setBackground(new Color(240, 240, 240));
                    }
                }
                hoveredRegionId = null;
                svgPanel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (hoveredRegionId != null) {
                    seleccionarRegionDesdeMapa(hoveredRegionId);
                }
            }
        });

        add(layeredPane, BorderLayout.CENTER);
    }
    
    private void configurarBotonesRegiones() {
        panelBotones = new JPanel(new GridLayout(5, 5, 5, 5));
        panelBotones.setBackground(Color.WHITE);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelContenedor.add(panelBotones, BorderLayout.CENTER);
        panelContenedor.setBounds(260, 90, 650, 200);

        String[][] regiones = {
            {"Amazonas", "PE-AMA"}, {"Ancash", "PE-ANC"}, {"Apurímac", "PE-APU"},
            {"Arequipa", "PE-ARE"}, {"Ayacucho", "PE-AYA"}, {"Cajamarca", "PE-CAJ"},
            {"Cusco", "PE-CUS"}, {"Huancavelica", "PE-HUV"}, {"Huánuco", "PE-HUC"},
            {"Ica", "PE-ICA"}, {"Junín", "PE-JUN"}, {"La Libertad", "PE-LAL"},
            {"Lambayeque", "PE-LAM"}, {"Lima", "PE-LIM"}, {"Loreto", "PE-LOR"},
            {"Madre de Dios", "PE-MDD"}, {"Moquegua", "PE-MOQ"}, {"Pasco", "PE-PAS"},
            {"Piura", "PE-PIU"}, {"Puno", "PE-PUN"}, {"San Martín", "PE-SAM"},
            {"Tacna", "PE-TAC"}, {"Tumbes", "PE-TUM"}, {"Ucayali", "PE-UCA"}
        };

        for (String[] region : regiones) {
            JButton btnRegion = crearBotonRegion(region[0], region[1]);
            botonesRegiones.put(region[1], btnRegion);
            panelBotones.add(btnRegion);
        }

        int espaciosVacios = 25 - regiones.length;
        for (int i = 0; i < espaciosVacios; i++) {
            panelBotones.add(new JLabel(""));
        }

        layeredPane.add(panelContenedor, JLayeredPane.PALETTE_LAYER);
    }

    private JButton crearBotonRegion(String nombre, String id) {
        JButton btn = new JButton(nombre);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setPreferredSize(new Dimension(120, 30));
        btn.setFont(new Font("Yu gothic UI Semibold", Font.PLAIN, 11));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(240, 240, 240));
        btn.setBorder(null);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);

        btn.addActionListener(e -> seleccionarRegionDesdeBoton(id));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.ORANGE);
                hoveredRegionId = id;
                svgPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.BLACK);
                if (!id.equals(selectedRegionId)) {
                    btn.setBackground(new Color(240, 240, 240));
                }
                if (hoveredRegionId != null && hoveredRegionId.equals(id)) {
                    hoveredRegionId = null;
                    svgPanel.repaint();
                }
            }
        });

        return btn;
    }

    private void seleccionarRegionDesdeBoton(String regionId) {
            seleccionarRegion(regionId);
        }

        private void seleccionarRegionDesdeMapa(String regionId) {
            seleccionarRegion(regionId);
            if (botonesRegiones.containsKey(regionId)) {
                JButton btn = botonesRegiones.get(regionId);
                Rectangle rect = btn.getBounds();
                btn.scrollRectToVisible(rect);
            }
        }

        private void seleccionarRegion(String regionId) {
        if (selectedRegionId != null && botonesRegiones.containsKey(selectedRegionId)) {
            JButton btnAnterior = botonesRegiones.get(selectedRegionId);
            btnAnterior.setBackground(new Color(240, 240, 240));
            btnAnterior.setForeground(Color.BLACK);
        }

        selectedRegionId = regionId;

        if (botonesRegiones.containsKey(selectedRegionId)) {
            botonesRegiones.get(selectedRegionId).setBackground(new Color(200, 220, 255));
        }

        svgPanel.repaint();

        // 🔥 Notificar al listener externo (BusquedaPanel o JFrame)
        if (regionListener != null) {
            String nombreRegion = obtenerNombreRegionDesdeID(regionId);
            regionListener.onRegionSeleccionada(nombreRegion);
        }
    }

    private String detectarRegion(Point punto) {
        for (Map.Entry<String, ShapeElement> entry : elementosMapa.entrySet()) {
            if (entry.getValue().getShape().contains(punto)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private String obtenerNombreRegionDesdeID(String id) {
        JButton boton = botonesRegiones.get(id);
        return boton != null ? boton.getText() : id;
    }

    private void cargarMapaSVG() {
        try {
            universe = new SVGUniverse();
            File svgFile = new File("src/main/resources/mapPeru.svg");
            URI uri = universe.loadSVG(svgFile.toURI().toURL());
            svgPanel.setSvgURI(uri);
            diagram = universe.getDiagram(uri);

            String[] ids = {
                "PE-AMA", "PE-ANC", "PE-APU", "PE-ARE", "PE-AYA", "PE-CAJ",
                "PE-CUS", "PE-HUV", "PE-HUC", "PE-ICA", "PE-JUN", "PE-LAL",
                "PE-LAM", "PE-LIM", "PE-LOR", "PE-MDD", "PE-MOQ", "PE-PAS",
                "PE-PIU", "PE-PUN", "PE-SAM", "PE-TAC", "PE-TUM", "PE-UCA"
            };

            for (String id : ids) {
                SVGElement element = diagram.getElement(id);
                if (element instanceof ShapeElement) {
                    elementosMapa.put(id, (ShapeElement) element);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al cargar el mapa SVG: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}