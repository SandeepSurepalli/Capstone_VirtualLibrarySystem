package qualitykiosk;

import DS4Books.Book;
import DS4Books.BookManager;
import DS4Books.SearchBook;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
        /*System.out.println("Starting from Here ");
        String filePath = "src//main//resources//Books.csv";
        BookManager BM = new BookManager(filePath);
        List<Book> bookList = BM.getBookList();
        DS4Books.SearchBook sb = new SearchBook(bookList);
        sb.searchBookInLibrary();
        List<Book> searchedBooksList = sb.getSearchedBook().isEmpty() ? bookList : sb.getSearchedBook();
        SelectBook selBook = new SelectBook(searchedBooksList);
        selBook.searchBook();*/
    }
}