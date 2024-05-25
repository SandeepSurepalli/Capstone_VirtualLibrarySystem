package DS4Books;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SearchBook {
    public List<Book> matchedBooks = new ArrayList<Book>();
    List<Book> bookList;
    private static Scanner scanner = new Scanner(System.in);

    public SearchBook(List<Book> bookList) {
        this.bookList = bookList;
        this.matchedBooks = new ArrayList<Book>();
    }

    public String getBookDetailsFromUser() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter book title, ISBN number or author name to search in virtual library" + "\n");
            return sc.nextLine();
        }
    }

    public void searchBookInLibrary() {
        String searchTerm = getBookDetailsFromUser();
        for (Book bookItem : bookList) {
            String title = bookItem.getTitle();
            String author = bookItem.getAuthor();
            String ISBN = bookItem.getISBN();
            String genre = bookItem.getGenre();
            String publicationDate = bookItem.getPublicationDate();
            long numberOfCopies = bookItem.getNumberOfCopies();
            if (title.equalsIgnoreCase(searchTerm) || title.toLowerCase().contains(searchTerm.toLowerCase()))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            else if (author.equalsIgnoreCase(searchTerm) || author.toLowerCase().contains(searchTerm.toLowerCase()))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            else if (ISBN.equalsIgnoreCase(searchTerm))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
        }
        printSearchedBook(searchTerm, matchedBooks);
    }

    public void printSearchedBook(String searchTerm, List<Book> booksList) {
        Scanner sc = new Scanner(System.in);
        if (booksList.isEmpty()) System.out.println("No books found");
        else {
            System.out.println(booksList.size() + " books are found in library which are matching the search " + searchTerm);
            System.out.println("--------------------------------------");
            for (Book bookItem : booksList) {
                System.out.println(bookItem);
            }
        }
    }

    public Book searchBookByISBN() {
        String bookISBN = bookISBNSearch();
        Book searchBook;
        if (matchedBooks.isEmpty())
            searchBook = bookList.stream().filter(book -> book.getISBN().equals(bookISBN)).findFirst().orElse(null);
        else
            searchBook = matchedBooks.stream().filter(book -> book.getISBN().equals(bookISBN)).findFirst().orElse(null);
        return searchBook;
    }

    public void selectBook() {
        Book selectedBook = searchBookByISBN();
        if (selectedBook == null) {
            System.out.println("No book found with the given ISBN.");
            return;
        }
        System.out.println("Selected book details:");
        System.out.println(selectedBook);
        if (selectedBook.getNumberOfCopies() > 0) {
            System.out.println("Available Copies: " + selectedBook.getNumberOfCopies());
        } else {
            System.err.println("ALERT: The requested book, '" + selectedBook.getTitle() + "', is out of stock.");
        }
    }

    public static String bookISBNSearch() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the ISBN number of the book you wish to search for: ");
            return sc.nextLine();
        }
    }

    public void borrowBook() {
        Book selectedBook = searchBookByISBN();
        if (selectedBook == null) {
            System.out.println("No book found with the given ISBN.");
            return;
        }
        System.out.println("Selected book details: " + selectedBook.getTitle());
        if (selectedBook.getNumberOfCopies() > 0) {
            System.out.print("Enter your user ID: "); // Prompt for user ID
            String userId = scanner.nextLine(); // Capture the user ID

            System.out.println("Do you want to proceed ? : Y/N");
            try (Scanner sc = new Scanner(System.in)) {
                String proceed = sc.next();
                if (proceed.equalsIgnoreCase("y")) {
                    long availableCopies = selectedBook.getNumberOfCopies();

                    selectedBook.setNumberOfCopies(availableCopies - 1);
                    selectedBook.updateStatus();
                    System.out.println("The book has been borrowed successfully. Remaining Copies: " + selectedBook.getNumberOfCopies());
                    System.out.println("Book Status: " + selectedBook.getStatus());

                    // Log the transaction (borrow) with captured user ID
                    BorrowTransaction transaction = new BorrowTransaction(userId, selectedBook.getISBN());
                    BookManager.addBorrowTransaction(transaction);
                }
            }
        } else {
            System.err.println("ALERT: The requested book, '" + selectedBook.getTitle() + "', is out of stock.");
            offerOptions();
        }
    }

    public void offerOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("The book is out of stock. Would you like to:");
        System.out.println("1. Return to the main menu");
        System.out.println("2. Search for another book");
        while (true) {
            System.out.print("Enter choice (1 or 2): ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Returning to the main menu...");
                    Main.displayMainMenu();
                    return;
                case 2:
                    searchBookInLibrary();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    public void returnBook() {
        System.out.print("Enter the ISBN of the book you want to return: ");
        String bookISBN = scanner.nextLine();

        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();

        Book bookToReturn = searchBookByISBN(bookISBN);
        if (bookToReturn == null) {
            System.out.println("No book found with the given ISBN.");
            return;
        }

        BorrowTransaction transaction = findBorrowTransaction(userId, bookISBN);
        if (transaction == null) {
            System.out.println("You haven't borrowed this book.");
            return;
        }

        System.out.println("Book Title: " + bookToReturn.getTitle());
        System.out.println("Borrowed By: " + userId);
        System.out.print("Confirm return? (Y/N): ");
        String confirmation = scanner.nextLine().toUpperCase();

        if (confirmation.equals("Y")) {
            long currentCopies = bookToReturn.getNumberOfCopies();
            bookToReturn.setNumberOfCopies(currentCopies + 1); // Increase available copies
            bookToReturn.updateStatus(); // Update book status based on new copies

            System.out.println("Book returned successfully.");

           transaction.setReturnDate(new Date());

           int transactionIndex = BookManager.borrowTransactions.indexOf(transaction);
            if (transactionIndex != -1) { // Check if transaction found
                BookManager.borrowTransactions.set(transactionIndex, transaction);
                System.out.println("Return logged: User ID: " + userId + ", Book ISBN: " + bookISBN + ", Return Date: " + transaction.getReturnDate());
            } else {
                System.err.println("Error: Transaction not found for update.");
            }
        } else {
            System.out.println("Return canceled.");
        }
    }

    private BorrowTransaction findBorrowTransaction(String userId, String bookISBN) {
        return BookManager.borrowTransactions.stream()
                .filter(transaction -> transaction.getUserID().equals(userId) && transaction.getBookISBN().equals(bookISBN))
                .findFirst()
                .orElse(null);
    }
    private Book searchBookByISBN(String bookISBN) {
        return bookList.stream()
                .filter(book -> book.getISBN().equals(bookISBN))
                .findFirst()
                .orElse(null);
    }

}
