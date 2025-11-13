public package com.ega.cms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comentario {
    private Usuario autor;
    private String texto;
    private LocalDateTime fecha;

    public Comentario(Usuario autor, String texto) {
        this.autor = autor;
        this.texto = texto;
        this.fecha = LocalDateTime.now();
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("\t[%s] %s: %s",
                this.fecha.format(formatter),
                this.autor.getNombreUsuario(),
                this.texto);
    }
} Comentario {
    
}
