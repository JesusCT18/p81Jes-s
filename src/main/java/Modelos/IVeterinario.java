package Modelos;

import java.sql.SQLException;
import java.util.List;

public interface IVeterinario {

    // Metodo para obtener todos los registros de la tabla
    List<VeterinarioDTO> getAll() throws SQLException;

    // Metodo para obtener un registro a partir de la ID
    VeterinarioDTO findById(int id) throws SQLException;

    // Metodo para insertar un registro
    int insertVeterinario(VeterinarioDTO veterinario) throws SQLException;

    // Metodo para borrar un veterinario especifico
    int deleteVeterinario(int idVeterinario) throws SQLException;

    // Metodo para borrar toda la tabla
    int deleteVeterinarios() throws SQLException;
    
    // Metodo para modificar un veterinario. Se modifica al veterinario que tenga esa 'id'
    // con los nuevos datos que traiga el objeto 'nuevosDatos'
    int updateVeterinario(int id, VeterinarioDTO nuevosDatos) throws SQLException;
}
