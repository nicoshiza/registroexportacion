/**
 *
 * @author nicol
 */
package registroexportacion;

public class ExportacionCargaPesada extends Exportacion {
    private String tipoCarga; // Contenedor Refrigerado, No Refrigerado, Embalada

    public ExportacionCargaPesada() {
        super();
    }

    public ExportacionCargaPesada(String nombreCompleto, String idCliente, String zonaEnvio, String tipoServicio,
                                 double kilogramos, double costoAprobado, String tipoCarga) {
        super(idCliente, nombreCompleto, "CargaPesada", zonaEnvio, tipoServicio, kilogramos, costoAprobado);
        this.tipoCarga = tipoCarga;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    @Override
    public double calcularCosto() {
        switch (tipoCarga.toLowerCase()) {
            case "contenedor refrigerado":
                return kilogramos * 950;
            case "contenedor no refrigerado":
                return kilogramos * 550;
            case "carga embalada":
                // Solo se permite con barco, de lo contrario error
                if (!tipoServicio.equalsIgnoreCase("Barco")) {
                    throw new ValidacionExcepcion("La carga embalada solo puede enviarse por barco.");
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
                tipoCarga
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo Carga: " + tipoCarga + " | Costo calculado: " + calcularCosto();
    }
}

/* w3school. Herencia de Java. Tomado de:https://www.w3schools.com/java/java_inheritance.asp 
*/