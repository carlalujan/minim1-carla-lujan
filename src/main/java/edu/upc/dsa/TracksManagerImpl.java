package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Reader;
import edu.upc.dsa.models.Book;
import edu.upc.dsa.models.Loan;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class TracksManagerImpl implements TracksManager {
    private static TracksManager instance;
    protected List<Reader> readers;
    protected List<Book> books;
    protected List<Loan> loans;
    protected java.util.Map<String, Integer> bookCopies;
    
    final static Logger logger = Logger.getLogger(TracksManagerImpl.class);

    private TracksManagerImpl() {
        this.readers = new LinkedList<>();
        this.books = new LinkedList<>();
        this.loans = new LinkedList<>();
        this.bookCopies = new java.util.HashMap<>();
        logger.info("LibraryManager initialized");
    }

    public static TracksManager getInstance() {
        if (instance==null) {
            instance = new TracksManagerImpl();
        }
        return instance;
    }

    public int size() {
        int ret = this.readers.size() + this.books.size() + this.loans.size();
        logger.info("Total items: readers=" + this.readers.size() + ", books=" + this.books.size() + ", loans=" + this.loans.size());
        return ret;
    }

    // ===== READER OPERATIONS =====
    @Override
    public Reader addReader(String name, String surname, String dni, String brithday, String placebirth, String address) {
        logger.info("Adding reader: name=" + name + ", surname=" + surname + ", dni=" + dni + ", brithday=" + brithday + ", placebirth=" + placebirth + ", address=" + address);
        
        Reader reader = new Reader(name, surname, dni, brithday, placebirth, address);
        this.readers.add(reader);
        
        logger.info("Reader added successfully: " + reader.getId());
        return reader;
    }

    @Override
    public Reader getReader(String id) throws TrackNotFoundException {
        logger.info("Getting reader: id=" + id);
        
        for (Reader r : this.readers) {
            if (r.getId().equals(id)) {
                logger.info("Reader found: " + r.getId());
                return r;
            }
        }
        
        logger.warn("Reader not found: " + id);
        throw new TrackNotFoundException();
    }

    @Override
    public List<Reader> findAllReaders() {
        logger.info("Finding all readers. Total: " + this.readers.size());
        return this.readers;
    }

    // ===== BOOK OPERATIONS =====
    @Override
    public Book addBook(String title, String author, String isbn, String editorial, String yearpublication, String theme, int copies) {
        logger.info("Adding book: title=" + title + ", author=" + author + ", isbn=" + isbn + ", editorial=" + editorial + ", yearpublication=" + yearpublication + ", theme=" + theme + ", copies=" + copies);
        
        Book book = new Book(title, author, isbn, editorial, yearpublication, theme);
        this.books.add(book);
        this.bookCopies.put(book.getId(), copies);
        
        logger.info("Book added successfully: " + book.getId() + " with " + copies + " copies");
        return book;
    }

    @Override
    public Book getBook(String id) throws TrackNotFoundException {
        logger.info("Getting book: id=" + id);
        
        for (Book b : this.books) {
            if (b.getId().equals(id)) {
                logger.info("Book found: " + b.getId() + ", available copies: " + this.bookCopies.get(id));
                return b;
            }
        }
        
        logger.warn("Book not found: " + id);
        throw new TrackNotFoundException();
    }

    @Override
    public List<Book> findAllBooks() {
        logger.info("Finding all books. Total: " + this.books.size());
        return this.books;
    }

    // ===== LOAN OPERATIONS =====
    @Override
    public Loan loanBook(String idReader, String idBook, String loanDate, String returnDate) throws TrackNotFoundException {
        logger.info("Loaning book: idReader=" + idReader + ", idBook=" + idBook + ", loanDate=" + loanDate + ", returnDate=" + returnDate);
        
        // Validate reader exists
        Reader reader = getReader(idReader);
        
        // Validate book exists
        Book book = getBook(idBook);
        
        // Check available copies
        int availableCopies = this.bookCopies.getOrDefault(idBook, 0);
        if (availableCopies <= 0) {
            logger.error("No copies available for book: " + idBook);
            throw new TrackNotFoundException();
        }
        
        // Create loan
        Loan loan = new Loan(idReader, idBook, loanDate, returnDate);
        this.loans.add(loan);
        this.bookCopies.put(idBook, availableCopies - 1);
        
        logger.info("Loan created successfully: " + loan.getId() + ", remaining copies: " + this.bookCopies.get(idBook));
        return loan;
    }

    @Override
    public List<Loan> getLoansByReader(String idReader) throws TrackNotFoundException {
        logger.info("Getting loans for reader: idReader=" + idReader);
        
        // Validate reader exists
        getReader(idReader);
        
        List<Loan> readerLoans = this.loans.stream()
                .filter(l -> l.idReader().equals(idReader))
                .collect(Collectors.toList());
        
        logger.info("Found " + readerLoans.size() + " loans for reader: " + idReader);
        return readerLoans;
    }

    @Override
    public Loan returnBook(String loanId) throws TrackNotFoundException {
        logger.info("Returning book: loanId=" + loanId);
        
        Loan loan = null;
        for (Loan l : this.loans) {
            if (l.getId().equals(loanId)) {
                loan = l;
                break;
            }
        }
        
        if (loan == null) {
            logger.error("Loan not found: " + loanId);
            throw new TrackNotFoundException();
        }
        
        String idBook = loan.idBook();
        int currentCopies = this.bookCopies.getOrDefault(idBook, 0);
        this.bookCopies.put(idBook, currentCopies + 1);
        this.loans.remove(loan);
        
        logger.info("Loan returned successfully: " + loanId + ", returned copies: " + this.bookCopies.get(idBook));
        return loan;
    }

    @Override
    public List<Loan> findAllLoans() {
        logger.info("Finding all loans. Total: " + this.loans.size());
        return this.loans;
    }

    @Override
    public void clear() {
        logger.info("Clearing all data.");
        this.readers.clear();
        this.books.clear();
        this.loans.clear();
        this.bookCopies.clear();
        logger.info("All data cleared.");
    }
}