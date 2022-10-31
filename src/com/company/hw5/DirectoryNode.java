package com.company.hw5;

public class DirectoryNode {
    public static void main(String[] args) {

    }

    private String name;
    private boolean isFile;
    private DirectoryNode left;
    private DirectoryNode right;
    private DirectoryNode middle;

    DirectoryNode() {
        name = "";
        isFile = false;
        left = null;
        right = null;
        middle = null;
    }

    public DirectoryNode(String name, boolean isFile, DirectoryNode left, DirectoryNode right, DirectoryNode middle) {
        this.name = name;
        this.isFile = isFile;
        this.left = left;
        this.right = right;
        this.middle = middle;
    }



    public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException {
        if(!isFile()) {
            if(this.getLeft() == null)
                this.setLeft(newChild);
            else if(this.getMiddle() == null)
                this.setMiddle(newChild);
            else if(this.getRight() == null)
                this.setRight(newChild);
            else
                throw new FullDirectoryException();

        }
        else
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

    public void setFile(boolean file) {
        isFile = file;
    }

    public DirectoryNode getLeft() {
        return left;
    }

    public void setLeft(DirectoryNode left) {
        this.left = left;
    }

    public DirectoryNode getRight() {
        return right;
    }

    public void setRight(DirectoryNode right) {
        this.right = right;
    }

    public DirectoryNode getMiddle() {
        return middle;
    }

    public void setMiddle(DirectoryNode middle) {
        this.middle = middle;
    }

    public boolean isEmpty() {
        return this.getName().isBlank();
    }
}
