//Karen García
//TERCER NIVEL
//29/10/2026
package colaprioridad;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ColaPrioridad cola = new ColaPrioridad();
        int opcion;
        menu();
        do {
            System.out.println();
            opcion = leerEntero("Opcion (9 = ver menú): ", 0, 9);
            System.out.println();
            switch (opcion) {
                case 1: registrar(cola); break;
                case 2: atender(cola); break;
                case 3: cola.mostrar(); break;
                case 4: verSiguiente(cola); break;
                case 5: buscar(cola); break;
                case 6: cancelar(cola); break;
                case 7: cola.mostrarHistorial(); break;
                case 9: menu(); break;
                case 0: System.out.println("Cerrando el sistema. Atendidos hoy: "
                        + cola.getTotalAtendidos() + ". En espera: " + cola.getTamano()); break;
            }
        } while (opcion != 0);
    }

    private static void menu() {
        System.out.println("   CAFETERIA LOS REINAS - ATENCION DE CLIENTES ");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Atender siguiente cliente");
        System.out.println("3. Mostrar cola de espera");
        System.out.println("4. Ver próximo cliente");
        System.out.println("5. Buscar cliente por ID");
        System.out.println("6. Cancelar cliente por ID");
        System.out.println("7. Ver historial de atendidos");
        System.out.println("0. Salir");
    }

    private static void registrar(ColaPrioridad cola) {
        System.out.print("Nombre: ");
        String nombre = SC.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        int id = leerEntero("ID: ", 1, Integer.MAX_VALUE);
        int prio = leerEntero("Prioridad (1 = mas urgente ... 5 = menos urgente): ", 1, 5);
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (cola.insertar(nombre, id, prio, hora)) {
            System.out.println("Cliente registrado: " + nombre + " (prioridad " + prio + ", " + hora + ")");
        } else {
            System.out.println("Error: ya existe un cliente con el ID " + id + ".");
        }
    }

    private static void atender(ColaPrioridad cola) {
        Nodo n = cola.atender();
        if (n == null) {
            System.out.println("No hay clientes en espera.");
        } else {
            System.out.println("Atendiendo a: " + n.nombre + " (ID " + n.id
                    + ", prioridad " + n.prioridad + ")");
        }
    }

    private static void verSiguiente(ColaPrioridad cola) {
        Nodo n = cola.verSiguiente();
        if (n == null) {
            System.out.println("No hay clientes en espera.");
        } else {
            System.out.println("Próximo en ser atendido: " + n.nombre + " (ID " + n.id
                    + ", prioridad " + n.prioridad + ")");
        }
    }

    private static void buscar(ColaPrioridad cola) {
        int id = leerEntero("ID a buscar: ", 1, Integer.MAX_VALUE);
        Nodo n = cola.buscar(id);
        if (n == null) {
            System.out.println("No se encontro ningún cliente con el ID " + id + ".");
        } else {
            System.out.println("Encontrado: " + n.nombre + ", prioridad " + n.prioridad
                    + ", hora de llegada " + n.horaRegistro);
        }
    }

    private static void cancelar(ColaPrioridad cola) {
        int id = leerEntero("ID a cancelar: ", 1, Integer.MAX_VALUE);
        Nodo n = cola.eliminar(id);
        if (n == null) {
            System.out.println("No se encontro ningún cliente con el ID " + id + ".");
        } else {
            System.out.println("Se canceló el turno de " + n.nombre + " (ID " + n.id + ").");
        }
    }

    // Lee un entero validado dentro de un rango; repite hasta que sea correcto.
    private static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String linea = SC.hasNextLine() ? SC.nextLine().trim() : "0";
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // cae al mensaje de abajo
            }
            System.out.println("Entrada inválida. Ingrese un numero entre " + min + " y " + max + ".");
        }
    }
}
