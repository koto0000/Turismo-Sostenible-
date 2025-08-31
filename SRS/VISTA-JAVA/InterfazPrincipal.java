package Vista;

import control.ControladorReserva;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import modelo.Destino;
import modelo.DestinoDAO;

public class InterfazPrincipal extends JFrame {

    private JTabbedPane pestañas;
    private JPanel panelInicio, panel5, panel6;
    private PanelBusqueda panelBusqueda;
    private PanelReserva panelReserva;
    private PanelPago panelPago;
    private ControladorReserva controlador;

    public InterfazPrincipal() {
        setTitle("Turismo Sostenible");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del tabbed pane
        pestañas = new JTabbedPane();
        pestañas.setBackground(Color.WHITE);
        pestañas.setBorder(null);
        pestañas.setUI(new BasicTabbedPaneUI() {
            @Override protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                                                         int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
            @Override protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {}
            @Override protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                                    int x, int y, int w, int h, boolean isSelected) {}
        });
        
        // PANEL INICIO
        panelInicio = new JPanel();
        panelInicio.setLayout(null);

        JLabel titulo = new JLabel("Encuentra tu ");
        titulo.setFont(new Font("Arial", Font.BOLD, 60));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(540, 200, 600, 50);
        panelInicio.add(titulo);

        JLabel titulo1 = new JLabel("Próximo viaje");
        titulo1.setFont(new Font("Arial", Font.BOLD, 60));
        titulo1.setForeground(Color.WHITE);
        titulo1.setBounds(540, 270, 600, 50);
        panelInicio.add(titulo1);

        JLabel titulo2 = new JLabel("Agencia de viajes y paquetes turísticos");
        titulo2.setFont(new Font("Arial", Font.BOLD, 16));
        titulo2.setForeground(Color.WHITE);
        titulo2.setBounds(540, 150, 600, 50);
        panelInicio.add(titulo2);

        JLabel titulo3 = new JLabel("Te llevamos a donde quieras ir. Recorre el Perú y el mundo");
        titulo3.setFont(new Font("Arial", Font.BOLD, 16));
        titulo3.setForeground(Color.WHITE);
        titulo3.setBounds(540, 320, 600, 50);
        panelInicio.add(titulo3);

        JLabel titulo4 = new JLabel("entero de la forma más sencilla");
        titulo4.setFont(new Font("Arial", Font.BOLD, 16));
        titulo4.setForeground(Color.WHITE);
        titulo4.setBounds(540, 340, 600, 50);
        panelInicio.add(titulo4);

        JButton botonInfo = new JButton("Buscar viajes");
        botonInfo.setBounds(540, 390, 170, 40);
        botonInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        botonInfo.setBackground(new Color(64, 224, 208));
        botonInfo.setForeground(Color.WHITE);
        botonInfo.setFocusPainted(false);
        botonInfo.setBorderPainted(false);

