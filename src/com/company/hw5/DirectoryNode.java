package com.company.hw5;

public class DirectoryNode {
    public static void main(String[] args) {

    }

    private String name;
    private final boolean isFile;
    private DirectoryNode[] children;
    private DirectoryNode parent;
    private int numChildren;

    DirectoryNode() {
        name = "";
        isFile = false;
        children = new DirectoryNode[10];
        parent = null;
        numChildren = 0;
    }

    public DirectoryNode(String name, boolean isFile, DirectoryNode parent) {
        this.name = name;
        this.isFile = isFile;
        children = new DirectoryNode[10];
        this.parent = parent;
        numChildren = 0;
    }


    public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException {
        if (!isFile()) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    children[i] = newChild;
                    newChild.setParent(this);
                    this.numChildren++;
                    break;
                } else if (i == children.length - 1)
                    throw new FullDirectoryException();
            }
        } else
            throw new NotADirectoryException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFile() {
        return isFile;
    }

    public DirectoryNode getParent() {
        return parent;
    }
}
