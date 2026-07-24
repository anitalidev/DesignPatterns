// TODO: implement accept() — calls visitor.visitRectangle(this)
class Rectangle {
    private final double width, height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double accept(Object visitor) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
