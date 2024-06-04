package DS4Books;

import java.util.Date;

public class BorrowTransaction {
    private String userID;
    private String bookISBN;
    private Date borrowingDate;
    private Date returnDate;
    private Date dueDate;
    private Book book;
    public static int borrowCount = 0;
    private String genre;

    public BorrowTransaction(String userID, String bookISBN) {
        this.userID = userID;
        this.bookISBN = bookISBN;
        this.borrowingDate = new Date();
        this.returnDate = null;
        this.dueDate = new Date(this.borrowingDate.getTime() + (14 * 24 * 60 * 60 * 1000));
    }

    public BorrowTransaction(String userID, String bookISBN, Date returnDate) {
        this.userID = userID;
        this.bookISBN = bookISBN;
        this.borrowingDate = new Date();
        this.returnDate = returnDate;
    }

    public BorrowTransaction(String userID, Book book, Date borrowDate, Date dueDate) {
        this.userID = userID;
        this.book = book;
        this.borrowingDate = borrowDate;
        this.dueDate = dueDate;
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

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isOverdue() {
        Date currentDate = new Date();
        return currentDate.after(dueDate);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
