/**
 *
 * @author nicol
 */
package registroexportacion;

import java.io.*; // se utiliza para traer todas las clases e interfaces públicas dentro del paquete java.io
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchivoExportacion {
    private final File archivo;

    public ArchivoExportacion(String nombreArchivo) {
        this.archivo = new File(nombreArchivo);
    }

    public void guardar(List<Exportacion> exportaciones) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("Tipo;Nombre;ID;Zona;Servicio;FechaRegistro;FechaModificacion;Kilogramos;Detalle"); // <- línea inicial
            for (Exportacion e : exportaciones) {
                writer.println(e.getLineaArchivo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }


    public List<Exportacion> cargar() {
        List<Exportacion> exportaciones = new ArrayList<>();

        if (!archivo.exists()) {
            return exportaciones;  // retorna lista vacía si no existe archivo
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            //Salta el encabezado
            reader.readLine();
            
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                String tipo = partes[0].trim();

                String nombre = partes[1];
                String id = partes[2];
                String zona = partes[3];
                String envio = partes[4];
                LocalDate fechaRegistro = LocalDate.parse(partes[5]);
                LocalDate fechaMod = LocalDate.parse(partes[6]);
                double peso = Double.parseDouble(partes[7]);

                if (tipo.equalsIgnoreCase("CargaPesada")) {
                    String tipoCarga = partes[8];
                    ExportacionCargaPesada cp = new ExportacionCargaPesada(nombre, id, zona, envio, peso, 0, tipoCarga);
                    cp.setFechaRegistro(fechaRegistro);
                    cp.setFechaModificacion(fechaMod);
                    exportaciones.add(cp);
                } else if (tipo.equalsIgnoreCase("CargaSuelta")) {
                    int piesCarga = Integer.parseInt(partes[8]);
                    ExportacionCargaSuelta cs = new ExportacionCargaSuelta(nombre, id, zona, envio, peso, 0, piesCarga);
                    cs.setFechaRegistro(fechaRegistro);
                    cs.setFechaModificacion(fechaMod);
                    exportaciones.add(cs);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }

        return exportaciones;
    }
}
/* CertiDevs. (2025, junio) Java:Entrada y salida IO. Tomado de: https://certidevs.com/aprender-java-io-manejo-entrada-y-salida */