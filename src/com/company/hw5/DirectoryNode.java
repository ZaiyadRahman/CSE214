package com.company.hw5;

import java.util.Arrays;

public class DirectoryNode {
    public static void main(String[] args) {

    }

    private String name;
    private final boolean isFile;
    private DirectoryNode[] children;
    private DirectoryNode parent;
    private int numChildren;

    public DirectoryNode() {
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

    public void removeChild(DirectoryNode child) {
        DirectoryNode[] newChildren =
                (DirectoryNode[]) Arrays.stream(children).filter(c -> !c.equals(child)).toArray();
        setChildren(newChildren);
        child.setParent(null);
        this.numChildren--;
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

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public DirectoryNode[] getChildren() {
        return children;
    }

    public DirectoryNode getChild(int index) {
        return children[index];
    }

    public void setChildren(DirectoryNode[] children) {
        this.children = children;
    }

    public int getNumChildren() {
        return numChildren;
    }
}
