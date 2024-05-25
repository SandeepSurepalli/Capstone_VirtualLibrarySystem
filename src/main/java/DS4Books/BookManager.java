package DS4Books;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static List<BorrowTransaction> borrowTransactions = new ArrayList<>();


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

    public static void addBorrowTransaction(String userID, String bookISBN, Date borrowingDate) {
        borrowTransactions.add(new BorrowTransaction(userID, bookISBN, borrowingDate));
    }

    public static void viewBorrowTransactions() {
        if (borrowTransactions.isEmpty()) {
            System.out.println("No borrowing transactions found.");
        } else {
            for (BorrowTransaction transaction : borrowTransactions) {
                System.out.println(transaction);
            }
        }
    }

    public List<Book> getBookList() {
        if (bookList.isEmpty()) {
            System.out.println("Library is not loaded");
            return null;
        }
        return bookList;
    }

}
