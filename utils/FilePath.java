package cp.utils;

public class FilePath {
    String extension;
    String fileName;
    String path;

    public FilePath(String path) {
        this.path = path;
        this.extension = path.substring(path.lastIndexOf(".") + 1);
        this.fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    }

    @Override
    public String toString() {
        return "Path [extension=" + extension + ", fileName=" + fileName + ", path=" + path + "]";
    }
}
