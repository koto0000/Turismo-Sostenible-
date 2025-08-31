// MapaInteractivoPeru.java
package Vista;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.ShapeElement;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MapaInteractivoPeru extends JPanel {
    private SVGPanel svgPanel;
    private SVGUniverse universe;
    private SVGDiagram diagram;
    private Map<String, ShapeElement> elementosMapa = new HashMap<>();
    private String hoveredRegionId = null;
    private String selectedRegionId = null;
    private final Color HOVER_COLOR = new Color(200, 200, 200, 100);
    private final Color SELECTED_COLOR = new Color(91, 250, 32, 150);
    
    private MapaClickListener clickListener;

    public interface MapaClickListener {
        void onRegionClicked(String regionId, String nombreRegion);
    }

    public MapaInteractivoPeru() {
        setLayout(new BorderLayout());
        configurarMapa();
        cargarMapaSVG();
    }

    private void configurarMapa() {
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
        add(svgPanel, BorderLayout.CENTER);

        // Configurar interactividad
        svgPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point mousePoint = e.getPoint();
                hoveredRegionId = detectarRegion(mousePoint);
                svgPanel.repaint();
            }
        });
        
        svgPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRegionId = null;
                svgPanel.repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hoveredRegionId != null) {
                    selectedRegionId = hoveredRegionId;
                    svgPanel.repaint();
                    if (clickListener != null) {
                        clickListener.onRegionClicked(selectedRegionId, obtenerNombreRegion(selectedRegionId));
                    }
                }
            }
        });
    }

    public void setMapaClickListener(MapaClickListener listener) {
        this.clickListener = listener;
    }

    private void cargarMapaSVG() {
        try {
            universe = new SVGUniverse();
            File svgFile = new File("src/main/resources/per.svg");
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

    private String detectarRegion(Point punto) {
        for (Map.Entry<String, ShapeElement> entry : elementosMapa.entrySet()) {
            if (entry.getValue().getShape().contains(punto)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String obtenerNombreRegion(String idRegion) {
        switch(idRegion) {
            case "PE-AMA": return "Amazonas";
            case "PE-ANC": return "Ancash";
            case "PE-APU": return "Apurímac";
            case "PE-ARE": return "Arequipa";
            case "PE-AYA": return "Ayacucho";
            case "PE-CAJ": return "Cajamarca";
            case "PE-CUS": return "Cusco";
            case "PE-HUV": return "Huancavelica";
            case "PE-HUC": return "Huánuco";
            case "PE-ICA": return "Ica";
            case "PE-JUN": return "Junín";
            case "PE-LAL": return "La Libertad";
            case "PE-LAM": return "Lambayeque";
            case "PE-LIM": return "Lima";
            case "PE-LOR": return "Loreto";
            case "PE-MDD": return "Madre de Dios";
            case "PE-MOQ": return "Moquegua";
            case "PE-PAS": return "Pasco";
            case "PE-PIU": return "Piura";
            case "PE-PUN": return "Puno";
            case "PE-SAM": return "San Martín";
            case "PE-TAC": return "Tacna";
            case "PE-TUM": return "Tumbes";
            case "PE-UCA": return "Ucayali";
            default: return "Desconocido";
        }
    }
}