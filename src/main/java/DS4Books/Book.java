package DS4Books;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private String publicationDate;
    private long numberOfCopies;
    private String status;
    private boolean borrowed;
    private Author author1;


    public Book(String bookTitle, String bookAuthor, String bookISBN, String bookGenre, String bookPublicationDate, long bookCopies) {
        this.title = bookTitle;
        this.author = bookAuthor;
        this.ISBN = bookISBN;
        this.genre = bookGenre;
        this.publicationDate = bookPublicationDate;
        this.numberOfCopies = bookCopies;
        updateStatus();
        this.borrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author1;
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

    public long getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNumberOfCopies(long numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String toString() {
        return "Title: " + getTitle() + "\n" + "Author: " + getAuthor() + "\n" + "ISBN: " + getISBN() + "\n" + "Genre: " + getGenre() + "\n" + "Publication Date: " + getPublicationDate() + "\n" + "Number of Copies: " + getNumberOfCopies() + "\n" + "--------------------------------------";
    }

    public void updateStatus() {
        if (numberOfCopies > 0) {
            status = "In Stock";
        } else {
            status = "Out of Stock";
        }
    }

    public String getStatus() {
        return status;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
