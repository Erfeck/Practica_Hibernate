package com.felipe.tiendavideojuegos.base;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "desarrollador_videojuego", schema = "h_juegos", catalog = "")
public class DesarrolladorVideojuego {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesarrolladorVideojuego that = (DesarrolladorVideojuego) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
