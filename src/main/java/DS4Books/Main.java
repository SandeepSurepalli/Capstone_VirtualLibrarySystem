package DS4Books;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting from Here ");
        String filePath = "src//main//resources//Books.csv";
        BookManager BM = new BookManager(filePath);
        BM.Books();
    }



}
