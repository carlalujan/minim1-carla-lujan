package edu.upc.dsa.services;

import edu.upc.dsa.TracksManager;
import edu.upc.dsa.TracksManagerImpl;
import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Reader;
import edu.upc.dsa.models.Book;
import edu.upc.dsa.models.Loan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/library", description = "Endpoint to Library Service")
@Path("/library")
public class TracksService {

    private TracksManager tm;
    final static Logger logger = Logger.getLogger(TracksService.class);

    public TracksService() {
        this.tm = TracksManagerImpl.getInstance();
        
        // Initialize sample data if empty
        if (tm.size() == 0) {
            logger.info("Initializing library with sample data");
            
            this.tm.addReader("Juan", "García López", "12345678A", "1990-01-15", "Barcelona", "Calle Principal 123");
            this.tm.addReader("María", "Rodríguez Pérez", "87654321B", "1985-05-20", "Madrid", "Avenida Central 456");
            
            this.tm.addBook("Don Quixote", "Miguel de Cervantes", "978-8432206969", "Penguin", "2010", "Fiction", 5);
            this.tm.addBook("El Código da Vinci", "Dan Brown", "978-8408069072", "Planeta", "2009", "Mystery", 3);
            this.tm.addBook("La Casa de Espíritus", "Isabel Allende", "978-8497402538", "Planeta", "2008", "Fiction", 2);
        }
    }

    // ===== READER ENDPOINTS =====
    
    @GET
    @ApiOperation(value = "Get all readers", notes = "Returns a list of all readers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Reader.class, responseContainer = "List"),
    })
    @Path("/readers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReaders() {
        logger.info("GET /readers");
        List<Reader> readers = this.tm.findAllReaders();
        GenericEntity<List<Reader>> entity = new GenericEntity<List<Reader>>(readers) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get a specific reader", notes = "Returns reader by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Reader.class),
            @ApiResponse(code = 404, message = "Reader not found")
    })
    @Path("/readers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReader(@PathParam("id") String id) {
        logger.info("GET /readers/" + id);
        try {
            Reader reader = this.tm.getReader(id);
            return Response.status(200).entity(reader).build();
        } catch (TrackNotFoundException e) {
            logger.warn("Reader not found: " + id);
            return Response.status(404).entity("Reader not found").build();
        }
    }

    @POST
    @ApiOperation(value = "Add a new reader", notes = "Creates a new reader")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reader created", response = Reader.class),
            @ApiResponse(code = 400, message = "Invalid reader data")
    })
    @Path("/readers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReader(Reader reader) {
        logger.info("POST /readers - Adding new reader");
        if (reader.getName() == null || reader.getSurname() == null) {
            logger.warn("Invalid reader data");
            return Response.status(400).entity("Name and surname are required").build();
        }
        Reader newReader = this.tm.addReader(reader.getName(), reader.getSurname(), reader.getDni(), 
                                             reader.getBrithday(), reader.getPlacebirth(), reader.getAddress());
        return Response.status(201).entity(newReader).build();
    }

    // ===== BOOK ENDPOINTS =====
    
    @GET
    @ApiOperation(value = "Get all books", notes = "Returns a list of all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Book.class, responseContainer = "List"),
    })
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        logger.info("GET /books");
        List<Book> books = this.tm.findAllBooks();
        GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(books) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get a specific book", notes = "Returns book by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") String id) {
        logger.info("GET /books/" + id);
        try {
            Book book = this.tm.getBook(id);
            return Response.status(200).entity(book).build();
        } catch (TrackNotFoundException e) {
            logger.warn("Book not found: " + id);
            return Response.status(404).entity("Book not found").build();
        }
    }

    @POST
    @ApiOperation(value = "Add a new book", notes = "Creates a new book with specified copies")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book created", response = Book.class),
            @ApiResponse(code = 400, message = "Invalid book data")
    })
    @Path("/books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        logger.info("POST /books - Adding new book");
        if (book.getTitle() == null || book.getAuthor() == null) {
            logger.warn("Invalid book data");
            return Response.status(400).entity("Title and author are required").build();
        }
        // Default to 1 copy if not specified
        int copies = book.getIsbn() != null ? 1 : 1;
        Book newBook = this.tm.addBook(book.getTitle(), book.getAuthor(), book.getIsbn(), 
                                       book.getEditorial(), book.getYearpublication(), book.getTheme(), copies);
        return Response.status(201).entity(newBook).build();
    }

    // ===== LOAN ENDPOINTS =====
    
    @GET
    @ApiOperation(value = "Get all loans", notes = "Returns a list of all loans")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Loan.class, responseContainer = "List"),
    })
    @Path("/loans")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoans() {
        logger.info("GET /loans");
        List<Loan> loans = this.tm.findAllLoans();
        GenericEntity<List<Loan>> entity = new GenericEntity<List<Loan>>(loans) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get loans by reader", notes = "Returns all loans for a specific reader")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Loan.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Reader not found")
    })
    @Path("/readers/{readerId}/loans")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoansByReader(@PathParam("readerId") String readerId) {
        logger.info("GET /readers/" + readerId + "/loans");
        try {
            List<Loan> loans = this.tm.getLoansByReader(readerId);
            GenericEntity<List<Loan>> entity = new GenericEntity<List<Loan>>(loans) {};
            return Response.status(200).entity(entity).build();
        } catch (TrackNotFoundException e) {
            logger.warn("Reader not found: " + readerId);
            return Response.status(404).entity("Reader not found").build();
        }
    }

    @POST
    @ApiOperation(value = "Create a new loan", notes = "Loan a book to a reader")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Loan created", response = Loan.class),
            @ApiResponse(code = 400, message = "Invalid loan data"),
            @ApiResponse(code = 404, message = "Reader or book not found or no copies available")
    })
    @Path("/loans")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLoan(Loan loan) {
        logger.info("POST /loans - Creating new loan");
        try {
            if (loan.idReader() == null || loan.idBook() == null) {
                logger.warn("Invalid loan data - missing reader or book ID");
                return Response.status(400).entity("Reader ID and Book ID are required").build();
            }
            Loan newLoan = this.tm.loanBook(loan.idReader(), loan.idBook(), loan.getLoanDate(), loan.getReturnDate());
            return Response.status(201).entity(newLoan).build();
        } catch (TrackNotFoundException e) {
            logger.warn("Error creating loan: " + e.getMessage());
            return Response.status(404).entity("Reader or book not found or no copies available").build();
        }
    }

    @DELETE
    @ApiOperation(value = "Return a book", notes = "Complete a loan by returning the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book returned successfully", response = Loan.class),
            @ApiResponse(code = 404, message = "Loan not found")
    })
    @Path("/loans/{loanId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(@PathParam("loanId") String loanId) {
        logger.info("DELETE /loans/" + loanId + " - Returning book");
        try {
            Loan returnedLoan = this.tm.returnBook(loanId);
            return Response.status(200).entity(returnedLoan).build();
        } catch (TrackNotFoundException e) {
            logger.warn("Loan not found: " + loanId);
            return Response.status(404).entity("Loan not found").build();
        }
    }
}