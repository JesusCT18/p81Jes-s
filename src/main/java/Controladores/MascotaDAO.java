package Controladores;

import java.sql.Connection;
import Conexion.Conexion;
import Modelos.MascotaDTO;
import Modelos.IMascota;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO implements IMascota {

    private Connection con = null;

    public MascotaDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<MascotaDTO> getAll() throws SQLException {
        List<MascotaDTO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM mascota");
            while (res.next()) {
                MascotaDTO m = new MascotaDTO();
                m.setId(res.getInt("id"));
                m.setNumeroChip(res.getString("numero_chip"));
                m.setNombre(res.getString("nombre"));
                m.setPeso(res.getDouble("peso"));
                m.setFechaNacimiento(res.getDate("fecha_nacimiento"));
                m.setTipo(res.getString("tipo"));
                m.setIdVeterinario(res.getInt("id_veterinario"));

                lista.add(m);
            }
        }

        return lista;
    }

    @Override
    public MascotaDTO findById(int id) throws SQLException {
        MascotaDTO mascota = null;
        String sql = "SELECT * FROM mascota WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mascota = new MascotaDTO();
                mascota.setId(rs.getInt("id"));
                mascota.setNumeroChip(rs.getString("numero_chip"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setPeso(rs.getDouble("peso"));
                mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                mascota.setTipo(rs.getString("tipo"));
                mascota.setIdVeterinario(rs.getInt("id_veterinario"));
            }
        }
        return mascota;
    }

    @Override
    public Integer insertMascota(MascotaDTO mascota) throws SQLException {
        // Consulta SQL para insertar una nueva mascota en la base de datos
        String sql = "INSERT INTO mascota (numero_chip, nombre, peso, fecha_nacimiento, tipo, id_veterinario) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Asignar el número de chip (almacenado como String)
            prest.setString(1, mascota.getNumeroChip());

            // Asignar el nombre de la mascota (String)
            prest.setString(2, mascota.getNombre());

            // Asignar el peso de la mascota (double)
            prest.setDouble(3, mascota.getPeso());

            // Manejar la fecha de nacimiento
            if (mascota.getFechaNacimiento() != null) {
                java.sql.Date sqlDate = new java.sql.Date(mascota.getFechaNacimiento().getTime()); // Convertir a java.sql.Date
                prest.setDate(4, sqlDate);  // Asignamos la fecha en el formato correcto para SQL
            } else {
                prest.setNull(4, java.sql.Types.DATE);  // Si la fecha es null, asignamos NULL a la base de datos
            }

            // Asignar el tipo de la mascota (String)
            prest.setString(5, mascota.getTipo());

            // Manejo del id_veterinario (puede ser null)
            Integer idVeterinario = mascota.getIdVeterinario();
            if (idVeterinario != null) {
                prest.setInt(6, idVeterinario); // Si idVeterinario no es null, lo asignamos como entero
            } else {
                prest.setNull(6, java.sql.Types.INTEGER);  // Si idVeterinario es null, asignamos NULL en la base de datos
            }

            // Ejecutar la inserción en la base de datos y devolver el número de filas afectadas
            return prest.executeUpdate();
        }
    }

    @Override
    public Integer insertMascota(List<MascotaDTO> lista) throws SQLException {
        int rows = 0;
        for (MascotaDTO m : lista) {
            rows += insertMascota(m);
        }
        return rows;
    }

    @Override
    public int deleteMascota(int idMascota) throws SQLException {
        String sql = "DELETE FROM mascota WHERE id = ?";
        try (PreparedStatement prest = con.prepareStatement(sql)) {
            prest.setInt(1, idMascota);
            return prest.executeUpdate();
        }
    }

    @Override
    public int deleteMascotas() throws SQLException {
        String sql = "DELETE FROM mascota";
        try (Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    @Override
    public int updateMascota(int id, MascotaDTO nuevosDatos) throws SQLException {
        String sql = "UPDATE mascota SET numero_chip = ?, nombre = ?, peso = ?, fecha_nacimiento = ?, tipo = ?, id_veterinario = ? WHERE id = ?";
        try (PreparedStatement prest = con.prepareStatement(sql)) {
            prest.setString(1, nuevosDatos.getNumeroChip());
            prest.setString(2, nuevosDatos.getNombre());
            prest.setDouble(3, nuevosDatos.getPeso());

            // Asegúrate de convertir correctamente java.util.Date a java.sql.Date
            if (nuevosDatos.getFechaNacimiento() != null) {
                java.sql.Date sqlDate = new java.sql.Date(nuevosDatos.getFechaNacimiento().getTime());
                prest.setDate(4, sqlDate);  // Usamos el constructor de java.sql.Date que toma un long
            } else {
                prest.setNull(4, java.sql.Types.DATE);  // Si la fecha es nula, asignamos null
            }

            prest.setString(5, nuevosDatos.getTipo());
            prest.setInt(6, nuevosDatos.getIdVeterinario());
            prest.setInt(7, id);
            return prest.executeUpdate();
        }
    }

    @Override
    public List<MascotaDTO> getMascotasByVeterinario(int idVeterinario) throws SQLException {
        List<MascotaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota WHERE id_veterinario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idVeterinario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MascotaDTO m = new MascotaDTO();
                m.setId(rs.getInt("id"));
                m.setNumeroChip(rs.getString("numero_chip"));
                m.setNombre(rs.getString("nombre"));
                m.setPeso(rs.getDouble("peso"));
                m.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                m.setTipo(rs.getString("tipo"));
                m.setIdVeterinario(rs.getInt("id_veterinario"));
                lista.add(m);
            }
        }
        return lista;
    }
}
