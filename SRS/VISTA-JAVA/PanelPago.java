package Vista;

import Control.Validaciones;
import control.ControladorReserva;
import control.Reserva; // Corregido: Usar el modelo de datos, no el controlador
import modelo.Turista;
import modelo.ReservaDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class PanelPago extends JPanel {
    private JTextFieldRedondeado Nombre, Apellido, DNI, Correo, Celular;
    private JCheckBox Tarjeta, Yape, Plin;
    private MetodoDePago panelMetodoPago;
    private JTabbedPane pestañas;
    private JButton sig, atras;
    private Reserva datosReserva; // Corregido: modelo de datos
    private ControladorReserva controlador;
    private StepProgressIndicator stepPago;


    public PanelPago(JTabbedPane pestañas) {
        this.pestañas = pestañas;
        setLayout(null);
        setBackground(Color.WHITE);
        initComponents();
        initMetodosPago();
    }

    private void initComponents() {
        JLabel lblTitulo = new JLabel("Realizar Pago");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitulo.setBounds(350, 120, 400, 60);
        add(lblTitulo);

        Nombre = new JTextFieldRedondeado(20);
        Apellido = new JTextFieldRedondeado(20);
        DNI = new JTextFieldRedondeado(20);
        Correo = new JTextFieldRedondeado(20);
        Celular = new JTextFieldRedondeado(20);

        //Nombre
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Color.BLACK);
        lblNombre.setBounds(75, 170, 600, 60);
        add(lblNombre);
        
        Nombre = new JTextFieldRedondeado(20);
        Nombre.setBounds(75,215, 170, 30);
        add(Nombre);
        //Apellido
        JLabel lblApellido = new JLabel("Apellidos");
        lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblApellido.setForeground(Color.BLACK);
        lblApellido.setBounds(280, 170, 600, 60);
        add(lblApellido);
        
        Apellido = new JTextFieldRedondeado(20);
        Apellido.setBounds(280, 215, 220, 30);
        add(Apellido); 
        //DNI
        JLabel lblDNI = new JLabel("DNI");
        lblDNI.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDNI.setForeground(Color.BLACK);
        lblDNI.setBounds(75, 308, 600, 15);
        add(lblDNI);
        
        DNI = new JTextFieldRedondeado(20);
        DNI.setBounds(75, 331, 170, 30);
        add(DNI); 
        
        //Correo
        JLabel lblCorreo = new JLabel("Correo Electronico");
        lblCorreo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCorreo.setForeground(Color.BLACK);
        lblCorreo.setBounds(75, 233, 600, 50);
        add(lblCorreo);
        
        Correo = new JTextFieldRedondeado(20);
        Correo.setBounds(75, 275, 425, 30);
        add(Correo);
        
        //Celular
        JLabel lblCelular = new JLabel("Celular");
        lblCelular.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCelular.setForeground(Color.BLACK);
        lblCelular.setBounds(280, 310, 600, 15);
        add(lblCelular);
        
        Celular = new JTextFieldRedondeado(20);
        Celular.setBounds(280, 331, 220, 30);
        add(Celular);

        sig = new JButton("Finalizar compra");
        sig.setBounds(675, 530, 150, 40);
        estilizarBoton(sig);
        add(sig);

        atras = new JButton("Atrás");
        atras.setBounds(225, 530, 150, 40);
        estilizarBoton(atras);
        add(atras);

        atras.addActionListener(e -> pestañas.setSelectedIndex(2));
        sig.addActionListener(e -> finalizarCompra());
    }

    private void addLabelAndField(String texto, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBounds(x, y - 20, 200, 20);
        add(lbl);
        campo.setBounds(x, y, 170, 30);
        add(campo);
    }

    private void initMetodosPago() {
        JLabel lblMetodoPago = new JLabel("Método de Pago:");
        lblMetodoPago.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMetodoPago.setBounds(90, 370, 200, 30);
        add(lblMetodoPago);

        Tarjeta = new JCheckBox("Tarjeta");
        Yape = new JCheckBox("Yape");
        Plin = new JCheckBox("Plin");

        configurarCheckBox(Tarjeta, 100, 400);
        configurarCheckBox(Yape, 100, 430);
        configurarCheckBox(Plin, 100, 460);

        panelMetodoPago = new MetodoDePago();
        panelMetodoPago.setVisible(false);
        panelMetodoPago.setBounds(580, 200, 300, 310);
        add(panelMetodoPago);

        ItemListener listener = e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                panelMetodoPago.setVisible(true);
                Tarjeta.setSelected(source == Tarjeta);
                Yape.setSelected(source == Yape);
                Plin.setSelected(source == Plin);
                panelMetodoPago.mostrarPanel(source.getText());
            } else {
                if (!Tarjeta.isSelected() && !Yape.isSelected() && !Plin.isSelected()) {
                    panelMetodoPago.setVisible(false);
                }
            }
        };

        Tarjeta.addItemListener(listener);
        Yape.addItemListener(listener);
        Plin.addItemListener(listener);

        add(Tarjeta);
        add(Yape);
        add(Plin);
    }

    private void configurarCheckBox(JCheckBox box, int x, int y) {
        box.setBounds(x, y, 200, 30);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        box.setFocusPainted(false);
        box.setBorderPainted(false);
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(64, 224, 208));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(new Color(48, 170, 158)); }
            public void mouseExited(MouseEvent e) { boton.setBackground(new Color(64, 224, 208)); }
            public void mousePressed(MouseEvent e) { boton.setBackground(new Color(102, 235, 220)); }
            public void mouseReleased(MouseEvent e) { boton.setBackground(new Color(48, 170, 158)); }
        });
    }

    public void setDatosReserva(ControladorReserva controlador) {
        this.controlador = controlador;

        // Aquí puedes hacer algo como mostrar los datos
        if (controlador.getDestinoSeleccionado() != null) {
            System.out.println("Destino seleccionado: " + controlador.getDestinoSeleccionado().getNombre());
        } else {
            System.out.println("⚠️ No se encontraron los datos de reserva");
        }
    }

    private void finalizarCompra() {
        // 1. Validar campos vacíos
        if (Nombre.getText().trim().isEmpty() || Apellido.getText().trim().isEmpty() ||
            DNI.getText().trim().isEmpty() || Correo.getText().trim().isEmpty() ||
            Celular.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos personales.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validar formato de los datos
        if (!Validaciones.esNombreValido(Nombre.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validaciones.esNombreValido(Apellido.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El apellido solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validaciones.esDNIValido(DNI.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validaciones.esCorreoValido(Correo.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Correo electrónico no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validaciones.esCelularValido(Celular.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El número de celular debe comenzar con 9 y tener 9 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validar método de pago seleccionado
        String tipoPago = Tarjeta.isSelected() ? "Tarjeta" : Yape.isSelected() ? "Yape" : Plin.isSelected() ? "Plin" : "";
        if (tipoPago.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un método de pago.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Validar existencia de datos de reserva
        if (datosReserva == null) {
            JOptionPane.showMessageDialog(this, "No se encontraron los datos de reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Turista turista = new Turista();
        turista.setNombre(Nombre.getText());
        turista.setApellidoPaterno(Apellido.getText());
        turista.setApellidoMaterno("");
        turista.setDni(DNI.getText());
        turista.setCorreo(Correo.getText());
        turista.setTelefono(Celular.getText());
        turista.setDireccion("Sin dirección");

        ReservaDAO dao = new ReservaDAO();
        boolean exito = dao.registrarReservaCompleta(
                turista,
                datosReserva.getIdDestino(),
                datosReserva.getNinos(),
                datosReserva.getAdultos(),
                datosReserva.getFechaInicio().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                datosReserva.getFechaFinal().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                tipoPago,
                datosReserva.getTotal(),
                LocalDate.now(),
                panelMetodoPago.getNumeroTarjeta(),
                panelMetodoPago.getFechaTarjeta(),
                panelMetodoPago.getCVC(),
                panelMetodoPago.getNumeroOperacion()
        );

        if (exito) {
            stepPago.marcarCompleto();
            JOptionPane.showMessageDialog(this, "¡Reserva registrada exitosamente!");
            pestañas.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar reserva.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarDatosReserva() {
        if (controlador == null) {
            JOptionPane.showMessageDialog(this, "No se ha recibido el controlador de reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controlador.getDestinoSeleccionado() == null) {
            JOptionPane.showMessageDialog(this, "No se encontraron los datos de reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        datosReserva = new Reserva();
        datosReserva.setIdDestino(controlador.getDestinoSeleccionado().getId());
        datosReserva.setNinos(controlador.getNinos());
        datosReserva.setAdultos(controlador.getAdultos());
        datosReserva.setFechaInicio(controlador.getFechaInicio());
        datosReserva.setFechaFinal(controlador.getFechaFin());

        double precioBase = controlador.getDestinoSeleccionado().getCosto();
        double total = precioBase * (datosReserva.getAdultos() + datosReserva.getNinos());
        datosReserva.setTotal(total);

        // Para debug
        System.out.println("✅ Datos de reserva cargados: " + datosReserva);
    }
    public void setStepPago(StepProgressIndicator stepPago) {
        this.stepPago = stepPago;
    }
}
