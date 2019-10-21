package com.jwcrain.autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrefixTrie {
    private static final char BEGIN_MARKER = '^';
    private static final char END_MARKER = '$';
    private PrefixNode root = new PrefixNode(BEGIN_MARKER);

    public PrefixTrie() {

    }

    public void insertWord(String s) {
        //System.out.printf("Inserting word %s\n", s);
        PrefixNode currentNode = this.root;

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            PrefixNode nextNode = currentNode.getChild(currentChar);

            if (nextNode != null) {
                currentNode = nextNode;
            } else {
                currentNode.addChild(currentChar);
                currentNode = currentNode.getChild(currentChar);
            }
        }

        currentNode.addChild(END_MARKER);
    }

    public void search(String s) {
        PrefixNode currentNode = this.root;
        List<String> answers = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            PrefixNode nextNode = currentNode.getChild(currentChar);

            if (nextNode != null) {
                //System.out.printf("Found entry for %c, proceeding\n", currentChar);
                stringBuilder.append(currentChar);
                currentNode = nextNode;
            } else {
                //System.out.printf("No matching entries found in tree for %s\n", stringBuilder.toString());
                return;
            }
        }

        System.out.printf("Printing all children of string %s\n", stringBuilder.toString());

        printAllChildren(currentNode, stringBuilder.toString());
    }

    /* Print all permutations of strings remaining */
    private void printAllChildren(PrefixNode currentNode, String currentString) {

        if (currentNode == null) {
            //System.out.printf("Current node is null, exiting\n");
            return;
        }

        for (Map.Entry<Character, PrefixNode> child : currentNode.getChildren().entrySet()) {
           // System.out.printf("Found child %c, proceeding\n", child.getKey());
            if (child.getKey() == '$') {
                System.out.printf("Found matching autocomplete: %s\n", currentString);
                return;
            }
            String substring = currentString + child.getKey();
            printAllChildren(child.getValue(), substring);
        }
    }

    public void print() {
        printSubtree(this.root, "");
    }

    private void printSubtree(PrefixNode prefixNode, String currentString) {
        if (prefixNode == null) {
            return;
        }

        for (Map.Entry<Character, PrefixNode> entry : prefixNode.getChildren().entrySet()) {
            if (entry.getKey() == '$') {
                System.out.printf("Found entire string %s\n", currentString);
            }
        }

        for (Map.Entry<Character, PrefixNode> entry : prefixNode.getChildren().entrySet()) {
            String substring = currentString + entry.getKey();
            printSubtree(entry.getValue(), substring);
        }
    }
}
