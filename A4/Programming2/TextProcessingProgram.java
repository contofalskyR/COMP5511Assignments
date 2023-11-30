package Programming2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


// trieNode class 
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // assuming only lowercase letters
    boolean isEndOfWord;
    Set<Integer> paragraphNumbers; // to store paragraph numbers

    TrieNode() {
        isEndOfWord = false;
        paragraphNumbers = new HashSet<>(); // initialize the set for paragraph numbers
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
        
    }
}

public class TextProcessingProgram {

    public static void main(String[] args) {
        // reading files
        String textFilePath = "The_State_of_Data_Final.txt";
        String noiseWordsFilePath = "noise_words.txt";
        
        
        TrieNode root = new TrieNode(); // root of the trie
        Set<String> noiseWords = new HashSet<>(); // initialize variable for noiseWords
        int paragraphNumber = 0; // initialize paragraph number

        
        loadNoiseWords(noiseWordsFilePath, noiseWords); // loading noisewords


         // process the State of Data Final text file, removing noise words and building the trie
         try (BufferedReader textReader = new BufferedReader(new FileReader(textFilePath))) {
            String line;
            StringBuilder paragraph = new StringBuilder(); // initialize variable for paragraphs
            while ((line = textReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (paragraph.length() > 0) {
                        processParagraph(root, paragraph.toString(), ++paragraphNumber, noiseWords);
                        paragraph.setLength(0); // resets paragraph builder
                    }
                } else {
                    paragraph.append(line).append(" "); // append line to the current paragraph
                }
            }

            // process the last paragraph if not empty
            if (paragraph.length() > 0) {
                processParagraph(root, paragraph.toString(), ++paragraphNumber, noiseWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the cleaned text...
        // printTrie(root, new StringBuilder());

        Scanner scanner = new Scanner(System.in);

        // getting user input for conjunctive search
        System.out.println("Enter keywords for conjunctive search (separated by space): ");
        String conjunctiveInput = scanner.nextLine();
        String[] conjunctiveKeywords = conjunctiveInput.toLowerCase().split("\\s+");

        // getting user input for disjunctive search
        System.out.println("Enter keywords for disjunctive search (separated by space): ");
        String disjunctiveInput = scanner.nextLine();
        String[] disjunctiveKeywords = disjunctiveInput.toLowerCase().split("\\s+");

        // perform searches
        Set<Integer> conjunctiveResult = searchConjunctive(root, conjunctiveKeywords);
        Set<Integer> disjunctiveResult = searchDisjunctive(root, disjunctiveKeywords);

        // Displaying results
        System.out.println("Conjunctive Search Result: " + conjunctiveResult);
        System.out.println("Disjunctive Search Result: " + disjunctiveResult);

        scanner.close();
    }

    // Function to insert a word into the trie with paragraph number
    private static void insertWord(TrieNode root, String word, int paragraphNumber) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                // skip any non-alphabetic characters
                continue;
            }
            int index = Character.toLowerCase(c) - 'a';
            if (index < 0 || index >= 26) {
                // ensure the index is within bounds
                continue;
            }
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
        current.paragraphNumbers.add(paragraphNumber); // add the paragraph number to the set 
    }

    // print the cleaned text
    private static void printTrie(TrieNode node, StringBuilder currentWord) {
        if (node.isEndOfWord) {
            System.out.println(currentWord.toString() + " " + node.paragraphNumbers);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            int index = c - 'a';
            if (node.children[index] != null) {
                currentWord.append(c);
                printTrie(node.children[index], currentWord);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    // Search for paragraphs where all keywords are present
private static Set<Integer> searchConjunctive(TrieNode root, String[] keywords) {
    Set<Integer> result = null;
    for (String keyword : keywords) {
        Set<Integer> paragraphs = searchWord(root, keyword);
        if (result == null) {
            result = new HashSet<>(paragraphs);
        } else {
            result.retainAll(paragraphs); // intersection between both sets 
        }
    }
    return result == null ? new HashSet<>() : result;
}

// Search for paragraphs where any of the keywords are present
private static Set<Integer> searchDisjunctive(TrieNode root, String[] keywords) {
    Set<Integer> result = new HashSet<>();
    for (String keyword : keywords) {
        Set<Integer> paragraphs = searchWord(root, keyword);
        result.addAll(paragraphs); // union operation both sets 
    }
    return result;
}

// Search for a word in the trie and return the paragraph numbers
private static Set<Integer> searchWord(TrieNode root, String word) {
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

private static void processParagraph(TrieNode root, String paragraph, int paragraphNumber, Set<String> noiseWords) {
    String[] words = paragraph.toLowerCase().split("\\s+"); // split paragraphs into words
    for (String word : words) {
        if (!word.isEmpty() && !noiseWords.contains(word)) {
            insertWord(root, word, paragraphNumber); // insert words into the trie 
        }
    }
}
    private static void loadNoiseWords(String noiseWordsFilePath, Set<String> noiseWords) {
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
