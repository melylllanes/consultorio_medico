package db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ConsultorioAdmin {

    static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String seleccion = "";
        String user = "";
        String password = "";
        BaseDatos persist = new BaseDatos("consultorio.db");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese su usuario y contraseña para iniciar:");
            System.out.println("Usuario:");
            user = scanner.nextLine();
            System.out.println("Contraseña:");
            password = scanner.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            System.out.println();
            if (!usuario.isEmpty()) {
                while (true) {
                    System.out.println("(1) Dar de alta doctores.");
                    System.out.println("(2) Dar de alta pacientes.");
                    System.out.println("(3) Crear una cita.");
                    System.out.println("(4) Mostrar doctores");
                    System.out.println("(5) Mostrar pacientes");
                    System.out.println("(6) Mostrar citas");
                    System.out.println("(7) Mostrar las citas por doctor");
                    System.out.println("(8) Mostrar las citas por paciente");
                    System.out.println("(0) Salir");
                    System.out.println("\nPor favor ingrese una opción: ");
                    // Fin de Menu
                    // Try Anidado
                    try {
                        // Asigna token Integer parseado
                        seleccion = scanner.nextLine();
                        System.out.println("----------------------------------------------------------------------------------------");

                        switch (seleccion) {
                            case "0":
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case "1":
                                System.out.println("Alta de Doctores:");
                                System.out.println("Nombre:");
                                String nombre = scanner.nextLine();
                                System.out.println("Apellido:");
                                String apellido = scanner.nextLine();
                                System.out.println("Especialidad:");
                                String especialidad = scanner.nextLine();
                                persist.agregarDoctor(nombre, apellido, especialidad);
                                System.out.println("----------------------------------------------------------------------------------------");

                                break;
                            case "2":
                                System.out.println("Alta de Pacientes:");
                                System.out.println("Nombre:");
                                nombre = scanner.nextLine();
                                System.out.println("Apellido:");
                                apellido = scanner.nextLine();
                                persist.agregarPaciente(nombre, apellido);
                                System.out.println("----------------------------------------------------------------------------------------");

                                break;
                            case "3":
                                System.out.println("Alta de Citas:");
                                System.out.println("Fecha: (Formato MM/DD/AA)");
                                String fecha = scanner.nextLine();
                                System.out.println("Hora: (Formato 24HR HH:MM )");
                                String hora = scanner.nextLine();
                                System.out.println("Motivo:");
                                String motivo = scanner.nextLine();
                                System.out.println("Escoga el paciente:");
                                persist.listarPacientes();
                                System.out.println("ID Paciente:");
                                String id_paciente = scanner.nextLine();
                                System.out.println("Escoga el doctor:");
                                persist.listarDoctores();
                                System.out.println("ID Doctor:");
                                String id_doctor = scanner.nextLine();
                                persist.agregarCita(fecha, hora, motivo, id_paciente, id_doctor);
                                System.out.println("----------------------------------------------------------------------------------------");

                                break;
                            case "4":
                                System.out.println("Lista de Doctores:");
                                persist.listarDoctores();
                                System.out.println("----------------------------------------------------------------------------------------");
                                break;
                            case "5":
                                System.out.println("Lista de Pacientes:");
                                persist.listarPacientes();
                                System.out.println("----------------------------------------------------------------------------------------");
                                break;
                            case "6":
                                System.out.println("Lista de Citas:");
                                persist.listarCitas();
                                System.out.println("----------------------------------------------------------------------------------------");
                                break;
                            case "7":
                                System.out.println("Citas del Doctor");
                                persist.listarDoctores();
                                System.out.println("Escoga el id del Doctor para ver sus citas:");
                                String doctor_citas = scanner.nextLine();
                                persist.listarDoctorCitas(doctor_citas);
                                System.out.println("----------------------------------------------------------------------------------------");
                                break;
                            case "8":
                                System.out.println("Citas del Paciente");
                                persist.listarPacientes();
                                System.out.println("Escoga el id del Paciente para ver sus citas:");
                                String paciente_citas = scanner.nextLine();
                                persist.listarPacienteCitas(paciente_citas);
                                System.out.println("----------------------------------------------------------------------------------------");
                                break;
                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }

                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally {
            persist.getConnection().close();
        }
    }
}
