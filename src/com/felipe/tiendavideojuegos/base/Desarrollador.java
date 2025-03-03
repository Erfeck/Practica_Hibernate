package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "desarrollador")
public class Desarrollador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "experiencia")
    private int experiencia;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @ManyToMany(mappedBy = "desarrolladores")
    private List<Videojuego> juegos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desarrollador that = (Desarrollador) o;
        return id == that.id && experiencia == that.experiencia && Objects.equals(nombre, that.nombre) && Objects.equals(email, that.email) && Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, email, experiencia, tipo);
    }

    public List<Videojuego> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Videojuego> juegos) {
        this.juegos = juegos;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
