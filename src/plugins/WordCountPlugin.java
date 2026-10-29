package plugins;

import core.TextPlugin;

// Counts whitespace-separated words in the supplied text.
public class WordCountPlugin implements TextPlugin {
    // Provides the name accepted by the application's --plugin option.
    @Override
    public String name() {
        return "counts";
    }

    // Provides a short description for the plugin list.
    @Override 
    public String description() {
        return "Counts words in the input.";
    }

    // Returns zero for blank text; otherwise counts non-empty whitespace-separated parts.
    @Override 
    public String process(String input) {
        String trimmedInput = input.trim();

        if(trimmedInput.isEmpty()) {
            return "0";
        }

        return String.valueOf(trimmedInput.split("\\s+").length);
    }
}
