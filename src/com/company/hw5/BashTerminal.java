/**
 * this <code>BashTerminal</code> class contains a single main method which
 * allows a user to interact with a file system implemented by an instance of
 * DirectoryTree using the following commands (note that commands are
 * case-sensitive and will always be lower-case):
 *
 * Command 	Description
 * pwd :	Print the "present working directory" of the cursor node (e.g.
 * root/home/user/Documents).
 *
 * ls :	List the names of all the child directories or files of the cursor.
 *
 * ls -R :	Recursive traversal of the directory tree. Prints the entire tree
 * starting from the cursor in pre-order traversal.
 *
 * cd {dir} :	Moves the cursor to the child directory with the indicated name
 * (Only consider the direct children of the cursor).
 *
 * cd / :	Moves the cursor to the root of the tree.
 *
 * mkdir {name} :	Creates a new directory with the indicated name as a
 * child of the cursor, as long as there is room.
 *
 * touch {name} :	Creates a new file with the indicated name as a child of
 * the cursor, as long as there is room.
 *
 * exit :	Terminates the program.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw5;

import java.util.Scanner;

public class BashTerminal {
    /**
     * The main method for the BashTerminal class.
     * @param args
     * The command line arguments.
     */
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
                    } catch (IllegalArgumentException exception) {
                        System.out.println("ERROR: Directory name cannot " +
                                "contain spaces or forward slashes.");
                    }
                    break;
                }

                case "touch": {
                    String arg = tokens[1];

                    try {
                        directoryTree.makeFile(arg);
                    } catch (FullDirectoryException exception) {
                        System.out.println("ERROR: Present directory is full.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("ERROR: File name cannot contain " +
                                "spaces or slashes.");
                    }
                    break;
                }
                case "find": {
                    String arg = tokens[1];
                    DirectoryNode found =
                            (directoryTree.findNode(directoryTree.getRoot(),
                                    arg));
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
