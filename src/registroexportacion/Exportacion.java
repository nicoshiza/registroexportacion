
/**
 *
 * @author nicol
 */
package registroexportacion;
 
import java.io.Serializable; // sirve para indicar que una clase puede ser serializada
import java.time.LocalDate; // sirve para representar exclusivamente una fecha en terminos de año, mes y dia
import java.time.format.DateTimeFormatter; //permite formatear y analizar objetos de fecha y hora utilizando patrones predefinidos


//Abstract class facilita el poliformismo permitiendo que los objetos de diferentes clases derivadas se traten como objectos de la clase base 
public abstract class Exportacion implements Serializable {
    protected String idCliente; // Se utiliza private para proteger los datos y permitir el control de acceso mediante métodos públicos
    protected String nombreCompleto;
    protected String tipoExportacion;
    protected LocalDate fechaExportacion;
    protected String zonaEnvio;
    protected String tipoServicio;
    protected double kilogramos; // Se utiliza double para representar números con decimales
    protected double costoAprobado;
    protected LocalDate fechaModificacion;
    protected String fechaRegistro;
    
    
    public Exportacion(){
        this.fechaModificacion = LocalDate.now();
        this.fechaExportacion = LocalDate.now(); // El sistema tomará automáticamente la fecha del día actual cuando se crea el objeto
    }

    // Constructor: se usa para crear objetos de una clase
    public Exportacion(String idCliente, String nombreCompleto, String tipoExportacion, String zonaEnvio,String tipoServicio, double kilogramos, double costoAprobado) {
        this.idCliente = idCliente; // Sirve para asignar al atributo el valor que recibe el constructor
        this.nombreCompleto = nombreCompleto;
        this.tipoExportacion = tipoExportacion;
        this.fechaExportacion = LocalDate.now();
        this.zonaEnvio = zonaEnvio;
        this.tipoServicio = tipoServicio;
        this.kilogramos = kilogramos;
        this.costoAprobado = costoAprobado;
        this.fechaModificacion = LocalDate.now();
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters y setters
    
     public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoExportacion() {
        return tipoExportacion;
    }

    public void setTipoExportacion(String tipoExportacion) {
        this.tipoExportacion = tipoExportacion;
    }

    public LocalDate getFechaExportacion() {
        return fechaExportacion;
    }

    public String getZonaEnvio() {
        return zonaEnvio;
    }

    public void setZonaEnvio(String zonaEnvio) {
        this.zonaEnvio = zonaEnvio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double getKilogramos() {
        return kilogramos;
    }

    public void setKilogramos(double kilogramos) {
        this.kilogramos = kilogramos;
    }

    public double getCostoAprobado() {
        return costoAprobado;
    }

    public void setCostoAprobado(double costoAprobado) {
        this.costoAprobado = costoAprobado;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaExportacion = fechaRegistro;
    } // se crea este set porque al leer desde el archivo los datos ya guardados se busca restaurar el objeto en lugar de dejar que se cree con la fecha actual del sistema
    
   // Método abstracto para calcular el costo de la exportación 
     public abstract double calcularCosto();

    // Método abstracto para que cada clase hija genere la línea para archivo
    public abstract String getLineaArchivo();
    
     @Override
    public String toString() {
        return String.format("%s | ID: %s | Zona: %s | Servicio: %s | Kg: %.2f | Costo: %.2f | Fecha Reg: %s | Fecha Mod: %s",
               nombreCompleto, idCliente, zonaEnvio, tipoServicio, kilogramos, costoAprobado,
                fechaExportacion.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), fechaModificacion.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ;
    }
}

/* Maldonado, R. (enero,2025) ¿Cómo crear y manipular fechas con java.time.LocalDate en Java?. Keepcoding. Tomado de:https://keepcoding.io/blog/como-usar-java-time-localdate-en-java/
Maldonado, R. (enero,2025) Java.time.format.DateTimeFormatter: ¿Cómo formatear fechas y horas fácilmente? KeepCooding Tomado de: https://keepcoding.io/blog/java-time-format-datetimeformatter-como-se-usa/
Obregón, A.(2023, noviembre) Una inmersión profunda en la serialización de Java. Tomado de: https://medium.com/@AlexanderObregon/a-deep-dive-into-java-serialization-e514346ac2b2
*/