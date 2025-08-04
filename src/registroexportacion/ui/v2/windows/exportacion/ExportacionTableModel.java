package registroexportacion.ui.v2.windows.exportacion;

import registroexportacion.models.Exportacion;
import registroexportacion.models.ExportacionCargaPesada;
import registroexportacion.models.ExportacionCargaSuelta;
import registroexportacion.models.enums.TipoExportacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ExportacionTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "Select",
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
    private final List<Exportacion> lista = new ArrayList<>();

    public ExportacionTableModel(List<Exportacion> lista) {
        this.lista.addAll(lista);
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        var expor = lista.get(row);
        return switch (col) {
            case 0 -> expor.isSelected();
            case 1 -> expor.getTipoExportacion().getDescripcion();
            case 2 -> expor.getNombreCompleto();
            case 3 -> expor.getIdCliente();
            case 4 -> expor.getZonaEnvio();
            case 5 -> expor.getTipoServicio();
            case 6 -> expor.getFechaExportacion();
            case 7 -> expor.getFechaModificacion();
            case 8 -> expor.getKilogramos();
            case 9 -> switch (expor.getTipoExportacion()) {
                case TipoExportacion.ECP -> ((ExportacionCargaPesada) expor).getTipoCarga().getDescripcion();
                case TipoExportacion.ECS -> "";
                default -> "";
            };
            case 10 -> switch (expor.getTipoExportacion()) {
                case TipoExportacion.ECP -> "";
                case TipoExportacion.ECS -> {
                    var piesCarga = ((ExportacionCargaSuelta) expor).getPiesCarga();
                    yield piesCarga != null ? piesCarga : 0;
                }
                default -> "";
            };
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) {
        lista.get(rowIndex).setSelected((Boolean) value);
        fireTableCellUpdated(rowIndex, colIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }
}
