public class Book implements Comparable <Book> {
    // creating book object 
    String isbn; 
    String title; 
    String author; 
    String publisher; 
    String address; 
    double price; 

    // adding constructur 
    public Book(String isbn, String title, String author, String publisher, String address, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.address = address;
        this.price = price;
    }

    // comparing book against another book 
    @Override
    public int compareTo(Book other) {
        return this.isbn.compareTo(other.isbn);
    }   
    
}