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
