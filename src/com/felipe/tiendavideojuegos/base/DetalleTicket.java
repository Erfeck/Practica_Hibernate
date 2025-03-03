package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ticket_videojuego", schema = "h_juegos", catalog = "")
public class DetalleTicket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "cantidad")
    private int cantidad;
    @Basic
    @Column(name = "total")
    private double total;
    @ManyToOne
    @JoinColumn(name = "id_videojuego", referencedColumnName = "id")
    private Videojuego juego;
    @ManyToOne
    @JoinColumn(name = "id_ticket", referencedColumnName = "id")
    private Ticket ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleTicket that = (DetalleTicket) o;
        return id == that.id && cantidad == that.cantidad && Double.compare(that.total, total) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, total);
    }

    public Videojuego getJuego() {
        return juego;
    }

    public void setJuego(Videojuego juego) {
        this.juego = juego;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return juego + ": " + "Cantidad= " + cantidad + ", Total=" + total;
    }
}
