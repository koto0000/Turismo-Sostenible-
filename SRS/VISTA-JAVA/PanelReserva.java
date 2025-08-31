package Vista;

import com.toedter.calendar.JDateChooser;
import modelo.Destino;
import modelo.DestinoDAO;
import control.ControladorReserva;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class PanelReserva extends JPanel {
    private JComboBox<String> comboDestinos;
    private JTable tablaResultados, tablaPrecios;
    private DefaultTableModel modeloTabla;
    private JDateChooser fechaEntrada;
    private SelectorPersonasPanel selector;
    private DefaultTableModel modeloPrecios;
    private JTabbedPane pestañas;
    private JButton sig, atras;
    private ControladorReserva controlador;


    public PanelReserva(JTabbedPane pestañas, ControladorReserva controlador) {
        this.pestañas = pestañas;
        this.controlador = controlador;

        setLayout(null);
        setBackground(Color.WHITE);
        initComponents();
        configurarEventos();
    }

    private void initComponents() {
        JLabel lblTitulo = new JLabel("Reserva del Lugar");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitulo.setBounds(300, 120, 450, 60);
        add(lblTitulo);

        // Combo box de destinos
        String[] destinos = {"Playa", "Montaña", "Cuidad", "Otro"};
        comboDestinos = new JComboBox<>(destinos);
        comboDestinos.setBounds(800, 250, 100, 30);
        comboDestinos.setFont(new Font("Arial", Font.PLAIN, 12));
        add(comboDestinos);

        // Crear modelo de tabla con columnas
        String[] columnas = {"ID", "Nombre", "Ubicación", "Precio Base", "Días"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        // Crear JTable y aplicar estilo moderno
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaResultados.setRowHeight(30); // Altura de filas
        tablaResultados.setShowGrid(false); // Quitar bordes de celdas
        tablaResultados.setIntercellSpacing(new Dimension(0, 0));
        tablaResultados.setSelectionBackground(new Color(64, 224, 208)); // Fondo al seleccionar
        tablaResultados.setSelectionForeground(Color.WHITE); // Texto seleccionado blanco
        tablaResultados.setFillsViewportHeight(true);
        tablaResultados.setBackground(Color.WHITE);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Encabezado estilizado
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaResultados.getTableHeader().setBackground(new Color(230, 230, 230));
        tablaResultados.getTableHeader().setForeground(Color.DARK_GRAY);
        tablaResultados.getTableHeader().setReorderingAllowed(false);

        // Centrar las columnas excepto el nombre (columna 1)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            if (i != 1) { // Dejar el nombre alineado a la izquierda
                tablaResultados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Ocultar columna ID
        tablaResultados.getColumnModel().getColumn(0).setMinWidth(0);
        tablaResultados.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaResultados.getColumnModel().getColumn(0).setWidth(0);

        // JScrollPane con borde suave
        JScrollPane scroll = new JScrollPane(tablaResultados);
        scroll.setBounds(110, 200, 600, 150);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Agregar a tu panel
        add(scroll);

        JLabel lblFecha = new JLabel("Fecha de inicio:");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFecha.setForeground(Color.BLACK);
        lblFecha.setBounds(110, 370, 200, 30);
        add(lblFecha);

        fechaEntrada = new JDateChooser();
        fechaEntrada.setMinSelectableDate(new Date());
        fechaEntrada.setDateFormatString("dd/MM/yyyy");
        fechaEntrada.setBounds(250, 370, 150, 30);
        add(fechaEntrada);

        JLabel cantPersonas = new JLabel("Personas");
        cantPersonas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cantPersonas.setForeground(Color.BLACK);
        cantPersonas.setBounds(110, 415, 200, 30);
        add(cantPersonas);

        selector = new SelectorPersonasPanel();
        JButton btnSeleccionarPersonas = new JButton("Seleccionar personas ▼");
        btnSeleccionarPersonas.setBounds(250, 415, 150, 30);
        add(btnSeleccionarPersonas);

        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BorderLayout());
        popup.add(selector, BorderLayout.CENTER);
        btnSeleccionarPersonas.addActionListener(e -> popup.show(btnSeleccionarPersonas, 0, btnSeleccionarPersonas.getHeight()));

        modeloPrecios = new DefaultTableModel(new Object[][]{
                {"Precio base", "S/ 0.00"},
                {"Total", "S/ 0.00"}
        }, new Object[]{"", ""}) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaPrecios = new JTable(modeloPrecios);
        tablaPrecios.setShowGrid(false);
        tablaPrecios.setTableHeader(null);
        tablaPrecios.setBorder(BorderFactory.createEmptyBorder());
        tablaPrecios.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {{
            setHorizontalAlignment(JLabel.RIGHT);
        }});

        JScrollPane scrollPrecios = new JScrollPane(tablaPrecios);
        scrollPrecios.setBounds(450, 400, 300, 33);
        scrollPrecios.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPrecios);

        sig = new JButton("¡Comprar ahora!");
        sig.setBounds(675, 530, 150, 40);
        sig.setFont(new Font("Arial", Font.PLAIN, 16));
        sig.setBackground(new Color(64, 224, 208));
        sig.setForeground(Color.WHITE);
        sig.setFocusPainted(false);
        sig.setBorderPainted(false);

        sig.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                sig.setBackground(new Color(48, 170, 158));
            }

            @Override public void mouseExited(MouseEvent e) {
                sig.setBackground(new Color(64, 224, 208));
            }

            @Override public void mousePressed(MouseEvent e) {
                sig.setBackground(new Color(102, 235, 220));
            }

            @Override public void mouseReleased(MouseEvent e) {
                sig.setBackground(new Color(48, 170, 158));
            }
        });
        ;add(sig);
        
        atras = new JButton("Atras");
        atras.setBounds(225, 530, 150, 40);
        atras.setFont(new Font("Arial", Font.PLAIN, 16));
        atras.setBackground(new Color(64, 224, 208));
        atras.setForeground(Color.WHITE);
        atras.setFocusPainted(false);
        atras.setBorderPainted(false);

        atras.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                atras.setBackground(new Color(48, 170, 158));
            }

            @Override public void mouseExited(MouseEvent e) {
                atras.setBackground(new Color(64, 224, 208));
            }

            @Override public void mousePressed(MouseEvent e) {
                atras.setBackground(new Color(102, 235, 220));
            }

            @Override public void mouseReleased(MouseEvent e) {
                atras.setBackground(new Color(48, 170, 158));
            }
        });
        ;add(atras);
    }

    private void configurarEventos() {
        sig.addActionListener(e -> {
            int fila = tablaResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un destino primero.");
                return;
            }
            if (fechaEntrada.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una fecha de inicio.");
                return;
            }
            if (selector.getCantidadAdultos() == 0) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos un adulto.");
                return;
            }

            // Obtener datos
            Destino destino = obtenerDestinoSeleccionado();
            Date fechaInicio = fechaEntrada.getDate();

            // Calcular fecha final sumando los días del destino
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaInicio);
            cal.add(Calendar.DATE, destino.getDias());
            Date fechaFin = cal.getTime();

            // Guardar datos en el controlador
            controlador.setDatosReserva(destino, fechaInicio, fechaFin, selector.getCantidadAdultos(), selector.getCantidadNinos());

            // Cambiar a la pestaña de pago
            pestañas.setSelectedIndex(3);

            // Pasar datos al panel de pago
            Component comp = pestañas.getComponentAt(3);
            if (comp instanceof PanelPago panelPago) {
                panelPago.mostrarDatosReserva(); // Este método debe cargar los datos desde el controlador
            }
        });

        atras.addActionListener(e -> {
            limpiarFormulario();
            pestañas.setSelectedIndex(1);
        });
    }

    public void mostrarEnTabla(List<Destino> destinos) {
        modeloTabla.setRowCount(0);
        for (Destino d : destinos) {
            modeloTabla.addRow(new Object[]{
                    d.getId(),
                    d.getNombre(),
                    d.getLugar(),
                    d.getCosto(),
                    d.getDias()
            });
        }
    }

    public Destino obtenerDestinoSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila != -1) {
            Destino d = new Destino();
            d.setId(Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString()));
            d.setNombre(modeloTabla.getValueAt(fila, 1).toString());
            d.setLugar(modeloTabla.getValueAt(fila, 2).toString());
            d.setCosto(Double.parseDouble(modeloTabla.getValueAt(fila, 3).toString()));
            d.setDias(Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString()));
            return d;
        }
        return null;
    }

    public void actualizarTablaPrecios(double precioBase, double total) {
        modeloPrecios.setValueAt("S/ " + String.format("%.2f", precioBase), 0, 1);
        modeloPrecios.setValueAt("S/ " + String.format("%.2f", total), 1, 1);
    }

    public void limpiarFormulario() {
        tablaResultados.clearSelection();
        fechaEntrada.setDate(null);
        selector.reiniciarContadores();
        actualizarTablaPrecios(0.0, 0.0);
    }

    // ==== MÉTODOS NUEVOS PARA EL CONTROLADOR ====

    public SelectorPersonasPanel getSelectorPersonas() {
        return selector;
    }

    public JTable getTablaResultados() {
        return tablaResultados;
    }

    public void seleccionarPrimeraFilaSiExiste() {
        if (tablaResultados.getRowCount() > 0) {
            tablaResultados.setRowSelectionInterval(0, 0);
        }
    }

    public void cargarDestinosPorRegion(String nombreRegion) {
        List<Destino> destinos = new DestinoDAO().buscarPorLugar(nombreRegion);
        mostrarEnTabla(destinos);
    }
    
    public void setControlador(ControladorReserva controlador) {
        this.controlador = controlador;
        }
}
