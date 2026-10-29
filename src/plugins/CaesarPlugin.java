package plugins;

import core.TextPlugin;

// Shifts English alphabetic characters forward by three positions.
public class CaesarPlugin implements TextPlugin {
    // Provides the name accepted by the application's --plugin option.
    @Override 
    public String name() {
        return "caesar";
    }    

    // Provides a short description for the plugin list.
    @Override 
    public String description() {
        return "Shifts English letters forward by three positions.";
    }

    // Preserves non-English letters and punctuation while shifting ASCII letters.
    @Override 
    public String process(String input) {
        StringBuilder result = new StringBuilder();

        // Wrap each letter around to the start of its uppercase or lowercase alphabet.
        for(char character: input.toCharArray()) {
            if(character >= 'a' && character <= 'z') {
                result.append((char)('a' + (character - 'a' + 3) % 26));
            } else if(character >= 'A' && character <= 'Z') {
                result.append((char)('A' + (character - 'A' + 3) % 26));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}
