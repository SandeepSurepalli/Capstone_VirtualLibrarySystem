package DS4Books;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager;
    private static SearchBook searchBook;

    public static void main(String[] args) {
        System.out.println("Welcome to the Virtual Library!");
        String filePath = "src//main//resources//Books.csv";
        bookManager = new BookManager(filePath);
        String userId = "user123";
        bookManager.alertOnOverdueBooks(userId);
        displayMainMenu();
    }


    static void displayMainMenu() {
        String userId = "user123";

        while (true) {
            System.out.println("\nVirtual Library Main Menu:");
            System.out.println("1. Search for a book");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View borrowing transactions (Admin)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    if (BookManager.checkOverdueBooks(userId)) {
                        System.out.println("** You have overdue books. Please return them as soon as possible. **");
                    }
                    searchBook.searchBookInLibrary();
                    break;
                case 2:
                    searchBook.borrowBook();
                    break;
                case 3:
                    searchBook.returnBook();
                    break;
                case 4:
                    System.out.print("Enter admin ID: ");
                    String adminId = scanner.nextLine();
                    bookManager.viewBorrowTransactions(adminId);
                    break;
                case 5:
                    System.out.println("Exiting the application...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
