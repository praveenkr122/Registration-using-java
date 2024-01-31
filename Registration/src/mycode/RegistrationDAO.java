package mycode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/register";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "841406a@A";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void createRecord(String name, String email, Date dob) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Registration (Name, Email, DateOfBirth) VALUES (?, ?, ?)"
             )) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setDate(3, dob);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Registration> readRecords() {
        List<Registration> records = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Registration")) {
            while (resultSet.next()) {
                Registration record = new Registration(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getDate("DateOfBirth")
                        // Add additional fields as needed
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static void updateRecord(int id, String newName, String newEmail, Date newDob) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Registration SET Name=?, Email=?, DateOfBirth=? WHERE ID=?"
             )) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setDate(3, newDob);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecord(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Registration WHERE ID=?"
             )) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage
        createRecord("John Doe", "john.doe@example.com", Date.valueOf("1990-01-01"));
        createRecord("Jane Smith", "jane.smith@example.com", Date.valueOf("1985-05-15"));

        System.out.println("Records before update:");
        for (Registration record : readRecords()) {
            System.out.println(record.getId() + " " + record.getName() + " " + record.getEmail() + " " + record.getDateOfBirth());
        }

        updateRecord(1, "John Updated", "john.updated@example.com", Date.valueOf("1990-02-02"));

        System.out.println("\nRecords after update:");
        for (Registration record : readRecords()) {
            System.out.println(record.getId() + " " + record.getName() + " " + record.getEmail() + " " + record.getDateOfBirth());
        }

        deleteRecord(2);

        System.out.println("\nRecords after delete:");
        for (Registration record : readRecords()) {
            System.out.println(record.getId() + " " + record.getName() + " " + record.getEmail() + " " + record.getDateOfBirth());
        }
    }
}

