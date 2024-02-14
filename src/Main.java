import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void showTasks(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM tasks");

        while (result.next()) {
            System.out.println(result.getString("name") + ' ' + result.getString("status"));
        }
    }

    public static void createTask(Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement("insert into tasks (name, status) values (?, ?)");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name of new task: ");
        String enteredName = scanner.next();
        System.out.println("Enter status of new task: ");
        String enteredStatus = scanner.next();

        ps.setString(1, enteredName);
        ps.setString(2, enteredStatus);
        ps.executeUpdate();
    }

    public static void deleteTask(Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement("delete from tasks where name = ?");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name: ");
        String enteredName = scanner.next();

        ps.setString(1, enteredName);
        ps.executeUpdate();
    }


    public static void main(String[] args) throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" , "postgres" , "0202");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM tasks");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Task Manager!");

        while (true) {

            System.out.println("1. Show all tasks");
            System.out.println("2. Create task");
            System.out.println("3. Delete task");
            System.out.println("4. Quit");

            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();

            if (choice == 1) {
                showTasks(connection);
                continue;
            } else if (choice == 2){
                createTask(connection);
                continue;
            } else if (choice == 3) {
                deleteTask(connection);
                continue;
            } else if (choice == 4) {
                break;
            }

        }
    }
}