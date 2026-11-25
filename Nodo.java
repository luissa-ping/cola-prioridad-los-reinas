//Karen García
//TERCER NIVEL
//29/10/2026
package colaprioridad;

public class Nodo {
    String nombre;
    int id;
    int prioridad;          // menor numero = mayor prioridad
    String horaRegistro;
    Nodo siguiente;         // referencia (puntero) al proximo nodo

    public Nodo(String nombre, int id, int prioridad, String horaRegistro) {
        this.nombre = nombre;
        this.id = id;
        this.prioridad = prioridad;
        this.horaRegistro = horaRegistro;
        this.siguiente = null;
    }
}
