package registroexportacion.ui.v2.windows.exportacion;

import registroexportacion.dao.ExportacionDao;
import registroexportacion.models.Exportacion;
import registroexportacion.models.ExportacionCargaPesada;
import registroexportacion.models.enums.TipoCarga;
import registroexportacion.models.enums.TipoExportacion;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class RegistroExportationWindow extends JFrame {

    private ExportacionDao exportacionDao;

    // --- GUI componentes ---
    private final GridBagConstraints gbc = new GridBagConstraints();
    private JTable tablaExportacion;
    private ExportacionTableModel exportacionTablemodel;
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

        // --- Row 3 ---
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weighty = 7.0;
        gbc.gridx = 0;
        gbc.gridwidth = 6;
        gbc.weightx = 2.0;

        exportacionTablemodel = new ExportacionTableModel(exportaciones);
        exportacionTableRowSorter = new TableRowSorter<>(exportacionTablemodel);
        tablaExportacion = new JTable(exportacionTablemodel);
        tablaExportacion.setRowSorter(exportacionTableRowSorter);
        exportacionTablemodel.addTableModelListener(tableModelEv -> {
            if (tableModelEv.getColumn() == 0 && tableModelEv.getType() == TableModelEvent.UPDATE) {
                int row = tableModelEv.getFirstRow();
                var expo = exportaciones.get(row);
                if (expo.isSelected()) {
                    openUserDetailsDialog(expo);
                } else {
                }
            }
        });

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

    private void openUserDetailsDialog(Exportacion expor) {
        var dialog = new JDialog(this, "Exportacion Detalles", true);
        dialog.setLayout(new BorderLayout());

        var formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Detalles de Exportacion"));
        var gbcUpdate = new GridBagConstraints();
        gbcUpdate.insets = new Insets(8, 8, 8, 8);

        // ID Cliente
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 0; gbcUpdate.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("ID Cliente:"), gbcUpdate);
        gbcUpdate.gridx = 1; gbcUpdate.gridy = 0; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        var idFld = new JTextField(expor.getIdCliente(), 20);
        formPanel.add(idFld, gbcUpdate);

        // Nombre Completo
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 1; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
        formPanel.add(new JLabel("Nombre Completo:"), gbcUpdate);
        gbcUpdate.gridx = 1; gbcUpdate.gridy = 1; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        var nameFld = new JTextField(expor.getNombreCompleto(), 20);
        formPanel.add(nameFld, gbcUpdate);

        // Tipo
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 2; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
        formPanel.add(new JLabel("Tipo Exportacion:"), gbcUpdate);

        JComboBox<TipoExportacion> statusComboBox = new JComboBox<>(TipoExportacion.values());
        statusComboBox.setEditable(false);
        statusComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoExportacion) {
                    setText(((TipoExportacion) value).getDescripcion());
                }
                return this;
            }
        });
        statusComboBox.setSelectedItem(expor.getTipoExportacion());

        gbcUpdate.gridx = 1; gbcUpdate.gridy = 2; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        formPanel.add(statusComboBox, gbcUpdate);


        // Zona
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 3; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
        formPanel.add(new JLabel("Zona:"), gbcUpdate);
        gbcUpdate.gridx = 1; gbcUpdate.gridy = 3; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        var zoneFld = new JTextField(expor.getZonaEnvio(), 20);
        formPanel.add(zoneFld, gbcUpdate);

        // Tipo Servicio
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 4; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
        formPanel.add(new JLabel("Opciones de Envío:"), gbcUpdate);

        var optionsEnvioGroup = new ButtonGroup();
        JRadioButton avioRadio = new JRadioButton("Avion", expor.getTipoServicio().equalsIgnoreCase("Avion"));
        JRadioButton barcoRadio = new JRadioButton("Barco", expor.getTipoServicio().equalsIgnoreCase("Barco"));
        optionsEnvioGroup.add(avioRadio);
        optionsEnvioGroup.add(barcoRadio);

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        checkboxPanel.add(avioRadio);
        checkboxPanel.add(barcoRadio);

        gbcUpdate.gridx = 1; gbcUpdate.gridy = 4; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        formPanel.add(checkboxPanel, gbcUpdate);


        // Peso (kg)
        gbcUpdate.gridx = 0; gbcUpdate.gridy = 5; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
        formPanel.add(new JLabel("Peso (kg):"), gbcUpdate);
        gbcUpdate.gridx = 1; gbcUpdate.gridy = 5; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        formPanel.add(new JTextField(String.valueOf(expor.getKilogramos()), 20), gbcUpdate);

        // Tipo Carga
        JComboBox<TipoCarga> tipoCargaComboBox = new JComboBox<>(TipoCarga.values());
        if (expor.getTipoExportacion().equals(TipoExportacion.ECP)) {
            tipoCargaComboBox.setSelectedItem(((ExportacionCargaPesada) expor).getTipoCarga());
            tipoCargaComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof TipoCarga) {
                        setText(((TipoCarga) value).getDescripcion());
                    }
                    return this;
                }
            });
            gbcUpdate.gridx = 0; gbcUpdate.gridy = 6; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
            formPanel.add(new JLabel("Tipo de Carga:"), gbcUpdate);
            gbcUpdate.gridx = 1; gbcUpdate.gridy = 6; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
            formPanel.add(tipoCargaComboBox, gbcUpdate);
        }

        if (expor.getTipoExportacion().equals(TipoExportacion.ECS)) {
            gbcUpdate.gridx = 0; gbcUpdate.gridy = 7; gbcUpdate.fill = GridBagConstraints.NONE; gbcUpdate.weightx = 0;
            formPanel.add(new JLabel("Pies de Carga:"), gbcUpdate);
            gbcUpdate.gridx = 1; gbcUpdate.gridy = 7; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
            formPanel.add(new JTextField(String.valueOf(expor.getKilogramos()), 20), gbcUpdate);
        }

        gbcUpdate.gridx = 0; gbcUpdate.gridy = 6; gbcUpdate.fill = GridBagConstraints.HORIZONTAL; gbcUpdate.weightx = 1.0;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            expor.setNombreCompleto(nameFld.getText());
            expor.setIdCliente(idFld.getText());

            if (avioRadio.isSelected()) expor.setTipoServicio(avioRadio.getText());
            if (barcoRadio.isSelected()) expor.setTipoServicio(barcoRadio.getText());

            expor.setZonaEnvio(zoneFld.getText());

            System.out.printf("Exportacion guardada correctamente, exportacion= %s%n", expor);
            JOptionPane.showMessageDialog(dialog, "Exportacion guardada correctamente.");
            dialog.dispose();
            exportacionTablemodel.fireTableDataChanged();
        });
        JButton closeButton = new JButton("Cancelar");
        closeButton.addActionListener(e -> {
            dialog.dispose();
            expor.setSelected(false);
            exportacionTablemodel.fireTableDataChanged();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setMinimumSize(new Dimension(800, 600));
        dialog.setMaximumSize(new Dimension(800, 600));
        dialog.setSize(800, 600);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

}
