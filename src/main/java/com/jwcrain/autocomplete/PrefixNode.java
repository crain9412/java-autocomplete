package com.jwcrain.autocomplete;

import java.util.HashMap;

public class PrefixNode {
    private HashMap<Character, PrefixNode> children;
    private char data;

    public PrefixNode(char data) {
        this.data = data;
        this.children = new HashMap<>();
    }

    public void addChild(char c) {
        PrefixNode child = new PrefixNode(c);
        this.children.put(c, child);
    }

    public HashMap<Character, PrefixNode> getChildren() {
        return this.children;
    }

    public PrefixNode getChild(char c) {
        return this.children.get(c);
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }
}
