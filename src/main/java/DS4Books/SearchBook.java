package DS4Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchBook {
    public List<Book> matchedBooks = new ArrayList<Book>();
    List<Book> bookList;
    public List<Book> filterBooks = new ArrayList<>();

    public SearchBook(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getBookDetailsFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book title, ISBN number or author name to search in virtual library" + "\n");
        String searchTerm = sc.nextLine();
        return searchTerm;
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
            if (/*booksList.size() == 1*/false) {
                System.out.println("Only 1 book found in library." + "\n" + booksList.get(0) + "\n" + "Do yo want to select it ? Y/N");
                if (sc.next().equalsIgnoreCase("y")) selectBook(booksList.get(0));

            } else {
                System.out.println(booksList.size() + " books are found in library which are matching the search " + searchTerm);
                System.out.println("--------------------------------------");
                for (Book bookItem : booksList) {
                    System.out.println(bookItem);
                }
//                System.out.println("Want to filter books further ? Y/N");
                if (/*sc.next().equalsIgnoreCase("y")*/false) {
                    String advanceSearchTerm = advanceBookSearch();
                    searchBookInMatchedResults(advanceSearchTerm);
                } else {
//                    System.out.println("Do you want to select any book from searched books ? Y/N");
                    if (/*sc.next().equalsIgnoreCase("y")*/true) {
                        String bookISBN = bookISBNSearch();
                        Book searchedBook;
                        if (filterBooks.isEmpty())
                            searchedBook = matchedBooks.stream().filter(book -> book.getISBN().equals(bookISBN)).findFirst().orElse(null);
                        else
                            searchedBook = filterBooks.stream().filter(book -> book.getISBN().equals(bookISBN)).findFirst().orElse(null);
                        selectBook(searchedBook);
                    }
                }
            }
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
        printSearchedBook(advanceSearchTerm, filterBooks);
    }

    /*public List<Book> getSearchedBook() {
        if (filterBooks.isEmpty()) return matchedBooks.isEmpty() ? bookList : matchedBooks;
        else return null;
    }*/

   /* public void selectBook(Book selectedBook) {
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
            for (Book bookItem : bookList) {
                if (bookItem.getISBN().equals(selectedBook.getISBN())) {
                    bookItem.setNumberOfCopies(numberOfCopies - 1);
                }
            }
            System.out.println(selectedBook);
        }
    }*/

    public void selectBook(Book selectedBook) {
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
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the book number for more details:" + "\n");
        String searchTerm = sc.nextLine();
        return searchTerm;
    }
}
