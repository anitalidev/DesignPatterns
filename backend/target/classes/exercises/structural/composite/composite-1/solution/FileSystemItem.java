import java.util.ArrayList;
import java.util.List;

interface FileSystemItem {
    String getName();
    long getSize();
}

class File implements FileSystemItem {
    private final String name;
    private final long size;

    File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() { return name; }
    public long getSize()   { return size; }
}

class Folder implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        children.add(item);
    }

    public String getName() { return name; }

    public long getSize() {
        long total = 0;
        for (FileSystemItem child : children) {
            total += child.getSize();
        }
        return total;
    }
}
