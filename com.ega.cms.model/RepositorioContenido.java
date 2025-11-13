package com.ega.cms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositorioContenido {
    private List<Contenido> baseDeDatosContenidos;

    public RepositorioContenido() {
        this.baseDeDatosContenidos = new ArrayList<>();
    }

    public void agregarContenido(Contenido contenido) {
        this.baseDeDatosContenidos.add(contenido);
    }

    public Optional<Contenido> buscarPorTitulo(String titulo) {
        return this.baseDeDatosContenidos.stream()
                .filter(c -> c.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public boolean eliminarContenido(String titulo) {
        return this.baseDeDatosContenidos.removeIf(c -> c.getTitulo().equalsIgnoreCase(titulo));
    }

    public List<Contenido> listarTodo() {
        return new ArrayList<>(this.baseDeDatosContenidos);
    }

    public List<Contenido> buscarPorCategoria(String categoria) {
        return this.baseDeDatosContenidos.stream()
                .filter(c -> c.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Contenido> buscarPorTipo(String tipo) {
        return this.baseDeDatosContenidos.stream()
                .filter(c -> c.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }
}