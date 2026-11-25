//Karen García
//TERCER NIVEL
//29/10/2026
package colaprioridad;

public class ColaPrioridad {
    private Nodo frente;           
    private Nodo fin;               
    private int tamano;             

    // Historial de atendidos: segunda lista enlazada que tampoco usa arreglos
    private Nodo histFrente;
    private Nodo histFin;
    private int totalAtendidos;

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamano() {
        return tamano;
    }

    public int getTotalAtendidos() {
        return totalAtendidos;
    }

    // Busca un cliente por ID recorriendo punteros. Devuelve null si no existe.
    public Nodo buscar(int id) {
        for (Nodo a = frente; a != null; a = a.siguiente) {
            if (a.id == id) {
                return a;
            }
        }
        return null;
    }

    // Inserta segun prioridad, a igual prioridad respeta el orden de llegada.
    public boolean insertar(String nombre, int id, int prioridad, String hora) {
        if (buscar(id) != null) {
            return false;                           // ID repetido, no se inserta
        }
        Nodo nuevo = new Nodo(nombre, id, prioridad, hora);   // memoria dinámica
        tamano++;

        if (estaVacia()) {                          // Caso 1: cola vacía
            frente = fin = nuevo;
            return true;
        }
        if (prioridad < frente.prioridad) {         // Caso 2: nuevo va al inicio
            nuevo.siguiente = frente;
            frente = nuevo;
            return true;
        }
        Nodo actual = frente;                       // Caso 3: medio o final
        while (actual.siguiente != null
                && actual.siguiente.prioridad <= prioridad) {
            actual = actual.siguiente;
        }
        nuevo.siguiente = actual.siguiente;         // paso 1
        actual.siguiente = nuevo;                   // paso 2
        if (nuevo.siguiente == null) {
            fin = nuevo;                            // quedó de último
        }
        return true;
    }

    // Atiende al cliente del frente y lo pasa al historial.
    public Nodo atender() {
        if (estaVacia()) {
            return null;
        }
        Nodo atendido = frente;
        frente = frente.siguiente;                  // avanzar el frente
        if (frente == null) {
            fin = null;                             // la cola quedo vacía
        }
        atendido.siguiente = null;
        tamano--;
        agregarHistorial(atendido);
        return atendido;
    }

    // Elimina - cancela a un cliente por ID en cualquier posici+on de la cola.
    public Nodo eliminar(int id) {
        if (estaVacia()) {
            return null;
        }
        if (frente.id == id) {                      // Caso 1: es el primero
            Nodo borrado = frente;
            frente = frente.siguiente;
            if (frente == null) {
                fin = null;
            }
            borrado.siguiente = null;
            tamano--;
            return borrado;
        }
        Nodo anterior = frente;                     // Caso 2: medio o final
        while (anterior.siguiente != null && anterior.siguiente.id != id) {
            anterior = anterior.siguiente;
        }
        if (anterior.siguiente == null) {
            return null;                            // no existe ese ID
        }
        Nodo borrado = anterior.siguiente;
        anterior.siguiente = borrado.siguiente;     // saltar el nodo borrado
        if (borrado == fin) {
            fin = anterior;                         // era el último
        }
        borrado.siguiente = null;
        tamano--;
        return borrado;
    }

    // Devuelve - sin sacar al próximo cliente a atender.
    public Nodo verSiguiente() {
        return frente;
    }

    private void agregarHistorial(Nodo n) {
        if (histFrente == null) {
            histFrente = histFin = n;
        } else {
            histFin.siguiente = n;
            histFin = n;
        }
        totalAtendidos++;
    }

    public void mostrar() {
        if (estaVacia()) {
            System.out.println("La cola esta vacía.");
            return;
        }
        System.out.println("  COLA DE ESPERA (" + tamano + ")  ");
        System.out.printf("%-4s %-14s %-6s %-5s %-8s%n",
                "Pos", "Nombre", "ID", "Prio", "Hora");
        int pos = 1;
        for (Nodo a = frente; a != null; a = a.siguiente) {
            System.out.printf("%-4d %-14s %-6d %-5d %-8s%n",
                    pos++, a.nombre, a.id, a.prioridad, a.horaRegistro);
        }
        System.out.print("Cadena de punteros: ");
        for (Nodo a = frente; a != null; a = a.siguiente) {
            System.out.print(a.nombre + " -> ");
        }
        System.out.println("null");
    }

    public void mostrarHistorial() {
        if (histFrente == null) {
            System.out.println("Todavía no se ha atendido a nadie.");
            return;
        }
        System.out.println("  HISTORIAL DE ATENDIDOS (" + totalAtendidos + ") ");
        int orden = 1;
        for (Nodo a = histFrente; a != null; a = a.siguiente) {
            System.out.println(orden++ + ". " + a.nombre + " (ID " + a.id
                    + ", prioridad " + a.prioridad + ", llegó " + a.horaRegistro + ")");
        }
    }
}
