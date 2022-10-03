package com.company.hw3;

public class CodeBlock extends Complexity {
    public static void main(String[] args) {

    }

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

    public CodeBlock() {
        this.name = "1";
        this.blockComplexity = null;
        this.highestSubComplexity = null;
        this.loopVariable = null;
    }

    public CodeBlock(String name, Complexity blockComplexity,
                     Complexity highestSubComplexity, String loopVariable) {
        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        this.loopVariable = loopVariable;
    }

    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoopVariable() {
        return loopVariable;
    }

    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    public boolean compareComplexity(Complexity complex) {
        if(this.getBlockComplexity().toString().matches("O(1)") &
                complex.toString().matches("O(1)"))
            return false;
        else if(this.getPowers().matches("[0-9],0") &
                complex.getPowers().matches("[0-9],0"))
            return this.getnPower() > complex.getnPower();

        else if(this.getPowers().matches("0,[0-9]") &
                complex.getPowers().matches("0, [0-9]"))
            return this.getLogPower() > complex.getLogPower();

        else if(this.getPowers().matches("[0-9],[0-9]") &
                complex.getPowers().matches("[0-9], [0-9]"))
            return this.getnPower() > complex.getnPower();

        return false;
    }
}
