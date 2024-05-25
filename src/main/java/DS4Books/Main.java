package DS4Books;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting from Here ");
        String filePath = "src//main//resources//Books.csv";
        BookManager BM = new BookManager(filePath);
        List<Book> bookList = BM.getBookList();
        SearchBook sb = new SearchBook(bookList);
        sb.searchBookInLibrary();
        sb.selectBook();
        sb.borrowBook();
        System.out.println("Enter admin ID to view borrowing transactions: ");
        try (Scanner sc = new Scanner(System.in)) {
            String adminId = sc.next();
            BM.viewBorrowTransactions(adminId);
        }
    }
}
