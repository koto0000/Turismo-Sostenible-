package modelo;

import java.sql.*;
import java.time.LocalDate;

public class ReservaDAO {

    public boolean registrarReservaCompleta(
            Turista turista,
            int idDestino,
            int ninos,
            int adultos,
            LocalDate fechaInicio,
            LocalDate fechaFinal,
            String tipoPago,
            double monto,
            LocalDate fechaPago,
            String numeroTarjeta,
            String mmAa,
            String cvc,
            String numeroOperacion
    ) {
        Connection conn = null;
        CallableStatement stmtTurista = null;
        CallableStatement stmtReserva = null;
        CallableStatement stmtPago = null;
        CallableStatement stmtDetalle = null;

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false); // Inicia la transacción

            // 1. Insertar turista
            stmtTurista = conn.prepareCall("{call InsertarTurista(?, ?, ?, ?, ?, ?, ?)}");
            stmtTurista.setString(1, turista.getDni());
            stmtTurista.setString(2, turista.getNombre());
            stmtTurista.setString(3, turista.getApellidoPaterno());
            stmtTurista.setString(4, turista.getApellidoMaterno());
            stmtTurista.setString(5, turista.getTelefono());
            stmtTurista.setString(6, turista.getDireccion());
            stmtTurista.setString(7, turista.getCorreo());
            stmtTurista.execute();

            // 2. Insertar reserva
            stmtReserva = conn.prepareCall("{call InsertarReserva(?, ?, ?, ?, ?, ?, ?)}");
            stmtReserva.setString(1, turista.getDni());
            stmtReserva.setInt(2, idDestino);
            stmtReserva.setInt(3, ninos);
            stmtReserva.setInt(4, adultos);
            stmtReserva.setDate(5, Date.valueOf(fechaInicio));
            stmtReserva.setDate(6, Date.valueOf(fechaFinal));
            stmtReserva.registerOutParameter(7, Types.INTEGER);
            stmtReserva.execute();
            int idReserva = stmtReserva.getInt(7);

            // 3. Insertar metodo de pago
            stmtPago = conn.prepareCall("{call InsertarMetodoPago(?, ?, ?, ?, ?)}");
            stmtPago.setInt(1, idReserva);
            stmtPago.setString(2, tipoPago);
            stmtPago.setDouble(3, monto);
            stmtPago.setDate(4, Date.valueOf(fechaPago));
            stmtPago.registerOutParameter(5, Types.INTEGER);
            stmtPago.execute();
            int idMetodoPago = stmtPago.getInt(5);

            // 4. Insertar detalle según tipo de pago
            switch (tipoPago) {
                case "Tarjeta":
                    stmtDetalle = conn.prepareCall("{call InsertarTarjeta(?, ?, ?, ?)}");
                    stmtDetalle.setInt(1, idMetodoPago);
                    stmtDetalle.setString(2, numeroTarjeta);
                    stmtDetalle.setString(3, mmAa);
                    stmtDetalle.setString(4, cvc);
                    break;
                case "Yape":
                    stmtDetalle = conn.prepareCall("{call InsertarYape(?, ?)}");
                    stmtDetalle.setInt(1, idMetodoPago);
                    stmtDetalle.setString(2, numeroOperacion);
                    break;
                case "Plin":
                    stmtDetalle = conn.prepareCall("{call InsertarPlin(?, ?)}");
                    stmtDetalle.setInt(1, idMetodoPago);
                    stmtDetalle.setString(2, numeroOperacion);
                    break;
                default:
                    throw new SQLException("Tipo de pago no reconocido: " + tipoPago);
            }
            stmtDetalle.execute();

            conn.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try { if (stmtTurista != null) stmtTurista.close(); } catch (Exception e) {}
            try { if (stmtReserva != null) stmtReserva.close(); } catch (Exception e) {}
            try { if (stmtPago != null) stmtPago.close(); } catch (Exception e) {}
            try { if (stmtDetalle != null) stmtDetalle.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
