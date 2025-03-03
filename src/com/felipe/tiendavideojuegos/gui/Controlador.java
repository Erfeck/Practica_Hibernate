package com.felipe.tiendavideojuegos.gui;

import com.felipe.tiendavideojuegos.base.*;
import com.felipe.tiendavideojuegos.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que maneja la lógica de interacción entre la vista y el modelo.
 * Actúa como intermediario para gestionar las acciones del usuario y
 * actualizar la vista en consecuencia. Además, gestiona las operaciones
 * de conexión, cierre de ventana y la manipulación de las entidades.
 */
public class Controlador implements ActionListener, ListSelectionListener {

    private Modelo modelo;
    private Vista vista;
    private VistaListaEntidad vistaListaEntidad;

    /**
     * Constructor del controlador, que establece la conexión entre el modelo y la vista,
     * y agrega los listeners necesarios para las acciones del usuario.
     * @param modelo El modelo que maneja la lógica de datos.
     * @param vista La vista que interactúa con el usuario.
     */
    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;

        addActionListeners(this);
        addWindowClosingListener();
        addListListeners(this);
    }

    /**
     * Añade los listeners para las acciones del usuario en los componentes de la vista.
     * Esto incluye los botones y los ítems del menú.
     * @param listener El listener de acción que se aplica a los componentes.
     */
    private void addActionListeners(ActionListener listener) {
        //Items Menu
        vista.itemConectar.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);

        //Items Popup
        vista.itemVerJuegos.addActionListener(listener);
        vista.itemVerTickets.addActionListener(listener);

        //Atributos Desarrollador
        vista.bDevAgregar.addActionListener(listener);
        vista.bDevModificar.addActionListener(listener);
        vista.bDevEliminar.addActionListener(listener);
        vista.bDevLimpiar.addActionListener(listener);
        vista.bDevEfectuarModificacion.addActionListener(listener);
        vista.bDevCancelarModificacion.addActionListener(listener);

        //Atributos Videojuego
        vista.bJuegoAgregar.addActionListener(listener);
        vista.bJuegoModificar.addActionListener(listener);
        vista.bJuegoEliminar.addActionListener(listener);
        vista.bJuegoLimpiar.addActionListener(listener);
        vista.bJuegoAgregarDev.addActionListener(listener);
        vista.bJuegoEliminarDev.addActionListener(listener);
        vista.bJuegoEfectuarModificacion.addActionListener(listener);
        vista.bJuegoCancelarModificacion.addActionListener(listener);

        //Atributos Puntuación
        vista.bRatingAgregar.addActionListener(listener);
        vista.bRatingModificar.addActionListener(listener);
        vista.bRatingEliminar.addActionListener(listener);
        vista.bRatingLimpiar.addActionListener(listener);
        vista.bRatingEfectuarModificacion.addActionListener(listener);
        vista.bRatingCancelarModificacion.addActionListener(listener);

        //Atributos Usuario
        vista.bUserAgregar.addActionListener(listener);
        vista.bUserModificar.addActionListener(listener);
        vista.bUserEliminar.addActionListener(listener);
        vista.bUserLimpiar.addActionListener(listener);
        vista.bUserEfectuarModificacion.addActionListener(listener);
        vista.bUserCancelarModificacion.addActionListener(listener);

        //Atributos Compra
        vista.bCompraAgregar.addActionListener(listener);
        vista.bCompraModificar.addActionListener(listener);
        vista.bCompraEliminar.addActionListener(listener);
        vista.bCompraLimpiar.addActionListener(listener);
        vista.bCompraAgregarJuego.addActionListener(listener);
        vista.bCompraEliminarJuego.addActionListener(listener);
        vista.bCompraEfectuarModificacion.addActionListener(listener);
        vista.bCompraCancelarModificacion.addActionListener(listener);
    }

    /**
     * Añade un listener para gestionar el evento de cierre de ventana.
     * Muestra una ventana de confirmación antes de cerrar la aplicación.
     * Si el usuario confirma, la aplicación se cierra.
     */
    private void addWindowClosingListener() {
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcionParaSalir = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que quieres salir?",
                        "Salir App",
                        JOptionPane.YES_NO_OPTION);

                if (opcionParaSalir == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Añade un listener de selección a varias tablas en la vista.
     * Este listener se activa cuando el usuario selecciona una fila en alguna de las tablas
     * (Desarrolladores, Juegos, Puntuaciones, Usuarios, Compras).
     * @param listener El listener de selección que será agregado a las tablas.
     */
    private void addListListeners(ListSelectionListener listener) {
        vista.tablaDesarrolladores.getSelectionModel().addListSelectionListener(listener);
        vista.tablaJuegos.getSelectionModel().addListSelectionListener(listener);
        vista.tablaPuntuaciones.getSelectionModel().addListSelectionListener(listener);
        vista.tablaUsuarios.getSelectionModel().addListSelectionListener(listener);
        vista.tablaCompras.getSelectionModel().addListSelectionListener(listener);
    }

    /**
     * Método que maneja los eventos de acción generados por los componentes de la vista.
     * Este método se invoca cuando el usuario realiza una acción, como hacer clic en un botón
     * o seleccionar un ítem del menú.
     * El comportamiento específico de cada acción depende del comando asociado con el componente.
     * @param e El evento de acción que contiene información sobre el origen de la acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Conectar":
                modelo.conectar();
                listarTodo();
                break;
            case "Salir":
                System.exit(0);
                break;
            case "Ver Videojuegos":
                if (vistaListaEntidad == null) {
                    vistaListaEntidad = new VistaListaEntidad();
                }
                int filaSelec = vista.tablaDesarrolladores.getSelectedRow();
                int idid = Integer.parseInt(String.valueOf(vista.tablaDesarrolladores.getValueAt(filaSelec, 0)));
                vistaListaEntidad.prepararVistaJuego("Lista Videojuegos", modelo.getDevs().get(idid - 1).getJuegos());
                break;
            case "Ver Tickets":
                if (vistaListaEntidad == null) {
                    vistaListaEntidad = new VistaListaEntidad();
                }
                filaSelec = vista.tablaUsuarios.getSelectedRow();
                idid = Integer.parseInt(String.valueOf(vista.tablaUsuarios.getValueAt(filaSelec, 0)));
                vistaListaEntidad.prepararVistaTicket("Lista Tickets", modelo.getUsers().get(idid - 1).getTickets());
                break;
            case "agregarDesarrollador":
                Desarrollador nuevoDesarrollador = new Desarrollador();
                nuevoDesarrollador.setNombre(vista.tfDevNombre.getText());
                nuevoDesarrollador.setEmail(vista.tfDevEmail.getText());
                nuevoDesarrollador.setExperiencia(Integer.parseInt(vista.tfDevExperiencia.getText()));
                nuevoDesarrollador.setTipo(String.valueOf(vista.cbDevTipoDesarrollo.getSelectedItem()));
                modelo.guardar(nuevoDesarrollador);
                limpiarCamposDesarrollador();
                listarDesarrolladores();
                break;
            case "agregarJuego":
                Videojuego nuevoVideojuego = new Videojuego();
                nuevoVideojuego.setTitulo(vista.tfJuegoTitulo.getText());
                nuevoVideojuego.setGenero(String.valueOf(vista.cbJuegoGenero.getSelectedItem()));
                nuevoVideojuego.setPrecio(Double.parseDouble(vista.tfJuegoPrecio.getText()));
                nuevoVideojuego.setPlataforma(elegirPlataforma());
                nuevoVideojuego.setFechaLanzamiento(Date.valueOf(vista.dpJuegoLanzamiento.getDate()));
                nuevoVideojuego.setDesarrolladores(agregarDevsAJuego());
                modelo.guardar(nuevoVideojuego);
                limpiarCamposVideojuego();
                listarVideojuegos();
                break;
            case "agregarPuntuacion":
                Puntuacion nuevaPuntuacion = new Puntuacion();
                nuevaPuntuacion.setJuego((Videojuego) vista.cbRatingJuego.getSelectedItem());
                nuevaPuntuacion.setFuente(vista.tfRatingFuente.getText());
                nuevaPuntuacion.setNotaVideojuego(Double.parseDouble(vista.tfRatingPuntuacion.getText()));
                nuevaPuntuacion.setFechaNota(Date.valueOf(String.valueOf(vista.dpRatingFecha.getDate())));
                modelo.guardar(nuevaPuntuacion);
                limpiarCamposPuntuacion();
                listarPuntuaciones();
                break;
            case "agregarUsuario":
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(vista.tfUserNombre.getText());
                nuevoUsuario.setApellidos(vista.tfUserApellidos.getText());
                nuevoUsuario.setDni(vista.tfUserDNI.getText());
                nuevoUsuario.setDireccion(vista.tfUserDireccion.getText());
                nuevoUsuario.setEmail(vista.tfUserEmail.getText());
                nuevoUsuario.setTelefono(vista.tfUserTelefono.getText());
                modelo.guardar(nuevoUsuario);
                limpiarCamposUsuario();
                listarUsuarios();
                break;
            case "agregarCompra":
                Ticket nuevoTicket = new Ticket();
                nuevoTicket.setCodigoTicket(Util.generarTicket(modelo.getUltimaCompra().getCodigoTicket()));
                nuevoTicket.setUsuario((Usuario) vista.cbCompraUsuarios.getSelectedItem());
                nuevoTicket.setDetallesTicket(agregarDetallesATicket());
                asignarTicketADetalles(nuevoTicket, nuevoTicket.getDetallesTicket());
                nuevoTicket.setFormaPago(vista.rbCompraEfectivo.isSelected() ? "Efectivo" : "Tarjeta");
                nuevoTicket.setFechaTicket(Date.valueOf(String.valueOf(vista.dpCompraFecha.getDate())));
                nuevoTicket.setTotal(agregarTotalAlTicket(nuevoTicket.getDetallesTicket()));
                modelo.guardarTicket(nuevoTicket);
                limpiarCamposCompra();
                listarCompras();
                break;
            case "modificarDesarrollador":
                int filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                if (filaDesarrollador == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador");
                    return;
                }
                bloquearParametrosDesarrollador(false);
                habilitarModoModificacionDev(true);
                break;
            case "modificarJuego":
                int filaJuego = vista.tablaJuegos.getSelectedRow();
                if (filaJuego == -1) {
                    Util.showErrorAlert("Selecciona un juego");
                    return;
                }
                bloquearParametrosVideojuego(false);
                habilitarModoModificacionJuego(true);
                break;
            case "modificarPuntuacion":
                int filaRating = vista.tablaPuntuaciones.getSelectedRow();
                if (filaRating == -1) {
                    Util.showErrorAlert("Selecciona una puntuación");
                    return;
                }
                bloquearParametrosPuntuacion(false);
                habilitarModoModificacionPuntuacion(true);
                break;
            case "modificarUsuario":
                int filaUsuario = vista.tablaUsuarios.getSelectedRow();
                if (filaUsuario == -1) {
                    Util.showErrorAlert("Selecciona un usuario");
                    return;
                }
                bloquearParametrosUsuario(false);
                habilitarModoModificacionUsuario(true);
                break;
            case "modificarCompra":
                int filaCompra = vista.tablaCompras.getSelectedRow();
                if (filaCompra == -1) {
                    Util.showErrorAlert("Selecciona una compra");
                    return;
                }
                bloquearParametrosCompra(false);
                habilitarModoModificacionCompra(true);
                break;
            case "eliminarDesarrollador":
                filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                if (filaDesarrollador == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador");
                    return;
                }
                int idDev = Integer.parseInt(String.valueOf(vista.tablaDesarrolladores.getValueAt(filaDesarrollador, 0)));
                Desarrollador desarrolladorABorrar = modelo.getDevs().get(idDev - 1);
                modelo.eliminar(desarrolladorABorrar, "desarrollador");
                listarDesarrolladores();
                break;
            case "eliminarJuego":
                filaJuego = vista.tablaJuegos.getSelectedRow();
                int idJuego = Integer.parseInt(String.valueOf(vista.tablaJuegos.getValueAt(filaJuego, 0)));
                Videojuego juegoABorrar = modelo.getGames().get(idJuego - 1);
                modelo.eliminar(juegoABorrar, "videojuego");
                listarVideojuegos();
                break;
            case "eliminarPuntuacion":
                int filaPuntuacion = vista.tablaPuntuaciones.getSelectedRow();
                int idRating = Integer.parseInt(String.valueOf(vista.tablaDesarrolladores.getValueAt(filaPuntuacion, 0)));
                Puntuacion puntuacionABorrar = modelo.getRatings().get(idRating - 1);
                modelo.eliminar(puntuacionABorrar, "puntuación");
                listarPuntuaciones();
                break;
            case "eliminarUsuario":
                filaUsuario = vista.tablaUsuarios.getSelectedRow();
                int idUsuario = Integer.parseInt(String.valueOf(vista.tablaUsuarios.getValueAt(filaUsuario, 0)));
                Usuario usuarioABorrar = modelo.getUsers().get(idUsuario - 1);
                modelo.eliminar(usuarioABorrar, "usuario");
                listarUsuarios();
                break;
            case "eliminarCompra":
                filaCompra = vista.tablaCompras.getSelectedRow();
                int idCompra = Integer.parseInt(String.valueOf(vista.tablaUsuarios.getValueAt(filaCompra, 0)));
                Ticket ticketABorrar = modelo.getCompras().get(idCompra - 1);
                modelo.eliminar(ticketABorrar, "compra");
                listarCompras();
                break;
            case "limpiarSeleccionDesarrollador":
                bloquearParametrosDesarrollador(false);
                limpiarCamposDesarrollador();
                vista.tablaDesarrolladores.clearSelection();
                vista.bDevLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionJuego":
                bloquearParametrosVideojuego(false);
                limpiarCamposVideojuego();
                vista.tablaJuegos.clearSelection();
                vista.bJuegoLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionPuntuacion":
                bloquearParametrosPuntuacion(false);
                limpiarCamposPuntuacion();
                vista.tablaPuntuaciones.clearSelection();
                vista.bRatingLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionUsuario":
                bloquearParametrosUsuario(false);
                limpiarCamposUsuario();
                vista.tablaUsuarios.clearSelection();
                vista.bUserLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionCompra":
                bloquearParametrosCompra(false);
                limpiarCamposCompra();
                vista.tablaCompras.clearSelection();
                vista.bCompraLimpiar.setVisible(false);
                break;
            case "efectuarModificacionDev":
                filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                int idDesarrollador = Integer.parseInt(String.valueOf(vista.tablaDesarrolladores.getValueAt(filaDesarrollador, 0)));
                Desarrollador otroDesarrollador = new Desarrollador();
                otroDesarrollador.setId(idDesarrollador);
                otroDesarrollador.setNombre(vista.tfDevNombre.getText());
                otroDesarrollador.setEmail(vista.tfDevEmail.getText());
                otroDesarrollador.setExperiencia(Integer.parseInt(vista.tfDevExperiencia.getText()));
                otroDesarrollador.setTipo(String.valueOf(vista.cbDevTipoDesarrollo.getSelectedItem()));
                modelo.modificar(otroDesarrollador);
                limpiarCamposDesarrollador();
                listarDesarrolladores();
                listarVideojuegos();
                vista.bDevCancelarModificacion.doClick();
                break;
            case "efectuarModificacionJuego":
                filaJuego = vista.tablaJuegos.getSelectedRow();
                idJuego = Integer.parseInt(String.valueOf(vista.tablaJuegos.getValueAt(filaJuego, 0)));
                Videojuego otroJuego = new Videojuego();
                otroJuego.setId(idJuego);
                otroJuego.setTitulo(vista.tfJuegoTitulo.getText());
                otroJuego.setGenero(String.valueOf(vista.cbJuegoGenero.getSelectedItem()));
                otroJuego.setPrecio(Double.parseDouble(vista.tfJuegoPrecio.getText()));
                otroJuego.setPlataforma(elegirPlataforma());
                otroJuego.setFechaLanzamiento(Date.valueOf(vista.dpJuegoLanzamiento.getDate()));
                otroJuego.setDesarrolladores(agregarDevsAJuego());
                modelo.modificar(otroJuego);
                limpiarCamposVideojuego();
                listarVideojuegos();
                listarPuntuaciones();
                listarCompras();
                vista.bJuegoCancelarModificacion.doClick();
                break;
            case "efectuarModificaciónPuntuacion":
                filaPuntuacion = vista.tablaPuntuaciones.getSelectedRow();
                int idPuntuacion = Integer.parseInt(String.valueOf(vista.tablaPuntuaciones.getValueAt(filaPuntuacion, 0)));
                Puntuacion otraPuntuacion = new Puntuacion();
                otraPuntuacion.setId(idPuntuacion);
                otraPuntuacion.setJuego((Videojuego) vista.cbRatingJuego.getSelectedItem());
                otraPuntuacion.setFuente(vista.tfRatingFuente.getText());
                otraPuntuacion.setNotaVideojuego(Double.parseDouble(vista.tfRatingPuntuacion.getText()));
                otraPuntuacion.setFechaNota(Date.valueOf(String.valueOf(vista.dpRatingFecha.getDate())));
                modelo.modificar(otraPuntuacion);
                limpiarCamposPuntuacion();
                listarPuntuaciones();
                vista.bRatingCancelarModificacion.doClick();
                break;
            case "efectuarModificaciónUsuario":
                filaUsuario = vista.tablaUsuarios.getSelectedRow();
                idUsuario = Integer.parseInt(String.valueOf(vista.tablaUsuarios.getValueAt(filaUsuario, 0)));
                Usuario otroUsuario = new Usuario();
                otroUsuario.setId(idUsuario);
                otroUsuario.setNombre(vista.tfUserNombre.getText());
                otroUsuario.setApellidos(vista.tfUserApellidos.getText());
                otroUsuario.setDni(vista.tfUserDNI.getText());
                otroUsuario.setDireccion(vista.tfUserDireccion.getText());
                otroUsuario.setEmail(vista.tfUserEmail.getText());
                otroUsuario.setTelefono(vista.tfUserTelefono.getText());
                modelo.modificar(otroUsuario);
                limpiarCamposUsuario();
                listarUsuarios();
                listarCompras();
                vista.bUserCancelarModificacion.doClick();
                break;
            case "efectuarModificaciónCompra":
                int filaTicket = vista.tablaCompras.getSelectedRow();
                int idTicket = Integer.parseInt(String.valueOf(vista.tablaCompras.getValueAt(filaTicket, 0)));
                Ticket otroTicket = new Ticket();
                otroTicket.setId(idTicket);
                otroTicket.setCodigoTicket(String.valueOf(vista.tablaCompras.getValueAt(filaTicket, 1)));
                otroTicket.setUsuario((Usuario) vista.cbCompraUsuarios.getSelectedItem());
                otroTicket.setDetallesTicket(agregarDetallesATicket());
                asignarTicketADetalles(otroTicket, otroTicket.getDetallesTicket());
                otroTicket.setFormaPago(vista.rbCompraEfectivo.isSelected() ? "Efectivo" : "Tarjeta");
                otroTicket.setFechaTicket(Date.valueOf(String.valueOf(vista.dpCompraFecha.getDate())));
                otroTicket.setTotal(agregarTotalAlTicket(otroTicket.getDetallesTicket()));
                modelo.modificarTicket(otroTicket);
                limpiarCamposCompra();
                listarCompras();
                vista.bCompraCancelarModificacion.doClick();
                break;
            case "cancelarModificacionDev":
                habilitarModoModificacionDev(false);
                vista.bDevLimpiar.doClick();
                break;
            case "cancelarModificaciónJuego":
                habilitarModoModificacionJuego(false);
                vista.bJuegoLimpiar.doClick();
                break;
            case "cancelarModificaciónPuntuacion":
                habilitarModoModificacionPuntuacion(false);
                vista.bRatingLimpiar.doClick();
                break;
            case "cancelarModificaciónUsuario":
                habilitarModoModificacionUsuario(false);
                vista.bUserLimpiar.doClick();
                break;
            case "cancelarModificaciónCompra":
                habilitarModoModificacionCompra(false);
                vista.bCompraLimpiar.doClick();
                break;
            case "agregarDevAUnJuego":
                if (vista.cbJuegoDesarrolladores.getSelectedItem() != "Elegir desarrollador") {
                    if (!vista.dlmDevDeJuego.contains(vista.cbJuegoDesarrolladores.getSelectedItem())) {
                        Desarrollador desarrolladorSeleccionado = (Desarrollador) vista.cbJuegoDesarrolladores.getSelectedItem();
                        System.out.println(desarrolladorSeleccionado.getNombre() + " -- " + desarrolladorSeleccionado.getEmail() + " -- " +
                                desarrolladorSeleccionado.getTipo());
                        vista.dlmDevDeJuego.addElement(desarrolladorSeleccionado);
                    } else {
                        Util.showErrorAlert("Ese desarrollador ya esta en la lista");
                    }
                } else {
                    Util.showErrorAlert("Debes seleccionar un desarrollador");
                }
                break;
            case "agregarJuegoACompra":
                if (vista.cbCompraJuegos.getSelectedItem() == "Elegir videojuego") {
                    Util.showErrorAlert("Debes seleccionar un videojuego");
                    return;
                }
                if (vista.tfCompraCantidad.getText().isEmpty()) {
                    Util.showErrorAlert("Debes añadir una cantidad");
                    return;
                }
                int cantidad = Integer.parseInt(vista.tfCompraCantidad.getText());
                Videojuego juegoSeleccionado = (Videojuego) vista.cbCompraJuegos.getSelectedItem();
                if (!estaJuegoEnLaLista(juegoSeleccionado)) {
                    DetalleTicket unDetalle = new DetalleTicket();
                    unDetalle.setJuego(juegoSeleccionado);
                    unDetalle.setCantidad(cantidad);
                    unDetalle.setTotal(juegoSeleccionado.getPrecio() * cantidad);
                    vista.dlmJuegosDeCompra.addElement(unDetalle);
                } else {
                    DetalleTicket otroDetalle = null;
                    for (int i = 0; i < vista.dlmJuegosDeCompra.size(); i++) {
                        if (vista.dlmJuegosDeCompra.get(i).getJuego().getId() == juegoSeleccionado.getId()) {
                            otroDetalle = vista.dlmJuegosDeCompra.get(i);
                            int nuevaCantidad = otroDetalle.getCantidad() + cantidad;
                            otroDetalle.setCantidad(nuevaCantidad);
                            otroDetalle.setTotal(juegoSeleccionado.getPrecio() * nuevaCantidad);
                            vista.dlmJuegosDeCompra.set(i, otroDetalle);
                        }
                    }
                }
                break;
            case "eliminarDevAUnJuego":
                int devDeUnJuegoSeleccionado = vista.listaDesarrolladoresDeJuego.getSelectedIndex();
                if (devDeUnJuegoSeleccionado == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador de la lista");
                    return;
                }
                vista.dlmDevDeJuego.remove(devDeUnJuegoSeleccionado);
                break;
            case "eliminarJuegoDeCompra":
                int filaABorrar = vista.listaJuegosAComprar.getSelectedIndex();

                if (filaABorrar == -1) {
                    Util.showErrorAlert("Debes seleccionar un juego");
                    return;
                }
                vista.dlmJuegosDeCompra.remove(filaABorrar);
                break;
        }
    }

    /**
     * Método que maneja los eventos de cambio en la selección de filas en las tablas de la vista.
     * Este método se invoca cuando el usuario selecciona o deselecciona una fila en las tablas.
     * El comportamiento de la acción depende de la tabla que haya generado el evento de selección.
     * @param e El evento de selección que contiene la información sobre el cambio de selección,
     *          como las filas seleccionadas o deseleccionadas.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getSource() == vista.tablaDesarrolladores.getSelectionModel() &&
                    !vista.tablaDesarrolladores.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaDesarrolladores.getSelectedRow();

                vista.tfDevNombre.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 1)));
                vista.tfDevEmail.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 2)));
                vista.tfDevExperiencia.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 3)));
                vista.cbDevTipoDesarrollo.setSelectedItem(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 4)));

                vista.bDevLimpiar.setVisible(true);
                bloquearParametrosDesarrollador(true);
            } else if (e.getSource() == vista.tablaJuegos.getSelectionModel() &&
                    !vista.tablaJuegos.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaJuegos.getSelectedRow();

                vista.tfJuegoTitulo.setText(String.valueOf(vista.ctmJuegos.getValueAt(fila, 1)));
                vista.cbJuegoGenero.setSelectedItem(String.valueOf(vista.ctmJuegos.getValueAt(fila, 2)));
                vista.tfJuegoPrecio.setText(String.valueOf(vista.ctmJuegos.getValueAt(fila, 3)));
                setPlataformaVideojuego(String.valueOf(vista.ctmJuegos.getValueAt(fila, 4)));
                vista.dpJuegoLanzamiento.setDate(Date.valueOf(String.valueOf(vista.ctmJuegos.getValueAt(fila,5))).toLocalDate());
                setDesarrolladoresDeUnJuego(fila);

                vista.bJuegoLimpiar.setVisible(true);
                bloquearParametrosVideojuego(true);
            } else if (e.getSource() == vista.tablaPuntuaciones.getSelectionModel() &&
                    !vista.tablaPuntuaciones.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaPuntuaciones.getSelectedRow();

                vista.cbRatingJuego.setSelectedItem(vista.dtmPuntuaciones.getValueAt(fila, 1));
                vista.tfRatingFuente.setText(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 2)));
                vista.tfRatingPuntuacion.setText(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 3)));
                vista.dpRatingFecha.setDate(Date.valueOf(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 4))).toLocalDate());

                vista.bRatingLimpiar.setVisible(true);
                bloquearParametrosPuntuacion(true);
            } else if (e.getSource() == vista.tablaUsuarios.getSelectionModel() &&
                    !vista.tablaUsuarios.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaUsuarios.getSelectedRow();

                vista.tfUserNombre.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 1)));
                vista.tfUserApellidos.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 2)));
                vista.tfUserDNI.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 3)));
                vista.tfUserDireccion.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 4)));
                vista.tfUserEmail.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 5)));
                vista.tfUserTelefono.setText(String.valueOf(vista.dtmUsuario.getValueAt(fila, 6)));

                vista.bUserLimpiar.setVisible(true);
                bloquearParametrosUsuario(true);
            } else if (e.getSource() == vista.tablaCompras.getSelectionModel() &&
                    !vista.tablaCompras.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaCompras.getSelectedRow();

                vista.cbCompraUsuarios.setSelectedItem(vista.dtmCompras.getValueAt(fila, 2));
                vista.cbCompraJuegos.setSelectedItem(0);
                vista.tfCompraCantidad.setText(null);
                setJuegosDeUnaCompra(Integer.parseInt(String.valueOf(vista.dtmCompras.getValueAt(fila, 0))));
                setFormaPagoCompra(String.valueOf(vista.dtmCompras.getValueAt(fila, 3)));
                vista.dpCompraFecha.setDate(Date.valueOf(String.valueOf(vista.dtmCompras.getValueAt(fila, 4))).toLocalDate());

                vista.bCompraLimpiar.setVisible(true);
                bloquearParametrosCompra(true);
            }
        }
    }

    /**
     * Llama a todos los métodos de listado (desarrolladores, videojuegos, usuarios, puntuaciones y compras)
     * para actualizar la vista con los datos más recientes de la base de datos.
     */
    public void listarTodo() {
        listarDesarrolladores();
        listarVideojuegos();
        listarUsuarios();
        listarPuntuaciones();
        listarCompras();
    }

    /**
     * Lista todos los desarrolladores y los agrega a la vista.
     * Actualiza el combobox de desarrolladores y la tabla de desarrolladores en la vista.
     */
    public void listarDesarrolladores() {
        List<Desarrollador> listaDevs = modelo.getDevs();

        vista.cbJuegoDesarrolladores.removeAllItems();
        vista.dtmDesarrollador.setRowCount(0);

        vista.cbJuegoDesarrolladores.addItem("Elegir desarrollador");
        for (Desarrollador unDesarrollador : listaDevs) {
            vista.dtmDesarrollador.addRow(new Object[] {
                    unDesarrollador.getId(),
                    unDesarrollador.getNombre(),
                    unDesarrollador.getEmail(),
                    unDesarrollador.getExperiencia(),
                    unDesarrollador.getTipo()
            });
            vista.cbJuegoDesarrolladores.addItem(unDesarrollador);
        }
    }

    /**
     * Lista todos los videojuegos y los agrega a la vista.
     * Actualiza los combo boxes de videojuegos en las secciones de puntuación y compra.
     */
    public void listarVideojuegos() {
        List<Videojuego> listaJuegos = modelo.getGames();

        vista.cbRatingJuego.removeAllItems();
        vista.cbCompraJuegos.removeAllItems();
        //vista.dtmJuegos.setRowCount(0);
        vista.ctmJuegos.clearAll();

        vista.cbRatingJuego.addItem("Elegir videojuego");
        vista.cbCompraJuegos.addItem("Elegir videojuego");
        for (Videojuego unJuego : listaJuegos) {
            /*vista.dtmJuegos.addRow(new Object[] {
                    unJuego.getId(),
                    unJuego.getTitulo(),
                    unJuego.getGenero(),
                    unJuego.getPrecio(),
                    unJuego.getPlataforma(),
                    unJuego.getFechaLanzamiento()
            });*/
            vista.ctmJuegos.addGame(unJuego);
            vista.cbRatingJuego.addItem(unJuego);
            vista.cbCompraJuegos.addItem(unJuego);
        }
    }

    /**
     * Lista todas las puntuaciones de videojuegos y las agrega a la vista.
     * Actualiza la tabla de puntuaciones en la vista con los datos correspondientes.
     */
    public void listarPuntuaciones() {
        List<Puntuacion> listaPuntuaciones = modelo.getRatings();

        vista.dtmPuntuaciones.setRowCount(0);

        for (Puntuacion unaPuntuacion : listaPuntuaciones) {
            vista.dtmPuntuaciones.addRow(new Object[] {
                    unaPuntuacion.getId(),
                    unaPuntuacion.getJuego(),
                    unaPuntuacion.getFuente(),
                    unaPuntuacion.getNotaVideojuego(),
                    unaPuntuacion.getFechaNota()
            });
        }
    }

    /**
     * Lista todos los usuarios y los agrega a la vista.
     * Actualiza el combo box de usuarios y la tabla de usuarios en la vista.
     */
    public void listarUsuarios() {
        List<Usuario> listaUsuarios = modelo.getUsers();

        vista.cbCompraUsuarios.removeAllItems();
        vista.dtmUsuario.setRowCount(0);

        vista.cbCompraUsuarios.addItem("Elegir usuario");
        for (Usuario unUsuario : listaUsuarios) {
            vista.dtmUsuario.addRow(new Object[] {
                    unUsuario.getId(),
                    unUsuario.getNombre(),
                    unUsuario.getApellidos(),
                    unUsuario.getDni(),
                    unUsuario.getDireccion(),
                    unUsuario.getEmail(),
                    unUsuario.getTelefono()
            });
            vista.cbCompraUsuarios.addItem(unUsuario);
        }
    }

    /**
     * Lista todas las compras (tickets) y las agrega a la vista.
     * Actualiza la tabla de compras en la vista con los detalles correspondientes.
     */
    public void listarCompras() {
        List<Ticket> listarCompras = modelo.getCompras();

        vista.dtmCompras.setRowCount(0);

        for (Ticket unaCompra : listarCompras) {
            vista.dtmCompras.addRow(new Object[] {
                    unaCompra.getId(),
                    unaCompra.getCodigoTicket(),
                    unaCompra.getUsuario(),
                    unaCompra.getFormaPago(),
                    unaCompra.getFechaTicket(),
                    unaCompra.getTotal()
            });
        }
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con los desarrolladores.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosDesarrollador(boolean estado) {
        vista.tfDevNombre.setEnabled(!estado);
        vista.tfDevEmail.setEnabled(!estado);
        vista.tfDevExperiencia.setEnabled(!estado);
        vista.cbDevTipoDesarrollo.setEnabled(!estado);
        vista.bDevAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con los videojuegos.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosVideojuego(boolean estado) {
        vista.tfJuegoTitulo.setEnabled(!estado);
        vista.cbJuegoGenero.setEnabled(!estado);
        vista.tfJuegoPrecio.setEnabled(!estado);
        vista.rbJuegoPc.setEnabled(!estado);
        vista.rbJuegoPlaystation.setEnabled(!estado);
        vista.rbJuegoXbox.setEnabled(!estado);
        vista.dpJuegoLanzamiento.setEnabled(!estado);
        vista.cbJuegoDesarrolladores.setEnabled(!estado);
        vista.bJuegoAgregarDev.setEnabled(!estado);
        vista.bJuegoEliminarDev.setEnabled(!estado);
        //vista.listaDesarrolladoresDeJuego.setEnabled(!estado);
        vista.bJuegoAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con las puntuaciones.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosPuntuacion(boolean estado) {
        vista.cbRatingJuego.setEnabled(!estado);
        vista.tfRatingFuente.setEnabled(!estado);
        vista.tfRatingPuntuacion.setEnabled(!estado);
        vista.dpRatingFecha.setEnabled(!estado);
        vista.bRatingAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con los usuarios.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosUsuario(boolean estado) {
        vista.tfUserNombre.setEnabled(!estado);
        vista.tfUserApellidos.setEnabled(!estado);
        vista.tfUserDNI.setEnabled(!estado);
        vista.tfUserDireccion.setEnabled(!estado);
        vista.tfUserEmail.setEnabled(!estado);
        vista.tfUserTelefono.setEnabled(!estado);
        vista.bUserAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con las compras.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosCompra(boolean estado) {
        vista.cbCompraUsuarios.setEnabled(!estado);
        vista.cbCompraJuegos.setEnabled(!estado);
        vista.tfCompraCantidad.setEnabled(!estado);
        vista.bCompraAgregarJuego.setEnabled(!estado);
        vista.bCompraEliminarJuego.setEnabled(!estado);
        vista.listaJuegosAComprar.setEnabled(!estado);
        vista.rbCompraEfectivo.setEnabled(!estado);
        vista.rbCompraTarjeta.setEnabled(!estado);
        vista.dpCompraFecha.setEnabled(!estado);
        vista.bCompraAgregar.setEnabled(!estado);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con los desarrolladores.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionDev(boolean habilitar) {
        vista.bDevEfectuarModificacion.setVisible(habilitar);
        vista.bDevCancelarModificacion.setVisible(habilitar);

        vista.bDevAgregar.setEnabled(!habilitar);
        vista.bDevModificar.setEnabled(!habilitar);
        vista.bDevEliminar.setEnabled(!habilitar);
        vista.bDevLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con los videojuegos.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionJuego(boolean habilitar) {
        vista.bJuegoEfectuarModificacion.setVisible(habilitar);
        vista.bJuegoCancelarModificacion.setVisible(habilitar);

        vista.bJuegoAgregar.setEnabled(!habilitar);
        vista.bJuegoModificar.setEnabled(!habilitar);
        vista.bJuegoEliminar.setEnabled(!habilitar);
        vista.bJuegoLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con las puntuaciones.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionPuntuacion(boolean habilitar) {
        vista.bRatingEfectuarModificacion.setVisible(habilitar);
        vista.bRatingCancelarModificacion.setVisible(habilitar);

        vista.bRatingAgregar.setEnabled(!habilitar);
        vista.bRatingModificar.setEnabled(!habilitar);
        vista.bRatingEliminar.setEnabled(!habilitar);
        vista.bRatingLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con los usuarios.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionUsuario(boolean habilitar) {
        vista.bUserEfectuarModificacion.setVisible(habilitar);
        vista.bUserCancelarModificacion.setVisible(habilitar);

        vista.bUserAgregar.setEnabled(!habilitar);
        vista.bUserModificar.setEnabled(!habilitar);
        vista.bUserEliminar.setEnabled(!habilitar);
        vista.bUserLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con las compras.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionCompra(boolean habilitar) {
        vista.bCompraEfectuarModificacion.setVisible(habilitar);
        vista.bCompraCancelarModificacion.setVisible(habilitar);

        vista.bCompraAgregar.setEnabled(!habilitar);
        vista.bCompraModificar.setEnabled(!habilitar);
        vista.bCompraEliminar.setEnabled(!habilitar);
        vista.bCompraLimpiar.setEnabled(!habilitar);
    }

    /**
     * Limpia los campos de entrada relacionados con los desarrolladores en la vista.
     * Restablece los campos de texto y las selecciones a sus valores por defecto.
     */
    private void limpiarCamposDesarrollador() {
        vista.tfDevNombre.setText(null);
        vista.tfDevEmail.setText(null);
        vista.tfDevExperiencia.setText(null);
        vista.cbDevTipoDesarrollo.setSelectedIndex(0);
    }

    /**
     * Limpia los campos de entrada relacionados con los videojuegos en la vista.
     * Restablece los campos de texto, las selecciones y la lista de desarrolladores asociados al videojuego a sus valores por defecto.
     */
    public void limpiarCamposVideojuego() {
        vista.tfJuegoTitulo.setText(null);
        vista.cbJuegoGenero.setSelectedIndex(0);
        vista.tfJuegoPrecio.setText(null);
        vista.rbJuegoPc.setSelected(true);
        vista.dpJuegoLanzamiento.setText(null);
        vista.cbJuegoDesarrolladores.setSelectedIndex(0);
        vista.dlmDevDeJuego.clear();
    }

    /**
     * Limpia los campos de entrada relacionados con las puntuaciones en la vista.
     * Restablece los campos de texto y las selecciones a sus valores por defecto.
     */
    public void limpiarCamposPuntuacion() {
        vista.cbRatingJuego.setSelectedIndex(0);
        vista.tfRatingFuente.setText(null);
        vista.tfRatingPuntuacion.setText(null);
        vista.dpRatingFecha.setText(null);
    }

    /**
     * Limpia los campos de entrada relacionados con los usuarios en la vista.
     * Restablece los campos de texto a sus valores por defecto.
     */
    public void limpiarCamposUsuario() {
        vista.tfUserNombre.setText(null);
        vista.tfUserApellidos.setText(null);
        vista.tfUserDNI.setText(null);
        vista.tfUserDireccion.setText(null);
        vista.tfUserEmail.setText(null);
        vista.tfUserTelefono.setText(null);
    }

    /**
     * Limpia los campos de entrada relacionados con las compras en la vista.
     * Restablece los campos de selección, cantidad, lista de juegos y la fecha a sus valores por defecto.
     */
    public void limpiarCamposCompra() {
        vista.cbCompraUsuarios.setSelectedIndex(0);
        vista.cbCompraJuegos.setSelectedIndex(0);
        vista.tfCompraCantidad.setText(null);
        vista.dlmJuegosDeCompra.clear();
        vista.rbCompraEfectivo.setSelected(true);
        vista.dpCompraFecha.setText(null);
    }

    /**
     * Establece los desarrolladores de un videojuego seleccionado en la vista.
     * Este método limpia la lista de desarrolladores asociados al videojuego
     * y luego agrega los desarrolladores correspondientes al videojuego seleccionado
     * en la tabla o lista.
     * @param fila La fila de la tabla de videojuegos que contiene el videojuego seleccionado.
     */
    private void setDesarrolladoresDeUnJuego(int fila) {
        Videojuego juegoSeleccionado = vista.ctmJuegos.getVideojuegoAt(fila);
        vista.dlmDevDeJuego.clear();
        for (Desarrollador unDesarrollador : juegoSeleccionado.getDesarrolladores()) {
            vista.dlmDevDeJuego.addElement(unDesarrollador);
        }
    }

    /**
     * Establece los juegos asociados a una compra seleccionada en la vista.
     * Este método limpia la lista de juegos asociados a la compra y luego agrega
     * los juegos correspondientes al ticket de compra seleccionado.
     *
     * @param idCompra El identificador de la compra seleccionada.
     */
    private void setJuegosDeUnaCompra(int idCompra) {
        Ticket ticketSeleccionado = modelo.getCompras().get(idCompra - 1);
        vista.dlmJuegosDeCompra.clear();
        for (DetalleTicket unDetalleDelTicket : ticketSeleccionado.getDetallesTicket()) {
            vista.dlmJuegosDeCompra.addElement(unDetalleDelTicket);
        }
    }

    /**
     * Establece la plataforma de un videojuego en la vista.
     * Marca la plataforma del videojuego como seleccionada en los botones de opción
     * (Radio Buttons) según el valor proporcionado.
     * @param plataformaVideojuego La plataforma del videojuego. Puede ser "PC", "Playstation", o "Xbox".
     */
    private void setPlataformaVideojuego(String plataformaVideojuego) {
        switch (plataformaVideojuego) {
            case "PC":
                vista.rbJuegoPc.setSelected(true);
                break;
            case "Playstation":
                vista.rbJuegoPlaystation.setSelected(true);
                break;
            case "Xbox":
                vista.rbJuegoXbox.setSelected(true);
                break;
        }
    }

    /**
     * Establece la forma de pago de una compra en la vista.
     * Marca la forma de pago seleccionada (ya sea "Efectivo" o "Tarjeta")
     * en los botones de opción de la vista.
     * @param formaPago La forma de pago utilizada para la compra. Puede ser "Efectivo" o "Tarjeta".
     */
    private void setFormaPagoCompra(String formaPago) {
        switch (formaPago) {
            case "Efectivo":
                vista.rbCompraEfectivo.setSelected(true);
                break;
            case "Tarjeta":
                vista.rbCompraTarjeta.setSelected(true);
                break;
        }
    }


    /**
     * Agrega los desarrolladores asociados a un videojuego en la vista
     * a una lista de desarrolladores.
     * Este método recorre la lista de elementos en la vista y los agrega a una nueva lista.
     * @return Una lista de desarrolladores asociados al videojuego.
     */
    private List<Desarrollador> agregarDevsAJuego() {
        List<Desarrollador> listaDesarrolladores = new ArrayList<>();
        for (int i = 0; i < vista.dlmDevDeJuego.size(); i++) {
            listaDesarrolladores.add(vista.dlmDevDeJuego.getElementAt(i));
        }
        return listaDesarrolladores;
    }

    /**
     * Agrega los detalles de los juegos de una compra a una lista de detalles del ticket.
     * Este método recorre la lista de juegos en el carrito de compra y los agrega a una nueva lista de detalles.
     * @return Una lista de detalles del ticket de compra, representando los juegos comprados.
     */
    private List<DetalleTicket> agregarDetallesATicket() {
        List<DetalleTicket> listaDetalles = new ArrayList<>();
        for (int i = 0; i < vista.dlmJuegosDeCompra.size(); i++) {
            listaDetalles.add(vista.dlmJuegosDeCompra.getElementAt(i));
        }
        return  listaDetalles;
    }

    /**
     * Calcula el total de un ticket de compra sumando los precios de los detalles del ticket.
     * Este método recorre la lista de detalles del ticket y acumula el total correspondiente a cada juego.
     * @param detallesTicket Una lista de detalles del ticket de compra.
     * @return El total de la compra, sumando los precios de todos los juegos en el ticket.
     */
    private Double agregarTotalAlTicket(List<DetalleTicket> detallesTicket) {
        Double totalDelTicket = 0.0;
        for (DetalleTicket unJuegoDelTicket : detallesTicket) {
            totalDelTicket += unJuegoDelTicket.getTotal();
        }
        return totalDelTicket;
    }

    /**
     * Asigna un ticket a los detalles del ticket proporcionados.
     * Este método recorre la lista de detalles de la compra y asigna el ticket
     * proporcionado a cada uno de los detalles, utilizando el método {@link DetalleTicket#setTicket(Ticket)}.
     * @param nuevoTicket El ticket que se asignará a los detalles.
     * @param detallesTicket Una lista de detalles de ticket que se les asignará el ticket.
     */
    private void asignarTicketADetalles(Ticket nuevoTicket, List<DetalleTicket> detallesTicket) {
        for (DetalleTicket unDetalle : detallesTicket) {
            unDetalle.setTicket(nuevoTicket);
        }
    }


    /**
     * Obtiene la plataforma seleccionada para un videojuego en la vista.
     * Este método verifica cuál de los botones de radio de plataforma está seleccionado
     * y devuelve el nombre de la plataforma correspondiente.
     * @return El nombre de la plataforma seleccionada, que puede ser "PC", "Playstation" o "Xbox".
     */
    private String elegirPlataforma() {
        String plataforma = "";
        if (vista.rbJuegoPc.isSelected()) plataforma = "PC";
        if (vista.rbJuegoPlaystation.isSelected()) plataforma = "Playstation";
        if (vista.rbJuegoXbox.isSelected()) plataforma = "Xbox";

        return plataforma;
    }

    /**
     * Verifica si un videojuego ya está presente en la lista de juegos de compra.
     * Este método recorre la lista de juegos en el carrito de compra y verifica si el
     * juego seleccionado ya se encuentra en la lista, comparando los identificadores de los juegos.
     * @param juegoSeleccionado El videojuego que se desea verificar si está en la lista.
     * @return `true` si el videojuego ya está en la lista, `false` si no lo está.
     */
    private boolean estaJuegoEnLaLista(Videojuego juegoSeleccionado) {
        for (int i = 0; i < vista.dlmJuegosDeCompra.size(); i++) {
            Videojuego juegoDeLista = vista.dlmJuegosDeCompra.get(i).getJuego();
            if (juegoDeLista.getId() == juegoSeleccionado.getId()) {
                return true;
            }
        }
        return false;
    }
}