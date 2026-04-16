package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Reader;
import edu.upc.dsa.models.Book;
import edu.upc.dsa.models.Loan;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TracksManagerTest {
    TracksManager tm;

    @Before
    public void setUp() {
        this.tm = TracksManagerImpl.getInstance();
        
        // Add readers
        this.tm.addReader("Juan", "García", "12345678A", "1990-01-15", "Barcelona", "Calle Principal 123");
        this.tm.addReader("María", "López", "87654321B", "1985-05-20", "Madrid", "Avenida Central 456");
        
        // Add books
        this.tm.addBook("Don Quixote", "Miguel de Cervantes", "978-8432206969", "Penguin", "2010", "Fiction", 3);
        this.tm.addBook("El Código da Vinci", "Dan Brown", "978-8408069072", "Planeta", "2009", "Mystery", 2);
        this.tm.addBook("La Casa de Espíritus", "Isabel Allende", "978-8497402538", "Planeta", "2008", "Fiction", 1);
    }

    @After
    public void tearDown() {
        // Singleton - clear after each test
        this.tm.clear();
    }

    @Test
    public void addReaderTest() throws TrackNotFoundException {
        List<Reader> readers = this.tm.findAllReaders();
        int initialSize = readers.size();
        
        Reader newReader = this.tm.addReader("Carlos", "Martínez", "11111111C", "1995-03-10", "Valencia", "Plaza Mayor 789");
        
        Assert.assertNotNull(newReader);
        Assert.assertEquals(initialSize + 1, this.tm.findAllReaders().size());
    }

    @Test
    public void getReaderTest() throws TrackNotFoundException {
        List<Reader> readers = this.tm.findAllReaders();
        String readerId = readers.get(0).getId();
        
        Reader reader = this.tm.getReader(readerId);
        Assert.assertNotNull(reader);
        Assert.assertEquals("Juan", reader.getName());
        Assert.assertEquals("García", reader.getSurname());
    }

    @Test
    public void addBookTest() throws TrackNotFoundException {
        List<Book> books = this.tm.findAllBooks();
        int initialSize = books.size();
        
        Book newBook = this.tm.addBook("Cien Años de Soledad", "Gabriel García Márquez", "978-8497402033", "Planeta", "2007", "Fiction", 5);
        
        Assert.assertNotNull(newBook);
        Assert.assertEquals(initialSize + 1, this.tm.findAllBooks().size());
    }

    @Test
    public void getBookTest() throws TrackNotFoundException {
        List<Book> books = this.tm.findAllBooks();
        String bookId = books.get(0).getId();
        
        Book book = this.tm.getBook(bookId);
        Assert.assertNotNull(book);
        Assert.assertEquals("Don Quixote", book.getTitle());
    }

    @Test
    public void loanBookTest() throws TrackNotFoundException {
        List<Reader> readers = this.tm.findAllReaders();
        List<Book> books = this.tm.findAllBooks();
        
        String readerId = readers.get(0).getId();
        String bookId = books.get(0).getId();
        
        Loan loan = this.tm.loanBook(readerId, bookId, "2024-01-01", "2024-01-15");
        
        Assert.assertNotNull(loan);
        Assert.assertEquals(readerId, loan.idReader());
        Assert.assertEquals(bookId, loan.idBook());
    }

    @Test
    public void getLoansByReaderTest() throws TrackNotFoundException {
        List<Reader> readers = this.tm.findAllReaders();
        List<Book> books = this.tm.findAllBooks();
        
        String readerId = readers.get(0).getId();
        String bookId1 = books.get(0).getId();
        String bookId2 = books.get(1).getId();
        
        this.tm.loanBook(readerId, bookId1, "2024-01-01", "2024-01-15");
        this.tm.loanBook(readerId, bookId2, "2024-01-05", "2024-01-20");
        
        List<Loan> readerLoans = this.tm.getLoansByReader(readerId);
        Assert.assertEquals(2, readerLoans.size());
    }

    @Test
    public void returnBookTest() throws TrackNotFoundException {
        List<Reader> readers = this.tm.findAllReaders();
        List<Book> books = this.tm.findAllBooks();
        
        String readerId = readers.get(0).getId();
        String bookId = books.get(0).getId();
        
        Loan loan = this.tm.loanBook(readerId, bookId, "2024-01-01", "2024-01-15");
        Assert.assertNotNull(loan);
        
        Loan returnedLoan = this.tm.returnBook(loan.getId());
        Assert.assertNotNull(returnedLoan);
        Assert.assertEquals(loan.getId(), returnedLoan.getId());
    }
}


        Assert.assertThrows(TrackNotFoundException.class, () ->
                this.tm.getTrack2("XXXXXXX"));

    }

    @Test
    public void getTracksTest() {
        Assert.assertEquals(3, tm.size());
        List<Track> tracks  = tm.findAll();

        Track t = tracks.get(0);
        Assert.assertEquals("La Barbacoa", t.getTitle());
        Assert.assertEquals("Georgie Dann", t.getSinger());

        t = tracks.get(1);
        Assert.assertEquals("Despacito", t.getTitle());
        Assert.assertEquals("Luis Fonsi", t.getSinger());

        t = tracks.get(2);
        Assert.assertEquals("Ent3r S4ndm4n", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());

        Assert.assertEquals(3, tm.size());

    }

    @Test
    public void updateTrackTest() {
        Assert.assertEquals(3, tm.size());
        Track t = this.tm.getTrack("T3");
        Assert.assertEquals("Ent3r S4ndm4n", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());

        t.setTitle("Enter Sandman");
        this.tm.updateTrack(t);

        t = this.tm.getTrack("T3");
        Assert.assertEquals("Enter Sandman", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());
    }


    @Test
    public void deleteTrackTest() {
        Assert.assertEquals(3, tm.size());
        this.tm.deleteTrack("T3");
        Assert.assertEquals(2, tm.size());

        Assert.assertThrows(TrackNotFoundException.class, () ->
                this.tm.getTrack2("T3"));

    }
}
