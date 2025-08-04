package registroexportacion.dao;

import registroexportacion.models.Exportacion;

import java.util.List;

public abstract class ExportacionDao {

    public abstract void guardar(List<Exportacion>  exportacion);
    public abstract void modificar(Exportacion exportacion);
    public abstract int delete(int idExportacion);
    public abstract List<Exportacion> listar();

}
