// Provided — do not edit
class Book {
    private final String title;
    Book(String title) { this.title = title; }
    public String getTitle() { return title; }
}

// TODO: implement BookIterator
class BookIterator {
    BookIterator(Book[] books, int count) {
        // TODO
    }
    public boolean hasNext() { throw new UnsupportedOperationException("Not yet implemented"); }
    public Book next()       { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: complete BookShelf
class BookShelf {
    private final Book[] books;
    private int count = 0;

    BookShelf(int capacity) { books = new Book[capacity]; }

    public void add(Book book) {
        if (count < books.length) books[count++] = book;
    }

    public BookIterator iterator() {
        // TODO: return a new BookIterator
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
