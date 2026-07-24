class PerimeterVisitor implements ShapeVisitor {
    public double visitCircle(Circle c) { return 2 * Math.PI * c.getRadius(); }
    public double visitRectangle(Rectangle r) { return 2 * (r.getWidth() + r.getHeight()); }
}
