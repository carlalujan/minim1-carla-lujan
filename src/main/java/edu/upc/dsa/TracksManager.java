package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Reader;
import edu.upc.dsa.models.Book;
import edu.upc.dsa.models.Loan;

import java.util.List;

public interface TracksManager {

    // Reader operations
    public Reader addReader(String name, String surname, String dni, String brithday, String placebirth, String address);
    public Reader getReader(String id) throws TrackNotFoundException;
    public List<Reader> findAllReaders();
    
    // Book operations
    public Book addBook(String title, String author, String isbn, String editorial, String yearpublication, String theme, int copies);
    public Book getBook(String id) throws TrackNotFoundException;
    public List<Book> findAllBooks();
    
    // Loan operations
    public Loan loanBook(String idReader, String idBook, String loanDate, String returnDate) throws TrackNotFoundException;
    public List<Loan> getLoansByReader(String idReader) throws TrackNotFoundException;
    public Loan returnBook(String loanId) throws TrackNotFoundException;
    public List<Loan> findAllLoans();

    public void clear();
    public int size();
}
