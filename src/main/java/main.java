import org.w3c.dom.Attr;
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
        document.appendChild(rootElement);

        Scanner keyboard = new Scanner(System.in);
        String author;
        String language;
        String title;
        String isbn;
        String answer;

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
            } while (answer.equals("tak"));

            System.out.println("Podaj numer ISBN");
            isbn = keyboard.next();

            System.out.println("Czy dane sa poprawne?");

            if (keyboard.next().equals("tak")) {

                Element bookElem = document.createElement("book");

                rootElement.appendChild(bookElem);
                Element authorsElem = document.createElement("authors");
                bookElem.appendChild(authorsElem);

                for(String authorFromList : authors) {
                    Element authorElem = document.createElement("author");
                    authorsElem.appendChild(authorElem);
                    authorElem.appendChild(document.createTextNode(authorFromList));
                    authorsElem.appendChild(authorElem);
                }

                Element titlesElem = document.createElement("titles");
                bookElem.appendChild(titlesElem);

                for(Map.Entry<String, String> entry: titles.entrySet()) {
                    Element titleElem = document.createElement("title");
                    Attr attr = document.createAttribute("lang");
                    attr.setValue(entry.getKey());
                    titleElem.setAttributeNode(attr);
                    titleElem.appendChild(document.createTextNode(entry.getValue()));
                    titlesElem.appendChild(titleElem);
                }
                Element isbnElem = document.createElement("ISBN");
                bookElem.appendChild(isbnElem);
                isbnElem.appendChild(document.createTextNode(isbn));
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
