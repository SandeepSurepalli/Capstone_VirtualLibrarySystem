package DS4Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchBook {
    public List<Book> matchedBooks = new ArrayList<Book>();

    public List<Book> filterBooks = new ArrayList<>();

    public String getBookDetailsFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book title, ISBN number or author name to search in virtual library" + "\n");
        String searchTerm = sc.nextLine();
        return searchTerm;
    }

    public void searchBookInLibrary(List<Book> bookList) {
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
        if (booksList.isEmpty()) System.out.println("No books found");
        else {
            if (booksList.size() == 1) System.out.println("Only 1 book found in library");
            else
                System.out.println(booksList.size() + " books are found in library which are matching the search " + searchTerm);
            System.out.println("--------------------------------------");
            for (Book bookItem : booksList) {
                String title = bookItem.getTitle();
                String author = bookItem.getAuthor();
                String ISBN = bookItem.getISBN();
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
            Scanner sc = new Scanner(System.in);
            System.out.println("Want to filter books further ? Y/N");
            if (sc.next().equalsIgnoreCase("y")) {
                String advanceSearchTerm = advanceBookSearch();
                searchBookInMatchedResults(advanceSearchTerm);
            }else System.out.println("Thank you for visiting our library");
        }
    }

    public String advanceBookSearch() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book genre or publication date(yyyy-mm-dd) to filter search further" + "\n");
        String searchTerm = sc.nextLine();
        return searchTerm;
    }

    public void searchBookInMatchedResults(String advanceSearchTerm) {
        for (Book bookItem : matchedBooks) {
            String title = bookItem.getTitle();
            String author = bookItem.getAuthor();
            String ISBN = bookItem.getTitle();
            String genre = bookItem.getGenre();
            String publicationDate = bookItem.getPublicationDate();
            long numberOfCopies = bookItem.getNumberOfCopies();
            if (genre.equalsIgnoreCase(advanceSearchTerm) || genre.toLowerCase().contains(advanceSearchTerm.toLowerCase()))
                filterBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            else if (publicationDate.equals(advanceSearchTerm))
                filterBooks.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
        }
        printSearchedBook(advanceSearchTerm,filterBooks);
    }
}
