package control;

import Vista.PanelReserva;
import Vista.SelectorPersonasPanel;
import modelo.Destino;

import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ControladorReserva {
    private final PanelReserva vista;

    // NUEVOS CAMPOS para guardar la reserva seleccionada
    private Destino destinoSeleccionado;
    private Date fechaInicio;
    private Date fechaFin;
    private int adultos;
    private int ninos;

    public ControladorReserva(PanelReserva vista) {
        this.vista = vista;

        // Escuchar cambios en el selector de personas
        vista.getSelectorPersonas().addCambioListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPrecioTotal();
            }
        });

        // Escuchar selección de destino en la tabla
        JTable tabla = vista.getTablaResultados();
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarPrecioTotal();
            }
        });
    }

    private void actualizarPrecioTotal() {
        Destino destino = vista.obtenerDestinoSeleccionado();
        if (destino != null) {
            SelectorPersonasPanel selector = vista.getSelectorPersonas();
            int adultos = selector.getCantidadAdultos();
            int ninos = selector.getCantidadNinos();

            double precioBase = destino.getCosto();
            double total = (adultos + ninos) * precioBase;

            vista.actualizarTablaPrecios(precioBase, total);
        }
    }

    // NUEVO MÉTODO para recibir los datos desde PanelReserva
    public void setDatosReserva(Destino destino, Date fechaInicio, Date fechaFin, int adultos, int ninos) {
        this.destinoSeleccionado = destino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.adultos = adultos;
        this.ninos = ninos;
    }

    // GETTERS para PanelPago
    public Destino getDestinoSeleccionado() {
        return destinoSeleccionado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public int getAdultos() {
        return adultos;
    }

    public int getNinos() {
        return ninos;
    }
}
