package jdbc;


import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbc {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/beginning";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connection = null;

    public static boolean validate() {
        connection = getDBConnection();
        return connection != null;
    }

    public static boolean addUser(User user) {
        if (validate()) {
            String query = "insert into users(firstName, contactNumber) values (?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.firstName);
                preparedStatement.setString(2, user.contactNumber);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static boolean deleteUser(int id) {
        if (validate()) {
            String query = "DELETE FROM users WHERE id = ?";
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

    public static boolean updateUser(User user) {
        if (validate()) {
            String query = "UPDATE users SET firstName = ?, contactNumber = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,user.firstName);
                preparedStatement.setString(2,user.contactNumber);
                preparedStatement.setInt(3, user.id);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList();
        if (validate()) {
            String selectSQL = "select * from users";
            users = getUsers(selectSQL);
        }

        return users;
    }

    public static User getUserById(Integer id) {
        User user = null;
        if (validate()) {
            String selectSQL = "select * from users u where u.id = '" + id + "'";
            user = getUser(user, selectSQL);
        }

        return user;
    }


    private static User getUser(User user, String selectSQL) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            if (rs.next()) {
                user = new User();
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstName");
                user.contactNumber = rs.getString("contactNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> getUsers(String selectSQL) {
        validate();
        List<User> users = new ArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstName");
                user.contactNumber = rs.getString("contactNumber");

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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
