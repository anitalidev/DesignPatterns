class Book {
    private final String title;
    Book(String title) { this.title = title; }
    public String getTitle() { return title; }
}

class BookIterator {
    private final Book[] books;
    private final int count;
    private int index = 0;

    BookIterator(Book[] books, int count) {
        this.books = books;
        this.count = count;
    }

    public boolean hasNext() { return index < count; }
    public Book next()       { return books[index++]; }
}

class BookShelf {
    private final Book[] books;
    private int count = 0;

    BookShelf(int capacity) { books = new Book[capacity]; }

    public void add(Book book) {
        if (count < books.length) books[count++] = book;
    }

    public BookIterator iterator() { return new BookIterator(books, count); }
}
