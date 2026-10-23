package plugins;

import core.TextPlugin;
import java.text.Normalizer;

public class AccentlessPlugin implements TextPlugin {
    @Override
    public String name() {
        return "Accentless";
    }

    @Override
    public String description() {
        return "Removes accents from the input";
    }

    @Override
    public String process(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}