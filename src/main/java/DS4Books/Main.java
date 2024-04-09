package DS4Books;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting from Here ");
        String filePath = "src//main//resources//Books.json";
        BookManager BM = new BookManager(filePath);
        int[] Books = BM.aadBookToLibrary();
        System.out.println(Books[1]+" are added to library");
        System.out.println(Books[2]+" are not added due to duplicate");
    }

}
