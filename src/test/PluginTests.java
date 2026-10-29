package test;

import core.PluginPipeline;
import core.TextPlugin;
import org.junit.jupiter.api.Test;
import plugins.AccentlessPlugin;
import plugins.CaesarPlugin;
import plugins.PalindromePlugin;
import plugins.UppercasePlugin;
import plugins.WordCountPlugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Verifies the behavior of each built-in plugin and the plugin pipeline.
public class PluginTests {
    // Checks that whitespace-separated words are counted.
    @Test
    void wordCountCountsWords() {
        assertEquals("3", new WordCountPlugin().process("one two three"));
    }

    // Checks that text is converted to uppercase.
    @Test
    void uppercaseConvertsText() {
        assertEquals("HELLO", new UppercasePlugin().process("Hello"));
    }

    // Checks that accented characters are converted to their unaccented forms.
    @Test
    void accentlessRemovesAccents() {
        assertEquals("arvizturo", new AccentlessPlugin().process("árvíztűrő"));
    }

    // Checks that palindrome words are found in a sentence.
    @Test
    void palindromePluginFindsPalindromeWords() {
        assertEquals("radar", new PalindromePlugin().process("hello radar world"));
    }

    // Checks that the Caesar shift wraps correctly and preserves punctuation.
    @Test
    void caesarShiftsLetters() {
        assertEquals("def XYZ!", new CaesarPlugin().process("abc UVW!"));
    }

    // Checks that each plugin receives the previous plugin's output.
    @Test
    void pipelineRunsPluginsInOrder() {
        Map<String, TextPlugin> plugins = new LinkedHashMap<>();
        plugins.put("nagybetu", new UppercasePlugin());
        plugins.put("ekezettelen", new AccentlessPlugin());

        PluginPipeline pipeline = new PluginPipeline(plugins);

        assertEquals(
            "ARVIZTURO",
            pipeline.process("árvíztűrő", List.of("nagybetu", "ekezettelen"))
        );
    }

    // Checks that a failing plugin is skipped and later plugins still run.
    @Test
    void failedPluginIsSkippedAndNextPluginRuns() {
        Map<String, TextPlugin> plugins = new LinkedHashMap<>();
        plugins.put("hibas", new TextPlugin() {
            @Override
            public String name() {
                return "hibas";
            }

            @Override
            public String description() {
                return "Test plugin that fails";
            }

            @Override
            public String process(String input) {
                throw new RuntimeException("Test failure");
            }
        });
        plugins.put("nagybetu", new UppercasePlugin());

        PluginPipeline pipeline = new PluginPipeline(plugins);

        assertEquals(
            "HELLO",
            pipeline.process("hello", List.of("hibas", "nagybetu"))
        );
    }
}