package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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


            /*byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            String payload = new String(encoded, encoding);
            return new StringBuilder(payload);*/
        /*} catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
                throw new IOException();
        }*/
    }


    public static StringBuilder readFile(File file) throws Exception {
        return readFile(file, StandardCharsets.UTF_8);
    }
}
