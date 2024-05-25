package DS4Books;

import java.util.Date;

public class BorrowTransaction {
    private String userID;
    private String bookISBN;
    private Date borrowingDate;

    public BorrowTransaction(String userID, String bookISBN) {
        this.userID = userID;
        this.bookISBN = bookISBN;
        this.borrowingDate = new Date();
    }

    public String getUserID() {
        return userID;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    @Override
    public String toString() {
        return "User ID: " + userID + ", Book ISBN: " + bookISBN + ", Borrowing Date: " + borrowingDate;
    }
}
