package file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CmdPuzzleParser {
    private Map<String, Integer> argsIndexMap = new HashMap<>();
    private String inputArgumentKey = "-input";
    private String fileInputPath;
    private String outputArgumentKey = "-output";
    private String fileOutputPath;
    private String rotateArgumentKey = "-rotate";
    private boolean isRotate = false;
    private String threadArgumentKey = "-threads";
    private int threadAmount = 4;
    private int indexOfInputKey;
    private int indexOfOutputKey;
    private int indexOfThreadKey;

    public CmdPuzzleParser(String[] args) {
        parse(args);
    }

    public CmdPuzzleParser() {

    }

    public void parse(String[] args) {
        if (args.length >= 4 && args.length <= 7) {
            populateArgsInMap(args);
            validateAndPopulateMandatoryArgs(args);
            if (args.length == 5) {
                populateValueForisRotate(); //In this case argument rotateArgumentKey must exist
            } else if (args.length == 6) {
                populateValueForThreadAmount(args); //In this case argument threadArgumentKey must exist
            } else if (args.length == 7) {
                populateValuesForIsRotateAndThreadAmount(args); //In this case both arguments rotateArgumentKey and threadArgumentKey must exist
            }
        } else {
            printUsage();
        }

    }

    private void populateValuesForIsRotateAndThreadAmount(String[] args) {
        if (!argsIndexMap.containsKey(rotateArgumentKey) || !argsIndexMap.containsKey(threadArgumentKey)) {
            printUsage();
        } else {
            int indexOfRotateKey = argsIndexMap.get(rotateArgumentKey);
            indexOfThreadKey = argsIndexMap.get(threadArgumentKey);

            //Make sure that threadArgumentKey and rotateArgumentKey indexes are not sequential to other argument's indexes
            if ((((indexOfThreadKey - indexOfInputKey) > 1 || (indexOfThreadKey - indexOfInputKey) < -1) && ((indexOfThreadKey - indexOfOutputKey) > 1 || (indexOfThreadKey - indexOfOutputKey) < -1)) && indexOfRotateKey != indexOfThreadKey + 1) {
                try {
                    threadAmount = Integer.parseInt(args[indexOfThreadKey + 1]);
                } catch (NumberFormatException e) {
                    printUsage();
                }
                isRotate = true;
            } else {
                printUsage();
            }
        }
    }

    private void populateValueForThreadAmount(String[] args) {
        if (!argsIndexMap.containsKey(threadArgumentKey)) {
            printUsage();
        } else {
            indexOfThreadKey = argsIndexMap.get(threadArgumentKey);

            //Make sure that threadArgumentKey index is not sequential to other argument's indexes
            if (((indexOfThreadKey - indexOfInputKey) > 1 || (indexOfThreadKey - indexOfInputKey) < -1) && ((indexOfThreadKey - indexOfOutputKey) > 1 || (indexOfThreadKey - indexOfOutputKey) < -1)) {
                try {
                    threadAmount = Integer.parseInt(args[indexOfThreadKey + 1]);
                } catch (NumberFormatException e) {
                    printUsage();
                }
            } else {
                printUsage();
            }
        }
    }

    private void populateValueForisRotate() {
        if (!argsIndexMap.containsKey(rotateArgumentKey)) {
            printUsage();
        } else isRotate = true;
    }

    private void validateAndPopulateMandatoryArgs(String[] args) {
        if (argsIndexMap.containsKey(inputArgumentKey) && argsIndexMap.containsKey(outputArgumentKey)) {

            indexOfInputKey = argsIndexMap.get(inputArgumentKey);
            indexOfOutputKey = argsIndexMap.get(outputArgumentKey);
            if ((indexOfInputKey - indexOfOutputKey) > 1 || (indexOfInputKey - indexOfOutputKey) < -1) {
                fileInputPath = args[indexOfInputKey + 1];
                fileOutputPath = args[indexOfOutputKey + 1];
            } else {
                printUsage();
            }
        }
    }

    private void populateArgsInMap(String[] args) {
        String[] argumrntKeys = {inputArgumentKey, outputArgumentKey, rotateArgumentKey, threadArgumentKey};
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            int j = 0;
            for (; j < argumrntKeys.length; j++) {
                String currentKey = argumrntKeys[j];
                if (arg.equals(currentKey)) {
                    if (!argsIndexMap.containsKey(arg)) {
                        argsIndexMap.put(arg, i);
                    } else {
                        printUsage();
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmdPuzzleParser that = (CmdPuzzleParser) o;
        return isRotate == that.isRotate &&
                threadAmount == that.threadAmount &&
                Objects.equals(fileInputPath, that.fileInputPath) &&
                Objects.equals(fileOutputPath, that.fileOutputPath);
    }

    @Override
    public int hashCode() {
        int result = argsIndexMap.hashCode();
        result = 31 * result + inputArgumentKey.hashCode();
        result = 31 * result + fileInputPath.hashCode();
        result = 31 * result + outputArgumentKey.hashCode();
        result = 31 * result + fileOutputPath.hashCode();
        result = 31 * result + rotateArgumentKey.hashCode();
        result = 31 * result + (isRotate ? 1 : 0);
        result = 31 * result + threadArgumentKey.hashCode();
        result = 31 * result + threadAmount;
        result = 31 * result + indexOfInputKey;
        result = 31 * result + indexOfOutputKey;
        result = 31 * result + indexOfThreadKey;
        return result;
    }

    void printUsage() {

        System.out.println("Command-line parameters: ");
        System.out.println("-input <input_file_name>");
        System.out.println("Mandatory - position of the puzzle file to solve");
        System.out.println("-output <output_file_name>");
        System.out.println("Mandatory - position of the puzzle output file (for solution or errors)");
        System.out.println("-rotate");
        System.out.println("Optional - indicating whether the puzzle pieces can be rotated");
        System.out.println("-threads <num_threads>");
        System.out.println("Optional - indicating number of threads to use (including main)\n" +
                "if not provided default should be 4 threads\n");

        System.exit(1);

    }

    @Override
    public String toString() {
        return "CmdPuzzleParser{" +
                "fileInputPath='" + fileInputPath + '\'' +
                ", fileOutputPath='" + fileOutputPath + '\'' +
                ", isRotate=" + isRotate +
                ", threadAmount=" + threadAmount +
                '}';
    }

    public String getFileInputPath() {
        return fileInputPath;
    }

    public String getFileOutputPath() {
        return fileOutputPath;
    }

    public boolean isRotate() {
        return isRotate;
    }

    public int getThreadAmount() {
        return threadAmount;
    }


    public void setFileInputPath(String fileInputPath) {
        this.fileInputPath = fileInputPath;
    }

    public void setFileOutputPath(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
    }

    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }

    public void setThreadAmount(int threadAmount) {
        this.threadAmount = threadAmount;
    }

}
