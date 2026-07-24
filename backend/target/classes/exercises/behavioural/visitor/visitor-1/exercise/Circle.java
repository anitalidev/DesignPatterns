// TODO: implement accept() — calls visitor.visitCircle(this)
class Circle {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double accept(Object visitor) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
