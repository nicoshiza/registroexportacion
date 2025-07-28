/**
 *
 * @author nicol
 */
package registroexportacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroExportacionApp {

    private static final List<Exportacion> exportaciones = new ArrayList<>();
    private static final ArchivoExportacion archivoExportacion = new ArchivoExportacion("Exportaciones.txt");

    public static void main(String[] args) {
        exportaciones.addAll(archivoExportacion.cargar());
        
        archivoExportacion.guardar(exportaciones);
    }

    public static void agregarExportacion(Exportacion exp) {
        exp.setFechaRegistro(LocalDate.now());
        exp.setFechaModificacion(LocalDate.now());
        exp.setCostoAprobado(exp.calcularCosto());
        exportaciones.add(exp);
        archivoExportacion.guardar(exportaciones);
    }

    public static void modificarExportacion(String idCliente, int index, Exportacion nueva) {
        List<Exportacion> lista = buscarPorCliente(idCliente);
        if (index >= 0 && index < lista.size()) {
            Exportacion original = lista.get(index);
            nueva.setFechaRegistro(original.getFechaRegistro()); // No cambiar fecha original
            nueva.setFechaModificacion(LocalDate.now()); // Fecha modificación actual
            nueva.setCostoAprobado(nueva.calcularCosto());
            exportaciones.set(exportaciones.indexOf(original), nueva);
            archivoExportacion.guardar(exportaciones);
        }
    }

    public static void eliminarExportacion(String idCliente, int index) {
        List<Exportacion> lista = buscarPorCliente(idCliente);
        if (index >= 0 && index < lista.size()) {
            exportaciones.remove(lista.get(index));
            archivoExportacion.guardar(exportaciones);
        }
    }

    public static List<Exportacion> buscarPorCliente(String idCliente) {
        List<Exportacion> resultado = new ArrayList<>();
        for (Exportacion e : exportaciones) {
            if (e.getIdCliente().equalsIgnoreCase(idCliente)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public static List<Exportacion> buscarPorZona(String zona) {
        List<Exportacion> resultado = new ArrayList<>();
        for (Exportacion e : exportaciones) {
            if (e.getZonaEnvio().equalsIgnoreCase(zona)) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}
/* Deitel, H & P. (2021) Java Cómo Programar. Pearson Educación. ISBN: 978-607-32-5693-3 */