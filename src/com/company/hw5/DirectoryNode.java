/**
 * this <code>DirectoryNode</code> class is a node of a tree that represents
 * a directory. The node has a name, a parent, and a list of children. The
 * node can be a file or a directory.
 *
 *  @author Zaiyad Munair Rahman
 *  SBU ID: 114578879
 *  CSE 214.01
 */

package com.company.hw5;

public class DirectoryNode {
    public static void main(String[] args) {

    }

    private String name;
    private final boolean isFile;
    private DirectoryNode[] children;
    private DirectoryNode parent;
    private int numChildren;

    /**
     * Default constructor for the DirectoryNode class.
     */
    public DirectoryNode() {
        name = "";
        isFile = false;
        children = new DirectoryNode[10];
        parent = null;
        numChildren = 0;
    }

    /**
     * Constructor for the DirectoryNode class.
     * @param name
     * The name of the node.
     * @param isFile
     * Whether the node is a file or not.
     * @param parent
     * The parent of the node.
     */
    public DirectoryNode(String name, boolean isFile, DirectoryNode parent) {
        this.name = name;
        this.isFile = isFile;
        children = new DirectoryNode[10];
        this.parent = parent;
        numChildren = 0;
    }

    /**
     * Adds newChild to any of the open child positions of this node.
     * Children should be added to this node in left-to-right order.
     * @param newChild
     * The new child to be added.
     * @throws FullDirectoryException
     * Thrown if  all child references of this directory are occupied.
     * @throws NotADirectoryException
     * Thrown if the current node is a file, as files cannot contain
     * DirectoryNode references (i.e. all files are leaves).
     */
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

    /**
     * Removes the specified child from this node.
     * @param child
     * The child to be removed.
     */
    public void removeChild(DirectoryNode child) {
        DirectoryNode[] newChildren = new DirectoryNode[children.length];
        for (int i = 0; i < this.getNumChildren(); i++) {
            if (children[i] == child) {
                System.arraycopy(children, 0, newChildren, 0, i);
                System.arraycopy(children, i + 1, newChildren, i, children.length - i - 1);
            }
        }
        setChildren(newChildren);
        child.setParent(null);
        this.numChildren--;
    }

    /**
     * Returns the child of this node at the specified index.
     * @return
     * The name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this node.
     * @param name
     * The new name of this node.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns whether this node is a file or not.
     * @return
     * True if this node is a file, false otherwise.
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * Returns the parent of this node.
     * @return
     * The parent of this node.
     */
    public DirectoryNode getParent() {
        return parent;
    }

    /**
     * Sets the parent of this node.
     * @param parent
     * The new parent of this node.
     */
    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    /**
     * Returns the children of this node.
     * @return
     * The children of this node as an array.
     */
    public DirectoryNode[] getChildren() {
        return children;
    }

    /**
     * returns a child of this node at the specified index.
     * @param index
     * The index of the child to be returned.
     * @return
     * The child at the specified index.
     */
    public DirectoryNode getChild(int index) {
        return children[index];
    }

    /**
     * Sets the children of this node.
     * @param children
     * The new children of this node.
     */
    public void setChildren(DirectoryNode[] children) {
        this.children = children;
    }

    /**
     * Returns the number of children of this node.
     * @return
     * The number of children of this node.
     */
    public int getNumChildren() {
        return numChildren;
    }

    public int getDepth() {
        int depth = 0;
        DirectoryNode current = this;
        while (current.getParent() != null) {
            depth++;
            current = current.getParent();
        }
        return depth;
    }
}
