package com.ega.cms.model;

public class Video extends Contenido {
    private String url;
    private int duracionSegundos;

    public Video(String titulo, Usuario autor, String categoria, String url, int duracionSegundos) {
        super(titulo, autor, categoria);
        this.url = url;
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public String visualizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============================================\n");
        sb.append("TIPO: VIDEO\n");
        sb.append("TÍTULO: ").append(getTitulo()).append("\n");
        sb.append("AUTOR: ").append(getAutor().getNombreUsuario()).append("\n");
        sb.append("CATEGORÍA: ").append(getCategoria()).append("\n");
        sb.append("ESTADO: ").append(estaPublicado() ? "Publicado" : "Borrador").append("\n");
        sb.append("--------------------------------------------\n");
        sb.append("Reproduciendo video desde: ").append(this.url).append("\n");
        sb.append("Duración: ").append(this.duracionSegundos).append(" segundos\n");
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
            try {
                this.duracionSegundos = Integer.parseInt(nuevaResolucionDuracion);
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }
        }
    }
}