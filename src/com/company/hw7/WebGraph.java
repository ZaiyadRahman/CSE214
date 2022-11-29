package com.company.hw7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WebGraph {
    public static int MAX_PAGES = 40;
    private ArrayList<WebPage> pages;
    private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
    private int numPages;

    public WebGraph() {
        pages = new ArrayList<>();
    }

    public WebGraph(ArrayList<WebPage> pages, int[][] edges) {
        this.pages = pages;
        this.edges = edges;
    }

        // TODO: Implement this method
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException {
        WebGraph graph = new WebGraph();
        try {
            FileInputStream fis = new FileInputStream(pagesFile);
            InputStreamReader inStream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inStream);

            while (reader.ready()) {
                String line = reader.readLine();
                String[] parts = line.split(" ");
                String pageName = parts[0];
                parts = Arrays.copyOfRange(parts, 1, parts.length);
                graph.addPage(pageName, parts);
            }

            fis = new FileInputStream(linksFile);
            inStream = new InputStreamReader(fis);
            reader = new BufferedReader(inStream);

            while (reader.ready()) {
                String line = reader.readLine();
                String[] parts = line.split(" ");
                String srcURL = parts[0];
                String dstURL = parts[1];
                graph.addLink(srcURL, dstURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public void addPage(String url, String[] keywords) throws IllegalArgumentException {
        if (pages.size() >= MAX_PAGES) {
            throw new IllegalArgumentException("Maximum number of pages reached");
        }
        for (WebPage page : this.pages) {
            if (page.getUrl().equals(url)) {
                throw new IllegalArgumentException();
            }
        }
        pages.add(new WebPage(url, this.pages.size(), 1, keywords));
        numPages++;
        // TODO add another row and column to edges arraylist
    }

    public void addLink(String source, String destination) throws IllegalArgumentException {
        int sourceIndex = -1;
        int destinationIndex = -1;
        for (WebPage page : this.pages) {
            if (page.getUrl().equals(source)) {
                sourceIndex = page.getIndex();
            }
            if (page.getUrl().equals(destination)) {
                destinationIndex = page.getIndex();
            }
        }
        if (sourceIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException();
        }
        this.edges[sourceIndex][destinationIndex] = 1;
    }

    public void removePage(String url) throws IllegalArgumentException {
        int index = -1;
        for (WebPage page : this.pages) {
            if (page.getUrl().equals(url)) {
                index = page.getIndex();
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        pages.remove(index);
        for (int i = 0; i < numPages - index; i++) {
            edges[i][index + 1] = edges[i][index];
            edges[index + 1][i] = edges[index][i];
        }
        numPages--;
        // FIXME Make sure the removal from adjacency matrix works
    }

    public void removeLink(String source, String destination) {
        int sourceIndex = -1;
        int destinationIndex = -1;
        for (WebPage page : this.pages) {
            if (page.getUrl().equals(source)) {
                sourceIndex = page.getIndex();
            }
            if (page.getUrl().equals(destination)) {
                destinationIndex = page.getIndex();
            }
        }
        if (sourceIndex != -1 && destinationIndex != -1) {
        this.edges[sourceIndex][destinationIndex] = 0;
        }
    }

    public void updatePageRanks() {
        for (WebPage page : this.pages) {
            int index = page.getIndex();
            int rank = 0;
            for (int i = 0; i < numPages; i++) {
                if (edges[i][index] == 1) {
                    rank++;
                }
            }
            page.setRank(rank);
        }
    }

    public void printTable() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Index     URL               " +
                "PageRank  Links               Keywords\n");
        sb.append(
                "------------------------------------------------------------" +
                        "------------------------------------------\n");

        for (WebPage page :
                pages) {
            sb.append(page.toString().replace("***", this.getPageLinks(page)));
        }
        return sb.toString();
    }

    public String getPageLinks(WebPage page) {
        int index = page.getIndex();
        StringBuilder links = new StringBuilder(" ");
        for (int i = 0; i < numPages; i++) {
            if (edges[i][index] == 1) {
                links.append(i).append(", ");
            }
            if(i + 1 == numPages)
                links = new StringBuilder(links.toString().replace(links.substring(links.length() - 3), " "));
        }
        return links.toString();
    }

    public String search(String keyword) {
        StringBuilder sb = new StringBuilder("Rank   PageRank    URL\n");
        sb.append("---------------------------------------------\n");
        int counter = 1;
        ArrayList<WebPage> keywordFound = new ArrayList<>();
        for (WebPage page : pages) {
            for (String word : page.getKeywords()) {
                if (word.equals(keyword)) {
                    keywordFound.add(page);
                }
            }
        }
        keywordFound.sort(new RankComparator());
        for (WebPage page : keywordFound) {
            sb.append(String.format("%-5d%-11d%s\n", counter, page.getRank(),
                    page.getUrl()));
            counter++;
        }
        return sb.toString();
    }

    public int getNumPages() {
        return numPages;
    }

    public ArrayList<WebPage> getPages() {
        return pages;
    }
}