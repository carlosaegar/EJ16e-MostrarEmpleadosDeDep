package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String usuario = "RIBERA";
        String contraseña = "ribera";

        String deptoBuscar = "SISTEMAS";

        String sql = "SELECT e.NOMBRE, e.SALARIO " +
                "FROM EMPLEADO e " +
                "JOIN DEPARTAMENTO d ON e.departamento_id = d.id " +
                "WHERE d.NOMBRE = ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, deptoBuscar);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Empleados del departamento: " + deptoBuscar);

                boolean hayEmpleados = false;
                while (rs.next()) {
                    hayEmpleados = true;

                    String nombre = rs.getString("NOMBRE");
                    double salario = rs.getDouble("SALARIO");
                    System.out.println("Nombre: " + nombre + " Salario: " + salario + "€");
                }
                if (!hayEmpleados) {
                    System.out.println("No hay empleados .");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
