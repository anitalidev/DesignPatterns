interface ShapeVisitor {
    double visitCircle(Circle circle);
    double visitRectangle(Rectangle rectangle);
}

interface Shape {
    double accept(ShapeVisitor visitor);
}

class Circle implements Shape {
    private final double radius;
    Circle(double radius) { this.radius = radius; }
    public double getRadius() { return radius; }
    public double accept(ShapeVisitor visitor) { return visitor.visitCircle(this); }
}

class Rectangle implements Shape {
    private final double width, height;
    Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double getWidth()  { return width; }
    public double getHeight() { return height; }
    public double accept(ShapeVisitor visitor) { return visitor.visitRectangle(this); }
}

class AreaVisitor implements ShapeVisitor {
    public double visitCircle(Circle c)       { return Math.PI * c.getRadius() * c.getRadius(); }
    public double visitRectangle(Rectangle r) { return r.getWidth() * r.getHeight(); }
}

class PerimeterVisitor implements ShapeVisitor {
    public double visitCircle(Circle c)       { return 2 * Math.PI * c.getRadius(); }
    public double visitRectangle(Rectangle r) { return 2 * (r.getWidth() + r.getHeight()); }
}
