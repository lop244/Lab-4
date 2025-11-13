package com.ega.cms.model;

public class Imagen extends Contenido {
    private String url;
    private String resolucion;

    public Imagen(String titulo, Usuario autor, String categoria, String url, String resolucion) {
        super(titulo, autor, categoria);
        this.url = url;
        this.resolucion = resolucion;
    }

    @Override
    public String visualizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============================================\n");
        sb.append("TIPO: IMAGEN\n");
        sb.append("TÍTULO: ").append(getTitulo()).append("\n");
        sb.append("AUTOR: ").append(getAutor().getNombreUsuario()).append("\n");
        sb.append("CATEGORÍA: ").append(getCategoria()).append("\n");
        sb.append("ESTADO: ").append(estaPublicado() ? "Publicado" : "Borrador").append("\n");
        sb.append("--------------------------------------------\n");
        sb.append("Mostrando imagen: ").append(this.url).append("\n");
        sb.append("Resolución: ").append(this.resolucion).append("\n");
        sb.append("--------------------------------------------");
        sb.append(mostrarComentarios());
        sb.append("\n============================================\n");
        return sb.toString();
    }

    @Override
    public void actualizar(String nuevoContenido, String nuevaResolucionDuracion) {
        if (nuevoContenido != null && !nuevoContenido.isEmpty()) {
            this.url = nuevoContenido;
        }
        if (nuevaResolucionDuracion != null && !nuevaResolucionDuracion.isEmpty()) {
            this.resolucion = nuevaResolucionDuracion;
        }
    }
}