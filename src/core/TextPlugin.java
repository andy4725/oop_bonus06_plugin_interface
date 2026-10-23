package core;

public interface TextPlugin {
    String name();
    String description();
    String process(String input);
    default boolean supports(String input) {
        return input != null;
    }
}
