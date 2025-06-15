/**
 *
 * @author nicol
 */
package registroexportacion;

import java.util.ArrayList;
import java.util.Scanner;

public class registroExportacionApp {

    private static final ArrayList<Exportacion> exportaciones = new ArrayList<>(); // no se va a poder modificar en otra parte del código
    private static final Scanner entradaUsuario = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion; // variable para el menú

        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(entradaUsuario.nextLine());

                switch (opcion) {
                    case 1:
                        ingresarMultiplesExportaciones();
                        break;
                    case 2:
                        mostrarReporteGeneral();
                        break;
                    case 3:
                        mostrarReporteAgrupado();
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema, vuelva pronto.");
                        break;
                    default:
                        System.out.println("¡Opción inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                opcion = 0;   // si el usuario ingresa letras en lugar de números muestra el mensaje y luego asigna el valor de 0 a opción
            }
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("\n   Menú de Exportación    ");
        System.out.println("1. Ingresar exportación");
        System.out.println("2. Reporte general ");
        System.out.println("3. Reporte agrupado ");
        System.out.println("4. Salir ");
        System.out.print("Seleccione una opción: ");
    }

    // Ingreso de múltiples registros 
    private static void ingresarMultiplesExportaciones(){
        String continuar;
        do {
            ingresarexportacion();
            System.out.print("¿Desea ingresar otro registro? (S-N)");
            continuar = entradaUsuario.nextLine();
        } while (continuar.equalsIgnoreCase("S"));   
    }    
     
    //Ingreso de expotaciones
    private static void ingresarexportacion() {
        String id = "", nombre = "", tipoExportacion = "", zona = "", servicio = "";
        double kg = 0;
        
        // Validación para el ID con el formato exacto y que no permita letras
        while (id == null) {
            System.out.print("Inserte ID del Cliente con el formato 1-1111-1111: ");
            try {
                id = InputValidador.validateClientId(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }   
            
        // Validación de nombre
        while (nombre == null) {
            System.out.print("Nombre completo: ");
            try {
                nombre = InputValidador.validateName(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
      
        // Tipo de exportación
        // se opta por mostrar un menú con las dos opciones así el usuario solo puede ingresar un tipo de exportación válido
        while (tipoExportacion == null) {
            System.out.println("Seleccione el tipo de exportación: ");
            System.out.println("1. ECP - Exportación Carga Pesada");
            System.out.println("2. ECS - Exportación Carga Suelta");
            System.out.print("Opción: ");
            try {
                tipoExportacion = InputValidador.validateExportTypeOption(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
         
         // Zona de envío
        while (zona == null) {
            System.out.print("Zona de envío (país de envío): ");
            try {
                zona = InputValidador.validateZone(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        // Tipo de servicio, al igual que el tipo de exportación se crea un menú para que el usuario seleccione únicamente los tipos disponibles de servicio
        while (servicio == null) {
            System.out.println("Seleccione el tipo de servicio:");
            System.out.println("1. Avión");
            System.println("2. Barco");
            System.out.print("Opción: ");
            try {
                servicio = InputValidador.validateServiceTypeOption(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        while (kg == 0) {
            System.out.print("Kilogramos a embalar: ");
            try {
                kg = InputValidador.validateKilograms(entradaUsuario.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }    
        
    // Crear y guardar la exportación 
     exportacion exp = new exportacion(id, nombre, tipoExportacion, zona, servicio, kg);
     exportaciones.add(exp);
     System.out.println("Exportación registrada con éxito");
    }

    private static void mostrarReporteGeneral() {
        if (exportaciones.isEmpty()) {
            System.out.println("No existen datos almacenados. Debe ingresarlos en la opción 1. Ingresar exportación");
            return;
        }
        System.out.println("\n--- Reporte General ---");
        for (exportacion exp : exportaciones) {
            System.out.println(exp);
        }
    }

    private static void mostrarReporteAgrupado() {
        if (exportaciones.isEmpty()) {
            System.out.println("No existen datos almacenados. Debe ingresarlos en la opción 1. Ingresar exportación");
            return;
        }
        
        double totalECP = 0, totalECS = 0;
        double totalKg = 0;

        for (exportacion exp : exportaciones) {
            if (exp.getTipoExportacion().equals("ECP")) {
                totalECP += exp.calcularCosto();
            } else {
                totalECS += exp.calcularCosto();
            }
            totalKg += exp.getKilogramos();
        }

        System.out.println("\n--- Reporte Agrupado ---");
        System.out.printf("Total ECP: $%.2f\n", totalECP);
        System.out.printf("Total ECS: $%.2f\n", totalECS);
        System.out.printf("Total en Kg: %.2f\n", totalKg);
        System.out.printf("En gramos: %.2f\n", totalKg * 1000);
        System.out.printf("En libras: %.2f\n", totalKg * 2.20462);
        System.out.printf("En toneladas: %.2f\n", totalKg / 1000);
    }
}
