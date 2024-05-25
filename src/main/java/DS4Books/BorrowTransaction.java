package DS4Books;

import java.util.Date;

public class BorrowTransaction {
    private String userID;
    private String bookISBN;
    private Date borrowingDate;
    private Date returnDate;
    private Date dueDate;

    public BorrowTransaction(String userID, String bookISBN) {
        this.userID = userID;
        this.bookISBN = bookISBN;
        this.borrowingDate = new Date();
        this.returnDate = null;
    }

    public BorrowTransaction(String userID, String bookISBN, Date returnDate) {
        this.userID = userID;
        this.bookISBN = bookISBN;
        this.borrowingDate = new Date();
        this.returnDate = returnDate;
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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
