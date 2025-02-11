package Controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;
import Modelos.VeterinarioDTO;
import Modelos.IVeterinario;

public class VeterinarioDAO implements IVeterinario {

    private Connection con = null;

    public VeterinarioDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<VeterinarioDTO> getAll() throws SQLException {
        List<VeterinarioDTO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM veterinario");
            while (res.next()) {
                VeterinarioDTO v = new VeterinarioDTO();
                v.setId(res.getInt("id"));
                v.setNif(res.getString("nif"));
                v.setNombre(res.getString("nombre"));
                v.setDireccion(res.getString("direccion"));
                v.setTelefono(res.getString("telefono"));
                v.setEmail(res.getString("email"));
                lista.add(v);
            }
        }

        return lista;
    }

    @Override
    public VeterinarioDTO findById(int id) throws SQLException {
        String sql = "SELECT * FROM veterinario WHERE id = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            prest.setInt(1, id);
            try (ResultSet res = prest.executeQuery()) {
                if (res.next()) {
                    VeterinarioDTO veterinario = new VeterinarioDTO();
                    veterinario.setId(res.getInt("id"));
                    veterinario.setNif(res.getString("nif"));
                    veterinario.setNombre(res.getString("nombre"));
                    veterinario.setDireccion(res.getString("direccion"));
                    veterinario.setTelefono(res.getString("telefono"));
                    veterinario.setEmail(res.getString("email"));
                    return veterinario;
                }
            }
        }
        return null; // Si no encuentra un veterinario, devuelve null
    }

    @Override
    public int insertVeterinario(VeterinarioDTO veterinario) throws SQLException {
        int numFilas = 0;
        String sql = "INSERT INTO veterinario (nif, nombre, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";

        if (findById(veterinario.getId()) != null) {
            return numFilas;
        } else {
            try (PreparedStatement prest = con.prepareStatement(sql)) {
                prest.setString(1, veterinario.getNif());
                prest.setString(2, veterinario.getNombre());
                prest.setString(3, veterinario.getDireccion());
                prest.setString(4, veterinario.getTelefono());
                prest.setString(5, veterinario.getEmail());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int deleteVeterinario(int idVeterinario) throws SQLException {
        int numFilas = 0;

        String sql = "DELETE FROM veterinario WHERE id = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            prest.setInt(1, idVeterinario);
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deleteVeterinarios() throws SQLException {  // Implementamos el método que falta
        String sql = "DELETE FROM veterinario";
        int nfilas = 0;

        try (Statement st = con.createStatement()) {
            nfilas = st.executeUpdate(sql);
        }

        return nfilas;
    }

    @Override
    public int updateVeterinario(int id, VeterinarioDTO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "UPDATE veterinario SET nif = ?, nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id=?";

        if (findById(id) == null) {
            return numFilas;
        } else {
            try (PreparedStatement prest = con.prepareStatement(sql)) {
                prest.setString(1, nuevosDatos.getNif());
                prest.setString(2, nuevosDatos.getNombre());
                prest.setString(3, nuevosDatos.getDireccion());
                prest.setString(4, nuevosDatos.getTelefono());
                prest.setString(5, nuevosDatos.getEmail());
                prest.setInt(6, id);

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}
