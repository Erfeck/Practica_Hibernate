package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "videojuego")
public class Videojuego {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "precio")
    private double precio;
    @Basic
    @Column(name = "plataforma")
    private String plataforma;
    @Basic
    @Column(name = "fecha_lanzamiento")
    private Date fechaLanzamiento;
    @OneToMany(mappedBy = "juego")
    private List<Puntuacion> puntuaciones;
    @ManyToMany
    @JoinTable(name = "desarrollador_videojuego", catalog = "", schema = "h_juegos", joinColumns = @JoinColumn(name = "id_videojuego", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_desarrollador", referencedColumnName = "id"))
    private List<Desarrollador> desarrolladores;
    @OneToMany(mappedBy = "juego")
    private List<DetalleTicket> detallesticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Videojuego that = (Videojuego) o;
        return id == that.id && Double.compare(that.precio, precio) == 0 && Objects.equals(titulo, that.titulo) && Objects.equals(genero, that.genero) && Objects.equals(plataforma, that.plataforma) && Objects.equals(fechaLanzamiento, that.fechaLanzamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, genero, precio, plataforma, fechaLanzamiento);
    }

    public List<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(List<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public List<Desarrollador> getDesarrolladores() {
        return desarrolladores;
    }

    public void setDesarrolladores(List<Desarrollador> desarrolladores) {
        this.desarrolladores = desarrolladores;
    }

    public List<DetalleTicket> getDetallesticket() {
        return detallesticket;
    }

    public void setDetallesticket(List<DetalleTicket> detallesticket) {
        this.detallesticket = detallesticket;
    }

    @Override
    public String toString() {
        return titulo + " (" + plataforma + ")";
    }
}
