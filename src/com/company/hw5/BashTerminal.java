package com.company.hw5;

import java.util.Scanner;

public class BashTerminal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userID = "[114578879@Lovelace]: $ ";
        String choice;
        DirectoryTree directoryTree = new DirectoryTree();
        System.out.println("Starting bash terminal.");
        do {
            System.out.print(userID);
            choice = input.nextLine();
            String[] tokens = choice.split(" ");
            String command = tokens[0];

            switch (command) {
                case "pwd": {
                    System.out.println(directoryTree.presentWorkingDirectory());
                    break;
                }

                case "ls": {
                    if (tokens.length > 1) {
                        if (tokens[1].equals("-R"))
                            directoryTree.printDirectoryTree();
                    }
                    else
                        System.out.println(directoryTree.listDirectory());
                    break;
                }
                case "cd": {
                    String arg = tokens[1];
                    switch (arg) {
                        case "..": {
                            directoryTree.changeDirectoryToParent();
                            break;
                        }
                        case "/": {
                            directoryTree.resetCursor();
                        }
                        default: {
                            if (tokens[1].contains("/")) {
                                // EXTRA

                            } else {
                                try {
                                    directoryTree.changeDirectory(arg);
                                } catch (NotADirectoryException e) {
                                    System.out.println("ERROR: Cannot change directory into a file.");
                                }
                            }
                        }
                    }
                    break;
                }

                case "mkdir": {
                    String arg = tokens[1];
                    try {
                        directoryTree.makeDirectory(arg);
                    } catch (FullDirectoryException exception) {
                        System.out.println("ERROR: Present directory is full.");
                    }
                    break;
                }

                case "touch": {
                    String arg = tokens[1];

                    try {
                        directoryTree.makeFile(arg);
                    } catch (FullDirectoryException exception) {
                        System.out.println("ERROR: Present directory is full.");
                    }
                    break;
                }
                case "find": {
                    String arg = tokens[1];
                    DirectoryNode found = (directoryTree.search(arg));
                    if (found == null) {
                        System.out.println("ERROR: No such file exists.");
                    } else {
                        System.out.println(directoryTree.getFullPath(found));
                    }
                    break;
                    // EXTRA
                }

                case "mv": {
                    String src = tokens[1];
                    String dst = tokens[2];
                    directoryTree.moveNode(src, dst);
                    break;
                }

                case "exit": {
                    System.out.println("Program terminating normally");
                    break;
                }
            }

        } while (!choice.equalsIgnoreCase("exit"));
    }


}
