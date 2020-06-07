package miniScheme;

import miniScheme.parser.api.Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class App {
    public static void main(String... args) {
        String source = fileToString( Paths.get(args[0]) );
        Object result = new Evaluator().eval(loadCode(source), GlobalEnvironment.build());
        System.out.println(result.toString());
    }

    private static List<Object> loadCode(String source) {
        List<Object> parseTree = null;
        try {
            parseTree = Parser.parseString(source);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return parseTree;
    }

    private static String fileToString(Path path) {
        String content = "";
        try {
            content = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException | OutOfMemoryError | SecurityException e) {
            System.err.println(e.getMessage());
        } catch (Exception e ) {
            System.err.println("FUCK:" + e.getMessage());
        }
        return content;
    }
}
