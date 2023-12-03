import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Clase principal que inicia la aplicación
public class Main {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        inicializarUsuarios();
        mostrarMenuPrincipal();
    }

    private static void inicializarUsuarios() {
        // Crear al menos un usuario de cliente y un usuario de empleado
        Cliente cliente = new Cliente("cliente", "1234");
        Empleado empleado = new Empleado("empleado", "abcd");

        usuarios.add(cliente);
        usuarios.add(empleado);
    }

    private static void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al Sistema de Punto de Venta");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    System.out.println("Gracias por usar el Sistema de Punto de Venta. ¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void iniciarSesion() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = validarCredenciales(nombreUsuario, contrasena);

        if (usuario != null) {
            usuario.mostrarMenu();
        } else {
            System.out.println("Credenciales incorrectas. Inténtelo de nuevo.");
        }
    }

    private static Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(nombreUsuario, contrasena)) {
                return usuario;
            }
        }
        return null;
    }
}

abstract class Usuario {
    protected String nombre;
    protected String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public abstract void mostrarMenu();

    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        return this.nombre.equals(nombreUsuario) && this.contrasena.equals(contrasena);
    }
}

class Cliente extends Usuario {
    private CarritoDeCompra carrito;
    private Inventario inventario;

    public Cliente(String nombre, String contrasena) {
        super(nombre, contrasena);
        this.carrito = new CarritoDeCompra();
    }

