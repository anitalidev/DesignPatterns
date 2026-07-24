class BookIterator {
    private final Book[] books;
    private final int count;
    private int index = 0;

    BookIterator(Book[] books, int count) {
        this.books = books;
        this.count = count;
    }

    public boolean hasNext() {
        return index < count;
    }

    public Book next() {
        return books[index++];
    }
}
