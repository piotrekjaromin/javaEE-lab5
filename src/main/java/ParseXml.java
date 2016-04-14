import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by piotr on 14.04.16.
 */
public class ParseXml {
    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File("abc.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList books = doc.getElementsByTagName("book");

        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
            NodeList titles = book.getElementsByTagName("title");

            for (int j = 0; j < titles.getLength(); j++) {
                Element title = (Element) titles.item(j);
                System.out.println("tytul w jezyku " + title.getAttribute("lang") + ": " + title.getTextContent());
            }

            NodeList authors = book.getElementsByTagName("author");
            System.out.print("autorzy: ");
            for (int j = 0; j < authors.getLength(); j++) {
                Element author = (Element) authors.item(j);
                System.out.print(author.getTextContent() + "; ");
            }
            System.out.println("");

            System.out.println("ISBN: " + ((Element) book.getElementsByTagName("ISBN").item(0)).getTextContent());
            System.out.println("");
        }
    }
}

