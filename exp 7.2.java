import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your database name
    private static final String USER = "your_username"; // Replace with your database username
    private static final String PASSWORD = "your_password"; // Replace with your database password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Create Product");
            System.out.println("2. Read Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createProduct(scanner);
                    break;
                case 2:
                    readProducts();
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void createProduct(Scanner scanner) {
        System.out.print("Enter ProductID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter ProductName: ");
        String productName = scanner.next();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String query = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false); // Start transaction
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, productName);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, quantity);
            preparedStatement.executeUpdate();
            connection.commit(); // Commit transaction
            System.out.println("Product created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readProducts() {
        String query = "SELECT * FROM Product";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("ProductID: " + resultSet.getInt("ProductID") +
                        ", ProductName: " + resultSet.getString ("ProductName") +
                        ", Price: " + resultSet.getDouble("Price") +
                        ", Quantity: " + resultSet.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter ProductID to update: ");
        int productId = scanner.nextInt();
        System.out.print("Enter new ProductName: ");
        String productName = scanner.next();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();

        String query = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false); // Start transaction
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, productId);
            preparedStatement.executeUpdate();
            connection.commit(); // Commit transaction
            System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter ProductID to delete: ");
        int productId = scanner.nextInt();

        String query = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false); // Start transaction
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            connection.commit(); // Commit transaction
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
