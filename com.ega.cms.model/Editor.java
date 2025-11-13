package com.ega.cms.model;

public class Editor extends Usuario {
    public Editor(String nombreUsuario, String password) {
        super(nombreUsuario, password);
    }

    @Override
    protected void asignarPermisos() {
        this.permisos.add(Permiso.CREAR);
        this.permisos.add(Permiso.EDITAR);
    }
}