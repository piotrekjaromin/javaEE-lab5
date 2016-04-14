import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

/**
 * Created by piotrek on 13.04.16.
 */

class Book {
    private List<String> authors = new ArrayList<String>();
    private Map<String, String> titles = new HashMap<String, String>();
    private String isbn;

    public Book(List<String> authors, Map<String, String> titles, String isbn) {
        this.authors = authors;
        this.titles = titles;
        this.isbn = isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Map<String, String> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    public void addTitle(String language, String title) {
        titles.put(language, title);
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return "authors: " + authors + "\n titles: " + titles + "\n isbn: " + isbn;
    }
}

public class main {
    public static void main(String[] args) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        final Document document= db.newDocument();
        Element rootElement = document.createElement("books");

        Scanner keyboard = new Scanner(System.in);
        String author;
        String language;
        String title;
        String isbn;
        String answer;
        List<Book> books = new ArrayList<Book>();
        while (true) {
            System.out.println("Czy wprowadzic nową książke?");
            if (keyboard.next().equals("nie")) break;

            List<String> authors = new ArrayList<String>();
            Map<String, String> titles = new HashMap<String, String>();

            do {
                System.out.println("Czy dodac autora?");
                answer = keyboard.next();
                if (!answer.equals("nie")) {
                    do {
                        System.out.println("Podaj autora ksiazki");
                        author = keyboard.next();
                    } while (author.equals("\n"));
                    authors.add(author);
                }
            } while (answer.equals("tak"));

            do {
                System.out.println("Czy dodac tytul?");
                answer = keyboard.next();
                if (!answer.equals("nie")) {

                    do {
                        System.out.println("Podaj jezyk tytulu");
                        language = keyboard.next();
                        System.out.println("Podaj tytul w tym jezyku");
                        title = keyboard.next();
                    } while (language.equals("\n") || title.equals("\n"));
                    titles.put(language, title);
                }
            } while (answer.equals("nie"));

            System.out.println("Podaj numer ISBN");
            isbn = keyboard.next();

            System.out.println("Czy dane sa poprawne?");

            if (keyboard.next().equals("tak")) {
                Book book = new Book(authors, titles, isbn);
                books.add(book);
                Element elem = document.createElement("book");
                rootElement.appendChild(elem);
                elem = document.createElement("authors");
                rootElement.appendChild(elem);

                for(String authorFromList : authors) {
                    elem.appendChild(document.createTextNode(authorFromList));
                }
                document.appendChild(elem);
            }

        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = null;
        try {
            t = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT,"yes");
        Source source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("abc.xml"));
        try {
            t.transform(source, result);
        } catch (TransformerException e) {
        }
        System.out.println("done");
    }
}
