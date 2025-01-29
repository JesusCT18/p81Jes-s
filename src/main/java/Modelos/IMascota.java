package Modelos;

import java.sql.SQLException;
import java.util.List;

public interface IMascota {

    // Metodo para obtener todos los registros de la tabla
    List<MascotaDTO> getAll() throws SQLException;

    // Metodo para obtener un registro a partir de la ID
    MascotaDTO findById(int id) throws SQLException;

    // Metodo para insertar un registro
    int insertMascota(MascotaDTO mascota) throws SQLException;

    // Metodo para borrar una mascota específica
    int deleteMascota(int idMascota) throws SQLException;

    // Metodo para modificar una mascota. Se modifica a la mascota que tenga esa 'id'
    // con los nuevos datos que traiga el objeto 'nuevosDatos'
    int updateMascota(int id, MascotaDTO nuevosDatos) throws SQLException;

    // Metodo para obtener una lista de mascotas asociadas a un veterinario por su ID
    List<MascotaDTO> getMascotasByVeterinario(int idVeterinario) throws SQLException;
}
