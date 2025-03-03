import com.felipe.tiendavideojuegos.gui.Controlador;
import com.felipe.tiendavideojuegos.gui.Modelo;
import com.felipe.tiendavideojuegos.gui.Vista;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
    }
}