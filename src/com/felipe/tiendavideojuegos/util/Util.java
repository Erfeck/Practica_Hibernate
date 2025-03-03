package com.felipe.tiendavideojuegos.util;

import javax.swing.*;

/**
 * Clase Util contiene métodos para mostrar diferentes tipos de alertas utilizando JOptionPane.
 * Esta clase proporciona métodos estáticos para mostrar alertas de error, advertencia e información.
 */
public class Util {

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de advertencia.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showWarningAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de información.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showInfoAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Genera el siguiente código de ticket en la secuencia.
     *
     * @param ultimoCodigo El último código generado en formato "A001" a "Z999".
     * @return El siguiente código en la secuencia.
     * @throws IllegalStateException si se han agotado los códigos disponibles.
     */
    public static String generarTicket(String ultimoCodigo) {
        char letra = ultimoCodigo.charAt(0);
        int numero = Integer.parseInt(ultimoCodigo.substring(1));

        numero++;
        if (numero > 999) {
            numero = 1;
            letra++;
            if (letra > 'Z') {
                throw new IllegalStateException("Se han agotado los tickets disponibles");
            }
        }

        return String.format("%c%03d", letra, numero);
    }
}

