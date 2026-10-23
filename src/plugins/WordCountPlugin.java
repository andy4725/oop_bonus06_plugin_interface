package plugins;

import core.TextPlugin;

public class WordCountPlugin implements TextPlugin {
    @Override
    public String name() {
        return "Counts";
    }

    @Override 
    public String description() {
        return "Counts words in the input.";
    }

    @Override 
    public String process(String input) {
        String trimmedInput = input.trim();

        if(trimmedInput.isEmpty()) {
            return "0";
        }

        return String.valueOf(trimmedInput.split("\\s+").length);
    }
}
