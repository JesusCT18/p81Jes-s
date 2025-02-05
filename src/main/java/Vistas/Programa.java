package Vistas;

import Controladores.MascotaDAO;
import Controladores.VeterinarioDAO;
import Modelos.MascotaDTO;
import Modelos.VeterinarioDTO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Programa {

    private static MascotaDAO mascotaDAO = new MascotaDAO();
    private static VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    public static void main(String[] args) {
        while (true) {
            String menu = "---- Menú ----\n"
                    + "1. Listar mascotas\n"
                    + "2. Listar veterinarios\n"
                    + "3. Agregar mascota\n"
                    + "4. Agregar veterinario\n"
                    + "5. Asignar veterinario a mascota\n"
                    + "6. Eliminar mascota\n"
                    + "7. Eliminar veterinario\n"
                    + "8. Actualizar mascota\n"
                    + "9. Actualizar veterinario\n"
                    + "10. Buscar mascota por ID\n"
                    + "11. Buscar veterinario por ID\n"
                    + "0. Salir";

            String opcionStr = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.INFORMATION_MESSAGE);
            int opcion = Integer.parseInt(opcionStr);

            switch (opcion) {
                case 1:
                    listarMascotas();
                    break;
                case 2:
                    listarVeterinarios();
                    break;
                case 3:
                    agregarMascota();
                    break;
                case 4:
                    agregarVeterinario();
                    break;
                case 5:
                    asignarVeterinario();
                    break;
                case 6:
                    eliminarMascota();
                    break;
                case 7:
                    eliminarVeterinario();
                    break;
                case 8:
                    actualizarMascota();
                    break;
                case 9:
                    actualizarVeterinario();
                    break;
                case 10:
                    buscarMascotaPorId();
                    break;
                case 11:
                    buscarVeterinarioPorId();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }

    // Listar todas las mascotas
    private static void listarMascotas() {
        try {
            List<MascotaDTO> mascotas = mascotaDAO.getAll();
            StringBuilder message = new StringBuilder("Mascotas:\n");
            for (MascotaDTO m : mascotas) {
                message.append(m.getId()).append(": ").append(m.getNombre()).append(" (").append(m.getTipo()).append(")\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar las mascotas: " + e.getMessage());
        }
    }

    // Listar todos los veterinarios
    private static void listarVeterinarios() {
        try {
            List<VeterinarioDTO> veterinarios = veterinarioDAO.getAll();
            StringBuilder message = new StringBuilder("Veterinarios:\n");
            for (VeterinarioDTO v : veterinarios) {
                message.append(v.getId()).append(": ").append(v.getNombre()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los veterinarios: " + e.getMessage());
        }
    }

    private static void agregarMascota() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre de la mascota:");
            String tipo = JOptionPane.showInputDialog("Ingrese tipo de mascota (perro, gato, otros):");
            double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese peso de la mascota:"));
            String fechaNacimientoStr = JOptionPane.showInputDialog("Ingrese fecha de nacimiento (yyyy-mm-dd):");
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr);

            String numeroChip = JOptionPane.showInputDialog("Ingrese número de chip (alfanumérico):");

            if (numeroChip == null || numeroChip.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El número de chip no puede estar vacío.");
                return;
            }

            String idVeterinarioStr = JOptionPane.showInputDialog("Ingrese el ID del veterinario (deje vacío si no tiene):");
            Integer idVeterinario = null;

            // Verificar si se ingresó un ID veterinario
            if (idVeterinarioStr != null && !idVeterinarioStr.trim().isEmpty()) {
                idVeterinario = Integer.parseInt(idVeterinarioStr);
            }

            // Crear objeto de Mascota
            MascotaDTO nuevaMascota = new MascotaDTO();
            nuevaMascota.setNombre(nombre);
            nuevaMascota.setTipo(tipo);
            nuevaMascota.setPeso(peso);
            nuevaMascota.setFechaNacimiento(fechaNacimiento);
            nuevaMascota.setNumeroChip(numeroChip);
            nuevaMascota.setIdVeterinario(idVeterinario);

            // Inserción de la mascota en la base de datos
            int filasInsertadas = mascotaDAO.insertMascota(nuevaMascota);
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Mascota agregada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar la mascota.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la mascota: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID veterinario debe ser un número entero.");
        }
    }

    // Agregar un nuevo veterinario
    private static void agregarVeterinario() {
        try {
            String nif = JOptionPane.showInputDialog("Ingrese NIF del veterinario:");
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del veterinario:");
            String direccion = JOptionPane.showInputDialog("Ingrese dirección del veterinario:");
            String telefono = JOptionPane.showInputDialog("Ingrese teléfono del veterinario:");
            String email = JOptionPane.showInputDialog("Ingrese email del veterinario:");

            VeterinarioDTO nuevoVeterinario = new VeterinarioDTO();
            nuevoVeterinario.setNif(nif);
            nuevoVeterinario.setNombre(nombre);
            nuevoVeterinario.setDireccion(direccion);
            nuevoVeterinario.setTelefono(telefono);
            nuevoVeterinario.setEmail(email);

            int filasInsertadas = veterinarioDAO.insertVeterinario(nuevoVeterinario);
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Veterinario agregado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el veterinario.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el veterinario: " + e.getMessage());
        }
    }

    // Asignar un veterinario a una mascota
    private static void asignarVeterinario() {
        try {
            int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la mascota a asignar:"));
            int idVeterinario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del veterinario a asignar:"));
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota != null) {
                mascota.setIdVeterinario(idVeterinario);
                mascotaDAO.updateMascota(idMascota, mascota);
                JOptionPane.showMessageDialog(null, "Veterinario asignado a la mascota.");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al asignar veterinario: " + e.getMessage());
        }
    }

    // Eliminar una mascota
    private static void eliminarMascota() {
        try {
            int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la mascota a eliminar:"));
            int filasEliminadas = mascotaDAO.deleteMascota(idMascota);
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "Mascota eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la mascota.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la mascota: " + e.getMessage());
        }
    }

    // Eliminar un veterinario
    private static void eliminarVeterinario() {
        try {
            int idVeterinario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del veterinario a eliminar:"));
            int filasEliminadas = veterinarioDAO.deleteVeterinario(idVeterinario);
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "Veterinario eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el veterinario.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el veterinario: " + e.getMessage());
        }
    }

    // Actualizar una mascota
    private static void actualizarMascota() {
        try {
            int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la mascota a actualizar:"));
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota != null) {
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para la mascota:");
                double nuevoPeso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese nuevo peso para la mascota:"));
                String nuevaFechaStr = JOptionPane.showInputDialog("Ingrese nueva fecha de nacimiento (yyyy-mm-dd):");
                java.sql.Date nuevaFecha = java.sql.Date.valueOf(nuevaFechaStr);
                mascota.setNombre(nuevoNombre);
                mascota.setPeso(nuevoPeso);
                mascota.setFechaNacimiento(nuevaFecha);
                mascotaDAO.updateMascota(idMascota, mascota);
                JOptionPane.showMessageDialog(null, "Mascota actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la mascota: " + e.getMessage());
        }
    }

    // Actualizar un veterinario
    private static void actualizarVeterinario() {
        try {
            int idVeterinario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del veterinario a actualizar:"));
            VeterinarioDTO veterinario = veterinarioDAO.findById(idVeterinario);
            if (veterinario != null) {
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para el veterinario:");
                String nuevaDireccion = JOptionPane.showInputDialog("Ingrese nueva dirección para el veterinario:");
                String nuevoTelefono = JOptionPane.showInputDialog("Ingrese nuevo teléfono para el veterinario:");
                String nuevoEmail = JOptionPane.showInputDialog("Ingrese nuevo email para el veterinario:");
                veterinario.setNombre(nuevoNombre);
                veterinario.setDireccion(nuevaDireccion);
                veterinario.setTelefono(nuevoTelefono);
                veterinario.setEmail(nuevoEmail);
                veterinarioDAO.updateVeterinario(idVeterinario, veterinario);
                JOptionPane.showMessageDialog(null, "Veterinario actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El veterinario no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el veterinario: " + e.getMessage());
        }
    }

    // Buscar mascota por ID
    private static void buscarMascotaPorId() {
        try {
            int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la mascota a buscar:"));
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota != null) {
                JOptionPane.showMessageDialog(null, "Mascota encontrada: " + mascota.getNombre() + " (" + mascota.getTipo() + ")");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la mascota: " + e.getMessage());
        }
    }

    // Buscar veterinario por ID
    private static void buscarVeterinarioPorId() {
        try {
            int idVeterinario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del veterinario a buscar:"));
            VeterinarioDTO veterinario = veterinarioDAO.findById(idVeterinario);
            if (veterinario != null) {
                JOptionPane.showMessageDialog(null, "Veterinario encontrado: " + veterinario.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "El veterinario no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el veterinario: " + e.getMessage());
        }
    }
}
