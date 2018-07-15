package com.springsecurity.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class WikipediaParser {

    private Document document;
    private String tag;

    public WikipediaParser(String url, String tag) throws IOException {
        this.tag = tag;
        this.document = Jsoup.connect(url).get();
    }

    public List<String> parse() {
        return document.getElementsByTag(tag).eachText();
    }
}
