package com.felipe.tiendavideojuegos.tables;

import com.felipe.tiendavideojuegos.base.Videojuego;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VideojuegosTableModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Título", "Género", "Precio", "Plataforma", "Lanzamiento"};
    private final List<Videojuego> videojuegos;


    public VideojuegosTableModel() {
        videojuegos = new ArrayList<>();
    }
    public VideojuegosTableModel(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public int getRowCount() {
        return videojuegos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Videojuego unVideojuego = videojuegos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return unVideojuego.getId();
            case 1:
                return unVideojuego.getTitulo();
            case 2:
                return unVideojuego.getGenero();
            case 3:
                return unVideojuego.getPrecio();
            case 4:
                return unVideojuego.getPlataforma();
            case 5:
                return unVideojuego.getFechaLanzamiento();
            default:
                return null;
        }
    }

    public void clearAll() {
        videojuegos.clear();
        fireTableDataChanged();
    }

    public void addGame(Videojuego videojuego) {
        videojuegos.add(videojuego);
        fireTableRowsInserted(videojuegos.size() - 1, videojuegos.size() - 1);
    }

    public Videojuego getVideojuegoAt(int rowIndex) {
        return videojuegos.get(rowIndex);
    }
}