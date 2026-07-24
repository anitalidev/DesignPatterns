class Circle implements Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double accept(ShapeVisitor visitor) {
        return visitor.visitCircle(this);
    }
}