        botonInfo.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                botonInfo.setBackground(new Color(48, 170, 158));
            }

            @Override public void mouseExited(MouseEvent e) {
                botonInfo.setBackground(new Color(64, 224, 208));
            }

            @Override public void mousePressed(MouseEvent e) {
                botonInfo.setBackground(new Color(102, 235, 220));
            }

            @Override public void mouseReleased(MouseEvent e) {
                botonInfo.setBackground(new Color(48, 170, 158));
            }
        });

        botonInfo.addActionListener(e -> pestañas.setSelectedIndex(1));
        panelInicio.add(botonInfo);

        // Fondo del panel inicio
        ImageIcon imagenFondo = new ImageIcon("Imagenes/fondo.jpg");
        JLabel fondoLabel = new JLabel(new ImageIcon(
                imagenFondo.getImage().getScaledInstance(1050, 700, Image.SCALE_SMOOTH)
        ));
        fondoLabel.setBounds(0, 0, 1050, 700);
        panelInicio.add(fondoLabel);

        // PASOS DEL INDICADOR
        List<String> pasos = List.of("", "", "");

        panelBusqueda = new PanelBusqueda();
        panelReserva = new PanelReserva(pestañas, null);        // primero reserva sin controlador

        controlador = new ControladorReserva(panelReserva);     // luego creas controlador y se lo pasas

        panelReserva.setControlador(controlador);               // le devuelves el controlador al panel

        panelPago = new PanelPago(pestañas);
        panelPago.setDatosReserva(controlador);

        // PANEL BÚSQUEDA
        panelBusqueda.setLayout(new BorderLayout());
        StepProgressIndicator stepBusqueda = new StepProgressIndicator(pasos);
        stepBusqueda.setCurrentStep(0);

        JPanel wrapperBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        wrapperBusqueda.setOpaque(false);
        wrapperBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 151, 0, 0)); // Mueve 150px a la derecha
        wrapperBusqueda.add(stepBusqueda);
        panelBusqueda.add(wrapperBusqueda, BorderLayout.NORTH);

        // PANEL RESERVA
        panelReserva.setLayout(new BorderLayout());
        StepProgressIndicator stepReserva = new StepProgressIndicator(pasos);
        stepReserva.setCurrentStep(1);

        JPanel wrapperReserva = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        wrapperReserva.setOpaque(false);
        wrapperReserva.setBorder(BorderFactory.createEmptyBorder(0, 151, 0, 0));
        wrapperReserva.add(stepReserva); // ✅ usar stepReserva aquí
        panelReserva.add(wrapperReserva, BorderLayout.NORTH);

        // PANEL PAGO
        panelPago.setLayout(new BorderLayout());
        StepProgressIndicator stepPago = new StepProgressIndicator(pasos);
        stepPago.setCurrentStep(2);
        panelPago.setStepPago(stepPago);

        JPanel wrapperPago = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        wrapperPago.setOpaque(false);
        wrapperPago.setBorder(BorderFactory.createEmptyBorder(0, 151, 0, 0));
        wrapperPago.add(stepPago); // ✅ usar stepPago aquí
        panelPago.add(wrapperPago, BorderLayout.NORTH);

        // Otros paneles
        panel5 = new JPanel(); panel5.setBackground(Color.WHITE);
        panel6 = new JPanel(); panel6.setBackground(Color.WHITE);

        // Añadir pestañas con íconos
        pestañas.addTab("INICIO", escalarIcono("Imagenes/home.png", 24, 24), panelInicio);
        pestañas.addTab("BÚSQUEDA", escalarIcono("", 24, 24), panelBusqueda);
        pestañas.addTab("RESERVA", escalarIcono("", 24, 24), panelReserva);
        pestañas.addTab("PAGO", escalarIcono("", 24, 24), panelPago);
        pestañas.addTab("PERFIL", escalarIcono("", 24, 24), panel5);
        pestañas.addTab("AYUDA", escalarIcono("", 24, 24), panel6);
        add(pestañas);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
        
        panelBusqueda.setRegionSeleccionListener(new RegionSeleccionListener() {
            @Override
            public void onRegionSeleccionada(String nombreRegion) {
                pestañas.setSelectedComponent(panelReserva);
                panelReserva.cargarDestinosPorRegion(nombreRegion);
            }

            @Override
            public void onLugarBuscado(String nombreLugar) {
                List<Destino> resultados = new DestinoDAO().buscarPorLugar(nombreLugar);

                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún destino con ese nombre o lugar.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    return; // No cambia de pestaña
                }

                pestañas.setSelectedComponent(panelReserva);
                panelReserva.mostrarEnTabla(resultados);
                panelReserva.seleccionarPrimeraFilaSiExiste();
            }
        });
    }

    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        if (iconoOriginal.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Error al cargar la imagen: " + ruta);
            return new ImageIcon();
        }

        Image imagenOriginal = iconoOriginal.getImage();
        BufferedImage imagenEscalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagenEscalada.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(imagenOriginal, 0, 0, ancho, alto, null);
        g2d.dispose();

        return new ImageIcon(imagenEscalada);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(7, 10, 10, 10));
            UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 10, 0, 10));
            UIManager.put("TabbedPane.tabInsets", new Insets(6, 20, 6, 20));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new InterfazPrincipal());
    }
}
