package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Applies selected text plugins sequentially while allowing the pipeline to continue after failures.
public class PluginPipeline {
    private final Map<String, TextPlugin> plugins;

    // Keeps the available plugins indexed by their command names.
    public PluginPipeline(Map<String, TextPlugin> plugins) {
        this.plugins = plugins;
    }

    // Returns plugin names in the order they were registered.
    public List<String> getPluginNames() {
        return new ArrayList<>(plugins.keySet());
    }

    // Passes each plugin's output to the next requested plugin.
    public String process(String input, List<String> pluginNames) {
        String result = input;

        for(String pluginName: pluginNames) {
            TextPlugin plugin = plugins.get(pluginName);

            if(plugin == null) {
                System.err.println("Unknown plugin: " + pluginName);
                continue;
            }

            // Skip unsupported input and isolate failures to the plugin that caused them.
            try {
                if(plugin.supports(result)) {
                    result = plugin.process(result);
                }
            } catch (Exception exception) {
                System.err.println(
                    "Plugin '" + pluginName + "' failed and was skipped: " +
                    exception.getMessage()
                );
            }
        }

        return result;
    }
}
