package com.felipe.tiendavideojuegos.gui;

import com.felipe.tiendavideojuegos.base.*;
import com.felipe.tiendavideojuegos.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Clase encargada de gestionar la conexión con la base de datos utilizando Hibernate.
 * Proporciona métodos para realizar operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre las entidades mapeadas, como Desarrollador, Videojuego, Usuario, Ticket, etc.
 */
public class Modelo {

    SessionFactory sessionFactory;

    /**
     * Establece la conexión con la base de datos configurada en el archivo hibernate.cfg.xml.
     * Carga las clases mapeadas y configura el registro de servicios necesario para Hibernate.
     */
    public void conectar() {
        Configuration configuration = new Configuration();

        //Cargo fichero hibernate
        configuration.configure("hibernate.cfg.xml");

        //Indico las clases mapeadas
        configuration.addAnnotatedClass(Desarrollador.class);
        configuration.addAnnotatedClass(DesarrolladorVideojuego.class);
        configuration.addAnnotatedClass(DetalleTicket.class);
        configuration.addAnnotatedClass(Puntuacion.class);
        configuration.addAnnotatedClass(Ticket.class);
        configuration.addAnnotatedClass(Usuario.class);
        configuration.addAnnotatedClass(Videojuego.class);

        //Creamos un objeto ServiceRegistry a partir de los parámetros de configuración
        //Esta clase se usa para gestionar y proveer acceso a servicios
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        //Finalmente, creamos un objeto de sessionFactory a partir de la configuración del registro de servicios
        sessionFactory = configuration.buildSessionFactory(ssr);
    }

    /**
     * Cierra la conexión con la base de datos y libera los recursos de la SessionFactory.
     */
    public void desconectar() {
        //Cierro la factoria de sesiones
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    /**
     * Recupera la lista completa de desarrolladores de la base de datos.
     * @return Una lista de objetos {@link Desarrollador}.
     */
    public List<Desarrollador> getDevs() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Desarrollador");
        List<Desarrollador> listaDevs = query.getResultList();
        for (Desarrollador unDev : listaDevs) {
            Hibernate.initialize(unDev.getJuegos());
        }
        session.close();

        return listaDevs;
    }

    /**
     * Recupera la lista completa de videojuegos de la base de datos.
     * @return Una lista de objetos {@link Videojuego}.
     */
    public List<Videojuego> getGames() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Videojuego");
        List<Videojuego> listaJuegos = query.getResultList();
        for (Videojuego unJuego : listaJuegos) {
            Hibernate.initialize(unJuego.getDesarrolladores());
        }
        session.close();

        return listaJuegos;
    }

    /**
     * Recupera la lista completa de puntuaciones de la base de datos.
     * @return Una lista de objetos {@link Puntuacion}.
     */
    public List<Puntuacion> getRatings() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Puntuacion");
        List<Puntuacion> listaPuntuaciones = query.getResultList();
        session.close();

        return listaPuntuaciones;
    }

    /**
     * Recupera la lista completa de usuarios de la base de datos.
     * @return Una lista de objetos {@link Usuario}.
     */
    public List<Usuario> getUsers() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Usuario");
        List<Usuario> listaUsuarios = query.getResultList();
        for (Usuario unUsuario : listaUsuarios) {
            Hibernate.initialize(unUsuario.getTickets());
        }
        session.close();

        return listaUsuarios;
    }

    /**
     * Recupera la lista completa de tickets de la base de datos.
     * @return Una lista de objetos {@link Ticket}.
     */
    public List<Ticket> getCompras() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Ticket");
        List<Ticket> listaCompras = query.getResultList();
        for (Ticket unTicket : listaCompras) {
            Hibernate.initialize(unTicket.getDetallesTicket());
        }
        session.close();

        return listaCompras;
    }

    /**
     * Recupera la última compra registrada en la base de datos.
     * @return El último objeto {@link Ticket} creado.
     */
    public Ticket getUltimaCompra() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Ticket ORDER BY codigoTicket DESC");
        query.setMaxResults(1);
        Ticket ultimaCompra = (Ticket) query.getSingleResult();
        session.close();

        return ultimaCompra;
    }

    /**
     * Guarda un nuevo elemento en la base de datos.
     * @param nuevoElemento El objeto que se desea guardar.
     */
    public void guardar(Object nuevoElemento) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(nuevoElemento);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Guarda un nuevo ticket y sus detalles asociados en la base de datos.
     * @param nuevoElemento El objeto {@link Ticket} que se desea guardar.
     */
    public void guardarTicket(Ticket nuevoElemento) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(nuevoElemento);
        for (DetalleTicket unDetalle : nuevoElemento.getDetallesTicket()) {
            session.save(unDetalle);
        }
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Modifica un ticket existente en la base de datos, eliminando los detalles antiguos
     * y guardando los nuevos detalles.
     * @param ticketModificado El objeto {@link Ticket} que se desea modificar.
     */
    public void modificarTicket(Ticket ticketModificado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (DetalleTicket unDetalle : getCompras().get(ticketModificado.getId() - 1).getDetallesTicket()) {
            session.delete(unDetalle);
        }
        session.update(ticketModificado);
        for (DetalleTicket unDetalle : ticketModificado.getDetallesTicket()) {
            session.save(unDetalle);
        }
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Modifica un elemento existente en la base de datos.
     * @param elementoSeleccionado El objeto a modificar.
     */
    public void modificar(Object elementoSeleccionado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(elementoSeleccionado);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Elimina un elemento de la base de datos.
     * Si el elemento está vinculado con otras entidades, se muestra un mensaje de error.
     * @param elementoABorrar El objeto a eliminar.
     * @param entidad El nombre de la entidad a eliminar.
     */
    public void eliminar(Object elementoABorrar, String entidad) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(elementoABorrar);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            Util.showErrorAlert("No puedes borrar este " + entidad + " porque esta vinculado con otra tabla.\n" +
                    "Borra esa conexión antes de borrar el " + entidad);
        } finally {
            session.close();
        }
    }
}