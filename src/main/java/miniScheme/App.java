package miniScheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    private static String fileToString(String path) {
        String content = "";
        try {
            content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException | OutOfMemoryError | SecurityException e) {
            System.err.println(e.getMessage());
        } catch (Exception e ) {
            System.err.println("FUCK:" + e.getMessage());
        }
        return content;
    }
}
