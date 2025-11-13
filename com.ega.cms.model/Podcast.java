public package com.ega.cms.model;

public class Podcast extends Contenido {
    private String urlAudio;
    private int duracionMinutos;

    public Podcast(String titulo, Usuario autor, String categoria, String urlAudio, int duracionMinutos) {
        super(titulo, autor, categoria);
        this.urlAudio = urlAudio;
        this.duracionMinutos = duracionMinutos;
    }

    @Override
    public String visualizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("TIPO: PODCAST\n");
        sb.append("TÍTULO: ").append(getTitulo()).append("\n");
        sb.append("AUTOR: ").append(getAutor().getNombreUsuario()).append("\n");
        sb.append("CATEGORÍA: ").append(getCategoria()).append("\n");
        sb.append("ESTADO: ").append(estaPublicado() ? "Publicado" : "Borrador").append("\n");
        sb.append("Reproduciendo podcast: ").append(this.urlAudio).append("\n");
        sb.append("Duración: ").append(this.duracionMinutos).append(" minutos\n");
        sb.append(mostrarComentarios());
        return sb.toString();
    }

    @Override
    public void actualizar(String nuevoContenido, String nuevaResolucionDuracion) {
        if (nuevoContenido != null && !nuevoContenido.isEmpty()) {
            this.urlAudio = nuevoContenido;
        }
        if (nuevaResolucionDuracion != null && !nuevaResolucionDuracion.isEmpty()) {
            try {
                this.duracionMinutos = Integer.parseInt(nuevaResolucionDuracion);
            } catch (NumberFormatException e) {
                 // Ignorar si no es un número válido
            }
        }
    }
} {
    
}
