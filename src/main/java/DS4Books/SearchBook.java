package DS4Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchBook {
    public List<Book> matchedBooks = new ArrayList<Book>();

    public String getBookDetailsFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book title, ISBN number or author name to search in virtual library" + "\n");
        String bookDetails = sc.nextLine();
        return bookDetails;
    }

    public void searchBookInLibrary(List<Book> bookList) {
        String bookTobeSearched = getBookDetailsFromUser();
        for (Book bookItem : bookList) {
            String title = bookItem.getTitle();
            String author = bookItem.getAuthor();
            String ISBN = bookItem.getTitle();
            String genre = bookItem.getGenre();
            String publicationDate = bookItem.getPublicationDate();
            long numberOfCopies = bookItem.getNumberOfCopies();
            if (title.equalsIgnoreCase(bookTobeSearched) || title.toLowerCase().contains(bookTobeSearched.toLowerCase()))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            else if (author.equalsIgnoreCase(bookTobeSearched) || author.toLowerCase().contains(bookTobeSearched.toLowerCase()))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            else if (ISBN.equalsIgnoreCase(bookTobeSearched))
                matchedBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
        }
        printSearchedBook(bookTobeSearched);
    }

    public void printSearchedBook(String bookTobeSearched) {
        if (matchedBooks.isEmpty()) System.out.println("No books found");
        else {
            if (matchedBooks.size() == 1) System.out.println("Only 1 book found in library");
            else
                System.out.println(matchedBooks.size() + " books are found in library which are matching the search " + bookTobeSearched);
            System.out.println("--------------------------------------");
            for (Book bookItem : matchedBooks) {
                String title = bookItem.getTitle();
                String author = bookItem.getAuthor();
                String ISBN = bookItem.getTitle();
                String genre = bookItem.getGenre();
                String publicationDate = bookItem.getPublicationDate();
                long numberOfCopies = bookItem.getNumberOfCopies();
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("ISBN: " + ISBN);
                System.out.println("Genre: " + genre);
                System.out.println("Publication Date: " + publicationDate);
                System.out.println("Number of Copies: " + numberOfCopies);
                System.out.println("--------------------------------------");
            }
        }
    }
}
