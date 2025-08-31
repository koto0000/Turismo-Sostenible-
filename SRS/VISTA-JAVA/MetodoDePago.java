package Vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MetodoDePago extends JPanel {
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private final Color COLOR_PRIMARIO = new Color(40, 167, 69); // Verde moderno
    private final Color COLOR_SECUNDARIO = new Color(240, 240, 240); // Gris claro
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    private JTextField txtNumeroTarjeta;
    private JTextField txtFechaExpiracion;
    private JTextField txtCVC;
    private JTextField txtCodigoSeguridadYape;
    private JTextField txtCodigoSeguridadPlin;

    public MetodoDePago() {
        setLayout(new BorderLayout());
        setBackground(COLOR_SECUNDARIO);

        // Panel principal con borde redondeado sin márgenes extra
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new RoundedBorder(20, COLOR_PRIMARIO));
        panelPrincipal.setBackground(Color.WHITE);

        // Contenedor con CardLayout
        cardLayout = new CardLayout(0, 0); // sin espacio entre tarjetas
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(null); // sin márgenes adicionales

        // Agregar métodos de pago
        panelContenedor.add(crearPanelTarjeta(), "Tarjeta");
        panelContenedor.add(crearPanelYape(), "Yape");
        panelContenedor.add(crearPanelPlin(), "Plin");

        panelPrincipal.add(panelContenedor, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearPanelTarjeta() {
        JPanel panel = crearSubPanel("Tarjeta de Crédito/Débito");

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(crearCampo("Número de tarjeta", "1234 5678 9012 3456"));

        panel.add(Box.createRigidArea(new Dimension(10, 0))); // separación antes del grid

        JPanel panelDatos = new JPanel(new GridLayout(1, 2, 15, 0));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(new EmptyBorder(0, 0, 60, 0)); // separación inferior del grid
        panelDatos.add(crearCampo("Fecha exp.", "MM/AA"));
        panelDatos.add(crearCampo("CVC", "123"));

        panel.add(panelDatos);
        return panel;
    }

    private JPanel crearPanelYape() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding interno

        // Imagen
        int anchoDeseado = 270;
        int altoDeseado = 220;
        ImageIcon iconoOriginal = new ImageIcon("Imagenes/yape.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        JLabel imagenQR = new JLabel(iconoEscalado);
        imagenQR.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imagenQR);

        panel.add(Box.createRigidArea(new Dimension(0, 0))); // Espacio debajo de la imagen

        // Campo de código
        panel.add(crearCampo("Código de seguridad", "123"));

        return panel;
    }

    private JPanel crearPanelPlin() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding interno

        // Imagen
        int anchoDeseado = 270;
        int altoDeseado = 220;
        ImageIcon iconoOriginal = new ImageIcon("Imagenes/plin.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        JLabel imagenQR = new JLabel(iconoEscalado);
        imagenQR.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imagenQR);

        panel.add(Box.createRigidArea(new Dimension(0, 0))); // Espacio debajo de la imagen

        // Campo de código
        panel.add(crearCampo("Código de seguridad", "123"));

        return panel;
    }

    private JPanel crearSubPanel(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(FUENTE_TITULO);
        tituloLabel.setForeground(COLOR_PRIMARIO);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(tituloLabel);
        return panel;
    }

    private JPanel crearCampo(String etiqueta, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(etiqueta);
        label.setFont(FUENTE_NORMAL);
        label.setForeground(new Color(80, 80, 80));

        JTextField textField = new JTextField();
        textField.setFont(FUENTE_NORMAL);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_SECUNDARIO),
                new EmptyBorder(5, 0, 5, 0)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(60, 60, 60));

        if (placeholder != null) {
            textField.setText(placeholder);
            textField.setForeground(Color.GRAY);
            textField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (textField.getText().equals(placeholder)) {
                        textField.setText("");
                        textField.setForeground(new Color(60, 60, 60));
                    }
                }

                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (textField.getText().isEmpty()) {
                        textField.setForeground(Color.GRAY);
                        textField.setText(placeholder);
                    }
                }
            });
        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        return panel;
    }

    // Métodos requeridos por PanelPago
    public String getNumeroTarjeta() {
        return txtNumeroTarjeta != null && !txtNumeroTarjeta.getText().equals("1234 5678 9012 3456") 
               ? txtNumeroTarjeta.getText() : "";
    }

    public String getFechaTarjeta() {
        return txtFechaExpiracion != null && !txtFechaExpiracion.getText().equals("MM/AA") 
               ? txtFechaExpiracion.getText() : "";
    }

    public String getCVC() {
        return txtCVC != null && !txtCVC.getText().equals("123") 
               ? txtCVC.getText() : "";
    }

    public String getNumeroOperacion() {
        return txtCodigoSeguridadYape != null && !txtCodigoSeguridadYape.getText().equals("123") 
               ? txtCodigoSeguridadYape.getText() : "";
    }

    public void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }

    // Clase para bordes redondeados
    private class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = 5;
            return insets;
        }
    }
}