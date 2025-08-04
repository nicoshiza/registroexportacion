/**
 *
 * @author nicol
 */
package registroexportacion.utils;

import registroexportacion.dao.ExportacionDao;
import registroexportacion.models.Exportacion;
import registroexportacion.models.ExportacionCargaPesada;
import registroexportacion.models.ExportacionCargaSuelta;
import registroexportacion.models.enums.TipoCarga;
import registroexportacion.models.enums.TipoExportacion;

import java.io.*; // se utiliza para traer todas las clases e interfaces públicas dentro del paquete java.io
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExportacionArchivoDao extends ExportacionDao {

    private final File archivo;
    private List<Exportacion> exportacionesInMemory = new ArrayList<>();

    public ExportacionArchivoDao(String nombreArchivo) {
        this.archivo = new File(nombreArchivo);
    }

    @Override
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

    @Override
    public void modificar(Exportacion exportacion) {

    }

    @Override
    public int delete(int idExportacion) {
        return 0;
    }

    @Override
    public List<Exportacion> listar() {
        if (!exportacionesInMemory.isEmpty()) {
            return exportacionesInMemory;
        }

        if (!archivo.exists()) {
            return exportacionesInMemory;  // retorna lista vacía si no existe archivo
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

                switch (TipoExportacion.fromValue(tipo)) {
                    case TipoExportacion.ECP -> {
                        String tipoCarga = partes[8].trim().toUpperCase();
                        ExportacionCargaPesada cp = new ExportacionCargaPesada(nombre, id, zona, envio, peso, 0, TipoCarga.fromValue(tipoCarga));
                        cp.setFechaRegistro(fechaRegistro);
                        cp.setFechaModificacion(fechaMod);
                        exportacionesInMemory.add(cp);
                    }
                    case TipoExportacion.ECS-> {
                        int piesCarga = (int) Double.parseDouble( partes[9]);
                        ExportacionCargaSuelta cs = new ExportacionCargaSuelta(nombre, id, zona, envio, peso, 0, piesCarga);
                        cs.setFechaRegistro(fechaRegistro);
                        cs.setFechaModificacion(fechaMod);
                        exportacionesInMemory.add(cs);
                    }
                    default -> System.out.println("Tipo exportacion invalido, fila saltada: " + linea);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }

        return exportacionesInMemory;
    }
}
/* CertiDevs. (2025, junio) Java:Entrada y salida IO. Tomado de: https://certidevs.com/aprender-java-io-manejo-entrada-y-salida */