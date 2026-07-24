// Provided — do not edit
// Tree stores the extrinsic state (unique per tree): x, y, and a reference to a shared TreeType
class Tree {
    private final int x;
    private final int y;
    private final TreeType type;

    Tree(int x, int y, TreeType type) {
        this.x    = x;
        this.y    = y;
        this.type = type;
    }

    public int      getX() { return x; }
    public int      getY() { return y; }
    public TreeType getType() { return type; }
}
