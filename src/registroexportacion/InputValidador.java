import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern CLIENT_ID_PATTERN = Pattern.compile("^[1-9]-\\d{4}-\\d{4}$");

    public static String validateClientId(String id) {
        if (id == null || !CLIENT_ID_PATTERN.matcher(id).matches()) {
            throw new ValidationException("Formato de ID inválido. Por favor ingrese el ID con el formato 1-1111-1111 y sin letras.");
        }
        return id;
    }


    public static String validateName(String name) {
        if (name == null || name.length() < 7) {
            throw new ValidationException("El nombre debe tener al menos 7 caracteres.");
        }
        return name;
    }

    public static String validateExportTypeOption(String option) {
        return switch (option) {
            case "1" -> "ECP";
            case "2" -> "ECS";
            default -> throw new ValidationException("La opción de tipo de exportación seleccionada no es válida.");
        };
    }


    public static String validateZone(String zone) {
        if (zone == null || zone.isBlank()) {
            throw new ValidationException("Debe ingresar una zona de envío.");
        }
        return zone;
    }

    public static String validateServiceTypeOption(String option) {
        return switch (option) {
            case "1" -> "Avión";
            case "2" -> "Barco";
            default -> throw new ValidationException("La opción de tipo de servicio seleccionada no es válida.");
        };
    }


    public static double validateKilograms(String kgString) {
        try {
            double kg = Double.parseDouble(kgString);
            if (kg <= 0) {
                throw new ValidationException("El peso ingresado debe ser mayor que cero.");
            }
            return kg;
        } catch (NumberFormatException e) {
            throw new ValidationException("Por favor, ingrese un número válido para los kilogramos.");
        }
    }
}
