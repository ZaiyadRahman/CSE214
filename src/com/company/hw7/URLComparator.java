package com.company.hw7;

import java.util.Comparator;

public class URLComparator implements Comparator {
    public static void main(String[] args) {

    }


    public int compare(Object o1, Object o2) {
        WebPage wp1 = (WebPage) o1;
        WebPage wp2 = (WebPage) o2;
        return wp1.getUrl().compareTo(wp2.getUrl());
    }
}
