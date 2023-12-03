package Programming2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



// trieNode class
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // assuming only lowercase letters
    boolean isEndOfWord; // checks if the current node in the trie represents the end of a complete word or not
    Set<Integer> paragraphNumbers; // to store paragraph numbers

    TrieNode() {
        isEndOfWord = false;
        paragraphNumbers = new HashSet<>(); // initialize the set for paragraph numbers
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
    }
}


// text processing program 
public class TextProcessingProgram {
    private static TrieNode root = new TrieNode(); // root of the trie
    private static Set<String> noiseWords = new HashSet<>(); // initialize variable for noise words
    private static List<String> paragraphs = new ArrayList<>(); // initialize a list to store paragraphs

    public static void main(String[] args) {
        // reading files
        String textFilePath = "The_State_of_Data_Final.txt";
        String noiseWordsFilePath = "noise_words.txt";

        loadNoiseWords(noiseWordsFilePath); // loading noise words from the file 

        // process the text file, remove noise words and build the trie
        try (BufferedReader textReader = new BufferedReader(new FileReader(textFilePath))) {
            String line; // declaring variable to store each line of text read from the file
            StringBuilder paragraph = new StringBuilder(); // initialize variable for paragraphs which will then be used to construct paragraphs from the lines of text
            int paragraphNumber = 0; // initialize paragraph number

            while ((line = textReader.readLine()) != null) { // loop running as long as there are more lines to read in the file 
                if (line.trim().isEmpty()) { // check if the given line consists only of white space (empty)
                    if (paragraph.length() > 0) {
                        processParagraph(paragraph.toString(), ++paragraphNumber);
                        paragraph.setLength(0); // resets paragraph builder
                    }
                } else {
                    paragraph.append(line).append(" "); // Append line to the current paragraph
                }
            }
            // process the last paragraph if not empty
            if (paragraph.length() > 0) {
                processParagraph(paragraph.toString(), ++paragraphNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        // Perform searches
        String[] keywords;

        // // Query 1 single keyword
        // String keyword1 = "urban";
        // Set<Integer> query1Result = searchSingle(keyword1);
        // displayResults("Query 1 (Single Keyword: " + keyword1 + ")", query1Result);

        // Query 2 conjunctive search
        // keywords = new String[]{"urban", "data"};
        // Set<Integer> query2Result = searchConjunctive(keywords);
        // displayResults("Query 2 (Conjunctive Search: " + String.join(", ", keywords) + ")", query2Result);

        // Query 3 disjunctive search
        // keywords = new String[]{"growth", "rate"};
        // Set<Integer> disjunctiveResult = searchDisjunctive(keywords);
        // displayResults("Query 3 (Disjunctive Search: " + String.join(", ", keywords) + ")", disjunctiveResult);

        // Query 4 keyword1 and (keyword2 or keyword3)
        // keywords = new String[]{"bullying", "growth", "harassment"};
        // Set<Integer> conjunctiveWithOrResult = searchConjunctiveWithOr(keywords);
        // displayResults("Query 4 (Conjunctive with 'Or': " + String.join(", ", keywords) + ")", conjunctiveWithOrResult);

        // // Query 5 keyword1 or (keyword2 and keyword3)
        // keywords = new String[]{"data", "urban", "way"};
        // Set<Integer> disjunctiveWithAndResult = searchDisjunctiveWithAnd(keywords);
        // displayResults("Query 5 (Disjunctive with 'And': " + String.join(", ", keywords) + ")", disjunctiveWithAndResult);
    }



   // function to process a paragraph, split into words, and insert into the trie
        private static void processParagraph(String paragraph, int paragraphNumber) {
        // split the paragraph into words and keep only alphanumeric characters
        String[] words = paragraph.toLowerCase().split("[^a-zA-Z0-9]+");

        for (String word : words) {
            if (!word.isEmpty() && !noiseWords.contains(word)) {
                insertWord(word, paragraphNumber); // inserting words into the trie
            }
        }
        // store the cleaned paragraph in the list
        paragraphs.add(paragraph.replaceAll("[^a-zA-Z0-9]+", " "));
        }

        // function to insert a word into the trie with paragraph number
        private static void insertWord(String word, int paragraphNumber) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                continue; // skip any non-alphabetic characters
            }
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
        current.paragraphNumbers.add(paragraphNumber); // adding the paragraph number to the set
        }

        // Method to display search results
        private static void displayResults(String queryDescription, Set<Integer> paragraphNumbers) {
        System.out.println(queryDescription);
        if (paragraphNumbers.isEmpty()) {
            System.out.println("No results found.");
        } else {
        // Convert the set to a list and sort it
        List<Integer> sortedParagraphNumbers = new ArrayList<>(paragraphNumbers);
        Collections.sort(sortedParagraphNumbers);

        for (Integer paragraphNumber : sortedParagraphNumbers) {
            // Retrieve and display the paragraph
            String paragraph = getParagraphByNumber(paragraphNumber);
            System.out.println("Paragraph " + paragraphNumber + ": " + paragraph);
        }
    }
}


   // Function to search for paragraphs where all keywords are present (Conjunctive)
    private static Set<Integer> searchConjunctive(String[] keywords) {
        Set<Integer> result = null;
        for (String keyword : keywords) {
            keyword = keyword.toLowerCase(); // Convert the keyword to lowercase
            Set<Integer> paragraphs = searchWord(keyword);
            if (result == null) {
                result = new HashSet<>(paragraphs);
            } else {
                result.retainAll(paragraphs); // Intersection between both sets
            }
        }

        return result == null ? new HashSet<>() : result;
    }

    // Function to search for paragraphs where any of the keywords are present (Disjunctive)
    private static Set<Integer> searchDisjunctive(String[] keywords) {
        Set<Integer> result = new HashSet<>();
        for (String keyword : keywords) {
            keyword = keyword.toLowerCase(); // Convert the keyword to lowercase
            Set<Integer> paragraphs = searchWord(keyword);
            result.addAll(paragraphs); // Union operation both sets
        }

        return result;
    }

    // Function to search for paragraphs containing a single keyword
    private static Set<Integer> searchSingle(String keyword) {
        keyword = keyword.toLowerCase(); // Convert the keyword to lowercase
        Set<Integer> result = searchWord(keyword);

        return result;
    }

    // Function to search for paragraphs containing the first keyword and any one of the subsequent keywords
    private static Set<Integer> searchConjunctiveWithOr(String[] keywords) {
        if (keywords.length < 2) {
            return new HashSet<>(); // Not enough keywords for the query
        }

        Set<Integer> result = new HashSet<>();
        Set<Integer> firstKeywordParagraphs = searchWord(keywords[0].toLowerCase());

        // Creating a set for paragraphs containing any of the subsequent keywords
        Set<Integer> orKeywordsParagraphs = new HashSet<>();
        for (int i = 1; i < keywords.length; i++) {
            orKeywordsParagraphs.addAll(searchWord(keywords[i].toLowerCase()));
        }

        // Adding paragraphs that contain the first keyword and any one of the subsequent keywords
        for (Integer paragraphNumber : firstKeywordParagraphs) {
            if (orKeywordsParagraphs.contains(paragraphNumber)) {
                result.add(paragraphNumber);
            }
        }

        return result;
    }


    private static Set<Integer> searchDisjunctiveWithAnd(String[] keywords) {
        Set<Integer> result = new HashSet<>();

        if (keywords.length < 2) {
            return result; // Not enough keywords for the query
        }

        Set<Integer> firstKeywordParagraphs = searchWord(keywords[0]);
        result.addAll(firstKeywordParagraphs);

        for (int i = 1; i < keywords.length; i++) {
            Set<Integer> paragraphs = searchWord(keywords[i]);
            result.retainAll(paragraphs); // Intersection between both sets
        }

        // Print the paragraphs corresponding to the paragraph numbers
        for (Integer paragraphNumber : result) {
            String paragraph = getParagraphByNumber(paragraphNumber);
            System.out.println("Paragraph " + paragraphNumber + ": " + paragraph);
        }

        return result;
    }

    

    // function to retrieve a paragraph by its paragraph number 
    private static String getParagraphByNumber(int paragraphNumber) {
        if (paragraphNumber >= 1 && paragraphNumber <= paragraphs.size()) {
            return "\n" + paragraphs.get(paragraphNumber - 1) + "\n"; // adding newline characters for spacing
        } else {
            return "Paragraph not found"; // handling the case where the paragraph number is invalid
        }
    }


    // function to search for a word in the trie and return the paragraph numbers
    private static Set<Integer> searchWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) continue;
            int index = c - 'a';
            if (current.children[index] == null) {
                return new HashSet<>(); // Word not found
            }
            current = current.children[index];
        }
        return current.isEndOfWord ? current.paragraphNumbers : new HashSet<>();
    }

    // function to load noise words from the file path
    private static void loadNoiseWords(String noiseWordsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(noiseWordsFilePath))) {
            String noiseWord;
            while ((noiseWord = br.readLine()) != null) {
                noiseWords.add(noiseWord.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading noise words file: " + e.getMessage());
        }
    }
}
