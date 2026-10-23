package plugins;

import core.TextPlugin;
import java.util.Locale;

public class UppercasePlugin implements TextPlugin {
    @Override 
    public String name() {
        return "uppercase";
    }

    @Override 
    public String description() {
        return "Converts all letters to uppercase";
    }

    @Override 
    public String process(String input) {
        if(input.isEmpty())
            throw new IllegalArgumentException("Input can't be empty.");

        return input.toUpperCase(Locale.ROOT);
    }
}
