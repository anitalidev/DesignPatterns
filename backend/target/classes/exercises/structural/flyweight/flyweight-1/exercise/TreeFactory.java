import java.util.HashMap;
import java.util.Map;

// TODO: implement the flyweight factory — caches and reuses TreeType instances
class TreeFactory {
    // TODO: add a map to cache TreeType instances
    //       key should uniquely identify a species+color+texture combination

    public TreeType getTreeType(String species, String color, String texture) {
        // TODO: return the cached TreeType if one exists for this combination,
        //       otherwise create a new one, cache it, and return it
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int typeCount() {
        // TODO: return the number of distinct TreeType instances in the cache
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
