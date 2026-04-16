package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Loan {

    String id;
    String idreader;
    String idbook;
    String loanDate;
    String returnDate;

    static int lastId;

    public Loan() {
        this.setId(RandomUtils.getId());
    }
    public Loan(String idreader, String idbook, String loanDate, String returnDate) {
        this(null, idreader, idbook, loanDate, returnDate);
    }

    public Loan(String id, String idreader, String idbook, String loanDate, String returnDate) {
        this();
        if (id != null) this.setId(id);
        this.setIdReader(idreader);
        this.setIdBook(idbook);
        this.setLoanDate(loanDate);
        this.setReturnDate(returnDate);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String idReader() {
        return idreader;
    }

    public void setIdReader(String idreader) {
        this.idreader = idreader;
    }

    public String idBook() {
        return idbook;
    }

    public void setIdBook(String idbook) {
        this.idbook = idbook;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    @Override
    public String toString() {
        return "Loan [id=" + id + ", idreader=" + idreader + ", idbook=" + idbook + ", loanDate=" + loanDate + ", returnDate=" + returnDate + "]";
    }

}