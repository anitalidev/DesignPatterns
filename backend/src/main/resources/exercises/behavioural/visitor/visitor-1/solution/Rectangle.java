class Rectangle implements Shape {
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

    public double accept(ShapeVisitor visitor) {
        return visitor.visitRectangle(this);
    }
}
