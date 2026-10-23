package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginPipeline {
    private final Map<String, TextPlugin> plugins;

    public PluginPipeline(Map<String, TextPlugin> plugins) {
        this.plugins = plugins;
    }

    public List<String> getPluginNames() {
        return new ArrayList<>(plugins.keySet());
    }

    public String process(String input, List<String> pluginNames) {
        String result = input;

        for(String pluginName: pluginNames) {
            TextPlugin plugin = plugins.get(pluginName);

            if(plugin == null) {
                System.err.println("Unknown plugin: " + pluginName);
                continue;
            }

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
