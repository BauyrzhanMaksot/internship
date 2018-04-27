package jdbc;


import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolesJdbc {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/beginning";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connection = null;

    public static boolean validate() {
        connection = getDBConnection();
        return connection != null;
    }

    public static boolean addRole(Role role) {
        if (validate()) {
            String query = "insert into roles(role) values (?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, role.role);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean deleteRole(int id) {
        if (validate()) {
            String query = "DELETE FROM roles WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean updateRole(Role role) {
        if (validate()) {
            String query = "UPDATE roles SET role = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,role.role);
                preparedStatement.setInt(2, role.id);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static List<Role> getAllRoles() {
        List<Role> roles = new ArrayList();
        if (validate()) {
            String selectSQL = "select * from roles";
            roles = getRoles(selectSQL);
        }

        return roles;
    }

    public static Role getRoleById(Integer id) {
        Role role = null;
        if (validate()) {
            String selectSQL = "select * roles u where u.id = '" + id + "'";
            role = getRole(role, selectSQL);
        }

        return role;
    }


    private static Role getRole(Role role, String selectSQL) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            if (rs.next()) {
                role = new Role();
                role.id = rs.getInt("id");
                role.role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public static List<Role> getRoles(String selectSQL) {
        validate();
        List<Role> roles = new ArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                Role role = new Role();
                role.id = rs.getInt("id");
                role.role = rs.getString("role");

                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

}
