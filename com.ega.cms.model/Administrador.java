package com.ega.cms.model;

public class Administrador extends Usuario {
    public Administrador(String nombreUsuario, String password) {
        super(nombreUsuario, password);
    }

    @Override
    protected void asignarPermisos() {
        this.permisos.add(Permiso.CREAR);
        this.permisos.add(Permiso.EDITAR);
        this.permisos.add(Permiso.ELIMINAR);
        this.permisos.add(Permiso.PUBLICAR);
    }
}