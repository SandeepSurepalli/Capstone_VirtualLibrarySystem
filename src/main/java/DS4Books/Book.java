package DS4Books;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private String publicationDate;
    private int numberOfCopies;

   public Book(String bookTitle,String bookAuthor,String bookISBN,String bookGenre,String bookPublicationDate, int bookCopies){
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

    public static boolean isISBNUnique(String ISBN){
    Book book = new Book("Java Programming", "John Doe", "978-0321776823", "Programming", "15/05/2020", 100);
       if(book.getISBN().equalsIgnoreCase(ISBN))
          return false;
       else
          return true;
    }

    public static void main(String[] args) {
        System.out.println(isISBNUnique("978-0321576823"));
    }
}
