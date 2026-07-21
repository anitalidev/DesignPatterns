// TODO: define ShapeVisitor interface with:
//   double visitCircle(Circle circle)
//   double visitRectangle(Rectangle rectangle)

// TODO: define Shape interface with:
//   double accept(ShapeVisitor visitor)

// TODO: implement Circle — stores radius; accept() calls visitor.visitCircle(this)
class Circle {
    private final double radius;
    Circle(double radius) { this.radius = radius; }
    public double getRadius() { return radius; }
    public double accept(Object visitor) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement Rectangle — stores width and height; accept() calls visitor.visitRectangle(this)
class Rectangle {
    private final double width, height;
    Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double getWidth()  { return width; }
    public double getHeight() { return height; }
    public double accept(Object visitor) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement AreaVisitor
// visitCircle: Math.PI * r * r
// visitRectangle: w * h
class AreaVisitor {
    public double visitCircle(Circle c)       { throw new UnsupportedOperationException("Not yet implemented"); }
    public double visitRectangle(Rectangle r) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement PerimeterVisitor
// visitCircle: 2 * Math.PI * r
// visitRectangle: 2 * (w + h)
class PerimeterVisitor {
    public double visitCircle(Circle c)       { throw new UnsupportedOperationException("Not yet implemented"); }
    public double visitRectangle(Rectangle r) { throw new UnsupportedOperationException("Not yet implemented"); }
}
