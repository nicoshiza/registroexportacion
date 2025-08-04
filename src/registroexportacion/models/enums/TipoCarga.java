package registroexportacion.models.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoCarga {
    CR("Contenedor Refrigerado"),
    CNR("Contenedor No Refrigerado"),
    CE("Contenedor Embalada");

    private final String descripcion;

    TipoCarga(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    private static final Map<TipoCarga, String> lookupDescripcionMap = new HashMap<>();
    private static final Map<String, TipoCarga> tipoCargaEnumLookupMap = new HashMap<>();

    static {
        for (TipoCarga tipo : TipoCarga.values()) {
            lookupDescripcionMap.put(tipo, tipo.getDescripcion());
        }
        for (TipoCarga tipo : values()) {
            tipoCargaEnumLookupMap.put(tipo.name(), tipo);
        }
    }

    public static String getDescripcionByTipo(TipoCarga tipo) {
        return lookupDescripcionMap.get(tipo);
    }

    public static TipoCarga fromValue(String value) {
        return tipoCargaEnumLookupMap.getOrDefault(value, null);
    }
}