    @Override
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú de Cliente");
            System.out.println("1. Carrito de compra");
            System.out.println("2. Caja");
            System.out.println("3. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    carrito.mostrarMenu(inventario);
                    break;
                case 2:
                    Caja.realizarPago(carrito);
                    break;
                case 3:
                    System.out.println("Sesión cerrada. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }
}

class Empleado extends Usuario {
    public Empleado(String nombre, String contrasena) {
        super(nombre, contrasena);
    }

    @Override
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú de Empleado");
            System.out.println("1. Inventario");
            System.out.println("2. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    Inventario.mostrarMenu();
                    break;
                case 2:
                    System.out.println("Sesión cerrada. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }
}

class Inventario {
    private static List<String> elementos = new ArrayList<>();

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú de Inventario");
            System.out.println("1. Registrar elemento");
            System.out.println("2. Borrar elemento");
            System.out.println("3. Ordenar elementos");
            System.out.println("4. Mostrar elementos");
            System.out.println("5. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    registrarElemento();
                    break;
                case 2:
                    borrarElemento();
                    break;
                case 3:
                    ordenarElementos();
                    break;
                case 4:
                    mostrarElementos();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void registrarElemento() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del elemento a registrar: ");
        String elemento = scanner.nextLine();
        elementos.add(elemento);

        System.out.println("Elemento registrado exitosamente: " + elemento);
    }

    private static void borrarElemento() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del elemento a borrar: ");
        String elemento = scanner.nextLine();

        if (elementos.remove(elemento)) {
            System.out.println("Elemento borrado exitosamente: " + elemento);
        } else {
            System.out.println("Elemento no encontrado en el inventario.");
        }
    }

    private static void ordenarElementos() {
        Collections.sort(elementos);
        System.out.println("Elementos ordenados alfabéticamente.");
    }

    static void mostrarElementos() {
        System.out.println("Elementos en el inventario:");
        for (String elemento : elementos) {
            System.out.println(elemento);
        }
    }

    public boolean validarElementoEnInventario(String elemento) {
        return false;
    }
}

class CarritoDeCompra {
    private List<String> elementos = new ArrayList<>();

    public void mostrarMenu(Inventario inventario) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Carrito de Compra");
            System.out.println("1. Visualizar elementos de inventario");
            System.out.println("2. Agregar elemento al carrito");
            System.out.println("3. Eliminar elemento del carrito");
            System.out.println("4. Mostrar elementos en el carrito");
            System.out.println("5. Realizar pago");
            System.out.println("6. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    inventario.mostrarElementos();
                    break;
                case 2:
                    agregarElemento(inventario);
                    break;
                case 3:
                    eliminarElemento();
                    break;
                case 4:
                    mostrarElementos();
                    break;
                case 5:
                    realizarPago();
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private void agregarElemento(Inventario inventario) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del elemento a agregar al carrito: ");
        String elemento = scanner.nextLine();

        if (inventario.validarElementoEnInventario(elemento)) {
            elementos.add(elemento);
            System.out.println("Elemento agregado al carrito: " + elemento);
        } else {
            System.out.println("Elemento no encontrado en el inventario.");
        }
    }

    private void eliminarElemento() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del elemento a eliminar del carrito: ");
        String elemento = scanner.nextLine();

        if (elementos.remove(elemento)) {
            System.out.println("Elemento eliminado del carrito: " + elemento);
        } else {
            System.out.println("Elemento no encontrado en el carrito.");
        }
    }

    private void mostrarElementos() {
        System.out.println("Elementos en el carrito de compra:");
        for (String elemento : elementos) {
            System.out.println(elemento);
        }
    }

    private void realizarPago() {
        Scanner scanner = new Scanner(System.in);

        // Mostrar el total a pagar y los elementos en el carrito
        System.out.println("Contenido del carrito de compra:");
        mostrarElementos();

        double total = calcularTotal();
        System.out.println("Total a pagar: $" + total);

        // Preguntar al usuario si desea realizar el pago
        System.out.print("¿Desea realizar el pago? (S/N): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            // Lógica para procesar el pago (puede ser un sistema externo, tarjeta, etc.)
            System.out.println("Procesando el pago...");

            // Vaciar el carrito después de realizar el pago
            elementos.clear();

            System.out.println("Pago realizado con éxito. Gracias por su compra.");
        } else {
            System.out.println("Pago cancelado. Los elementos en el carrito siguen ahí.");
        }
    }

    private double calcularTotal() {
        // Lógica para calcular el total basado en los elementos en el carrito (puedes
        // personalizar esta lógica)
        double total = 0.0;

        for (String elemento : elementos) {
            // Aquí podrías tener un catálogo de precios o realizar cálculos específicos
            total += 10.0; // Precio ficticio por cada elemento
        }

        return total;
    }

    public void vaciarCarrito() {
    }
}

class CarritoDeCompra {
    private List<String> elementos = new ArrayList<>();

    public void mostrarMenu(Inventario inventario) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Carrito de Compra");
                System.out.println("1. Visualizar elementos de inventario");
                System.out.println("2. Agregar elemento al carrito");
                System.out.println("3. Eliminar elemento del carrito");
                System.out.println("4. Volver al menú anterior");
                System.out.print("Seleccione una opción: ");

                int opcion;
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer del scanner
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Por favor, ingrese un número válido.");
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    continue;
                }

                switch (opcion) {
                    case 1:
                        inventario.mostrarElementos();
                        break;
                    case 2:
                        agregarElemento(inventario);
                        break;
                    case 3:
                        eliminarElemento();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }
        }
    }

    private void agregarElemento(Inventario inventario) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese el nombre del elemento a agregar al carrito: ");
            String elemento = scanner.nextLine();

            if (inventario.validarElementoEnInventario(elemento)) {
                elementos.add(elemento);
                System.out.println("Elemento agregado al carrito: " + elemento);
            } else {
                System.out.println("Elemento no encontrado en el inventario.");
            }
        }
    }

    private void eliminarElemento() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese el nombre del elemento a eliminar del carrito: ");
            String elemento = scanner.nextLine();

            if (elementos.remove(elemento)) {
                System.out.println("Elemento eliminado del carrito: " + elemento);
            } else {
                System.out.println("Elemento no encontrado en el carrito.");
            }
        }
    }
}

class Caja {
    public static void realizarPago(CarritoDeCompra carrito) {
        // Lógica para realizar el pago y vaciar el carrito
        // (Implementa esta lógica según tus necesidades)
        System.out.println("Pago realizado con éxito. Gracias por su compra.");
        carrito.vaciarCarrito();
    }
}
