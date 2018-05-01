package file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CmdPuzzleParser {
    private Map<String, Integer> argsIndexMap = new HashMap<>();
    private String inputArgumentKey = "-input";
    private String inputArgumentValue;
    private String outputArgumentKey = "-output";
    private String outputArgumentValue;
    private String rotateArgumentKey = "-rotate";
    private boolean isRotateArgumentExist = false;
    private String threadArgumentKey = "-threads";
    private int threadArgumentValue = 4;
    private int indexOfInputKey;
    private int indexOfOutputKey;
    private int indexOfthreadKey;

    public CmdPuzzleParser(String[] args) {
        parse(args);
    }

    public CmdPuzzleParser(){

    }

    public void parse(String[] args) {


        if (args.length >= 4 && args.length <= 7) {


            for (int i = 0; i < args.length; i++) {

                String arg = args[i];

                if (arg == inputArgumentKey) {
                    if (!argsIndexMap.containsKey(arg)) {
                        argsIndexMap.put(arg, i);
                    } else {
                        printUsage();
                    }
                }
                if (arg == outputArgumentKey) {
                    if (!argsIndexMap.containsKey(arg)) {
                        argsIndexMap.put(arg, i);
                    } else {
                        printUsage();
                    }
                }
                if (arg == rotateArgumentKey) {
                    if (!argsIndexMap.containsKey(arg)) {
                        argsIndexMap.put(arg, i);
                    } else {
                        printUsage();
                    }
                }
                if (arg == threadArgumentKey) {
                    if (!argsIndexMap.containsKey(arg)) {
                        argsIndexMap.put(arg, i);
                    } else {
                        printUsage();
                    }
                }
            }
        } else {
            printUsage();

        }

        if (argsIndexMap.containsKey(inputArgumentKey) && argsIndexMap.containsKey(outputArgumentKey)) {

            indexOfInputKey = argsIndexMap.get(inputArgumentKey);
            indexOfOutputKey = argsIndexMap.get(outputArgumentKey);
            if ((indexOfInputKey - indexOfOutputKey) > 1 || (indexOfInputKey - indexOfOutputKey) < -1) {
                inputArgumentValue = args[indexOfInputKey + 1];
                outputArgumentValue = args[indexOfOutputKey + 1];

                if (args.length == 5) {
                    if (!argsIndexMap.containsKey(rotateArgumentKey)) {
                        printUsage();
                    } else isRotateArgumentExist = true;
                } else if (args.length == 6) {
                    if (!argsIndexMap.containsKey(threadArgumentKey)) {
                        printUsage();
                    } else {
                        indexOfthreadKey = argsIndexMap.get(threadArgumentKey);
                        if (((indexOfthreadKey - indexOfInputKey) > 1 || (indexOfthreadKey - indexOfInputKey) < -1) && ((indexOfthreadKey - indexOfOutputKey) > 1 || (indexOfthreadKey - indexOfOutputKey) < -1)) {
                            try {
                                threadArgumentValue = Integer.parseInt(args[indexOfthreadKey + 1]);
                            } catch (NumberFormatException e) {
                               printUsage();
                            }
                        } else {
                            printUsage();
                        }
                    }
                } else if (args.length == 7) {
                    if (!argsIndexMap.containsKey(rotateArgumentKey) || !argsIndexMap.containsKey(threadArgumentKey)) {
                        printUsage();
                    } else {
                        int indexOfRotateKey = argsIndexMap.get(rotateArgumentKey);
                        indexOfthreadKey = argsIndexMap.get(threadArgumentKey);
                        if ((((indexOfthreadKey - indexOfInputKey) > 1 || (indexOfthreadKey - indexOfInputKey) < -1) && ((indexOfthreadKey - indexOfOutputKey) > 1 || (indexOfthreadKey - indexOfOutputKey) < -1)) && indexOfRotateKey != indexOfthreadKey + 1) {

                            try {
                                threadArgumentValue = Integer.parseInt(args[indexOfthreadKey + 1]);
                            } catch (NumberFormatException e) {
                                printUsage();
                            }
                            isRotateArgumentExist = true;
                        } else {
                            printUsage();
                        }

                    }

                }

            } else {
                printUsage();
            }

        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmdPuzzleParser that = (CmdPuzzleParser) o;
        return isRotateArgumentExist == that.isRotateArgumentExist &&
                threadArgumentValue == that.threadArgumentValue &&
                Objects.equals(inputArgumentValue, that.inputArgumentValue) &&
                Objects.equals(outputArgumentValue, that.outputArgumentValue);
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
                "inputArgumentValue='" + inputArgumentValue + '\'' +
                ", outputArgumentValue='" + outputArgumentValue + '\'' +
                ", isRotateArgumentExist=" + isRotateArgumentExist +
                ", threadArgumentValue=" + threadArgumentValue +
                '}';
    }

    public String getInputArgumentValue() {
        return inputArgumentValue;
    }

    public String getOutputArgumentValue() {
        return outputArgumentValue;
    }

    public boolean isRotateArgumentExist() {
        return isRotateArgumentExist;
    }

    public int getThreadArgumentValue() {
        return threadArgumentValue;
    }



    public void setInputArgumentValue(String inputArgumentValue) {
        this.inputArgumentValue = inputArgumentValue;
    }

    public void setOutputArgumentValue(String outputArgumentValue) {
        this.outputArgumentValue = outputArgumentValue;
    }

    public void setRotateArgumentExist(boolean rotateArgumentExist) {
        isRotateArgumentExist = rotateArgumentExist;
    }

    public void setThreadArgumentValue(int threadArgumentValue) {
        this.threadArgumentValue = threadArgumentValue;
    }

}
