package DS4Books;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting from Here ");
        String filePath = "src//main//resources//Books.csv";
        BookManager BM = new BookManager(filePath);
        List<Book> bookList = BM.getBookList();
        SearchBook sb = new SearchBook(bookList);
        sb.searchBookInLibrary();
        /*List<Book> searchedBooksList = sb.getSearchedBook().isEmpty() ? bookList : sb.getSearchedBook();
        SelectBook selBook = new SelectBook(searchedBooksList);
        selBook.searchBook();*/
    }
}
