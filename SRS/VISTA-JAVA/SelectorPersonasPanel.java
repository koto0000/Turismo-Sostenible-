package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectorPersonasPanel extends JPanel {
    private int ninos = 0;
    private int adultos = 0;

    private JLabel lblNinosValor;
    private JLabel lblAdultosValor;

    private final List<ActionListener> listeners = new ArrayList<>();

    public SelectorPersonasPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel contenido = new JPanel(new GridLayout(2, 4, 10, 10));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // NIÑOS
        JLabel lblNinos = new JLabel("Niños");
        lblNinos.setHorizontalAlignment(SwingConstants.CENTER);
        JButton btnRestarNinos = new JButton("-");
        lblNinosValor = new JLabel(String.valueOf(ninos), SwingConstants.CENTER);
        JButton btnSumarNinos = new JButton("+");

        // ADULTOS
        JLabel lblAdultos = new JLabel("Adultos");
        lblAdultos.setHorizontalAlignment(SwingConstants.CENTER);
        JButton btnRestarAdultos = new JButton("-");
        lblAdultosValor = new JLabel(String.valueOf(adultos), SwingConstants.CENTER);
        JButton btnSumarAdultos = new JButton("+");

        // Agregar al panel
        contenido.add(lblNinos);
        contenido.add(btnRestarNinos);
        contenido.add(lblNinosValor);
        contenido.add(btnSumarNinos);

        contenido.add(lblAdultos);
        contenido.add(btnRestarAdultos);
        contenido.add(lblAdultosValor);
        contenido.add(btnSumarAdultos);

        add(contenido, BorderLayout.CENTER);

        // Eventos
        btnSumarNinos.addActionListener(e -> actualizarContador("ninos", 1));
        btnRestarNinos.addActionListener(e -> actualizarContador("ninos", -1));
        btnSumarAdultos.addActionListener(e -> actualizarContador("adultos", 1));
        btnRestarAdultos.addActionListener(e -> actualizarContador("adultos", -1));
    }

    private void actualizarContador(String tipo, int valor) {
        if (tipo.equals("ninos")) {
            int nuevoNinos = ninos + valor;
            // Solo permitir niños si hay al menos un adulto
            if (nuevoNinos >= 0 && (adultos > 0 || nuevoNinos == 0)) {
                ninos = nuevoNinos;
                lblNinosValor.setText(String.valueOf(ninos));
            } else if (nuevoNinos > 0 && adultos == 0) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos un adulto para agregar niños.");
            }
        } else if (tipo.equals("adultos")) {
            adultos = Math.max(0, adultos + valor);
            lblAdultosValor.setText(String.valueOf(adultos));

            // Si se reduce adultos a 0, también reiniciar niños
            if (adultos == 0 && ninos > 0) {
                ninos = 0;
                lblNinosValor.setText("0");
            }
        }

        // Notificar a los listeners
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "cambio");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }


    // Método para que el controlador se suscriba al cambio
    public void addCambioListener(ActionListener listener) {
        listeners.add(listener);
    }

    // Métodos de acceso
    public int getCantidadNinos() {
        return ninos;
    }

    public int getCantidadAdultos() {
        return adultos;
    }

    public int getTotalPersonas() {
        return ninos + adultos;
    }
    
    public void reiniciarContadores() {
    adultos = 0;
    ninos = 0;
    lblAdultosValor.setText("0");
    lblNinosValor.setText("0");
    }
}
