package com.company.hw7;

import java.util.Scanner;

public class SearchEngine {
    public static void main(String[] args) {
        System.out.println("Loading WebGraph data...");
        SearchEngine se = new SearchEngine();
        se.web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
        System.out.println("Success!");
        Scanner input = new Scanner(System.in);
        String choice;
        SearchEngine.printMenu();
        do {
            choice = input.nextLine();
            switch (choice.toUpperCase()) {
                case "AP" -> {
                    //TODO add page
                    System.out.println("Enter a URL: ");
                    String url = input.nextLine();
                    System.out.println("Enter keywords (space-separated: ");
                    String keywords = input.nextLine();
                    String[] keywordArray = keywords.split(" ");
                    try {
                        se.web.addPage(url, keywordArray);
                        se.web.updatePageRanks();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    printMenu();
                }

                case "RP" -> {
                    //TODO remove page
                    System.out.println("Enter a URL: ");
                    String url = input.nextLine();
                    se.web.removePage(url);
                    se.web.updatePageRanks();
                    System.out.println(url + "has been removed from the " +
                            "graph!");
                    printMenu();
                }

                case "AL" -> {
                    //TODO add link src dst
                    System.out.println("Enter a source URL: ");
                    String src = input.nextLine();
                    System.out.println("Enter a destination URL: ");
                    String dst = input.nextLine();
                    se.web.addLink(src, dst);
                    se.web.updatePageRanks();
                    System.out.println("Link successfully added from " + src + " to " + dst + "!");
                    printMenu();
                }

                case "RL" -> {
                    //TODO remove link src dst
                    System.out.println("Enter a source URL: ");
                    String src = input.nextLine();
                    System.out.println("Enter a destination URL: ");
                    String dst = input.nextLine();
                    se.web.removeLink(src, dst);
                    se.web.updatePageRanks();
                    System.out.println("Link successfully removed from " + src + " to " + dst + "!");
                }

                case "P" -> {
                    System.out.println("(I) Sort based on index (ASC)\n" +
                            "(U) Sort based on URL (ASC)\n" +
                            "(R) Sort based on rank (DSC)\n" +
                            "\n" +
                            "Please select an option: ");
                    String sort = input.nextLine();
                    switch (sort.toUpperCase()) {
                        case "I" -> se.web.getPages().sort(new IndexComparator());
                        case "U" -> se.web.getPages().sort(new URLComparator());
                        case "R" -> se.web.getPages().sort(new RankComparator());
                    }
                    System.out.println(se.web);
                    printMenu();
                }

                case "S" -> {
                    //TODO search for pages with keyword
                    System.out.println("Search keyword: ");
                    String keyword = input.nextLine();
                    System.out.println(se.web.search(keyword));
                }
            }
        } while(choice.equalsIgnoreCase("Q"));
    }

    public static void printMenu() {
        System.out.println("(AP) - Add a new page to the graph.\n" +
                "(RP) - Remove a page from the graph.\n" +
                "(AL) - Add a link between pages in the graph.\n" +
                "(RL) - Remove a link between pages in the graph.\n" +
                "(P)  - Print the graph.\n" +
                "(S)  - Search for pages with a keyword.\n" +
                "(Q)  - Quit.");
        System.out.print("Please select an option: ");
    }

    public SearchEngine() {}
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private WebGraph web = new WebGraph();
}
