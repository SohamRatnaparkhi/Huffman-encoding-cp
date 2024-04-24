package cp.utils;

public class Tree {
    Tree right;
    Tree left;
    Data data;

    public Tree(Data data) {
        this.data = data;
        this.right = null;
        this.left = null;
    }
}

