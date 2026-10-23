package plugins;

import core.TextPlugin;

public class CaesarPlugin implements TextPlugin {
    @Override 
    public String name() {
        return "Caesar";
    }    

    @Override 
    public String description() {
        return "Shifts English letters forward by three positions.";
    }

    @Override 
    public String process(String input) {
        StringBuilder result = new StringBuilder();

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
