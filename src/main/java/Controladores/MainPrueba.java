package Controladores;

import Modelos.MascotaDTO;
import Modelos.VeterinarioDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class MainPrueba {

    public static void main(String[] args) {
        
        MascotaDTO m1 = new MascotaDTO();
       // VeterinarioDTO v1 = new VeterinarioDTO(1,"totis","peo","alaakbar","telefons","l@gmail.com");
        System.out.println(m1);
        //System.out.println(v1);
        
        
        Scanner scanner = new Scanner(System.in);
        MascotaDAO mascotaDAO = new MascotaDAO();
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
        int opcion;

        try {
            while (true) {  // Bucle infinito, solo se sale cuando el usuario elige la opción 9
                // Menú principal
                System.out.println("\n--- MENÚ ---");
                
                System.out.println("1. Ver todos los veterinarios");
                System.out.println("2. Ver todas las mascotas");
                System.out.println("3. Insertar veterinario");
                System.out.println("4. Insertar mascota");
                System.out.println("5. Actualizar veterinario");
                System.out.println("6. Actualizar mascota");
                System.out.println("7. Eliminar veterinario");
                System.out.println("8. Eliminar mascota");
                System.out.println("9. Salir");
                System.out.print("Selecciona una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        // Ver todos los veterinarios
                        System.out.println("---- Veterinarios registrados ----");
                        for (VeterinarioDTO v : veterinarioDAO.getAll()) {
                            System.out.println(v);
                        }
                        break;

                    case 2:
                        // Ver todas las mascotas
                        System.out.println("---- Mascotas registradas ----");
                        for (MascotaDTO m : mascotaDAO.getAll()) {
                            System.out.println(m);
                        }
                        break;

                    case 3:
                        // Insertar veterinario
                        System.out.println("Introduce los datos del veterinario:");
                        System.out.print("NIF: ");
                        String nif = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombreVeterinario = scanner.nextLine();
                        System.out.print("Dirección: ");
                        String direccion = scanner.nextLine();
                        System.out.print("Teléfono: ");
                        String telefono = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        VeterinarioDTO veterinario = new VeterinarioDTO();
                        veterinario.setNif(nif);
                        veterinario.setNombre(nombreVeterinario);
                        veterinario.setDireccion(direccion);
                        veterinario.setTelefono(telefono);
                        veterinario.setEmail(email);

                        // Insertar Veterinario
                        int filasInsertadasVeterinario = veterinarioDAO.insertVeterinario(veterinario);
                        if (filasInsertadasVeterinario > 0) {
                            System.out.println("Veterinario insertado correctamente.");
                        } else {
                            System.out.println("El veterinario ya existe o no se pudo insertar.");
                        }
                        break;

                    case 4:
                        // Insertar mascota
                        System.out.println("Introduce los datos de la mascota:");
                        System.out.print("Número de chip: ");
                        String numeroChip = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombreMascota = scanner.nextLine();
                        System.out.print("Peso: ");
                        double peso = scanner.nextDouble();  // Lee el peso como un número decimal
                        scanner.nextLine();  // Limpiar el buffer
                        System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
                        String fechaNacimientoStr = scanner.nextLine();
                        System.out.print("Tipo de mascota: ");
                        String tipo = scanner.nextLine();

                        // Convertir la fecha ingresada por el usuario a LocalDate
                        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                        // Convertir LocalDate a java.sql.Date
                        Date fechaNacimientoSQL = Date.valueOf(fechaNacimiento);

                        // Mostrar los veterinarios disponibles
                        System.out.println("---- Veterinarios disponibles ----");
                        for (VeterinarioDTO v : veterinarioDAO.getAll()) {
                            System.out.println(v.getId() + ". " + v.getNombre());  // Muestra el ID y nombre del veterinario
                        }

                        // Seleccionar un veterinario
                        System.out.print("Selecciona el ID del veterinario para esta mascota (o 0 para no asignar veterinario): ");
                        int idVeterinario = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        // Si el usuario ingresa 0, se asignará NULL (sin veterinario)
                        if (idVeterinario == 0) {
                            idVeterinario = -1;  // Esto puede indicar que no hay veterinario asignado
                        }

                        // Crear la mascota
                        MascotaDTO mascota = new MascotaDTO();
                        mascota.setNumeroChip(numeroChip);
                        mascota.setNombre(nombreMascota);
                        mascota.setPeso(peso);
                        mascota.setFechaNacimiento(fechaNacimientoSQL);
                        mascota.setTipo(tipo);
                        mascota.setIdVeterinario(idVeterinario == -1 ? null : idVeterinario);  // Si no hay veterinario asignado, se pone null

                        // Insertar Mascota
                        int filasInsertadasMascota = mascotaDAO.insertMascota(mascota);
                        System.out.println("Filas insertadas en mascota: " + filasInsertadasMascota);
                        break;

                    case 5:
                        // Actualizar veterinario
                        System.out.print("Introduce el ID del veterinario que deseas actualizar: ");
                        int idVeterinarioActualizar = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        VeterinarioDTO nuevosDatosVeterinario = new VeterinarioDTO();
                        System.out.print("Nuevo NIF: ");
                        nuevosDatosVeterinario.setNif(scanner.nextLine());
                        System.out.print("Nuevo Nombre: ");
                        nuevosDatosVeterinario.setNombre(scanner.nextLine());
                        System.out.print("Nueva Dirección: ");
                        nuevosDatosVeterinario.setDireccion(scanner.nextLine());
                        System.out.print("Nuevo Teléfono: ");
                        nuevosDatosVeterinario.setTelefono(scanner.nextLine());
                        System.out.print("Nuevo Email: ");
                        nuevosDatosVeterinario.setEmail(scanner.nextLine());

                        int filasActualizadasVeterinario = veterinarioDAO.updateVeterinario(idVeterinarioActualizar, nuevosDatosVeterinario);
                        System.out.println("Filas actualizadas en veterinario: " + filasActualizadasVeterinario);
                        break;

                    case 6:
                        // Actualizar mascota
                        System.out.print("Introduce el ID de la mascota que deseas actualizar: ");
                        int idMascotaActualizar = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        MascotaDTO nuevosDatosMascota = new MascotaDTO();
                        System.out.print("Nuevo Número de Chip: ");
                        nuevosDatosMascota.setNumeroChip(scanner.nextLine());
                        System.out.print("Nuevo Nombre: ");
                        nuevosDatosMascota.setNombre(scanner.nextLine());
                        System.out.print("Nuevo Peso: ");
                        nuevosDatosMascota.setPeso(scanner.nextDouble());
                        scanner.nextLine();  // Limpiar el buffer
                        System.out.print("Nueva Fecha de Nacimiento (yyyy-MM-dd): ");
                        String fechaNacimientoStrMascota = scanner.nextLine();
                        System.out.print("Nuevo Tipo: ");
                        nuevosDatosMascota.setTipo(scanner.nextLine());

                        // Convertir la fecha ingresada por el usuario a LocalDate
                        LocalDate fechaNacimientoMascota = LocalDate.parse(fechaNacimientoStrMascota);
                        // Convertir LocalDate a java.sql.Date
                        Date fechaNacimientoSQLMascota = Date.valueOf(fechaNacimientoMascota);

                        nuevosDatosMascota.setFechaNacimiento(fechaNacimientoSQLMascota);

                        int filasActualizadasMascota = mascotaDAO.updateMascota(idMascotaActualizar, nuevosDatosMascota);
                        System.out.println("Filas actualizadas en mascota: " + filasActualizadasMascota);
                        break;

                    case 7:
                        // Eliminar veterinario
                        System.out.print("Introduce el ID del veterinario que deseas eliminar: ");
                        int idVeterinarioEliminar = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        int filasEliminadasVeterinario = veterinarioDAO.deleteVeterinario(idVeterinarioEliminar);
                        System.out.println("Filas eliminadas en veterinario: " + filasEliminadasVeterinario);
                        break;

                    case 8:
                        // Eliminar mascota
                        System.out.print("Introduce el ID de la mascota que deseas eliminar: ");
                        int idMascotaEliminar = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        int filasEliminadasMascota = mascotaDAO.deleteMascota(idMascotaEliminar);
                        System.out.println("Filas eliminadas en mascota: " + filasEliminadasMascota);
                        break;

                    case 9:
                        // Salir del programa
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        return;  // Salir del programa

                    default:
                        System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
