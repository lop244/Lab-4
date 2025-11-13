package com.ega.cms.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Usuario {
    private String nombreUsuario;
    private String password;
    protected Set<Permiso> permisos;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.permisos = new HashSet<>();
        this.asignarPermisos();
    }

    protected abstract void asignarPermisos();

    public boolean tienePermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean verificarPassword(String password) {
        return this.password.equals(password);
    }
}