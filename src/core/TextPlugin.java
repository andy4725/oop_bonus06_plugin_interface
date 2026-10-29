package core;

// Defines the common contract used by all text-processing plugins.
public interface TextPlugin {
    // Returns the command-line name used to select this plugin.
    String name();

    // Describes the plugin's behavior to users.
    String description();

    // Processes the supplied text and returns the result.
    String process(String input);

    // Allows a plugin to decline input it cannot process.
    default boolean supports(String input) {
        return input != null;
    }
}
