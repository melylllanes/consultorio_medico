package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String database;
    private Connection connection;
    private Statement statement;

    public BaseDatos(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario  where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {

            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setNombre(rs.getString("nombre"));
            temp.setPassword(rs.getString("password"));
            temp.setRol(rs.getString("rol"));
            usuario.add(temp);
        }
        return usuario;
    }

    public boolean agregarDoctor(String nombre, String apellido, String especialidad) throws SQLException {

        String sql = "insert into doctor(doc_nombre, doc_apellido, doc_especialidad)"
                + "values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, apellido);
        prepStmt.setString(3, especialidad);
        return prepStmt.execute();
    }

    public void listarDoctores() throws SQLException {

        ResultSet rs = this.statement.executeQuery("select * from doctor");
        while (rs.next()) {
            System.out.println("Id:" + rs.getString("id_doctor") + " Nombre:" + rs.getString("doc_nombre") + " "
                    + rs.getString("doc_apellido") + " Especialidad:" + rs.getString("doc_especialidad"));

        }

    }

    public boolean agregarPaciente(String nombre, String apellido) throws SQLException {

        String sql = "insert into paciente(pac_nombre, pac_apellido)"
                + "values (?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, apellido);
        return prepStmt.execute();
    }

    public void listarPacientes() throws SQLException {

        ResultSet rs = this.statement.executeQuery("select * from paciente");

        while (rs.next()) {
            System.out.println("Id:" + rs.getString("id_paciente") + " Nombre:" + rs.getString("pac_nombre")
                    + " " + rs.getString("pac_apellido"));

        }
    }

    public boolean agregarCita(String fecha, String hora, String motivo, String id_paciente, String id_doctor) throws SQLException {

        String sql = "insert into cita(fecha, hora, motivo, id_paciente, id_doctor)"
                + "values (?,?,?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, fecha);
        prepStmt.setString(2, hora);
        prepStmt.setString(3, motivo);
        prepStmt.setString(4, id_paciente);
        prepStmt.setString(5, id_doctor);

        return prepStmt.execute();
    }

    public void listarCitas() throws SQLException {

        ResultSet rs = this.statement.executeQuery("select * from cita");

        while (rs.next()) {
            System.out.println("Id:" + rs.getString("id_cita") + " Fecha:" + rs.getString("fecha")
                    + " Hora:" + rs.getString("hora") + " Motivo:" + rs.getString("motivo") + " ID_Paciente:" + rs.getString("id_paciente")
                    + " ID_Doctor:" + rs.getString("id_doctor"));

        }
    }

    public void listarDoctorCitas(String doctor_citas) throws SQLException {

        ResultSet rs = this.statement.executeQuery("SELECT doctor.id_doctor,doctor.doc_nombre, doctor.doc_apellido ,doctor.doc_especialidad,"
                + " cita.id_cita, cita.hora, cita.fecha, cita.motivo, paciente.pac_nombre, paciente.pac_apellido from cita join doctor "
                + "on doctor.id_doctor = cita.id_doctor join paciente on paciente.id_paciente = cita.id_paciente  "
                + "where doctor.id_doctor='" + doctor_citas + "'");

        while (rs.next()) {
            System.out.println("IdDoctor:" + rs.getString("id_doctor") + " Doctor:" + rs.getString("doc_nombre") + " "
                    + rs.getString("doc_apellido") + " Especialidad:" + rs.getString("doc_especialidad") + " IdCita:" + rs.getString("id_cita") + " Fecha:"
                    + rs.getString("fecha") + " Hora:" + rs.getString("hora") + " Motivo:" + rs.getString("motivo")
                    + " Paciente:" + rs.getString("pac_nombre") + " " + rs.getString("pac_apellido"));
        }
    }

    public void listarPacienteCitas(String paciente_citas) throws SQLException {

        ResultSet rs = this.statement.executeQuery("SELECT paciente.pac_nombre, paciente.pac_apellido,cita.id_cita, cita.hora, cita.fecha, "
                + "cita.motivo,doctor.doc_nombre, doctor.doc_apellido ,doctor.doc_especialidad from cita join doctor on doctor.id_doctor "
                + "= cita.id_doctor join paciente on paciente.id_paciente = cita.id_paciente  where paciente.id_paciente='" + paciente_citas + "'");

        while (rs.next()) {
            System.out.println("Paciente:" + rs.getString("pac_nombre") + " " + rs.getString("pac_apellido") + " IdCita:"
                    + rs.getString("id_cita") + " Fecha:" + rs.getString("fecha") + " Hora:" + rs.getString("hora") + " Motivo:"
                    + rs.getString("motivo") + "IdDoctor:" + " Doctor:" + rs.getString("doc_nombre") + " "
                    + rs.getString("doc_apellido") + " Especialidad:" + rs.getString("doc_especialidad"));
        }
    }

}
