package file;

import java.util.Objects;

//This Class validates the command line arguments and populate them as members for Orchestrator Class use.
public class CmdPuzzleParser {
    private String fileInputPath;
    private String fileOutputPath;
    private int threadAmount = 4;
    private boolean isRotate = false;
    /*private Map<String, Integer> argsIndexMap = new HashMap<>();
    private String inputArgumentKey = "-input";
    private String outputArgumentKey = "-output";
    private String rotateArgumentKey = "-rotate";
    private String threadArgumentKey = "-threads";
    private int indexOfInputKey;
    private int indexOfOutputKey;
    private int indexOfThreadKey;*/

    public CmdPuzzleParser(String[] args) {
        //parse(args);
        menu(args);
        if (getFileInputPath() == null || getFileOutputPath() == null) {
            printUsage();
        }
    }

    public CmdPuzzleParser() {

    }

    public void menu(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            switch (key) {
                case "-input":
                    setFileInputPath(args[++i]);
                    break;
                case "-output":
                    setFileOutputPath((args[++i]));
                    break;
                case "-rotate":
                    setRotate(true);
                    break;
                case "-threads":
                    int maxNumOfThreads;
                    try {
                        maxNumOfThreads = Integer.parseInt((args[++i]));
                        setThreadAmount(maxNumOfThreads);
                        break;
                    } catch (NumberFormatException e) {
                        printUsage();
                        break;
                    }
                default:
                    printUsage();
            }
        }

    }

    private void printUsage() {

        System.out.println("Command-line parameters: ");
        System.out.println("-input <input_file_name> - mandatory parameter: position of the puzzle file to solve");
        System.out.println("-output <output_file_name> - mandatory parameter: position of the puzzle output file (for solution or errors)");
        System.out.println("-rotate - optional parameter: indicating whether the puzzle pieces can be rotated, if not provided default false");
        System.out.println("-threads <num_threads> - optional parameter: indicating number of threads to use, if not provided default should be 4 threads");
        System.out.println("Example:");
        System.out.println("-input c:/input.txt -output c:/output.txt -rotate -threads 3");
        System.out.println("-input c:/input.txt -output c:/output.txt -threads 5");
        System.out.println("-input c:/input.txt -output c:/output.txt -rotate");
        System.out.println("-input c:/input.txt -output c:/output.txt");
        System.exit(1);

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
        int result = fileInputPath.hashCode();
        result = 31 * result + fileOutputPath.hashCode();
        result = 31 * result + threadAmount;
        result = 31 * result + (isRotate ? 1 : 0);
        return result;
    }
    /*public void parse(String[] args) {
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

            //Make sure that threadArgumentKey and rotateArgumentKey indexes are not sitting next to inputArgumentKey and outputArgumentKey indexes and also that rotateArgumentKey's index is not sequential to threadArgumentKey's index.
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

            //Make sure that threadArgumentKey's index is not sitting next to inputArgumentKey and outputArgumentKey indexes.
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

            //Make sure that inputArgumentKey and outputArgumentKey indexes don't sit next to each other.
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
    }*/

/*@Override
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
    }*/

    /*@Override
    public String toString() {
        return "CmdPuzzleParser{" +
                "fileInputPath='" + fileInputPath + '\'' +
                ", fileOutputPath='" + fileOutputPath + '\'' +
                ", isRotate=" + isRotate +
                ", threadAmount=" + threadAmount +
                '}';
    }
*/

}
