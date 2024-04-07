package DS4Books;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private String publicationDate;
    private int numberOfCopies;

    public Book(String bookTitle, String bookAuthor, String bookISBN, String bookGenre, String bookPublicationDate, int bookCopies) {
        this.title = bookTitle;
        this.author = bookAuthor;
        this.ISBN = bookISBN;
        this.genre = bookGenre;
        this.publicationDate = bookPublicationDate;
        this.numberOfCopies = bookCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public static boolean isISBNUnique(String ISBN, List<Book> bookList) {
        for (Book bookItem : bookList) {
            if (bookItem.getISBN().equals(ISBN))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Java Programming", "John Doe", "9780321776823", "Programming", "15/05/2020", 100));
        if (isISBNUnique("9780321776823", bookList)) {
            bookList.add(new Book("Python Programming", "Jane Smith", "9781491919538", "Programming", "21/08/1996", 50));
            System.out.println("New book is added to library");
        } else {
            System.out.println("Book is already exist in library");
        }
    }
}
