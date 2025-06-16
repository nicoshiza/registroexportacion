/**
 *
 * @author nicol
 */
package registroexportacion;

import java.util.regex.Pattern;//Importa la clase Pattern para trabajar con expresiones regulares

public class InputValidador {

    // Expresión regular para validar el ID del cliente con formato 1-1111-1111
    private static final Pattern CLIENT_ID_PATTERN = Pattern.compile("^[1-9]-\\d{4}-\\d{4}$");

    //Valida el formatp del ID del cliente
    public static String validateClientId(String id) {
        if (id == null || !CLIENT_ID_PATTERN.matcher(id).matches()) {
            throw new ValidacionExcepcion("Formato de ID inválido. Por favor ingrese el ID con el formato 1-1111-1111 y sin letras.");
        }
        return id; // Retorn el ID si es válido
    }

    //Valida el nombre completo
    public static String validateName(String name) {
        // si el nombre es nulo o menor a 7 caracteres muestra una excepción
        if (name == null || name.length() < 7) {
            throw new ValidacionExcepcion("El nombre debe tener al menos 7 caracteres.");
        }
        return name; // Retorna el nombre si es válido
    }

    //Valida la opción de tipo de exportación ingresada 
    public static String validateExportTypeOption(String option) {
        return switch (option) {
            case "1" -> "ECP";// Exportación Carga Pesada
            case "2" -> "ECS";// Exportación Carga Suelta
            default -> throw new ValidacionExcepcion("La opción de tipo de exportación seleccionada no es válida.");
        };
    }

    // Valida la zona de envío 
    public static String validateZone(String zone) {
        // Si es vacio o nulo, lanza una excepción
        if (zone == null || zone.isBlank()) {
            throw new ValidacionExcepcion("Debe ingresar una zona de envío.");
        }
        return zone;//Retorna la zona si es válida
    }

    //Valida el tipo de servicio seleccionado
    public static String validateServiceTypeOption(String option) {
        return switch (option) {
            case "1" -> "Avión";
            case "2" -> "Barco";
            default -> throw new ValidacionExcepcion("La opción de tipo de servicio seleccionada no es válida.");
        };
    }

    
    //Valida que el peso en kilogramos sea mayor a cero y un valor válido 
    public static double validateKilograms(String kgString) {
        try {
            double kg = Double.parseDouble(kgString);// Convierte la cadena a número
            if (kg <= 0) {
                throw new ValidacionExcepcion("El peso ingresado debe ser mayor que cero.");
            }
            return kg;//Retorna el valor si este es válido 
        } catch (NumberFormatException e) {
            // si se ingresan letras o símbolos lanza una excepción
            throw new ValidacionExcepcion("Por favor, ingrese un número válido para los kilogramos.");
        }
    }
    
    //Valida que el usuario ingreso solo S o N 
    public static String validateContinuar(String input) {
    if (input == null || (!input.equalsIgnoreCase("S") && !input.equalsIgnoreCase("N"))) {
        throw new ValidacionExcepcion("Respuesta inválida. Por favor ingrese S para sí o N para no.");
    }
    return input.toUpperCase();
    }
}

/* w3Schools.(2025)Expresiones regulares de Java.Tomado de: https://www.w3schools.com/java/java_regex.asp */