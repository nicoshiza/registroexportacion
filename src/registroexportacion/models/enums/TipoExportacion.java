package registroexportacion.models.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoExportacion {
    ECP("Exportación Carga Pesada"),
    ECS("Exportación Carga Suelta"),
    NONE("Tipo Exportación Invalido");

    private final String descripcion;

    TipoExportacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    private static final Map<String, TipoExportacion> tipoExportacionEnumLookup = new HashMap<>();
    private static final Map<TipoExportacion, String> tipoExportacionDescLookupMap = new HashMap<>();

    static {
        for (TipoExportacion tipo : TipoExportacion.values()) {
            tipoExportacionDescLookupMap.put(tipo, tipo.getDescripcion());
        }
        for (TipoExportacion tipo : values()) {
            tipoExportacionEnumLookup.put(tipo.name(), tipo);
        }
    }

    public static String getDescripcionByTipo(TipoExportacion tipo) {
        return tipoExportacionDescLookupMap.get(tipo);
    }

    public static TipoExportacion fromValue(String value) {
        return tipoExportacionEnumLookup.getOrDefault(value, TipoExportacion.NONE);
    }
}
