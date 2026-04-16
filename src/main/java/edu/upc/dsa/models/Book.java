package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Book {

    String id;
    String isbn;
    String title;
    String editorial;
    String yearpublication;
    String author;
    String theme;

    static int lastId;

    public Book() {
        this.setId(RandomUtils.getId());
    }
    public Book(String title, String author, String isbn, String editorial, String yearpublication, String theme) {
        this(null, title, author, isbn, editorial, yearpublication, theme);
    }

    public Book(String id, String title, String author, String isbn, String editorial, String yearpublication, String theme) {
        this();
        if (id != null) this.setId(id);
        this.setAuthor(author);
        this.setTitle(title);
        this.setIsbn(isbn);
        this.setEditorial(editorial);
        this.setYearpublication(yearpublication);
        this.setTheme(theme);

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public String getYearpublication() {
        return yearpublication;
    }
    public void setYearpublication(String yearpublication) {
        this.yearpublication = yearpublication;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    @Override
    public String toString() {
        return "Book [id="+id+", title=" + title + ", author=" + author + ", isbn=" + isbn + ", editorial=" + editorial + ", yearpublication=" + yearpublication + ", theme=" + theme + "]";
    }

}