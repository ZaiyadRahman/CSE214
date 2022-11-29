package com.company.hw7;

import java.util.ArrayList;

public class WebPage {
    private String url;
    private int index;
    private int rank;
    private String[] keywords;

    public WebPage(String url, int index, int rank, String[] keywords) {
        this.url = url;
        this.index = index;
        this.rank = rank;
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return String.format("%6s%21s%10s%20s%45s", index, url, rank, "***",
                keywords.toString().replace("[", "").replace("]", ""));
    }
}

//   0   | google.com        |   rank  |     |***|        search, knowledge,tech
//  0   | google.com         |    2    | 1, 2, 3, 4, 5, 6  | search, knowledge, tech
//  1   | stonybrook.edu     |    1    | 0, 2              | university, seawolf, knowledge, journalism
//  2   | cnn.com            |    3    | 3                 | news, current, world, journalism
//  3   | facebook.com       |    3    | 0, 2, 4           | social, friends, tech
//  4   | youtube.com        |    2    | 3                 | video, music, cats, tech
//  5   | wikipedia.org      |    2    |                   | knowledge, encyclopedia
//  6   | stackoverflow.com  |    1    | 5                 | forum, knowledge, social