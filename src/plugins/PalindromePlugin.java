package plugins;

import core.TextPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PalindromePlugin implements TextPlugin {
    @Override
    public String name() {
        return "palindrome";
    }

    @Override
    public String description() {
        return "Finds palindromes in the input.";
    }

    @Override 
    public String process(String input) {
        List<String> palindromes = new ArrayList<>();

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
