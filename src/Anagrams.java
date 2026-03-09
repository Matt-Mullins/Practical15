//Matthew Mullins
//4397399
//09 March 2026
//Practical 5

import java.io.*;
import java.util.*;

public class Anagrams {

    // Function to create the signature (alphabetically sorted letters)
    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Anagrams ulysses.text");
            return;
        }

        String inputFile = args[0];

        // HashMap to store signature -> list of words
        HashMap<String, ArrayList<String>> dictionary = new HashMap<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] words = line.split("\\s+");

                for (String w : words) {

                    // Clean word but keep apostrophes
                    w = w.replaceAll("[\\[\\]0123456789(),.;:_!?\\-]", "");

                    w = w.toLowerCase();

                    if (w.length() == 0)
                        continue;

                    String key = signature(w);

                    if (!dictionary.containsKey(key)) {
                        dictionary.put(key, new ArrayList<>());
                    }

                    dictionary.get(key).add(w);
                }
            }

            reader.close();

            // Print all anagram groups
            for (String key : dictionary.keySet()) {
                ArrayList<String> list = dictionary.get(key);

                if (list.size() > 1) {
                    System.out.println(key + " -> " + list);
                }
            }

            // Create LaTeX output file
            PrintWriter tex = new PrintWriter(new FileWriter("theAnagrams.tex"));

            char currentLetter = ' ';

            ArrayList<String> sortedKeys = new ArrayList<>(dictionary.keySet());
            Collections.sort(sortedKeys);

            for (String key : sortedKeys) {

                ArrayList<String> words = dictionary.get(key);

                if (words.size() > 1) {

                    Collections.sort(words);

                    char firstLetter = words.get(0).charAt(0);

                    if (firstLetter != currentLetter) {
                        currentLetter = firstLetter;
                        tex.println();
                        tex.println("\\vspace{14pt}");
                        tex.println("\\noindent\\textbf{\\Large " +
                                Character.toUpperCase(currentLetter) +
                                "}\\\\*[+12pt]");
                    }

                    for (String word : words) {
                        tex.print(word + " ");
                    }

                    tex.println("\\\\");
                }
            }

            tex.close();

            System.out.println("Anagram dictionary written to theAnagrams.tex");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
