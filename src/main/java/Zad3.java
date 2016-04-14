import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.*;

/**
 * Created by piotr on 14.04.16.
 */
@XmlRootElement(name = "books")
class Books {
    List<Book> books = new ArrayList<>();

    public Books(){}

    public List<Book> getBooks() {
        return books;
    }

    @XmlElement(name="book")
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

class Book {
    private List<String> authors = new ArrayList<>();
    private Map<String, String> titles = new HashMap<>();
    private String isbn;

    public Book() {
    }


    public List<String> getAuthors() {
        return authors;
    }
    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author", required = true)
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


    public Map<String, String> getTitles() {
        return titles;
    }

    @XmlElementWrapper(name = "titles")
    @XmlElement(name = "title", required = true)
    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }


    public String getIsbn() {
        return isbn;
    }

    @XmlElement(name = "isbn", required = true)
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

public class Zad3 {
    public static void main(String[] args) throws JAXBException {
        Scanner keyboard = new Scanner(System.in);
        String author;
        String language;
        String title;
        String isbn;
        String answer;
        List<Book> listOfBooks = new ArrayList<>();
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
                Book book = new Book();
                book.setAuthors(authors);
                book.setTitles(titles);
                book.setIsbn(isbn);
                Books books = new Books();
                listOfBooks.add(book);
                books.setBooks(listOfBooks);

                File file = new File("file.xsd");
                JAXBContext jaxbContext = JAXBContext.newInstance(Books.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(books, file);
                jaxbMarshaller.marshal(books, System.out);

            }
        }

        System.out.println("done");
    }
}
