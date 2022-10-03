package com.company.hw3;

import java.io.*;
import java.util.Stack;

public class PythonTracer {
    public static void main(String[] args) {

    }

    public static final int SPACE_COUNT = 4;

    public static Complexity traceFile(String filename) {
        try {
            Stack<CodeBlock> codeBlocks = new Stack<>();

            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader inStream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inStream);
            String data = reader.readLine();

            while (data != null) {

                if (!data.startsWith("#")) {
                    int indent = data.indexOf(data.trim()) / SPACE_COUNT;
                    while (indent < codeBlocks.size()) {
                        if (indent == 0) {
                            fis.close();
                            return codeBlocks.firstElement().getBlockComplexity();
                        } else {
                            CodeBlock oldTop = codeBlocks.pop();
                            Complexity oldTopComplexity =
                                    oldTop.getBlockComplexity();
                            if (oldTop.compareComplexity(codeBlocks.peek().getHighestSubComplexity()))
                                codeBlocks.peek().setHighestSubComplexity(oldTopComplexity);
                        }
                    }

                    if (data.startsWith("    def ") || data.contains("    for ")
                            || data.contains("    while ") || data.contains(
                            "    if ") || data.contains("    elif ") ||
                            data.contains("    else ")) {
                        String keyword = "";
                        if (data.contains(" def ")) {
                            keyword = "def";
                        } else if (data.contains(" for ")) {
                            keyword = "for";
                            codeBlocks.push(new CodeBlock("1",
                                    new Complexity(), new Complexity(), null));
                            if (data.endsWith("N:"))
                                codeBlocks.peek().getBlockComplexity().setnPower(1);
                            else if (data.endsWith("log_N"))
                                codeBlocks.peek().getBlockComplexity().setLogPower(1);
                        } else if (data.contains(" while ")) {
                            keyword = "while";
                            codeBlocks.push(new CodeBlock("1",
                                    new Complexity(), new Complexity(), null));
                            if (data.contains(">")) {
                                int i = data.indexOf(">");
                                String var = data.substring(11, i);
                                codeBlocks.peek().setLoopVariable(var);
                                codeBlocks.peek().getBlockComplexity().setnPower(0);
                                codeBlocks.peek().getBlockComplexity().setLogPower(0);
                            }
                            if (data.contains("<")) {
                                int i = data.indexOf("<");
                                String var = data.substring(11, i);
                                codeBlocks.peek().setLoopVariable(var);
                                codeBlocks.peek().getBlockComplexity().setnPower(0);
                                codeBlocks.peek().getBlockComplexity().setLogPower(0);
                            }

                            if (!keyword.equals("for") & !keyword.equals("while"))
                                codeBlocks.push(new CodeBlock("1",
                                        new Complexity(), null, null));

                        } else if (codeBlocks.peek().getName().trim().equals("while")
                                && data.contains(codeBlocks.peek().getLoopVariable()))
                            codeBlocks.peek().getBlockComplexity().setnPower(1);
                    } else
                        data = reader.readLine();
                }
            }

            while(codeBlocks.size() > 1) {
                CodeBlock oldTop = codeBlocks.pop();
                Complexity oldTopComplexity = oldTop.getBlockComplexity();
                if(oldTop.compareComplexity(codeBlocks.peek().getHighestSubComplexity()))
                    codeBlocks.peek().setBlockComplexity(oldTopComplexity);
            }

            return codeBlocks.pop();


        } catch (FileNotFoundException e) {
            System.out.println("File was not found. Please try again.");
        } catch (IOException e) {
            System.out.println("File has no more lines.");
        }
    }

}
