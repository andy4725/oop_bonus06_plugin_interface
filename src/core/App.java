package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

// Parses command-line options, loads plugins, and runs the requested text pipeline.
public class App {
    // Starts the application and reports command-line or input errors.
    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception exception) {
            System.err.println("Error: " + exception.getMessage());
            System.err.println(
                "Usage: java -cp build/core:build/plugins core.App "
                + "--plugin name1,name2 [--in input.txt] [--out output.txt]"
            );
        }
    }

    // Reads options, selects plugins, and routes the result to the console or a file.
    private static void run(String[] args) throws IOException {
        List<String> requestedPlugins = new ArrayList<>();
        String inputFile = null;
        String outputFile = null;

        // Collect the requested plugins and optional input/output file paths.
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--plugin":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("--plugin needs a value");
                    }
                    for (String name : args[++i].split(",")) {
                        if (!name.isBlank()) {
                            requestedPlugins.add(name.trim());
                        }
                    }
                    break;

                case "--in":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("--in needs a file path");
                    }
                    inputFile = args[++i];
                    break;

                case "--out":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("--out needs a file path");
                    }
                    outputFile = args[++i];
                    break;

                case "--list":
                    listPlugins();
                    return;

                default:
                    throw new IllegalArgumentException("Unknown option: " + args[i]);
            }
        }

        // Discover plugins before building the pipeline that will execute them.
        Map<String, TextPlugin> plugins = loadPlugins();
        PluginPipeline pipeline = new PluginPipeline(plugins);

        if (requestedPlugins.isEmpty()) {
            throw new IllegalArgumentException(
                "Choose at least one plugin with --plugin"
            );
        }

        // Read the input, apply plugins in order, and write the processed text.
        String input = readInput(inputFile);
        String output = pipeline.process(input, requestedPlugins);

        if (outputFile == null) {
            System.out.println(output);
        } else {
            Files.writeString(Path.of(outputFile), output);
        }
    }

    // Uses the service registration file to discover available plugin implementations.
    private static Map<String, TextPlugin> loadPlugins() {
        Map<String, TextPlugin> plugins = new LinkedHashMap<>();

        for (TextPlugin plugin : ServiceLoader.load(TextPlugin.class)) {
            plugins.put(plugin.name(), plugin);
        }

        return plugins;
    }

    // Prints each discovered plugin's command name and description.
    private static void listPlugins() {
        Map<String, TextPlugin> plugins = loadPlugins();

        for (TextPlugin plugin : plugins.values()) {
            System.out.println(plugin.name() + " - " + plugin.description());
        }
    }

    // Reads all text from the named file, or from standard input when no file is given.
    private static String readInput(String inputFile) throws IOException {
        if (inputFile != null) {
            return Files.readString(Path.of(inputFile));
        }

        return new String(System.in.readAllBytes());
    }
}