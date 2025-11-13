package com.ega.cms.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Contenido implements Publicable {
    private String titulo;
    private Usuario autor;
    private String categoria;
    private LocalDate fechaCreacion;
    private boolean estaPublicado;
    private List<Comentario> comentarios;

    public Contenido(String titulo, Usuario autor, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.fechaCreacion = LocalDate.now();
        this.estaPublicado = false;
        this.comentarios = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean estaPublicado() {
        return estaPublicado;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void agregarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    protected String mostrarComentarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t--- Comentarios (" + comentarios.size() + ") ---");
        if (comentarios.isEmpty()) {
            sb.append("\n\t(No hay comentarios)");
        } else {
            for (Comentario c : comentarios) {
                sb.append("\n").append(c.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public void publicar(Usuario usuario) {
        if (usuario.tienePermiso(Permiso.PUBLICAR)) {
            this.estaPublicado = true;
            System.out.println(">> ÉXITO: '" + this.titulo + "' ha sido publicado.");
        } else {
            System.out.println(">> ACCESO DENEGADO: " + usuario.getNombreUsuario() + " no tiene permisos para publicar.");
        }
    }

    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public abstract void actualizar(String nuevoContenido, String nuevaResolucionDuracion);
}