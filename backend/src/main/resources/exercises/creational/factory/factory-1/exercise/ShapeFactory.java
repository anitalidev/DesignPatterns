class ShapeCreator {
    static Shape get(String type, double... args) {
        if (type.equals("circle"))    return new Circle(args[0]);
        if (type.equals("rectangle")) return new Rectangle(args[0], args[1]);
        if (type.equals("triangle"))  return new Triangle(args[0], args[1]);
        throw new IllegalArgumentException("Unknown shape: " + type);
    }
}
