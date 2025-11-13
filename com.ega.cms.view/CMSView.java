package com.ega.cms.view;

import com.ega.cms.controller.CMSController;
import com.ega.cms.model.Usuario;
import java.util.List;
import java.util.Scanner;

public class CMSView {
    private CMSController controller;
    private Scanner scanner;

    public CMSView(CMSController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciarAplicacion() {
        System.out.println("Bienvenido al Sistema de Gestión de Contenidos (CMS) de EGA");
        while (true) {
            if (controller.getUsuarioActual() == null) {
                mostrarMenuLogin();
            } else {
                mostrarMenuPrincipal();
            }
        }
    }

    private void mostrarMenuLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Ver Contenido (Invitado)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                manejarLogin();
                break;
            case "2":
                manejarVisualizarTodo();
                break;
            case "0":
                System.out.println("Cerrando el sistema. Adiós.");
                System.exit(0);
                break;
            default:
                System.out.println(">> ERROR: Opción no válida.");
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL (Usuario: " + controller.getNombreUsuarioActual() + ") ---");
        System.out.println("1. Visualizar todo el contenido");
        System.out.println("2. Buscar contenido");
        System.out.println("3. Agregar comentario");
        System.out.println("--- Funciones de Creación (Editor+) ---");
        System.out.println("4. Crear nuevo contenido");
        System.out.println("5. Editar contenido existente");
        System.out.println("--- Funciones de Admin (Admin) ---");
        System.out.println("6. Publicar contenido");
        System.out.println("7. Eliminar contenido");
        System.out.println("8. Generar Reporte");
        System.out.println("9. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                manejarVisualizarTodo();
                break;
            case "2":
                manejarBusqueda();
                break;
            case "3":
                manejarAgregarComentario();
                break;
            case "4":
                manejarCrearContenido();
                break;
            case "5":
                manejarEditarContenido();
                break;
            case "6":
                manejarPublicarContenido();
                break;
            case "7":
                manejarEliminarContenido();
                break;
            case "8":
                System.out.println(controller.generarReporte());
                break;
            case "9":
                controller.logout();
                System.out.println("Sesión cerrada.");
                break;
            default:
                System.out.println(">> ERROR: Opción no válida.");
        }
    }

    private void manejarLogin() {
        System.out.print("Usuario (admin o editor): ");
        String user = scanner.nextLine();
        System.out.print("Contraseña (123): ");
        String pass = scanner.nextLine();
        if (controller.login(user, pass)) {
            System.out.println(">> ÉXITO: Inicio de sesión correcto. Bienvenido, " + user + ".");
        } else {
            System.out.println(">> ERROR: Credenciales incorrectas.");
        }
    }

    private void manejarVisualizarTodo() {
        System.out.println("\n--- MOSTRANDO TODO EL CONTENIDO ---");
        List<String> visualizaciones = controller.visualizarTodoElContenido();
        if (visualizaciones.isEmpty()) {
            System.out.println("(No hay contenido para mostrar)");
        } else {
            visualizaciones.forEach(System.out::println);
        }
    }

    private void manejarBusqueda() {
        System.out.println("Buscar por: 1. Categoría 2. Tipo (Articulo, Video, etc.)");
        String tipoBusqueda = scanner.nextLine();
        System.out.print("Ingrese el término de búsqueda: ");
        String termino = scanner.nextLine();
        List<String> resultados;

        if (tipoBusqueda.equals("1")) {
            resultados = controller.buscarPorCategoria(termino);
        } else if (tipoBusqueda.equals("2")) {
            resultados = controller.buscarPorTipo(termino);
        } else {
            System.out.println(">> ERROR: Tipo de búsqueda no válido.");
            return;
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados para '" + termino + "'.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private void manejarAgregarComentario() {
        System.out.print("Ingrese el título exacto del contenido a comentar: ");
        String titulo = scanner.nextLine();
        System.out.print("Escriba su comentario: ");
        String texto = scanner.nextLine();
        String resultado = controller.agregarComentario(titulo, texto);
        System.out.println(resultado);
    }

    private void manejarCrearContenido() {
        System.out.println("¿Qué tipo de contenido desea crear?");
        System.out.println("1. Artículo 2. Video 3. Imagen 4. Podcast");
        String tipo = scanner.nextLine();
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        
        String resultado = ">> ERROR: Tipo no válido.";
        Usuario autor = controller.getUsuarioActual();

        switch (tipo) {
            case "1":
                System.out.print("Texto del artículo: ");
                String texto = scanner.nextLine();
                resultado = controller.crearArticulo(titulo, categoria, texto, autor);
                break;
            case "2":
                System.out.print("URL del Video: ");
                String urlVideo = scanner.nextLine();
                System.out.print("Duración (segundos): ");
                int duracionVideo = Integer.parseInt(scanner.nextLine());
                resultado = controller.crearVideo(titulo, categoria, urlVideo, duracionVideo, autor);
                break;
            case "3":
                System.out.print("URL de la Imagen: ");
                String urlImagen = scanner.nextLine();
                System.out.print("Resolución (ej. 1920x1080): ");
                String resolucion = scanner.nextLine();
                resultado = controller.crearImagen(titulo, categoria, urlImagen, resolucion, autor);
                break;
            case "4":
                System.out.print("URL del Audio (Podcast): ");
                String urlAudio = scanner.nextLine();
                System.out.print("Duración (minutos): ");
                int duracionAudio = Integer.parseInt(scanner.nextLine());
                resultado = controller.crearPodcast(titulo, categoria, urlAudio, duracionAudio, autor);
                break;
        }
        System.out.println(resultado);
    }
    
    private void manejarEditarContenido() {
        System.out.print("Ingrese el título exacto del contenido a editar: ");
        String tituloActual = scanner.nextLine();
        
        System.out.println(controller.visualizarContenidoUnico(tituloActual));
        
        System.out.print("Nuevo Título (dejar en blanco para no cambiar): ");
        String nuevoTitulo = scanner.nextLine();
        System.out.print("Nueva Categoría (dejar en blanco para no cambiar): ");
        String nuevaCategoria = scanner.nextLine();
        
        System.out.print("Nuevo Contenido (URL/Texto) (dejar en blanco para no cambiar): ");
        String nuevoContenido = scanner.nextLine();
        System.out.print("Nuevo Valor Secundario (Resolución/Duración) (dejar en blanco para no cambiar): ");
        String nuevoValorSecundario = scanner.nextLine();

        String resultado = controller.editarContenido(tituloActual, nuevoTitulo, nuevaCategoria, nuevoContenido, nuevoValorSecundario);
        System.out.println(resultado);
    }

    private void manejarPublicarContenido() {
        System.out.print("Ingrese el título exacto del contenido a publicar: ");
        String titulo = scanner.nextLine();
        String resultado = controller.publicarContenido(titulo);
        System.out.println(resultado);
    }

    private void manejarEliminarContenido() {
        System.out.print("Ingrese el título exacto del contenido a eliminar: ");
        String titulo = scanner.nextLine();
        String resultado = controller.eliminarContenido(titulo);
        System.out.println(resultado);
    }
}