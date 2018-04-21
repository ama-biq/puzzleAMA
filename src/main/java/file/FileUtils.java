package file;

import impl.EventHandler;
import impl.PuzzleElementDefinition;
import impl.Solver;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    //todo . Previous version of this finction was
    //public static void writeFile(File file, List<String> output) throws IOException
    public static void writeFile() throws IOException {
        Set<String> output = EventHandler.getEventList();
        FileOutputStream fos = new FileOutputStream((new File("src\\test\\resources\\OutPutFile.txt")));
        try (OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            for(String str : output) {
                writer.write(str + '\n');
            }
        }
    }
    public static void writeSolutionToFile(Map<Integer, List<PuzzleElementDefinition>> solverMap) throws IOException {
        Solver solver = new Solver();
        Map<Integer, List<PuzzleElementDefinition>>outMap = solverMap;
        FileOutputStream fos = new FileOutputStream((new File("src\\test\\resources\\OutPutFile.txt")));
        try (OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            for(Map.Entry<Integer, List<PuzzleElementDefinition>> entry : outMap.entrySet()) {
                List<PuzzleElementDefinition> list = entry.getValue();
                StringBuilder sb = new StringBuilder();
                for (PuzzleElementDefinition element : list){
                    sb.append(Integer.toString(element.getId())+ ' ');
//                    writer.write(Integer.toString(element.getId())+ ' ');
                }
                writer.write(sb.toString().trim() + '\n');
            }
        }
    }


}
