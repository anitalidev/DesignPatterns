class AreaVisitor implements ShapeVisitor {
    public double visitCircle(Circle c) { return Math.PI * c.getRadius() * c.getRadius(); }
    public double visitRectangle(Rectangle r) { return r.getWidth() * r.getHeight(); }
}
