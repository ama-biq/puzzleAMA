package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static StringBuilder readFileToStringBuilder(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder;
    }

    public static StringBuilder readFile(File file, Charset encoding) throws Exception {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            String payload = new String(encoded, encoding);
            return new StringBuilder(payload);
        } catch (FileNotFoundException e) {
            try {
                throw new NoSuchFieldException(e.getMessage());
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            try {
                throw new IOException();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        throw new Exception();
    }


    public static StringBuilder readFile(File file) throws Exception {
        return readFile(file, StandardCharsets.UTF_8);
    }
}
