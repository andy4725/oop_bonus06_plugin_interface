package plugins;

import core.TextPlugin;
import java.util.Locale;

// Converts the input text to uppercase.
public class UppercasePlugin implements TextPlugin {
    // Provides the name accepted by the application's --plugin option.
    @Override 
    public String name() {
        return "uppercase";
    }

    // Provides a short description for the plugin list.
    @Override 
    public String description() {
        return "Converts all letters to uppercase";
    }

    // Uses a stable locale so the result does not depend on the machine's language.
    @Override 
    public String process(String input) {
        if(input.isEmpty())
            throw new IllegalArgumentException("Input can't be empty.");

        return input.toUpperCase(Locale.ROOT);
    }
}
