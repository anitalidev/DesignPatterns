interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;
    Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}

class Rectangle implements Shape {
    private double width, height;
    Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double area() { return width * height; }
}

class Triangle implements Shape {
    private double base, height;
    Triangle(double base, double height) { this.base = base; this.height = height; }
    public double area() { return 0.5 * base * height; }
}

class ShapeFactory {
    static Shape create(String type, double... args) {
        switch (type) {
            case "circle":    return new Circle(args[0]);
            case "rectangle": return new Rectangle(args[0], args[1]);
            case "triangle":  return new Triangle(args[0], args[1]);
            default: throw new IllegalArgumentException("Unknown shape: " + type);
        }
    }
}
