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
    private JPanel panelInicio, panelPerfil, panelAyuda;
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

        // PANEL INICIO (fondo Machu Picchu)
        panelInicio = new JPanel() {
            private Image fondo = new ImageIcon("Imagenes/machupicchu.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
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

        JButton botonInfo = crearBoton("Buscar viajes", new Color(0, 102, 204));
        botonInfo.setBounds(540, 390, 170, 40);
        botonInfo.addActionListener(e -> pestañas.setSelectedIndex(1));
        panelInicio.add(botonInfo);

        // PANEL BÚSQUEDA
        panelBusqueda = new PanelBusqueda();

        // PANEL RESERVA (fondo azul degradado)
        panelReserva = new PanelReserva(pestañas, null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 70, 160),
                                                     getWidth(), getHeight(), new Color(0, 120, 220));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        controlador = new ControladorReserva(panelReserva);
        panelReserva.setControlador(controlador);

        // PANEL PAGO (fondo azul degradado)
        panelPago = new PanelPago(pestañas) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 80, 170),
                                                     getWidth(), getHeight(), new Color(100, 180, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPago.setDatosReserva(controlador);

        // PANEL PERFIL (fondo azul + datos de usuario)
        panelPerfil = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 90, 180),
                                                     getWidth(), getHeight(), new Color(120, 200, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPerfil.setLayout(new GridLayout(5, 1, 10, 10));
        panelPerfil.setOpaque(false);

        JLabel nombre = new JLabel("Nombre: Juan Pérez");
        JLabel correo = new JLabel("Correo: juan.perez@example.com");
        JLabel telefono = new JLabel("Teléfono: +51 987654321");
        JLabel ciudad = new JLabel("Ciudad: Cusco, Perú");
        JLabel plan = new JLabel("Plan actual: Premium Traveler");

        for (JLabel l : List.of(nombre, correo, telefono, ciudad, plan)) {
            l.setFont(new Font("Arial", Font.BOLD, 18));
            l.setForeground(Color.WHITE);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panelPerfil.add(l);
        }

        // PANEL AYUDA (fondo azul + info de contacto)
        panelAyuda = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 60, 140),
                                                     getWidth(), getHeight(), new Color(80, 160, 240));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelAyuda.setLayout(new BoxLayout(panelAyuda, BoxLayout.Y_AXIS));
        panelAyuda.setOpaque(false);

        JLabel ayudaTitulo = new JLabel("Centro de Ayuda", SwingConstants.CENTER);
        ayudaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        ayudaTitulo.setForeground(Color.WHITE);

        JLabel contacto = new JLabel("📧 soporte@turismo.com | ☎ +51 912345678", SwingConstants.CENTER);
        contacto.setFont(new Font("Arial", Font.PLAIN, 18));
        contacto.setForeground(Color.WHITE);

        JLabel faq1 = new JLabel("¿Cómo reservo un viaje? → Selecciona una región y confirma en PAGO.", SwingConstants.CENTER);
        faq1.setFont(new Font("Arial", Font.PLAIN, 16));
        faq1.setForeground(Color.WHITE);

        JLabel faq2 = new JLabel("¿Puedo cancelar una reserva? → Sí, hasta 48h antes del viaje.", SwingConstants.CENTER);
        faq2.setFont(new Font("Arial", Font.PLAIN, 16));
        faq2.setForeground(Color.WHITE);

        for (JLabel l : List.of(ayudaTitulo, contacto, faq1, faq2)) {
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelAyuda.add(Box.createRigidArea(new Dimension(0, 15)));
            panelAyuda.add(l);
        }

        // Añadir pestañas
        pestañas.addTab("INICIO", escalarIcono("Imagenes/home.png", 24, 24), panelInicio);
        pestañas.addTab("BÚSQUEDA", null, panelBusqueda);
        pestañas.addTab("RESERVA", null, panelReserva);
        pestañas.addTab("PAGO", null, panelPago);
        pestañas.addTab("PERFIL", null, panelPerfil);
        pestañas.addTab("AYUDA", null, panelAyuda);

        add(pestañas);
        setVisible(true);

        // Listeners búsqueda
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
                    JOptionPane.showMessageDialog(null,
                        "No se encontró ningún destino con ese nombre o lugar.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                pestañas.setSelectedComponent(panelReserva);
                panelReserva.mostrarEnTabla(resultados);
                panelReserva.seleccionarPrimeraFilaSiExiste();
            }
        });
    }

    // Método para crear botones estilizados
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            @Override public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
    }

    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
        if (ruta == null || ruta.isEmpty()) return new ImageIcon();
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        if (iconoOriginal.getImageLoadStatus() != MediaTracker.COMPLETE) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new InterfazPrincipal());
    }
}
