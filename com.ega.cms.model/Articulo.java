package com.ega.cms.model;

public class Articulo extends Contenido {
    private String textoCompleto;

    public Articulo(String titulo, Usuario autor, String categoria, String textoCompleto) {
        super(titulo, autor, categoria);
        this.textoCompleto = textoCompleto;
    }

    @Override
    public String visualizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============================================\n");
        sb.append("TIPO: ARTÍCULO\n");
        sb.append("TÍTULO: ").append(getTitulo()).append("\n");
        sb.append("AUTOR: ").append(getAutor().getNombreUsuario()).append("\n");
        sb.append("CATEGORÍA: ").append(getCategoria()).append("\n");
        sb.append("ESTADO: ").append(estaPublicado() ? "Publicado" : "Borrador").append("\n");
        sb.append("--------------------------------------------\n");
        sb.append(this.textoCompleto);
        sb.append("\n--------------------------------------------");
        sb.append(mostrarComentarios());
        sb.append("\n============================================\n");
        return sb.toString();
    }

    @Override
    public void actualizar(String nuevoContenido, String nuevaResolucionDuracion) {
        if (nuevoContenido != null && !nuevoContenido.isEmpty()) {
            this.textoCompleto = nuevoContenido;
        }
    }
}
