package plugins;

import core.TextPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Finds words that read the same forward and backward.
public class PalindromePlugin implements TextPlugin {
    // Provides the name accepted by the application's --plugin option.
    @Override
    public String name() {
        return "palindrome";
    }

    // Provides a short description for the plugin list.
    @Override
    public String description() {
        return "Finds palindromes in the input.";
    }

    // Returns the original forms of palindrome words, separated by commas.
    @Override 
    public String process(String input) {
        List<String> palindromes = new ArrayList<>();

        // Ignore punctuation and compare words without regard to letter case.
        for(String word: input.split("\\s+")) {
            String cleanedWord = word
                                 .replaceAll("[^\\p{L}\\p{N}]", "")
                                 .toLowerCase(Locale.ROOT);

            if(cleanedWord.length() > 1 && isPalindrome(cleanedWord)) {
                palindromes.add(word);
            }
        }

        return String.join(", ", palindromes);
    }

    // Compares matching characters from the start and end until they meet.
    private boolean isPalindrome(String word) {
        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if(word.charAt(left) != word.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
