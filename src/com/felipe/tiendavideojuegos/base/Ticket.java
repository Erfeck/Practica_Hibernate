package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "codigo_ticket")
    private String codigoTicket;
    @Basic
    @Column(name = "forma_pago")
    private String formaPago;
    @Basic
    @Column(name = "fecha_ticket")
    private Date fechaTicket;
    @Basic
    @Column(name = "total")
    private double total;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @OneToMany(mappedBy = "ticket")
    private List<DetalleTicket> detallesTicket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoTicket() {
        return codigoTicket;
    }

    public void setCodigoTicket(String codigoTicket) {
        this.codigoTicket = codigoTicket;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(Date fechaTicket) {
        this.fechaTicket = fechaTicket;
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
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(ticket.total, total) == 0 && Objects.equals(codigoTicket, ticket.codigoTicket) && Objects.equals(formaPago, ticket.formaPago) && Objects.equals(fechaTicket, ticket.fechaTicket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoTicket, formaPago, fechaTicket, total);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetalleTicket> getDetallesTicket() {
        return detallesTicket;
    }

    public void setDetallesTicket(List<DetalleTicket> detallesTicket) {
        this.detallesTicket = detallesTicket;
    }

    @Override
    public String toString() {
        return "Ticket " + codigoTicket + ": FormaPago= " + formaPago + ", Fecha= " + fechaTicket + ", Total= " + total;
    }
}
