package file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {


    public static StringBuilder readFile(File file, Charset encoding) throws Exception {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            String payload = new String(encoded, encoding);
            return new StringBuilder(payload);
        } catch (Exception e) {
            throw new Exception();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }


    public static StringBuilder readFile(File file) throws Exception {
        return readFile(file, StandardCharsets.UTF_8);
    }


    public static void writeFile(File file, List<String> output) throws IOException {
        FileOutputStream fos = new FileOutputStream((new File("")));
        try (OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            for(String str : output) {
                writer.write(str + '\n');
            }
        }
    }


}
