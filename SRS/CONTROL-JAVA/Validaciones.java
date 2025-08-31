package Control;

public class Validaciones {

    // Validar que solo tenga letras y espacios (nombre, apellido, etc.)
    public static boolean esNombreValido(String texto) {
        return texto != null && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    // Validar DNI con exactamente 8 dígitos
    public static boolean esDNIValido(String dni) {
        return dni != null && dni.matches("^\\d{8}$");
    }

    // Validar celular peruano (9 dígitos, empieza con 9)
    public static boolean esCelularValido(String celular) {
        return celular != null && celular.matches("^9\\d{8}$");
    }

    // Validar correo básico (no exhaustivo)
    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    // Validar número de tarjeta (básico: 16 dígitos)
    public static boolean esNumeroTarjetaValido(String numero) {
        return numero != null && numero.matches("^\\d{16}$");
    }

    // Validar CVC (3 o 4 dígitos)
    public static boolean esCVCValido(String cvc) {
        return cvc != null && cvc.matches("^\\d{3,4}$");
    }

    // Validar número de operación (6 a 12 dígitos, ejemplo para Yape/Plin)
    public static boolean esNumeroOperacionValido(String numero) {
        return numero != null && numero.matches("^\\d{3,12}$");
    }
}
