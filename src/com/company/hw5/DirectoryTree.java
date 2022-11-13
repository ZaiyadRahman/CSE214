/**
 * This <code>DirectoryTree</code> class implements a 10-ary tree of
 * DirectoryNodes. The class contains a reference to the root of the tree, a
 * cursor for the present working directory, and various methods for
 * insertion and deletion.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw5;

public class DirectoryTree {

    private final DirectoryNode root;
    private DirectoryNode cursor;

    /**
     * Default constructor for the DirectoryTree class.
     */
    public DirectoryTree() {
        this.root = new DirectoryNode("root", false, null);
        this.cursor = root;
    }

    /**
     * Resets the cursor to the root of the tree.
     */
    public void resetCursor() {
        this.cursor = root;
    }

    /**
     * Moves the cursor to the child of the current cursor with the given name.
     * @param path
     * The name or absolute path of the child to move the cursor to.
     * @throws NotADirectoryException
     * Thrown if the cursor is not a directory.
     */
    public void changeDirectory(String path) throws NotADirectoryException {
        DirectoryNode found = findNode(path);
        if (found == null) {
            System.out.println("ERROR: No such directory named " + path + " " +
                    "under the current directory. Please try again.");
        } else if (found.isFile()) {
            throw new NotADirectoryException();
        } else {
            this.cursor = found;
        }
    }

    /**
     * Moves the cursor to the parent of the current cursor.
     */

    public void changeDirectoryToParent() {
        DirectoryNode parent = cursor.getParent();
        if (parent == null) {
            System.out.println("ERROR: Already at root directory.");
        } else {
            this.cursor = parent;
        }
    }

    /**
     * Finds the node with the given name or absolute path.
     * @param path
     * The name or absolute path of the node to be found.
     * @return
     * The node with the given name or absolute path.
     */
    public DirectoryNode findNode(String path) {
        return findNode(cursor, path);
    }

    /**
     * Finds the node with the given name or absolute path.
     * @param node
     * The node to start searching from.
     * @param path
     * The name or absolute path of the node to be found.
     * @return
     * The node with the given name or absolute path.
     */
    public DirectoryNode findNode(DirectoryNode node, String path) {
        if (node.getName().equals(path)) {
            return node;
        } else if (node.isFile()) {
            return null;
        } else if (!path.contains("/")) {
            for (int i = 0; i < node.getNumChildren(); i++) {
                DirectoryNode found = findNode(node.getChild(i), path);
                if (found != null) {
                    return found;
                }
            }
            return null;
        } else {
//            split into two parts - the "parent-most" segment/dir and the rest of the path
            String[] split = path.split("/", 2);
//            see if any children of the current node match the "parent-most"
//            segment
//            thus, recurse on that child with the rest of the path
            for (int i = 0; i < node.getNumChildren(); i++) {
                if (node.getChild(i).getName().equals(split[0])) {
                    return findNode(node.getChild(i), split[1]);
                }
            }
            return null;
        }
    }



    /**
     * Returns a String containing the path of directory names from the root
     * node of the tree to the cursor, with each name separated by a forward
     * slash "/".
     * @return
     * The path of directory names from the root node of the tree to the
     * cursor.
     */
    public String presentWorkingDirectory() {
        return getFullPath(cursor);
    }

    /**
     * Returns a String containing the path of directory names from the root
     * node of the tree to the given node, with each name separated by a
     * forward slash "/".
     * @param node
     * The node to get the path of.
     * @return
     * The path of directory names from the root node of the tree to the
     * given node.
     */
    public String getFullPath(DirectoryNode node) {
        StringBuilder path = new StringBuilder(node.getName());
        DirectoryNode temp = node;
        while (temp != root) {
            temp = temp.getParent();
            if(!temp.isFile())
            path = new StringBuilder(temp.getName() + ("/" + path));
            else
                path = new StringBuilder(temp.getName() + path);
        }
        return path.toString();
    }


    /**
     * Returns a String containing a space-separated list of names of all the
     * child directories or files of the cursor.
     * e.g. dev home bin if the cursor is at root in the example above. The
     * cursor remains at the same DirectoryNode.
     * @return
     * A space-separated string of names of all the child directories or files
     * of the cursor.
     */
    public String listDirectory() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < cursor.getNumChildren(); i++) {
            list.append(cursor.getChild(i).getName()).append(" ");
        }
        return list.toString();
    }

    /**
     * Prints a formatted nested list of names of all the nodes in the
     * directory tree, starting from the cursor. The cursor remains at the
     * same DirectoryNode.
     */
    public void printDirectoryTree() {
        DirectoryNode temp = cursor;
        printDirectoryTreeHelper(temp);
    }

    /**
     * Prints a formatted nested list of names of all the nodes in the
     * directory tree, starting from the given node. The cursor remains at
     * the same DirectoryNode.
     * @param temp
     * The node to start printing from.
     */
    private void printDirectoryTreeHelper(DirectoryNode temp) {

        if (temp.isFile()) {
            for (int i = 0; i < temp.getDepth(); i++) {
                System.out.print("    ");
            }
                System.out.println("- " + temp.getName());
        } else {
            for (int i = 0; i < temp.getDepth(); i++) {
                System.out.print("    ");
            }
            System.out.println("|- " + temp.getName());

            if (temp.getNumChildren() != 0) {
                for (int i = 0; i < temp.getNumChildren(); i++) {
                    printDirectoryTreeHelper(temp.getChild(i));
                }
            }
        }
    }

    /**
     * Creates a file as a child of the current cursor node.
     * @param name
     * The name of the file to be created.
     * @throws FullDirectoryException
     * Thrown if the cursor node is full.
     * @throws IllegalArgumentException
     * Thrown if the name contains a forward slash "/" or a space " ".
     */
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

    /**
     * Creates a directory as a child of the current cursor node.
     * @param name
     * The name of the directory to be created.
     * @throws IllegalArgumentException
     * Thrown if the name contains a forward slash "/" or a space " ".
     * @throws FullDirectoryException
     * Thrown if the cursor node is full.
     */
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

    /**
     * Moves the source node to another destination node.
     * @param src
     * The node to be moved.
     * @param dst
     * The node to move the source node into.
     */
    public void moveNode(String src, String dst) {
        DirectoryNode srcNode = parseAbsPath(src);
        resetCursor();
        DirectoryNode dstNode = parseAbsPath(dst);
        resetCursor();
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

    public DirectoryNode parseAbsPath(String path) {
        String[] absPath = path.split("/");
        try {
            for (int i = 0; i < absPath.length - 1; i++) {
                this.changeDirectory(absPath[i]);
            }
        }
        catch (NotADirectoryException e) {
            System.out.println("Directory not found.");
        }
        return findNode(cursor, absPath[absPath.length - 1]);
    }

    public DirectoryNode getRoot() {
        return root;
    }
}


