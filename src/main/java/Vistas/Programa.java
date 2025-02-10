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
                    + "3. Buscar mascota por ID\n"
                    + "4. Buscar veterinario por ID\n"
                    + "5. Agregar mascota\n"
                    + "6. Agregar veterinario\n"
                    + "7. Asignar veterinario a mascota\n"
                    + "8. Eliminar mascota\n"
                    + "9. Eliminar veterinario\n"
                    + "10. Actualizar mascota\n"
                    + "11. Actualizar veterinario\n"
                    + "0. Salir";

            String opcionStr = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.INFORMATION_MESSAGE);
            if (opcionStr == null) {
                JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                System.exit(0); // Salir del programa si se presiona cancelar
            }

            int opcion = Integer.parseInt(opcionStr);

            switch (opcion) {
                case 1:
                    listarMascotas();
                    break;
                case 2:
                    listarVeterinarios();
                    break;
                case 3:
                    buscarMascotaPorId();
                    break;
                case 4:
                    buscarVeterinarioPorId();
                    break;
                case 5:
                    agregarMascota();
                    break;
                case 6:
                    agregarVeterinario();
                    break;
                case 7:
                    asignarVeterinario();
                    break;
                case 8:
                    eliminarMascota();
                    break;
                case 9:
                    eliminarVeterinario();
                    break;
                case 10:
                    actualizarMascota();
                    break;
                case 11:
                    actualizarVeterinario();
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

    // Buscar mascota por ID
    private static void buscarMascotaPorId() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID de la mascota a buscar:");
            if (inputId == null) {
                return; 
            }
            int idMascota = Integer.parseInt(inputId);
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota != null) {
                JOptionPane.showMessageDialog(null, "Mascota encontrada: " + mascota.getNombre() + " (" + mascota.getTipo() + ")");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la mascota: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // Buscar veterinario por ID
    private static void buscarVeterinarioPorId() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID del veterinario a buscar:");
            if (inputId == null) {
                return; 
            }
            int idVeterinario = Integer.parseInt(inputId);
            VeterinarioDTO veterinario = veterinarioDAO.findById(idVeterinario);
            if (veterinario != null) {
                JOptionPane.showMessageDialog(null, "Veterinario encontrado: " + veterinario.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "El veterinario no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el veterinario: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    private static void agregarMascota() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre de la mascota:");
            if (nombre == null) {
                return; 
            }
            String tipo = JOptionPane.showInputDialog("Ingrese tipo de mascota (perro, gato, otros):");
            if (tipo == null) {
                return;
            }

            double peso;
            try {
                String pesoStr = JOptionPane.showInputDialog("Ingrese peso de la mascota:");
                if (pesoStr == null) {
                    return;
                }
                peso = Double.parseDouble(pesoStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Peso inválido.");
                return;
            }

            String fechaNacimientoStr = JOptionPane.showInputDialog("Ingrese fecha de nacimiento (yyyy-mm-dd):");
            if (fechaNacimientoStr == null) {
                return;
            }
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr);

            String numeroChip = JOptionPane.showInputDialog("Ingrese número de chip (alfanumérico):");
            if (numeroChip == null || numeroChip.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El número de chip no puede estar vacío.");
                return;
            }

            String idVeterinarioStr = JOptionPane.showInputDialog("Ingrese el ID del veterinario (deje vacío si no tiene):");
            Integer idVeterinario = null;

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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del NIF, teléfono o email no es válido.");
        }
    }

    // Asignar un veterinario a una mascota
    private static void asignarVeterinario() {
        try {
            String inputIdMascota = JOptionPane.showInputDialog("Ingrese el ID de la mascota a asignar:");
            if (inputIdMascota == null) {
                return;
            }
            int idMascota = Integer.parseInt(inputIdMascota);

            String inputIdVeterinario = JOptionPane.showInputDialog("Ingrese el ID del veterinario a asignar:");
            if (inputIdVeterinario == null) {
                return;
            }
            int idVeterinario = Integer.parseInt(inputIdVeterinario);

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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // Eliminar una mascota
    private static void eliminarMascota() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID de la mascota a eliminar:");
            if (inputId == null) {
                return;
            }
            int idMascota = Integer.parseInt(inputId);

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
            String inputId = JOptionPane.showInputDialog("Ingrese el ID del veterinario a eliminar:");
            if (inputId == null) {
                return;
            }
            int idVeterinario = Integer.parseInt(inputId);

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
                String nuevoChip = JOptionPane.showInputDialog("Ingrese el nuevo número de chip para la mascota:");
                mascota.setNombre(nuevoNombre);
                mascota.setPeso(nuevoPeso);
                mascota.setFechaNacimiento(nuevaFecha);
                mascota.setNumeroChip(nuevoChip); 
                
                // Actualizar la mascota en la base de datos
                mascotaDAO.updateMascota(idMascota, mascota);
                JOptionPane.showMessageDialog(null, "Mascota actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la mascota: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del peso o ID de la mascota no es válido.");
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del veterinario debe ser un número entero.");
        }
    }
}
