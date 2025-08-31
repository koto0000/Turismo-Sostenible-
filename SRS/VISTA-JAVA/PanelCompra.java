package Vista;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PanelCompra extends JPanel {

    private JDateChooser dateChooser;
    private JComboBox<String> comboPersonas;
    private JButton btnComprar;

    public PanelCompra() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.RED, 2, true)); // Borde rojo redondeado

        initComponents();
    }

    private void initComponents() {
        // Etiqueta: Fecha de inicio
        JLabel lblFecha = new JLabel("Fecha de inicio");
        lblFecha.setBounds(40, 30, 200, 20);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblFecha);

        // Selector de fecha
        dateChooser = new JDateChooser();
        dateChooser.setBounds(40, 55, 220, 30);
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(dateChooser);

        // Etiqueta: Duración
        JLabel lblDuracion = new JLabel("Duración");
        lblDuracion.setBounds(40, 100, 200, 20);
        lblDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblDuracion);

        // Campo duración (solo texto)
        JTextField txtDuracion = new JTextField("3 días");
        txtDuracion.setBounds(40, 125, 220, 30);
        txtDuracion.setEditable(false);
        txtDuracion.setBackground(new Color(245, 245, 245));
        txtDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDuracion.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(txtDuracion);

        // Etiqueta: Personas
        JLabel lblPersonas = new JLabel("Personas");
        lblPersonas.setBounds(40, 170, 200, 20);
        lblPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblPersonas);

        // Combo box de personas
        comboPersonas = new JComboBox<>(new String[] {
                "seleccionar personas", "1", "2", "3", "4", "5+"
        });
        comboPersonas.setBounds(40, 195, 220, 30);
        comboPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(comboPersonas);

        // Botón Comprar ahora
        btnComprar = new JButton("¡Comprar ahora!");
        btnComprar.setBounds(40, 245, 220, 40);
        btnComprar.setBackground(new Color(240, 82, 82));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnComprar.setFocusPainted(false);
        btnComprar.setBorderPainted(false);
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnComprar);
    }

    // Getters si deseas acceder a los campos
    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public JComboBox<String> getComboPersonas() {
        return comboPersonas;
    }

    public JButton getBtnComprar() {
        return btnComprar;
    }
}
