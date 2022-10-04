/**
 * This <Code>PythonTracer</Code> class traces through the code of a Python
 * function contained within a file, and outputs the details of the trace and
 * the overall complexity to the console.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw3;

import java.io.*;
import java.util.Stack;
import java.util.Scanner;

public class PythonTracer {
    /**
     * Prompts the user for the name of a file containing a single Python
     * function, determines its order of complexity, and prints the result to
     * the console.
     * @param args
     *
     */
    public static void main(String[] args) {

        /**
         * A do-while loop to loop until the user types "quit".
         */
        do {

            System.out.println("\nPlease enter a file name (or 'quit' to " +
                    "quit):");
            // open scanner, as for input etc
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            if (userInput.trim().equalsIgnoreCase("quit")) {
                break;
            }
            Complexity complexity = traceFile(userInput);
            String fnName = userInput.split("\\.")[0];
            System.out.println("Overall complexity of " + fnName + " is " + complexity.toString());
        } while (true);
        System.out.println("Program terminating successfully...\n");
        System.exit(0);
    }

    /**
     * used to determine the indentation of each statement.
     */
    public static final int SPACE_COUNT = 4;

    /**
     * Opens the indicated file and traces through the code of the Python
     * function contained within the file, returning the Big-Oh order of
     * complexity of the function. During operation, the stack trace should
     * be printed to the console as code blocks are pushed to/popped from the
     * stack.
     * @param filename
     * The file input name as a string.
     * @return
     * A Complexity object representing the total order of complexity of the
     * Python code contained within the file.
     */
    public static Complexity traceFile(String filename) {
        Stack<CodeBlock> codeBlocks = new Stack<>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader inStream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inStream);
            String line = reader.readLine();
            while (line != null) {

                if (!line.startsWith("#") && !line.isBlank()) {
                    int indent = line.indexOf(line.trim()) / SPACE_COUNT;
                    while (indent < codeBlocks.size()) {
                        if (indent == 0) {
                            fis.close();
                            return codeBlocks.firstElement().getTotalComplexity();
                        } else {
                            CodeBlock oldTop = codeBlocks.pop();
                            Complexity oldTopComplexity =
                                    oldTop.getTotalComplexity();
                            //Test CompareComplexity()
                            if (oldTop.compareComplexity(codeBlocks.peek().getHighestSubComplexity())) {
                                codeBlocks.peek().setHighestSubComplexity(oldTopComplexity);
                                System.out.println("Leaving block " +
                                        oldTop.getName() + ", updating block " +
                                        codeBlocks.peek().getName());
                                System.out.println(codeBlocks.peek());
                            }
                            else {
                                System.out.println("Leaving block " + oldTop.getName() + ", nothing to update.");
                                System.out.println(codeBlocks.peek().toString());
                            }
                        }
                    }


                    // If starting with keyword => new codeblock
                    if (doesLineStartWithKeyword(line)) {
                        String newName;
                        if (codeBlocks.size() == 0) {
                            newName = "1";
                        } else {
                            CodeBlock oldTop = codeBlocks.peek();
                            String nameSoFar = oldTop.getName();
                            int lastNameSegment = oldTop.getNumChildren() + 1;
                            newName = nameSoFar + "." + lastNameSegment;
                            oldTop.setNumChildren(oldTop.getNumChildren() + 1);
                        }
                        String[] tokens = line.trim().split(" ");
                        String keyword = tokens[0];
                        System.out.println("Entering block " + newName + " '" + keyword + "':");
                        CodeBlock newBlock;
                        switch (keyword) {

                            case "for" -> {
                                newBlock = new CodeBlock(newName,
                                        new Complexity(), new Complexity(),
                                        new Complexity(), null,
                                        1, 0);

                                String lastToken = tokens[3];
                                if (lastToken.contains("log"))
                                    newBlock.getBlockComplexity().setLogPower(1);
                                else
                                    newBlock.getBlockComplexity().setnPower(1);
                                newBlock.setTotalComplexity(newBlock.getBlockComplexity());
                                codeBlocks.push(newBlock);
                            }
                            case "while" -> {
                                String loopVar = findLoopVar(line);

                                newBlock = new CodeBlock(newName,
                                        new Complexity(), new Complexity(),
                                        new Complexity(), loopVar,
                                        2, 0);
                                codeBlocks.push(newBlock);
                            }
                            default -> {
                                //Reminder: Determine what block type here
                                int blockType = -1;
                                for (int i = 0; i < CodeBlock.BLOCK_TYPES.length; i++) {
                                    if (keyword.equals(CodeBlock.BLOCK_TYPES[i])) {
                                        blockType = i;
                                    }
                                }

                                newBlock = new CodeBlock(newName,
                                        new Complexity(), new Complexity(),
                                        new Complexity(), null,
                                        blockType, 0);
                                codeBlocks.push(newBlock);

                            }
                        }
                        System.out.println(newBlock);
                        // otherwise, normal code, unless it updates loopVariable
                    } else {
                        CodeBlock oldTop = codeBlocks.peek();

                        // if it is inside a while loop
                        if (oldTop.getBlockType() == 2) {
                            String loopVar = oldTop.getLoopVariable();
                            if (doesLineContainLoopVariableUpdate(line,
                                    loopVar)) {
                                boolean isHalving = doesLineHaveLogNUpdate(line
                                );
                                if (isHalving) {
                                    //Check for what the actual complexity is
                                    oldTop.getBlockComplexity().setLogPower(1);
                                } else {
                                    //Check for what the actual complexity is
                                    oldTop.getBlockComplexity().setnPower(1);
                                }
                                oldTop.setTotalComplexity(oldTop.getBlockComplexity());
                                System.out.println("Found update statement, " +
                                        "updating block " + oldTop.getName());
                                System.out.println(oldTop);
                            }
                        }
                    }
                }
                    line = reader.readLine();
            }
            while (codeBlocks.size() > 1) {
                CodeBlock oldTop = codeBlocks.pop();
                Complexity oldTopComplexity = oldTop.getBlockComplexity();
                if (oldTop.compareComplexity(codeBlocks.peek().getHighestSubComplexity()))
                    codeBlocks.peek().setBlockComplexity(oldTopComplexity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found. Please try again.");
        } catch (IOException e) {
            System.out.println("File has no more lines.");
        }
        return codeBlocks.pop().getTotalComplexity();
    }

    /**
     * Determines whether the line being read has a loop variable being updated.
     * @param line
     * Line read from a file as a string.
     * @param loopVar
     * A loop variable to match against.
     * @return
     * True if loop variable is found and is being updated.
     */
    public static boolean doesLineContainLoopVariableUpdate(String line,
                                                      String loopVar) {
        String[] lineTokens = line.trim().split(" ");
        boolean doesLineContainLoopVar = lineTokens[0].equals(loopVar);
        boolean isOperation = lineTokens[1].charAt(1) == '=';
        return doesLineContainLoopVar && isOperation;
    }

    /**
     * Determines whether the line being read from a file updates a loop
     * variable by division, making the while loop have a big-O notation of O
     * (log(n)).
     * @param line
     * Line being read from file as a string.
     * @return
     * True if there exists division in the line.
     */
    public static boolean doesLineHaveLogNUpdate(String line) {
        String[] lineTokens = line.trim().split(" ");
        return lineTokens[1].equals("/=");
    }

    /**
     * Determines whether the line being read from a file starts with a
     * keyword: "def", "for", "while", "if", "elif", or "else".
     * @param line
     * Line being read from the file as a string.
     * @return
     * True if the line starts with a keyword. False if keyword is not found.
     */
    public static boolean doesLineStartWithKeyword(String line) {
        String firstToken = line.trim().split(" ")[0];
        boolean isKeyword = false;
        for (String key :
                CodeBlock.BLOCK_TYPES) {
            if(key.equals(firstToken)) {
                isKeyword = true;
                break;
            }
        }
        return isKeyword;
    }

    /**
     * Finds the loop variable for a "while" loop in a line read from a file.
     * @param line
     * Line being read from file as a string.
     * @return
     * The loop variable as a string.
     */
    public static String findLoopVar(String line) {
        return line.trim().split(" ")[1];
    }
}
