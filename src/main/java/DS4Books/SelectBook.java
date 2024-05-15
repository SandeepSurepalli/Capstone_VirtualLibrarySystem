package DS4Books;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class SelectBook {
    List<Book> bookList;

    public SelectBook(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void selectBook(Book selectedBook) {
        long numberOfCopies = selectedBook.getNumberOfCopies();
        if (numberOfCopies == 1) {
            System.out.println("Only 1 book is available");
            System.out.println(selectedBook);
            bookList.removeIf(book -> book.getISBN().equals(selectedBook.getISBN()));
        } else if (numberOfCopies == 0) {
            System.out.println("Out of Stock");
            bookList.removeIf(book -> book.getISBN().equals(selectedBook.getISBN()));
        } else if (numberOfCopies > 1) {
            System.out.println("Available Copies: " + numberOfCopies);
            System.out.println(selectedBook);
            for (Book bookItem : bookList) {
                if (bookItem.getISBN().equals(selectedBook.getISBN())) {
                    bookItem.setNumberOfCopies(numberOfCopies - 1);
                }
            }
        }
    }

    public void searchBook() {
        System.out.println("Select book from below list" + "\n" + "---------------------------");
        for (Book bookItem : bookList){
            System.out.println(bookItem);
        }
        String bookISBN = bookISBNSearch();
        Book searchedBook = bookList.stream().filter(book -> book.getISBN().equals(bookISBN)).findFirst().orElse(null);
        selectBook(searchedBook);
    }

    public static String bookISBNSearch() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book ISBN number to select book" + "\n");
        String searchTerm = sc.nextLine();
        return searchTerm;
    }
}
