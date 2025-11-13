package com.ega.cms.controller;

import com.ega.cms.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CMSController {
    private RepositorioContenido repositorio;
    private Map<String, Usuario> usuarios;
    private Usuario usuarioActual;

    public CMSController() {
        this.repositorio = new RepositorioContenido();
        this.usuarios = new HashMap<>();
        this.usuarioActual = null;
        precargarDatos();
    }

    private void precargarDatos() {
        Usuario admin = new Administrador("admin", "123");
        Usuario editor = new Editor("editor", "123");
        this.usuarios.put(admin.getNombreUsuario(), admin);
        this.usuarios.put(editor.getNombreUsuario(), editor);

        crearArticulo("Introducción a POO", "Académico", "Polimorfismo en Java...", admin);
        crearVideo("Tutorial MVC", "Desarrollo", "http://youtube.com/mvc", 600, admin);
        crearImagen("Diagrama UML", "Diseño", "http://imgur.com/diagrama.png", "1920x1080", editor);
    }

    public boolean login(String username, String password) {
        Usuario user = usuarios.get(username);
        if (user != null && user.verificarPassword(password)) {
            this.usuarioActual = user;
            return true;
        }
        return false;
    }

    public void logout() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public String getNombreUsuarioActual() {
        return (usuarioActual != null) ? usuarioActual.getNombreUsuario() : "Invitado";
    }

    public String crearArticulo(String titulo, String categoria, String texto, Usuario autor) {
        if (autor == null || !autor.tienePermiso(Permiso.CREAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para crear contenido.";
        }
        Articulo articulo = new Articulo(titulo, autor, categoria, texto);
        repositorio.agregarContenido(articulo);
        return ">> ÉXITO: Artículo '" + titulo + "' creado.";
    }

    public String crearVideo(String titulo, String categoria, String url, int duracion, Usuario autor) {
        if (autor == null || !autor.tienePermiso(Permiso.CREAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para crear contenido.";
        }
        Video video = new Video(titulo, autor, categoria, url, duracion);
        repositorio.agregarContenido(video);
        return ">> ÉXITO: Video '" + titulo + "' creado.";
    }

    public String crearImagen(String titulo, String categoria, String url, String resolucion, Usuario autor) {
        if (autor == null || !autor.tienePermiso(Permiso.CREAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para crear contenido.";
        }
        Imagen imagen = new Imagen(titulo, autor, categoria, url, resolucion);
        repositorio.agregarContenido(imagen);
        return ">> ÉXITO: Imagen '" + titulo + "' creada.";
    }

    public String crearPodcast(String titulo, String categoria, String url, int duracion, Usuario autor) {
        if (autor == null || !autor.tienePermiso(Permiso.CREAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para crear contenido.";
        }
        Podcast podcast = new Podcast(titulo, autor, categoria, url, duracion);
        repositorio.agregarContenido(podcast);
        return ">> ÉXITO: Podcast '" + titulo + "' creado.";
    }
    
    public String editarContenido(String titulo, String nuevoTitulo, String nuevaCategoria, String nuevoContenido, String nuevoValorSecundario) {
        if (usuarioActual == null || !usuarioActual.tienePermiso(Permiso.EDITAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para editar.";
        }
        Optional<Contenido> optContenido = repositorio.buscarPorTitulo(titulo);
        if (optContenido.isPresent()) {
            Contenido c = optContenido.get();
            if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) c.setTitulo(nuevoTitulo);
            if (nuevaCategoria != null && !nuevaCategoria.isEmpty()) c.setCategoria(nuevaCategoria);
            
            c.actualizar(nuevoContenido, nuevoValorSecundario);
            return ">> ÉXITO: Contenido '" + titulo + "' actualizado.";
        }
        return ">> ERROR: No se encontró contenido con el título '" + titulo + "'.";
    }

    public String eliminarContenido(String titulo) {
        if (usuarioActual == null || !usuarioActual.tienePermiso(Permiso.ELIMINAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para eliminar.";
        }
        if (repositorio.eliminarContenido(titulo)) {
            return ">> ÉXITO: Contenido '" + titulo + "' eliminado.";
        }
        return ">> ERROR: No se encontró contenido con el título '" + titulo + "'.";
    }

    public String publicarContenido(String titulo) {
        if (usuarioActual == null || !usuarioActual.tienePermiso(Permiso.PUBLICAR)) {
            return ">> ACCESO DENEGADO: No tiene permisos para publicar.";
        }
        Optional<Contenido> optContenido = repositorio.buscarPorTitulo(titulo);
        if (optContenido.isPresent()) {
            Contenido c = optContenido.get();
            c.publicar(usuarioActual);
            return ">> ÉXITO: Publicación procesada.";
        }
        return ">> ERROR: No se encontró contenido con el título '" + titulo + "'.";
    }

    public List<String> visualizarTodoElContenido() {
        return repositorio.listarTodo().stream()
                .map(Contenido::visualizar)
                .collect(Collectors.toList());
    }

    public String visualizarContenidoUnico(String titulo) {
        return repositorio.buscarPorTitulo(titulo)
                .map(Contenido::visualizar)
                .orElse(">> ERROR: No se encontró contenido con el título '" + titulo + "'.");
    }

    public List<String> buscarPorCategoria(String categoria) {
        return repositorio.buscarPorCategoria(categoria).stream()
                .map(Contenido::visualizar)
                .collect(Collectors.toList());
    }

    public List<String> buscarPorTipo(String tipo) {
        return repositorio.buscarPorTipo(tipo).stream()
                .map(Contenido::visualizar)
                .collect(Collectors.toList());
    }

    public String agregarComentario(String titulo, String textoComentario) {
        if (usuarioActual == null) {
            return ">> ERROR: Debe iniciar sesión para comentar.";
        }
        Optional<Contenido> optContenido = repositorio.buscarPorTitulo(titulo);
        if (optContenido.isPresent()) {
            Comentario comentario = new Comentario(usuarioActual, textoComentario);
            optContenido.get().agregarComentario(comentario);
            return ">> ÉXITO: Comentario agregado.";
        }
        return ">> ERROR: No se encontró contenido con el título '" + titulo + "'.";
    }

    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- REPORTE DE CONTENIDOS (EGA) ---\n");
        long total = repositorio.listarTodo().size();
        long publicados = repositorio.listarTodo().stream().filter(Contenido::estaPublicado).count();
        long articulos = repositorio.buscarPorTipo("Articulo").size();
        long videos = repositorio.buscarPorTipo("Video").size();
        long imagenes = repositorio.buscarPorTipo("Imagen").size();
        long podcasts = repositorio.buscarPorTipo("Podcast").size();

        reporte.append("Total de Contenidos: ").append(total).append("\n");
        reporte.append("Contenidos Publicados: ").append(publicados).append("\n");
        reporte.append("Contenidos en Borrador: ").append(total - publicados).append("\n");
        reporte.append("--- Desglose por Tipo ---\n");
        reporte.append("  Artículos: ").append(articulos).append("\n");
        reporte.append("  Videos: ").append(videos).append("\n");
        reporte.append("  Imágenes: ").append(imagenes).append("\n");
        reporte.append("  Podcasts: ").append(podcasts).append("\n");
        reporte.append("--------------------------------------\n");
        return reporte.toString();
    }
}