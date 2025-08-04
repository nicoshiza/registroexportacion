/**
 *
 * @author nicol
 */
package registroexportacion.models;

import registroexportacion.ValidacionExcepcion;
import registroexportacion.models.enums.TipoCarga;
import registroexportacion.models.enums.TipoExportacion;

public class ExportacionCargaPesada extends Exportacion {
    private TipoCarga tipoCarga; // Contenedor Refrigerado, No Refrigerado, Embalada

    public ExportacionCargaPesada() {
        super();
    }

    public ExportacionCargaPesada(String nombreCompleto, String idCliente, String zonaEnvio, String tipoServicio,
                                 double kilogramos, double costoAprobado, TipoCarga tipoCarga) {
        super(idCliente, nombreCompleto, TipoExportacion.ECP, zonaEnvio, tipoServicio, kilogramos, costoAprobado);
        this.tipoCarga = tipoCarga;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    @Override
    public double calcularCosto() {
        switch (tipoCarga) {
            case TipoCarga.CR:
                return kilogramos * 950;
            case TipoCarga.CNR:
                return kilogramos * 550;
            case TipoCarga.CE:
                // Solo se permite con barco, de lo contrario error
                if (!tipoServicio.equalsIgnoreCase("Barco")) {
                    // TODO: chquear si esto aplica aqui
//                    throw new ValidacionExcepcion("La carga embalada solo puede enviarse por barco.");
                }
                return kilogramos * 450;
            default:
                throw new ValidacionExcepcion("Tipo de carga no válido para Carga Pesada.");
        }
    }

    @Override
    public String getLineaArchivo() {
        return String.join(";",
                "CargaPesada",
                nombreCompleto,
                idCliente,
                zonaEnvio,
                tipoServicio,
                fechaExportacion.toString(),
                fechaModificacion.toString(),
                String.valueOf(kilogramos),
                tipoCarga.getDescripcion()
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo Carga: " + tipoCarga + " | Costo calculado: " + calcularCosto();
    }
}

/* w3school. Herencia de Java. Tomado de:https://www.w3schools.com/java/java_inheritance.asp 
*/