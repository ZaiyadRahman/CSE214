/**
 * This <code>CodeBlock</code> class describes a nested block of code in python.
 *
 *  @author Zaiyad Munair Rahman
 *  SBU ID: 114578879
 *  CSE 214.01
 */

package com.company.hw3;

public class CodeBlock {
    /**
     * An array of strings used to enumerate the types of blocks available
     * for nesting, "def", "for", "while", "if", "elif", and "else".
     */
    static final String[] BLOCK_TYPES = {"def","for","while","if",
            "elif","else"};

    static final int DEF = 0;
    static final int FOR = 1;
    static final int WHILE = 2;
    static final int IF = 3;
    static final int ELIF = 4;
    static final int ELSE = 5;

    private String name;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String loopVariable;
    int blockType;
    int numChildren;
    private Complexity totalComplexity;

    /**
     * Default constructor for the CodeBlock class.
     */
    public CodeBlock() {
        this.name = "1";
        this.blockComplexity = null;
        this.highestSubComplexity = null;
        this.loopVariable = null;
        this.numChildren = 0;
    }

    /**
     * Constructor for the CodeBlock class.
     * @param name
     * used to keep track of the nested structure of the blocks. The first
     * block in the stack will always be named "1". All blocks included
     * directly under a block will be numbered increasingly using a dot "."
     * separator after the block's own name.
     * @param blockComplexity
     * Keeps track of the Big-Oh complexity of this block,  ignoring the
     * statements inside.
     * @param highestSubComplexity
     * Represents the highest complexity of all
     * the blocks nested inside this block.
     * @param totalComplexity
     * Represents the total complexity of the block.
     * @param loopVariable
     * Used to keep track of the loop variable when a "while" loop is traced,
     * and is otherwise left null.
     * @param blockType
     * Represents the type of block currently traced.
     * @param numChildren
     * Number of nested, or "child" blocks under the current block.
     */
    public CodeBlock(String name, Complexity blockComplexity,
                     Complexity highestSubComplexity,
                     Complexity totalComplexity, String loopVariable,
                     int blockType, int numChildren) {
        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        this.loopVariable = loopVariable;
        this.blockType = blockType;
        this.numChildren = numChildren;
        this.totalComplexity = totalComplexity;
    }

    /**
     * @return
     * Returns Block complexity.
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * @param blockComplexity
     * Assigns block complexity given a Complexity object.
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * @return
     * Returns the highest sub-complexity.
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * Sets the highest sub complexity, and adjusts the total complexity to
     * be the product of the highest sub complexity and the current block
     * complexity.
     * @param highestSubComplexity
     * Sets the highest sub complexity given a Complexity class.
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
        getTotalComplexity().setnPower(getBlockComplexity().getnPower() +
                highestSubComplexity.getnPower());
        getTotalComplexity().setLogPower(getBlockComplexity().getLogPower() +
                highestSubComplexity.getLogPower());
    }

    /**
     * @return
     * The total complexity of the code block as a Complexity object.
     */
    public Complexity getTotalComplexity() {
        return totalComplexity;
    }

    /**
     * @param totalComplexity
     * Total complexity as a Complexity object to be assigned.
     */
    public void setTotalComplexity(Complexity totalComplexity) {
        this.totalComplexity = totalComplexity;
    }

    /**
     * @return
     * The name of the block.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * The name to be assigned to the block.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     * The loop variable, if this block is a "while" block. Else, it returns
     * null.
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * @param loopVariable
     * The name of the loop variable in the "while" block.
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    /**
     * @return
     * The type of block this CodeBlock is as an integer. Can be "def", "for",
     * "while", if", "elif", or "else", in order.
     */
    public int getBlockType() {
        return blockType;
    }

    /**
     * Sets the block type using an integer to point to the type of block.
     * @param blockType
     * Integer to assign to the block type.
     */
    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    /**
     * @return
     * Number of nested blocks under the current block.
     */
    public int getNumChildren() {
        return numChildren;
    }

    /**
     * @param numChildren
     * Number of nested blocks under the current block.
     */
    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    /**
     * Compares complexity of the BlockComplexity to the Complexity
     * of another CodeBlock.
     * @param complex
     * The complexity to be compared against.
     * @return
     * True if BlockComplexity is of higher order than complex.
     * Otherwise returns false.
     */
    public boolean compareComplexity(Complexity complex) {
        if(this.getTotalComplexity().toString().matches("O(1)") &&
                complex.toString().matches("O(1)"))
            return false;
        else if(this.getTotalComplexity().getPowers().matches("[0-9],0") &&
                complex.getPowers().matches("[0-9],0"))
            return this.getTotalComplexity().getnPower() > complex.getnPower();

        else if(this.getTotalComplexity().getPowers().matches("0,[0-9]") &&
                complex.getPowers().matches("0,[0-9]"))
            return this.getTotalComplexity().getLogPower() > complex.getLogPower();

        else if(this.getTotalComplexity().getPowers().matches("[0-9],[0-9]") &&
                complex.getPowers().matches("[0-9],[0-9]"))
            return this.getTotalComplexity().getnPower() > complex.getnPower();

        return false;
    }

    /**
     * Returns a neatly formatted String representation of the CodeBlock.
     * @return
     * A neatly formatted string containing the name of the block, the block
     * complexity, and the highest sub-complexity of the block.
     */
    @Override
    public String toString() {
               return "BLOCK " + getName() + "     block complexity = " +
                       getBlockComplexity().toString() +
                       " highest sub-complexity = " +
                       getHighestSubComplexity().toString();
    }
}
