/**
 *
 * @author nicol
 */
package registroexportacion.models;

import registroexportacion.ValidacionExcepcion;
import registroexportacion.models.enums.TipoExportacion;

public class ExportacionCargaSuelta extends Exportacion {
    private Integer piesCarga;

    public ExportacionCargaSuelta() {
        super();
    }

    public ExportacionCargaSuelta(String nombreCompleto, String idCliente, String zonaEnvio, String tipoServicio,
                                  double kilogramos, double costoAprobado, int piesCarga) {
        super(idCliente, nombreCompleto, TipoExportacion.ECS, zonaEnvio, tipoServicio, kilogramos, costoAprobado);
        this.piesCarga = piesCarga;
    }

    public Integer getPiesCarga() {
        return piesCarga;
    }

    public void setPiesCarga(Integer piesCarga) {
        this.piesCarga = piesCarga;
    }

    @Override
    public double calcularCosto() {
        double costoBase;
        double adicional = 0;

        if (tipoServicio.equalsIgnoreCase("Avión") || tipoServicio.equalsIgnoreCase("Avion")) {
            costoBase = 100;
            if (piesCarga > 18) {
                adicional = 100;
            }
        } else if (tipoServicio.equalsIgnoreCase("Barco")) {
            costoBase = 50;
            if (piesCarga > 18) {
                adicional = 50;
            }
        } else {
            throw new ValidacionExcepcion("Tipo de servicio inválido");
        }

        return (kilogramos * costoBase) + adicional;
    }

    @Override
    public String getLineaArchivo() {
        return String.join(";",
                "CargaSuelta",
                nombreCompleto,
                idCliente,
                zonaEnvio,
                tipoServicio,
                fechaExportacion.toString(),
                fechaModificacion.toString(),
                String.valueOf(kilogramos),
                String.valueOf(piesCarga)
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | Pies de carga: " + piesCarga + " | Costo calculado: " + calcularCosto();
    }
}