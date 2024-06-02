package DS4Books;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BookManager {

    public static List<Book> bookList = new ArrayList<>();
    public static JSONArray booksData;
    public static int noOfDuplicateBooks = 0;
    public static String fileType = "";
    public static int[] Books = new int[3];
    static List<BorrowTransaction> borrowTransactions = new ArrayList<>();
    private static Set<String> adminIds = new HashSet<>();

    static {
        // Read admin IDs from a file, database, or hardcode them here (for simplicity)
        adminIds = new HashSet<>(Arrays.asList("admin1", "admin2"));
    }

    public BookManager(String filePath) {
        fileType = com.google.common.io.Files.getFileExtension(filePath);
        if (fileType.equalsIgnoreCase("json")) readJSONFile(filePath);
        else if (fileType.equalsIgnoreCase("csv")) readCSVFile(filePath);
        else if (fileType.equalsIgnoreCase("xml")) readXMLFile(filePath);
        else System.out.println("Cannot read data from " + fileType + " file type");
    }

    public static void readJSONFile(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader(filePath);
            booksData = (JSONArray) parser.parse(file);
            Books[0] = booksData.size();
            for (Object o : booksData) {
                JSONObject bookObject = (JSONObject) o;
                String title = (String) bookObject.get("title");
                String author = (String) bookObject.get("author");
                String ISBN = (String) bookObject.get("ISBN");
                String genre = (String) bookObject.get("genre");
                String publicationDate = (String) bookObject.get("publicationDate");
                long numberOfCopies = (long) bookObject.get("numberOfCopies");
                addBookToLibrary(title, author, ISBN, genre, publicationDate, numberOfCopies);
            }
            System.out.println("Welcome to virtual library");
        } catch (Exception e) {
            System.err.println("Error parsing the file: " + e.getMessage());
        }
    }

    public static boolean isISBNUnique(String ISBN, List<Book> bookList) {
        for (Book bookItem : bookList) {
            if (bookItem.getISBN().equals(ISBN)) return false;
        }
        return true;
    }

    public static void addBookToLibrary(String title, String author, String ISBN, String genre, String publicationDate, long numberOfCopies) {

        if (isISBNUnique(ISBN, bookList)) {
            bookList.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
        } else {
            noOfDuplicateBooks++;
        }
    }

    public static void readCSVFile(String filePath) {
        String line = "";
        String splitBy = ",";
        int counter = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] bookDetails = line.split(splitBy);
                if (bookDetails[0].equalsIgnoreCase("title")) continue;
                counter++;
                String title = bookDetails[0];
                String author = bookDetails[1];
                String ISBN = bookDetails[2];
                String genre = bookDetails[3];
                String publicationDate = bookDetails[4];
                long numberOfCopies = Long.parseLong(bookDetails[5]);
                addBookToLibrary(title, author, ISBN, genre, publicationDate, numberOfCopies);
            }
            Books[0] = counter;
            System.out.println("Welcome to virtual library");
        } catch (IOException e) {
            System.err.println("Error parsing the file: " + e.getMessage());
        }
    }

    public static void readXMLFile(String filePath) {
        try {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("book");
            Books[0] = nList.getLength();
            for (int temp = 0; temp < Books[0]; temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    String ISBN = eElement.getElementsByTagName("ISBN").item(0).getTextContent();
                    String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
                    String publicationDate = eElement.getElementsByTagName("publicationDate").item(0).getTextContent();
                    long numberOfCopies = Long.parseLong(eElement.getElementsByTagName("numberOfCopies").item(0).getTextContent());
                    addBookToLibrary(title, author, ISBN, genre, publicationDate, numberOfCopies);
                }
            }
            System.out.println("Welcome to virtual library");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void Books() {
        Books[1] = Books[0] - noOfDuplicateBooks;
        Books[2] = noOfDuplicateBooks;
        System.out.println(Books[1] + " are added to library");
        System.out.println(Books[2] + " are not added due to duplicate");
    }

    public static void addBorrowTransaction(BorrowTransaction transaction) {
        borrowTransactions.add(transaction);
        System.out.println("Transaction logged: " + transaction);
    }

    public static void viewBorrowTransactions(String adminId) {
        if (isAdmin(adminId)) {
            if (borrowTransactions.isEmpty()) {
                System.out.println("No borrowing transactions found.");
            } else {
                for (BorrowTransaction transaction : borrowTransactions) {
                    System.out.println(transaction);
                }
            }
        } else {
            System.out.println("Access denied: Only admins can view the borrowing transactions.");
        }
    }
    private static boolean isAdmin(String userId) {
        return adminIds.contains(userId);
    }

    public List<Book> getBookList() {
        if (bookList.isEmpty()) {
            System.out.println("Library is not loaded");
            return null;
        }
        return bookList;
    }

    public static boolean checkOverdueBooks(String userId) {
        boolean hasOverdueBooks = false;
        for (BorrowTransaction transaction : borrowTransactions) {
            if (transaction.getUserID().equals(userId) && transaction.getReturnDate() == null) {
                if (transaction.getDueDate().before(new Date())) {
                    hasOverdueBooks = true;
                    break;
                }
            }
        }
        return hasOverdueBooks;
    }

    public static void notifyOverdueBooks(String userId) {
        List<BorrowTransaction> overdueTransactions = borrowTransactions.stream()
                .filter(transaction -> transaction.getUserID().equals(userId) && transaction.getReturnDate() == null && transaction.getDueDate().before(new Date()))
                .collect(Collectors.toList());

        if (!overdueTransactions.isEmpty()) {
            System.out.println("You have the following overdue books:");
            for (BorrowTransaction transaction : overdueTransactions) {
                // Fetch the book details using the ISBN from the transaction
                Book overdueBook = bookList.stream()
                        .filter(book -> book.getISBN().equals(transaction.getBookISBN()))
                        .findFirst()
                        .orElse(null);

                if (overdueBook != null) {
                    System.out.println("- " + overdueBook.getTitle() + " (ISBN: " + overdueBook.getISBN() + ")");
                }
            }
        }
    }

    public static void flagOverdueBooks(String userId) {
        List<BorrowTransaction> overdueBooks = borrowTransactions.stream()
                .filter(transaction -> transaction.getUserID().equals(userId) && transaction.getReturnDate() == null && transaction.getDueDate().before(new Date()))
                .collect(Collectors.toList());

        if (!overdueBooks.isEmpty()) {
            System.out.println("** Notice: You have overdue books. **");
            for (BorrowTransaction transaction : overdueBooks) {
                System.out.println("Book ISBN: " + transaction.getBookISBN() + " is overdue.");
            }
        }
    }

    public static void alertOnOverdueBooks(String userId) {
        if (checkOverdueBooks(userId)) {
            notifyOverdueBooks(userId);
        }
    }

    public static void displayLibraryStatistics() {
        int totalBooks = bookList.size();
        long totalCopies = bookList.stream().mapToLong(Book::getNumberOfCopies).sum();
        long inStockBooks = bookList.stream().filter(book -> book.getNumberOfCopies() > 0).count();
        long outOfStockBooks = totalBooks - inStockBooks;
        int totalBorrowTransactions = borrowTransactions.size();
        long overdueTransactions = borrowTransactions.stream().filter(BorrowTransaction::isOverdue).count();

        System.out.println("Library Statistics Overview");
        System.out.println("---------------------------");
        System.out.printf("Total number of books: %d%n", totalBooks);
        System.out.printf("Total number of copies: %d%n", totalCopies);
        System.out.printf("Books in stock: %d%n", inStockBooks);
        System.out.printf("Books out of stock: %d%n", outOfStockBooks);
        System.out.printf("Total borrow transactions: %d%n", totalBorrowTransactions);
        System.out.printf("Overdue transactions: %d%n", overdueTransactions);
    }

    public static void displayTotalNumberOfBooks() {
        int totalBooks = bookList.size();
        System.out.printf("Total number of books in the library: %d%n", totalBooks);
    }

    public static void displayNumberOfBorrowedBooks() {
        long borrowedCount = bookList.stream().filter(book -> book.isBorrowed()).count();
        System.out.printf("Number of currently borrowed books: %d%n", borrowedCount);
    }

    public static void displayBorrowedBooksList() {
        List<Book> borrowedBooks = bookList.stream().filter(Book::isBorrowed).collect(Collectors.toList());
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently borrowed.");
        } else {
            System.out.println("List of currently borrowed books:");
            for (int i = 0; i < borrowedBooks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, borrowedBooks.get(i).getTitle());
            }
        }
    }

    public List<Book> getTopBorrowedBooks(int count) {

        if (count <= 0) {
            throw new IllegalArgumentException("Count must be a positive integer");
        }

        // HashMap to store ISBNs and their borrow counts
        HashMap<String, Integer> borrowCountMap = new HashMap<>();

        // Iterate through all borrow transactions
        for (BorrowTransaction transaction : borrowTransactions) {
            String isbn = transaction.getBookISBN();
            int currentCount = borrowCountMap.getOrDefault(isbn, 0);
            borrowCountMap.put(isbn, currentCount + 1);
        }

        // Sort the HashMap by borrow count (descending order) using Apache Commons Collections
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(borrowCountMap.entrySet());
        Collections.sort(sortedList, (entry1, entry2) -> entry2.getValue() - entry1.getValue());

        // Extract top 'count' entries
        List<Book> topBooks = new ArrayList<>();
        int extractedCount = 0;
        for (Map.Entry<String, Integer> entry : sortedList) {
            Book book = bookList.stream().filter(searchBook -> searchBook.getISBN().equals(entry.getKey())).findFirst().orElse(null); // Assuming a method to get book by ISBN
            if (book != null) {
                topBooks.add(book);
                extractedCount++;
            }
            if (extractedCount >= count) {
                break;
            }
        }

        return topBooks;
    }

    public static void displayTopBorrowedBooks() {
        Map<String, Long> borrowCounts = borrowTransactions.stream()
                .collect(Collectors.groupingBy(BorrowTransaction::getBookISBN, Collectors.counting()));

        List<Book> sortedBooks = bookList.stream()
                .sorted(Comparator.comparing(book -> borrowCounts.getOrDefault(book.getISBN(), 0L), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        sortedBooks.forEach(book -> System.out.println(book.getTitle() + " - Borrowed: " + borrowCounts.getOrDefault(book.getISBN(), 0L) + " times"));
    }
}
