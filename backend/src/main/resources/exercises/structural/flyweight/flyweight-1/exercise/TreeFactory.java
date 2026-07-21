import java.util.HashMap;
import java.util.Map;

// Provided — do not edit
class TreeType {
    private final String species;
    private final String color;
    private final String texture;

    TreeType(String species, String color, String texture) {
        this.species = species;
        this.color   = color;
        this.texture = texture;
    }

    public String getSpecies() { return species; }
    public String getColor()   { return color; }
    public String getTexture() { return texture; }
}

// Provided — do not edit
class Tree {
    private final int x;
    private final int y;
    private final TreeType type;

    Tree(int x, int y, TreeType type) {
        this.x    = x;
        this.y    = y;
        this.type = type;
    }

    public int      getX()    { return x; }
    public int      getY()    { return y; }
    public TreeType getType() { return type; }
}

// TODO: implement the flyweight factory
class TreeFactory {
    // TODO: add a map to cache TreeType instances by species

    public TreeType getTreeType(String species, String color, String texture) {
        // TODO: return cached TreeType if it exists, otherwise create, cache, and return a new one
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int typeCount() {
        // TODO: return the number of distinct TreeType instances cached
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
