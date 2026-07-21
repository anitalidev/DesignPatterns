import java.util.HashMap;
import java.util.Map;

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

class TreeFactory {
    private final Map<String, TreeType> cache = new HashMap<>();

    public TreeType getTreeType(String species, String color, String texture) {
        if (!cache.containsKey(species)) {
            cache.put(species, new TreeType(species, color, texture));
        }
        return cache.get(species);
    }

    public int typeCount() {
        return cache.size();
    }
}
