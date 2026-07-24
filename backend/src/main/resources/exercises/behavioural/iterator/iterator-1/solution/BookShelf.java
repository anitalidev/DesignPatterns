class BookShelf {
    private final Book[] books;
    private int count = 0;

    BookShelf(int capacity) {
        books = new Book[capacity];
    }

    public void add(Book book) {
        if (count < books.length) books[count++] = book;
    }

    public BookIterator iterator() {
        return new BookIterator(books, count);
    }
}
