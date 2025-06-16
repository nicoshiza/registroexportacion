/**
 *
 * @author nicol
 */
package registroexportacion;


// Esta clase sirve para definir una excepción personalizada que extiende de RuntimeException
// Se usa para lanzar los errores con mensajes personalizados durante la validación
public class ValidacionExcepcion extends RuntimeException {
    //Constructor que recibe el mensaje personalizado 
    public ValidacionExcepcion(String mensaje) {
        super(mensaje); // Llama al constructor de RuntimeException con el mensaje
    }
}

/*
Álvarez, C (2024, mayo). Java RunException y su utilidad. Tomado de: https://www.arquitecturajava.com/java-runtimeexception-y-su-utilidad/
*/