package plugins;

import core.TextPlugin;
import java.text.Normalizer;

// Removes combining accent marks from Unicode text.
public class AccentlessPlugin implements TextPlugin {
    // Provides the name accepted by the application's --plugin option.
    @Override
    public String name() {
        return "accentless";
    }

    // Provides a short description for the plugin list.
    @Override
    public String description() {
        return "Removes accents from the input";
    }

    // Decomposes accented characters, then removes their combining marks.
    @Override
    public String process(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}