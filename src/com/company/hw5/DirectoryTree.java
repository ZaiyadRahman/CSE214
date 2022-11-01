package com.company.hw5;

public class DirectoryTree {

    private final DirectoryNode root;
    private DirectoryNode cursor;

    public DirectoryTree() {
        this.root = new DirectoryNode("root", false, null);
        this.cursor = root;
    }

    public void resetCursor() {
        this.cursor = root;
    }

    public void changeDirectory(String path) throws NotADirectoryException {
        DirectoryNode found = findNode(path);
        if (found == null) {
            System.out.println("ERROR: No such directory named " + path + ".");
        } else if (found.isFile()) {
            throw new NotADirectoryException();
        } else {
            this.cursor = found;
        }
    }

    public void changeDirectoryToParent() {
        DirectoryNode parent = cursor.getParent();
        if (parent == null) {
            System.out.println("ERROR: Already at root directory.");
        } else {
            this.cursor = parent;
        }
    }

    public DirectoryNode findNode(String path) {
        return findNode(cursor, path);
    }

    public DirectoryNode findNode(DirectoryNode node, String path) {
        if (node.getName().equals(path)) {
            return node;
        } else if (node.isFile()) {
            return null;
        } else if (!path.contains("/")) {
            for (DirectoryNode child : node.getChildren()) {
                DirectoryNode found = findNode(child, path);
                if (found != null) {
                    return found;
                }
            }
            return null;
        } else {
//            split into two parts - the "parent-most" segment/dir and the rest of the path
            String[] split = path.split("/", 2);
//            see if any children of the current node match the "parent-most"
//            segment - if so, that means we're getting warmer!
//            thus, recurse on that child with the rest of the path
            for (DirectoryNode child : node.getChildren()) {
                if (child.getName().equals(split[0])) {
                    return findNode(child, split[1]);
                }
            }
            return null;
        }
    }

    public DirectoryNode search(String name) {
        return searchHelper(root, name);
    }

    public DirectoryNode searchHelper(DirectoryNode node, String name) {
        if (node.getName().equals(name)) {
            return node;
        } else if (!node.isFile()) {
            for (DirectoryNode child : node.getChildren()) {
                return searchHelper(child, name);
            }
        }
        return null;
    }

    /*
     public String presentWorkingDirectory()
     Returns a String containing the path of directory names from the root
     node of the tree to the cursor, with each name separated by a forward
     slash "/".
     ***The cursor remains at the same DirectoryNode.***
    */
    public String presentWorkingDirectory() {
        return getFullPath(cursor);
    }

    public String getFullPath(DirectoryNode node) {
        StringBuilder path = new StringBuilder(node.getName());
        DirectoryNode temp = node;
        while (temp != root) {
            temp = temp.getParent();
            path = new StringBuilder(temp.getName() + ("/" + path));
        }
        return path.toString();
    }


    /*
       public String listDirectory()
       Returns a String containing a space-separated list of names of all the
       child directories or files of the cursor.
       e.g. dev home bin if the cursor is at root in the example above.
       ***The cursor remains at the same DirectoryNode.***
        */
    public String listDirectory() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < cursor.getNumChildren(); i++) {
            list.append(cursor.getChild(i).getName()).append(" ");
        }
        return list.toString();
    }

    /*
    Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
    The cursor remains at the same DirectoryNode.
    Prints in the following format:
    |- root
    |- dev
        - ttys0
        - ttys1
    |- home
        |- user
            |- Documents
                - hw5.java
                - resume.pdf
            |- Pictures
                - puppies.jpg
            |- Downloads
    |- bin
        - sublime
        - gcc
     */
    public void printDirectoryTree() {
        DirectoryNode temp = cursor;
        printDirectoryTreeHelper(temp);
    }

    private void printDirectoryTreeHelper(DirectoryNode temp) {
        // pre-order traversal
        // print *ROOT* first
        if (temp.isFile()) {
            System.out.println("- " + temp.getName());
        } else {
            System.out.println("|- " + temp.getName());

            if (temp.getNumChildren() == 0) {
                for (DirectoryNode child :
                        temp.getChildren()) {
                    printDirectoryTreeHelper(child);
                }
            }
        }
    }

    public void makeFile(String name) throws FullDirectoryException,
            IllegalArgumentException {
        if (name.contains(" ") || name.contains("/"))
            throw new IllegalArgumentException();
        try {
            cursor.addChild(new DirectoryNode(name, true, cursor));
        } catch (NotADirectoryException e) {
//            do nothing
        }
    }

    public void makeDirectory(String name) throws IllegalArgumentException,
            FullDirectoryException {
        if (name.contains(" ") || name.contains("/"))
            throw new IllegalArgumentException();
        try {
            cursor.addChild(new DirectoryNode(name, false, cursor));
        } catch (NotADirectoryException e) {
//            pass
        }
    }

    public void moveNode(String src, String dst) {
        DirectoryNode srcNode = findNode(root, src);
        DirectoryNode dstNode = findNode(root, dst);
        if (srcNode == null) {
            System.out.println("ERROR: No such file or directory named " + src + ".");
        } else if (dstNode == null) {
            System.out.println("ERROR: No such directory named " + dst + ".");
        } else {
            srcNode.getParent().removeChild(srcNode);
            try {
                dstNode.addChild(srcNode);
            } catch (FullDirectoryException e) {
                System.out.println("ERROR: Destination directory is full.");
            } catch (NotADirectoryException e) {
                System.out.println("ERROR: Cannot move to a file.");
            }
        }
    }


}


