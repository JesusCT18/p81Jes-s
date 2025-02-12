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

            String opcionStr = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.INFORMATION_MESSAGE);
            if (opcionStr == null) {
                JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                System.exit(0); // Salir del programa si se presiona cancelar
            } else if (opcionStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debes elegir una de las opciones.");
                continue; // Vuelve a mostrar el menu si no se ha seleccionado nada
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
                    JOptionPane.showMessageDialog(null, "Opcion no valida.");
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
            JOptionPane.showMessageDialog(null, "Error al listar las mascotas");
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
            JOptionPane.showMessageDialog(null, "Error al listar los veterinarios");
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
            JOptionPane.showMessageDialog(null, "Error al buscar la mascota");
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
            JOptionPane.showMessageDialog(null, "Error al buscar el veterinario");
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
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas del veterinario");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    private static void agregarMascota() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre de la mascota:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre de la mascota es obligatorio.");
                return;
            }

            String tipo = JOptionPane.showInputDialog("Ingrese tipo de mascota (perro, gato, otros):");
            if (tipo == null || tipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El tipo de mascota es obligatorio.");
                return;
            }

            // Validacion para asegurarse de que el tipo sea valido
            while (!tipo.equalsIgnoreCase("perro") && !tipo.equalsIgnoreCase("gato") && !tipo.equalsIgnoreCase("otros")) {
                JOptionPane.showMessageDialog(null, "Tipo de mascota invalido. Ingrese 'perro', 'gato' o 'otros'.");
                tipo = JOptionPane.showInputDialog("Ingrese tipo de mascota (perro, gato, otros):");
                if (tipo == null || tipo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El tipo de mascota es obligatorio.");
                    return;
                }
            }

            double peso;
            try {
                String pesoStr = JOptionPane.showInputDialog("Ingrese peso de la mascota:");
                if (pesoStr == null || pesoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El peso de la mascota es obligatorio.");
                    return;
                }
                peso = Double.parseDouble(pesoStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Peso invalido.");
                return;
            }

            String fechaNacimientoStr = JOptionPane.showInputDialog("Ingrese fecha de nacimiento (yyyy-mm-dd):");
            if (fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento es obligatoria.");
                return;
            }
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr);

            String numeroChip = JOptionPane.showInputDialog("Ingrese numero de chip (alfanumerico):");
            if (numeroChip == null || numeroChip.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El numero de chip no puede estar vacio.");
                return;
            }

            String idVeterinarioStr = JOptionPane.showInputDialog("Ingrese el ID del veterinario (deje vacio si no tiene):");
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

            // Insercion de la mascota en la base de datos
            int filasInsertadas = mascotaDAO.insertMascota(nuevaMascota);
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Mascota agregada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar la mascota.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la mascota.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID veterinario debe ser un numero entero.");
        }
    }

    private static void agregarVeterinario() {
        try {
            String nif = JOptionPane.showInputDialog("Ingrese NIF del veterinario:");
            if (nif == null || nif.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El NIF es obligatorio.");
                return;
            }

            String nombre = JOptionPane.showInputDialog("Ingrese nombre del veterinario:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
                return;
            }

            String direccion = JOptionPane.showInputDialog("Ingrese direccion del veterinario:");
            if (direccion == null || direccion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La direccion es obligatoria.");
                return;
            }

            String telefono = JOptionPane.showInputDialog("Ingrese telefono del veterinario:");
            if (telefono == null || telefono.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El telefono es obligatorio.");
                return;
            }

            String email = JOptionPane.showInputDialog("Ingrese email del veterinario:");
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El email es obligatorio.");
                return;
            }

            // Crear el objeto VeterinarioDTO con los datos proporcionados
            VeterinarioDTO nuevoVeterinario = new VeterinarioDTO();
            nuevoVeterinario.setNif(nif);
            nuevoVeterinario.setNombre(nombre);
            nuevoVeterinario.setDireccion(direccion);
            nuevoVeterinario.setTelefono(telefono);
            nuevoVeterinario.setEmail(email);

            // Intentar insertar el veterinario en la base de datos
            int filasInsertadas = veterinarioDAO.insertVeterinario(nuevoVeterinario);
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Veterinario agregado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el veterinario.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el veterinario.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del NIF, telefono o email no es valido.");
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
            JOptionPane.showMessageDialog(null, "Error al asignar veterinario");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
        }
    }

    // Eliminar una mascota
    private static void eliminarMascota() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID de la mascota a eliminar:");

            if (inputId == null || inputId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ingreso ningun ID. Volviendo al menu...");
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
            JOptionPane.showMessageDialog(null, "Error al eliminar la mascota");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID de la mascota debe ser un número entero.");
        }
    }

    private static void eliminarVeterinario() {
        try {
            String inputId = JOptionPane.showInputDialog("Ingrese el ID del veterinario a eliminar:");
            if (inputId == null || inputId.trim().isEmpty()) {
                return;
            }

            int idVeterinario = Integer.parseInt(inputId);

            // Verificar que los DAO no sean null
            if (mascotaDAO == null || veterinarioDAO == null) {
                JOptionPane.showMessageDialog(null, "Error interno: No se pudo acceder a la base de datos.");
                return;
            }

            // Obtener las mascotas asociadas al veterinario
            List<MascotaDTO> mascotas = mascotaDAO.getMascotasByVeterinario(idVeterinario);

            if (mascotas != null && !mascotas.isEmpty()) {
                // Confirmar eliminación
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "Este veterinario tiene mascotas asignadas. ¿Desea eliminar el veterinario y dejar las mascotas sin veterinario?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    // Actualizar todas las mascotas asociadas al veterinario para asignarles NULL en idVeterinario
                    mascotaDAO.desasignarVeterinario(idVeterinario);

                    // Eliminar el veterinario
                    int filasEliminadas = veterinarioDAO.deleteVeterinario(idVeterinario);
                    if (filasEliminadas > 0) {
                        JOptionPane.showMessageDialog(null, "Veterinario eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el veterinario.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operación cancelada. El veterinario no fue eliminado.");
                }
            } else {
                // Si no tiene mascotas asociadas, eliminar directamente
                int filasEliminadas = veterinarioDAO.deleteVeterinario(idVeterinario);
                if (filasEliminadas > 0) {
                    JOptionPane.showMessageDialog(null, "Veterinario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el veterinario.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el veterinario: ");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del veterinario debe ser un número entero.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: ");
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
                // Nuevo nombre
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para la mascota (Deje en blanco para mantener el actual):");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    mascota.setNombre(nuevoNombre);
                }

                // Nuevo peso
                String nuevoPesoStr = JOptionPane.showInputDialog("Ingrese nuevo peso para la mascota (Deje en blanco para mantener el actual):");
                if (nuevoPesoStr != null && !nuevoPesoStr.trim().isEmpty()) {
                    double nuevoPeso = Double.parseDouble(nuevoPesoStr);
                    if (nuevoPeso > 0) {
                        mascota.setPeso(nuevoPeso);
                    }
                }

                // Nueva fecha de nacimiento
                String nuevaFechaStr = JOptionPane.showInputDialog("Ingrese nueva fecha de nacimiento (yyyy-mm-dd) (Deje en blanco para mantener la actual):");
                if (nuevaFechaStr != null && !nuevaFechaStr.trim().isEmpty()) {
                    java.sql.Date nuevaFecha = java.sql.Date.valueOf(nuevaFechaStr);
                    mascota.setFechaNacimiento(nuevaFecha);
                }

                // Nuevo número de chip
                String nuevoChip = JOptionPane.showInputDialog("Ingrese el nuevo número de chip para la mascota (Deje en blanco para mantener el actual):");
                if (nuevoChip != null && !nuevoChip.trim().isEmpty()) {
                    mascota.setNumeroChip(nuevoChip);
                }

                // Nuevo tipo de mascota
                String nuevoTipo = JOptionPane.showInputDialog("Ingrese el nuevo tipo de mascota (perro, gato, otros) (Deje en blanco para mantener el actual):");
                if (nuevoTipo != null && !nuevoTipo.trim().isEmpty()) {
                    if (!nuevoTipo.equalsIgnoreCase("perro") && !nuevoTipo.equalsIgnoreCase("gato") && !nuevoTipo.equalsIgnoreCase("otros")) {
                        JOptionPane.showMessageDialog(null, "El tipo de mascota debe ser 'perro', 'gato' o 'otros'.");
                        return;
                    }
                    mascota.setTipo(nuevoTipo);
                }

                // Nuevo veterinario
                String nuevoVeterinarioStr = JOptionPane.showInputDialog("Ingrese el nuevo ID del veterinario para la mascota (Deje en blanco para mantener el actual):");
                if (nuevoVeterinarioStr != null && !nuevoVeterinarioStr.trim().isEmpty()) {
                    Integer nuevoVeterinario = Integer.parseInt(nuevoVeterinarioStr);
                    mascota.setIdVeterinario(nuevoVeterinario);
                }

                // Actualizar la mascota en la base de datos
                mascotaDAO.updateMascota(idMascota, mascota);
                JOptionPane.showMessageDialog(null, "Mascota actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "La mascota no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la mascota: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del peso, ID de la mascota o ID del veterinario no es válido.");
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
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre para el veterinario (Deje en blanco para mantener el actual):");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    veterinario.setNombre(nuevoNombre);
                }

                // Actualizar direccion
                String nuevaDireccion = JOptionPane.showInputDialog("Ingrese nueva dirección para el veterinario (Deje en blanco para mantener el actual):");
                if (nuevaDireccion != null && !nuevaDireccion.trim().isEmpty()) {
                    veterinario.setDireccion(nuevaDireccion);
                }

                // Actualizar telefono
                String nuevoTelefono = JOptionPane.showInputDialog("Ingrese nuevo teléfono para el veterinario (Deje en blanco para mantener el actual):");
                if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
                    veterinario.setTelefono(nuevoTelefono);
                }

                // Actualizar email
                String nuevoEmail = JOptionPane.showInputDialog("Ingrese nuevo email para el veterinario (Deje en blanco para mantener el actual):");
                if (nuevoEmail != null && !nuevoEmail.trim().isEmpty()) {
                    veterinario.setEmail(nuevoEmail);
                }

                // Actualizar NIF
                String nuevoNif = JOptionPane.showInputDialog("Ingrese nuevo NIF para el veterinario (Deje en blanco para mantener el actual):");
                if (nuevoNif != null && !nuevoNif.trim().isEmpty()) {
                    veterinario.setNif(nuevoNif);
                }

                // Actualización en la base de datos
                veterinarioDAO.updateVeterinario(idVeterinario, veterinario);
                JOptionPane.showMessageDialog(null, "Veterinario actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El veterinario no existe.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el veterinario");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del veterinario debe ser un número entero.");
        }
    }
}
