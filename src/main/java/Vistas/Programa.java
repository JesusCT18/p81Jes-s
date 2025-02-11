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
                    + "5. Mostrar mascotas por veterinario\n"
                    + "6. Agregar mascota\n"
                    + "7. Agregar veterinario\n"
                    + "8. Asignar veterinario a mascota\n"
                    + "9. Eliminar mascota\n"
                    + "10. Eliminar veterinario\n"
                    + "11. Actualizar mascota\n"
                    + "12. Actualizar veterinario\n"
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
                    mostrarMascotasPorVeterinario();
                    break;
                case 6:
                    agregarMascota();
                    break;
                case 7:
                    agregarVeterinario();
                    break;
                case 8:
                    asignarVeterinario();
                    break;
                case 9:
                    eliminarMascota();
                    break;
                case 10:
                    eliminarVeterinario();
                    break;
                case 11:
                    actualizarMascota();
                    break;
                case 12:
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

    // Metodo para mostrar las mascotas asignadas a un veterinario
    private static void mostrarMascotasPorVeterinario() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID del veterinario para ver sus mascotas:");
            if (inputId == null) {
                return;
            }
            int idVeterinario = Integer.parseInt(inputId);

            // Obtener las mascotas asignadas al veterinario
            List<MascotaDTO> mascotas = mascotaDAO.getMascotasByVeterinario(idVeterinario);

            if (mascotas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay mascotas asignadas a este veterinario.");
            } else {
                StringBuilder message = new StringBuilder("Mascotas asignadas al veterinario:\n");
                for (MascotaDTO m : mascotas) {
                    message.append(m.getId()).append(": ").append(m.getNombre()).append(" (" + m.getTipo() + ")\n");
                }
                JOptionPane.showMessageDialog(null, message.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas del veterinario: " + e.getMessage());
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

            // Validacion para asegurarse de que el tipo sea valido
            while (!tipo.equalsIgnoreCase("perro") && !tipo.equalsIgnoreCase("gato") && !tipo.equalsIgnoreCase("otros")) {
                JOptionPane.showMessageDialog(null, "Tipo de mascota inválido. Ingrese 'perro', 'gato' o 'otros'.");
                tipo = JOptionPane.showInputDialog("Ingrese tipo de mascota (perro, gato, otros):");
                if (tipo == null) {
                    return;
                }
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

    private static void agregarVeterinario() {
        try {
            String nif = JOptionPane.showInputDialog("Ingrese NIF del veterinario:");
            if (nif == null) {
                return;
            }
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del veterinario:");
            if (nombre == null || nombre.trim().isEmpty()) {
                return;
            }
            String direccion = JOptionPane.showInputDialog("Ingrese dirección del veterinario:");
            if (direccion == null || direccion.trim().isEmpty()) {
                return;
            }

            String telefono = JOptionPane.showInputDialog("Ingrese teléfono del veterinario:");
            if (telefono == null || telefono.trim().isEmpty()) {
                return;
            }

            String email = JOptionPane.showInputDialog("Ingrese email del veterinario:");
            if (email == null || email.trim().isEmpty()) {
                return;
            }

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
            // Solicitar ID de la mascota
            String inputIdMascota = JOptionPane.showInputDialog("Ingrese el ID de la mascota a asignar:");
            if (inputIdMascota == null) {
                return;
            }
            int idMascota = Integer.parseInt(inputIdMascota);

            // Solicitar ID del veterinario
            String inputIdVeterinario = JOptionPane.showInputDialog("Ingrese el ID del veterinario a asignar:");
            if (inputIdVeterinario == null) {
                return;
            }
            int idVeterinario = Integer.parseInt(inputIdVeterinario);

            // Verificar si la mascota existe
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "La mascota con ID " + idMascota + " no existe.");
                return;
            }

            // Verificar si el veterinario existe
            VeterinarioDTO veterinario = veterinarioDAO.findById(idVeterinario);
            if (veterinario == null) {
                JOptionPane.showMessageDialog(null, "El veterinario con ID " + idVeterinario + " no existe.");
                return;
            }

            // Si todo es valido, asignar el veterinario a la mascota
            mascota.setIdVeterinario(idVeterinario);
            mascotaDAO.updateMascota(idMascota, mascota);

            // Mensaje de exito
            JOptionPane.showMessageDialog(null, "Veterinario asignado correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al asignar veterinario: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
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

    private static void actualizarMascota() {
        try {
            int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la mascota a actualizar:"));
            if (idMascota <= 0) {
                return;
            }
            MascotaDTO mascota = mascotaDAO.findById(idMascota);
            if (mascota != null) {
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para la mascota:");
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                    return;
                }

                double nuevoPeso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese nuevo peso para la mascota:"));
                if (nuevoPeso <= 0) {
                    return;
                }

                String nuevaFechaStr = JOptionPane.showInputDialog("Ingrese nueva fecha de nacimiento (yyyy-mm-dd):");
                if (nuevaFechaStr == null || nuevaFechaStr.trim().isEmpty()) {
                    return;
                }
                java.sql.Date nuevaFecha = java.sql.Date.valueOf(nuevaFechaStr);

                String nuevoChip = JOptionPane.showInputDialog("Ingrese el nuevo número de chip para la mascota:");
                if (nuevoChip == null || nuevoChip.trim().isEmpty()) {
                    return;
                }

                // Actualizacion del tipo de mascota
                String nuevoTipo = JOptionPane.showInputDialog("Ingrese el nuevo tipo de mascota (perro, gato, otros):");
                if (nuevoTipo == null || nuevoTipo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El tipo de mascota no puede estar vacío.");
                    return;
                }

                // Validar que el tipo ingresado sea "perro", "gato" o "otros"
                if (!nuevoTipo.equalsIgnoreCase("perro") && !nuevoTipo.equalsIgnoreCase("gato") && !nuevoTipo.equalsIgnoreCase("otros")) {
                    JOptionPane.showMessageDialog(null, "El tipo de mascota debe ser 'perro', 'gato' o 'otros'.");
                    return;
                }

                // Establecer los valores actualizados en el objeto mascota
                mascota.setNombre(nuevoNombre);
                mascota.setPeso(nuevoPeso);
                mascota.setFechaNacimiento(nuevaFecha);
                mascota.setNumeroChip(nuevoChip);
                mascota.setTipo(nuevoTipo);

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

    private static void actualizarVeterinario() {
        try {
            int idVeterinario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del veterinario a actualizar:"));
            if (idVeterinario <= 0) {
                return;
            }
            VeterinarioDTO veterinario = veterinarioDAO.findById(idVeterinario);
            if (veterinario != null) {
                // Actualizar nombre
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para el veterinario:");
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                    return;
                }

                // Actualizar direccion
                String nuevaDireccion = JOptionPane.showInputDialog("Ingrese nueva dirección para el veterinario:");
                if (nuevaDireccion == null || nuevaDireccion.trim().isEmpty()) {
                    return;
                }

                // Actualizar telefono
                String nuevoTelefono = JOptionPane.showInputDialog("Ingrese nuevo teléfono para el veterinario:");
                if (nuevoTelefono == null || nuevoTelefono.trim().isEmpty()) {
                    return;
                }

                // Actualizar email
                String nuevoEmail = JOptionPane.showInputDialog("Ingrese nuevo email para el veterinario:");
                if (nuevoEmail == null || nuevoEmail.trim().isEmpty()) {
                    return;
                }

                // Actualizar NIF
                String nuevoNif = JOptionPane.showInputDialog("Ingrese nuevo NIF para el veterinario:");
                if (nuevoNif == null || nuevoNif.trim().isEmpty()) {
                    return;
                }

                // Actualizar datos del veterinario en el objeto
                veterinario.setNombre(nuevoNombre);
                veterinario.setDireccion(nuevaDireccion);
                veterinario.setTelefono(nuevoTelefono);
                veterinario.setEmail(nuevoEmail);
                veterinario.setNif(nuevoNif);

                // Actualizacion en la base de datos
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
