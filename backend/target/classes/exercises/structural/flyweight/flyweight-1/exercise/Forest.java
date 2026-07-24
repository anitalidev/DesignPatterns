import java.util.ArrayList;
import java.util.List;

// TODO: implement Forest — owns the list of trees and uses a TreeFactory to get shared TreeType instances
class Forest {
    // TODO: add a list to store all planted Tree objects
    // TODO: add a TreeFactory to reuse TreeType instances

    public void plantTree(int x, int y, String species, String color, String texture) {
        // TODO: use the factory to get (or create) a shared TreeType,
        //       then create a new Tree at (x, y) with that type and add it to the list
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Tree> getTrees() {
        // TODO: return all planted trees
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int treeTypeCount() {
        // TODO: return the number of distinct TreeType instances the factory has created
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
