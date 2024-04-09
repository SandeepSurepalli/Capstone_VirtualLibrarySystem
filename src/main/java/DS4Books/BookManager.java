package DS4Books;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BookManager {

    public static List<Book> bookList = new ArrayList<>();
    public static JSONArray booksData;
    public static int noOfDuplicateBooks = 0;

    public BookManager(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader(filePath);
            booksData = (JSONArray) parser.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //
    public static boolean isISBNUnique(String ISBN, List<Book> bookList) {
        for (Book bookItem : bookList) {
            if (bookItem.getISBN().equals(ISBN)) return false;
        }
        return true;
    }

    public static int[] aadBookToLibrary() {
        int[] Books = new int[3];
        Books[0] = booksData.size();
        for (Object o : booksData) {
            JSONObject bookObject = (JSONObject) o;
            String title = (String) bookObject.get("title");
            String author = (String) bookObject.get("author");
            String ISBN = (String) bookObject.get("ISBN");
            String genre = (String) bookObject.get("genre");
            String publicationDate = (String) bookObject.get("publicationDate");
            long numberOfCopies = (long) bookObject.get("numberOfCopies");

            if (isISBNUnique(ISBN, bookList)) {
                bookList.add(new Book(title, author, ISBN, genre, publicationDate, numberOfCopies));
            } else {
                noOfDuplicateBooks++;
            }
        }
        Books[1] = Books[0] - noOfDuplicateBooks;
        Books[2] = noOfDuplicateBooks;
        return Books;
    }

}
