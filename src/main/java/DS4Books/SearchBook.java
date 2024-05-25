package DS4Books;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SearchBook {
    public List<Book> matchedBooks = new ArrayList<Book>();
    List<Book> bookList;

    public SearchBook(List<Book> bookList) {
        this.bookList = bookList;
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
            System.out.println("Out of Stock");
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
        System.out.println("Do you want to proceed ? : Y/N");
        try (Scanner sc = new Scanner(System.in)) {
            String proceed = sc.next();
            if (proceed.equalsIgnoreCase("y")) {
                long availableCopies = selectedBook.getNumberOfCopies();
                if (availableCopies > 0) {
                    System.out.println("Enter your user ID: ");
                    String userId = sc.next();

                    selectedBook.setNumberOfCopies(availableCopies - 1);
                    selectedBook.updateStatus(); // Update the book's status
                    System.out.println("The book has been borrowed successfully. Remaining Copies: " + selectedBook.getNumberOfCopies());
                    System.out.println("Book Status: " + selectedBook.getStatus());

                    // Log the transaction
                    BorrowTransaction transaction = new BorrowTransaction(userId, selectedBook.getISBN());
                    BookManager.addBorrowTransaction(transaction);
                } else {
                    System.out.println("The book is out of stock.");
                }
            } else {
                System.out.println("Borrowing cancelled.");
            }
        }
    }
}
