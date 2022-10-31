package com.company.hw5;

public class DirectoryTree {
    public static void main(String[] args) {

    }

    /*
    TODO Overall
     Test algorithms.
     */

    private DirectoryNode root;
    private DirectoryNode cursor;

    public DirectoryTree() {
        this.root = new DirectoryNode("root", false, null, null, null);
        this.cursor = root;
    }

    public void resetCursor() {
        this.cursor = root;
    }

    public void changeDirectory(String name) throws NotADirectoryException {
        if(name.equals(".."))
            resetCursor();

        else {
            String[] strings = name.split("/");
            findNode(cursor, strings[strings.length - 1]);
        }
    }

    public void makeDirectory(String name) throws IllegalArgumentException,
            FullDirectoryException, NotADirectoryException {
        if (name.contains(" ") || name.contains("/"))
            throw new IllegalArgumentException();
        cursor.addChild(new DirectoryNode(name, false, null, null, null));
    }

    public void makeFile(String name) throws FullDirectoryException,
            IllegalArgumentException, NotADirectoryException {
        if (name.contains(" ") || name.contains("/"))
            throw new IllegalArgumentException();
        cursor.addChild(new DirectoryNode(name, true, null, null, null));
    }

    public void findNode(DirectoryNode cursor, String name) {
        if(cursor.getName().equals(name)) {
            this.cursor = cursor;
        }
        else {
            if(cursor.getLeft() != null)
                findNode(cursor.getLeft(), name);
            if(cursor.getMiddle() != null)
                findNode(cursor.getMiddle(), name);
            if(cursor.getRight() != null)
                findNode(cursor.getRight(), name);
        }
    }
    /*
    TODO 2
     public String presentWorkingDirectory()
     Returns a String containing the path of directory names from the root
     node of the tree to the cursor, with each name separated by a forward
     slash "/".
     ***The cursor remains at the same DirectoryNode.***
    */
    public void presentWorkingDirectory() {

    }
    /*
    TODO 3
     public String listDirectory()
     Returns a String containing a space-separated list of names of all the
     child directories or files of the cursor.
     e.g. dev home bin if the cursor is at root in the example above.
     ***The cursor remains at the same DirectoryNode.***
     */
    public String listDirectory() {
        return "this is not complete";
    }

    /*
    TODO 4
     public void printDirectoryTree()
     Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
     */
    public void printDirectoryTree() {

    }


}
