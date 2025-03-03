package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "puntuacion")
public class Puntuacion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "fuente")
    private String fuente;
    @Basic
    @Column(name = "nota_videojuego")
    private double notaVideojuego;
    @Basic
    @Column(name = "fecha_nota")
    private Date fechaNota;
    @ManyToOne
    @JoinColumn(name = "id_videojuego", referencedColumnName = "id")
    private Videojuego juego;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public double getNotaVideojuego() {
        return notaVideojuego;
    }

    public void setNotaVideojuego(double notaVideojuego) {
        this.notaVideojuego = notaVideojuego;
    }

    public Date getFechaNota() {
        return fechaNota;
    }

    public void setFechaNota(Date fechaNota) {
        this.fechaNota = fechaNota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puntuacion that = (Puntuacion) o;
        return id == that.id && Double.compare(that.notaVideojuego, notaVideojuego) == 0 && Objects.equals(fuente, that.fuente) && Objects.equals(fechaNota, that.fechaNota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fuente, notaVideojuego, fechaNota);
    }

    public Videojuego getJuego() {
        return juego;
    }

    public void setJuego(Videojuego juego) {
        this.juego = juego;
    }
}
