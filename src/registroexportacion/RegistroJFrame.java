/**
 *
 * @author nicol
 */
package registroexportacion;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;



public class RegistroJFrame extends javax.swing.JFrame {
    private List<Exportacion> listaExportaciones = new ArrayList<>();
    private JTextArea areaTexto;
    
     // Menú
    private JMenuBar menuBar;
    private JMenu menuExportaciones, menuReportes;
    private JMenuItem itemIngreso, itemModificacion, itemPorID, itemPorZona;

    public RegistroJFrame(List<Exportacion> exportaciones) {
        this.listaExportaciones = exportaciones;

        setTitle("Registro de Exportaciones");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        mostrarExportaciones();
    }

    private void inicializarComponentes() {
        // Área de texto para mostrar resultados
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);

        // Crear menú
        menuBar = new JMenuBar();

        menuExportaciones = new JMenu("Exportaciones");
        itemIngreso = new JMenuItem("Ingreso");
        itemModificacion = new JMenuItem("Modificación");

        itemIngreso.addActionListener(e -> agregarExportacion());
        itemModificacion.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad de modificación no implementada aún."));

        menuExportaciones.add(itemIngreso);
        menuExportaciones.add(itemModificacion);

        menuReportes = new JMenu("Reportes");
        itemPorID = new JMenuItem("Por ID Cliente");
        itemPorZona = new JMenuItem("Por Zona");

        itemPorID.addActionListener(e -> buscarPorID());
        itemPorZona.addActionListener(e -> buscarPorZona());

        menuReportes.add(itemPorID);
        menuReportes.add(itemPorZona);

        menuBar.add(menuExportaciones);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    private void mostrarExportaciones() {
        StringBuilder sb = new StringBuilder();
        for (Exportacion e : listaExportaciones) {
            sb.append(e).append("\n");
        }
        areaTexto.setText(sb.toString());
    }

    private void agregarExportacion() {
        IngresoDatos ingreso = new IngresoDatos(); // constructor sin parámetros
        ingreso.setVisible(true);// muestra ventana
        ingreso.setVisible(true);

        Exportacion nueva = ingreso.obtenerExportacion();
        if (nueva != null) {
            RegistroExportacionApp.agregarExportacion(nueva);
            listaExportaciones.add(nueva);
            JOptionPane.showMessageDialog(this, "Exportación agregada correctamente.");
            mostrarExportaciones();
        } else {
            JOptionPane.showMessageDialog(this, "No se ingresó ninguna exportación.");
        }
    }

    private void buscarPorID() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del cliente:");
        if (id != null && !id.trim().isEmpty()) {
            List<Exportacion> resultados = new ArrayList<>();
            for (Exportacion e : listaExportaciones) {
                if (e.getIdCliente().equalsIgnoreCase(id.trim())) {
                    resultados.add(e);
                }
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron exportaciones para el ID: " + id);
                areaTexto.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Exportacion e : resultados) {
                    sb.append(e).append("\n");
                }
                areaTexto.setText(sb.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorZona() {
        String zona = JOptionPane.showInputDialog(this, "Ingrese la zona de envío (por ejemplo: América, Europa, Asia):");
        if (zona != null && !zona.trim().isEmpty()) {
            List<Exportacion> resultados = new ArrayList<>();
            for (Exportacion e : listaExportaciones) {
                if (e.getZonaEnvio().equalsIgnoreCase(zona.trim())) {
                    resultados.add(e);
                }
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron exportaciones para la zona: " + zona);
                areaTexto.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Exportacion e : resultados) {
                    sb.append(e).append("\n");
                }
                areaTexto.setText(sb.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar una zona válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
