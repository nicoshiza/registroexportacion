package registroexportacion.ui.v2;

import registroexportacion.dao.ExportacionDao;
import registroexportacion.models.ExportacionCargaPesada;
import registroexportacion.models.ExportacionCargaSuelta;
import registroexportacion.models.enums.TipoExportacion;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.PatternSyntaxException;

public class RegistroExportationWindow extends JFrame {

    private ExportacionDao exportacionDao;

    // --- GUI componentes ---
    private final GridBagConstraints gbc = new GridBagConstraints();
    private JTable tablaExportacion;
    private DefaultTableModel exportacionTablemodel;
    private JTextField filterTextFld;
    private TableRowSorter<TableModel> exportacionTableRowSorter;

    public RegistroExportationWindow(ExportacionDao exportacionDao) {
        super("Registro Exportacion");
        this.exportacionDao = exportacionDao;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        createStaticComponents();
        // componentes dinamicos
        createExportacionesTable();


        this.pack();
//        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(1200, 800));
        this.setMaximumSize(new Dimension(1200, 800));
        this.setSize(1200, 800);
    }

    private void createExportacionesTable() {
        var exportaciones = exportacionDao.listar();
        String[] columnNames = {
                "Tipo",
                "Nombre",
                "ID",
                "Zona",
                "Servicio",
                "FechaRegistro",
                "FechaModificacion",
                "Kilogramos",
                "TipoCarga",
                "PiesCarga"
        };

        Object[][] data = new Object[exportaciones.size()][columnNames.length];
        for (int i = 0; i < exportaciones.size(); i++) {
            var exportacion = exportaciones.get(i);
            data[i][0] = exportacion.getTipoExportacion().getDescripcion();
            data[i][1] = exportacion.getNombreCompleto();
            data[i][2] = exportacion.getIdCliente();
            data[i][3] = exportacion.getZonaEnvio();
            data[i][4] = exportacion.getTipoServicio();
            data[i][5] = exportacion.getFechaExportacion();
            data[i][6] = exportacion.getFechaModificacion();
            data[i][7] = exportacion.getKilogramos();
            switch (exportacion.getTipoExportacion()) {
                case TipoExportacion.ECP -> {
                    data[i][8] = ((ExportacionCargaPesada) exportacion).getTipoCarga().getDescripcion();
                    data[i][9] = "";
                }
                case TipoExportacion.ECS -> {
                    data[i][8] = "";
                    data[i][9] = ((ExportacionCargaSuelta) exportacion).getPiesCarga();
                }
                default -> {
                    data[i][8] = "";
                    data[i][9] = "";
                }
            }
        }

        //
        // --- Row 3 ---
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weighty = 7.0;
        gbc.gridx = 0;
        gbc.gridwidth = 6;
        gbc.weightx = 2.0;

        exportacionTablemodel = new DefaultTableModel(data, columnNames);
        exportacionTableRowSorter = new TableRowSorter<TableModel>(exportacionTablemodel);
        tablaExportacion = new JTable(exportacionTablemodel);
        tablaExportacion.setRowSorter(exportacionTableRowSorter);
        var scrollPane = new JScrollPane(tablaExportacion);

        getContentPane().add(scrollPane, gbc);
    }

    private void createStaticComponents() {
        //        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- Row 0 ---
//        gbc.gridy = 0;
//
//        gbc.weightx = 1.0;
//        gbc.weighty = 0.5;
//        gbc.gridx = 0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        contentPane.add(new JLabel("Registro de Exportacion"), gbc);


        // --- Row 1 ---
        gbc.gridy = 1;
        gbc.weighty = 1;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        getContentPane().add(new JLabel("Buscar "), gbc);
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridx = 1;
        gbc.weightx = 3.0;
        getContentPane().add(createFilterTextFld(), gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        getContentPane().add(new JButton("Buscar"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 2.0;
        getContentPane().add(new JLabel(), gbc);
        gbc.gridx = 4;
        gbc.weightx = 0.5;
        getContentPane().add(new JButton("Agregar"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.5;
        getContentPane().add(new JButton("Modificar"), gbc);


        // --- Row 2 ---
//        gbc.gridy = 2;
//        gbc.weighty = 0.25;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridx = 0; gbc.weightx = 1; contentPane.add(new JLabel(), gbc);

    }

    private JTextField createFilterTextFld() {
        String placeHolder = "Filtrar por id o zona...";
        filterTextFld = new JTextField(placeHolder);
        filterTextFld.setForeground(Color.GRAY);

        filterTextFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = filterTextFld.getText();
                if (text.isEmpty()) {
                    exportacionTableRowSorter.setRowFilter(null); // lipia el filtro
                } else {
                    try {
                        // filtra solo por ID y zona de envio
                        exportacionTableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2, 3));
                    } catch (PatternSyntaxException pse) {
                        System.err.println("Regex incorrecto: " + pse.getMessage());
                    }
                }
            }
        });

        filterTextFld.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (filterTextFld.getText().equals("Filtrar por id o zona...")) {
                    filterTextFld.setText("");
                    filterTextFld.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (filterTextFld.getText().isEmpty()) {
                    filterTextFld.setText("Filtrar por id o zona...");
                    filterTextFld.setForeground(Color.GRAY);
                }
            }
        });
        return filterTextFld;
    }

}
