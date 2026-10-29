A small Java command-line tool that processes text with plugins discovered at runtime using ServiceLoader.
Plugins
Plugin name	Description
counts	        Counts the words in the input
uppercase   	Converts text to uppercase
accentless  	Removes accents
palindrome	    Finds palindrome words
caesar	        Shifts English letters forward by three positions


Plugin names are the values returned by each plugin’s name() method. Use these names with --plugin.
Requirements
- Java 17 or newer
- No build tool required

Compile
Run these commands from the project folder:
```sh
mkdir -p build/core build/plugins
javac -d build/core src/core/*.java
javac -cp build/core -d build/plugins src/plugins/*.java
cp -R src/META-INF build/plugins/
```

Start the app on macOS
1. Open Terminal.
2. Change to the project folder. Keep the quotes because the folder path contains spaces:
```sh
cd "/Users/endre/Downloads/Egyetem/07. Objektum Orientált Programozás/homework06_plugin_interface"
```
3. Compile the app by running the commands in the Compile section above.
4. List the available plugins:
```sh
java -cp build/core:build/plugins core.App --list
```
5. Run a plugin with text from the Terminal:
```sh
echo 'Hello, world!' | java -cp build/core:build/plugins core.App --plugin uppercase
```

The classpath uses : on macOS. If Terminal displays dquote>, press Control+C to cancel the unfinished command, then enter the command again with its opening and closing double quotes.

The service registration file must be named:
src/META-INF/services/core.TextPlugin
It should contain one fully qualified plugin class name per line:
plugins.WordCountPlugin
plugins.UppercasePlugin
plugins.AccentlessPlugin
plugins.PalindromePlugin
plugins.CaesarPlugin

Run
List the plugins available to the app:
```sh
java -cp build/core:build/plugins core.App --list
```

Process text from standard input:
```sh
echo 'Hello, world!' | java -cp build/core:build/plugins core.App --plugin uppercase
```

Run several plugins in order:
```sh
echo "árvíztűrő tükörfúrógép" | java -cp build/core:build/plugins core.App --plugin uppercase,accentless
```

Read from a file and write to another file:
```sh
java -cp build/core:build/plugins core.App --plugin uppercase --in input.txt --out output.txt
```

The classpath uses : on macOS and Linux. On Windows, use ; instead.
Adding a plugin in five steps
1. Create a class in src/plugins that implements core.TextPlugin.
2. Implement name(), description(), and process(String input).
3. Return a unique, command-friendly plugin name from name(), such as "word-count".
4. Add the class’s fully qualified name to src/META-INF/services/core.TextPlugin.
5. Recompile using the commands above and run the app with --plugin your-name.

The core depends only on TextPlugin. Each plugin implements that interface, so adding a plugin does not require adding plugin-specific code to the core. The pipeline catches plugin exceptions, reports them to standard error, and continues with the remaining plugins.

Tests
JUnit 5 tests are in src/test/PluginTests.java. The JUnit standalone JAR is in lib/.
Compile and run them from the project folder:
```sh
mkdir -p build/test
javac -cp "build/core:build/plugins:lib/junit-platform-console-standalone-6.1.3.jar" \
  -d build/test src/test/PluginTests.java

java -jar lib/junit-platform-console-standalone-6.1.3.jar \
  execute \
  --class-path "build/core:build/plugins:build/test" \
  --scan-class-path
```

On Windows, use ; instead of : in the javac classpath.