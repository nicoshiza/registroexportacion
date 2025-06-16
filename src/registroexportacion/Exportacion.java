
/**
 *
 * @author nicol
 */
package registroexportacion;

import java.time.LocalDate; // sirve para representar exclusivamente una fecha en terminos de año, mes y dia
import java.time.format.DateTimeFormatter; //permite formatear y analizar objetos de fecha y hora utilizando patrones predefinidos


public class Exportacion {
    private String idCliente; // Se utiliza private para proteger los datos y permitir el control de acceso mediante métodos públicos
    private String nombreCompleto;
    private String tipoExportacion;
    private LocalDate fechaExportacion;
    private String zonaEnvio;
    private String tipoServicio;
    private double kilogramos; // Se utiliza double para representar números con decimales

    // Constructor: se usa para crear objetos de una clase
    public Exportacion(String idCliente, String nombreCompleto, String tipoExportacion, String zonaEnvio, String tipoServicio, double kilogramos) {
        this.idCliente = idCliente; // Sirve para asignar al atributo el valor que recibe el constructor
        this.nombreCompleto = nombreCompleto;
        this.tipoExportacion = tipoExportacion;
        this.fechaExportacion = LocalDate.now(); // El sistema tomará automáticamente la fecha del día actual cuando se crea el objeto
        this.zonaEnvio = zonaEnvio;
        this.tipoServicio = tipoServicio;
        this.kilogramos = kilogramos;
    }
    
    // Métodos

    public double calcularCosto() {
        return tipoServicio.equalsIgnoreCase("Avión")? kilogramos * 450 : kilogramos * 150;
    // si el tipo de servico es Aviób el costo por kilogramo es 450 y sino 150 como solo se aceptan dos posibles tipos de servicio se puede utilizar esta expresion
    }
    
    public double convertirGramos() {
        return kilogramos * 1000;
        // para la conversion de kilogramos a gramos
    }
    
    public double convertirLibras() {
        return kilogramos *  2.20462; 
        // para la conversion de kilogramos a libras
    } 

    public double convertirToneladas() {
        return kilogramos / 1000;
        // para la conversion de kilogramos a toneladas
    }
    
    public String getIdCliente() {
        return idCliente;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public LocalDate getFechaExportacion() {
        return fechaExportacion;
    }
    
    public String getZonaEnvio() {
        return zonaEnvio;
    }
    
    public double getKilogramos() {
        return kilogramos;
    }
    
    public String getTipoExportacion() {
        return tipoExportacion;
    }
    
    public String getTipoServicio() {
        return tipoServicio;
    }
    
    @Override // sirve para sobreescribir un método que ya existe en una clase superior
    public String toString() {
        return String.format("ID: %s | Nombre: %s | Fecha: %s | Zona: %s | Servicio: %s | KG: %.2f | Costo: $%.2f",
                idCliente, nombreCompleto, fechaExportacion.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                zonaEnvio, tipoServicio, kilogramos, calcularCosto());
    }
}

/* Maldonado, R. (enero,2025) ¿Cómo crear y manipular fechas con java.time.LocalDate en Java?. Keepcoding. Tomado de:https://keepcoding.io/blog/como-usar-java-time-localdate-en-java/
Maldonado, R. (enero,2025) Java.time.format.DateTimeFormatter: ¿Cómo formatear fechas y horas fácilmente? KeepCooding Tomado de: https://keepcoding.io/blog/java-time-format-datetimeformatter-como-se-usa/

*/