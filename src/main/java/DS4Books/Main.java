package DS4Books;

import java.util.List;
import java.util.Scanner;

import static DS4Books.BookManager.TimeFrame.MONTHLY;

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

        BookManager manager = new BookManager("Books.json");
        List<Book> bookList = manager.getBookList();
        if (bookList != null) {
            for (Book book : bookList) {
                System.out.println(book);
            }
        }

        // Add a borrow transaction
        if (bookList != null && !bookList.isEmpty()) {
            Book bookToBorrow = bookList.get(0);
            BorrowTransaction transaction = new BorrowTransaction("user123", bookToBorrow, new java.util.Date(), new java.util.Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)); // Due date is 7 days from now
            BookManager.addBorrowTransaction(transaction);
        }

        // View borrow transactions (admin access)
        BookManager.viewBorrowTransactions("admin1");

        // Check for overdue books
        BookManager.checkOverdueBooks("user123");

        // CLI for library statistics overview
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'stats' to view library statistics overview:");
        String command = scanner.nextLine();

        if ("stats".equalsIgnoreCase(command)) {
            BookManager.displayLibraryStatistics();
        }

        scanner.close();
    }


    static void displayMainMenu() {
        String userId = "user123";

        while (true) {
            System.out.println("\nVirtual Library Main Menu:");
            System.out.println("1. Search for a book");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View borrowing transactions (Admin)");
            System.out.println("5. Display total books");
            System.out.println("6. Display number of borrowed books"); // Added menu option
            System.out.println("7. Display list of borrowed books titles"); // Added menu option
            System.out.println("8. Display most borrowed books list");
            System.out.println("9. Analyze Borrowing Trends");
            System.out.println("10. Display genre popularity rankings.");
            System.out.println("11. Display top borrowed authors");
            System.out.println("12. Exit");
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
                    BookManager.displayTotalNumberOfBooks();
                    break;
                case 6: // Call to display number of borrowed books
                    BookManager.displayNumberOfBorrowedBooks();
                    break;
                case 7: // Call to display list of borrowed book titles
                    BookManager.displayBorrowedBooksList();
                    break;
                case 8: // This would be a new case for the new menu item
                    BookManager.displayTopBorrowedBooks();
                    break;
                case 9:
                    BookManager.displayBorrowingTrends(MONTHLY);
                    break;
                case 10:
                    BookManager.displayGenrePopularity();
                    break;
                case 11:
                    displayTopAuthorsByBorrowCount();;
                    break;
                case 12:
                    System.out.println("Exiting the application...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void displayTopAuthorsByBorrowCount() {
        List<Author> topAuthors = BookManager.getTopAuthorsByBorrowCount();
        for (Author author : topAuthors) {
            System.out.println("Author: " + author.getName() + " - Borrow Count: " + author.getBorrowCount());
        }
    }
}
