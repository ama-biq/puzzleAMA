package file;

import java.util.Objects;

//This Class validates the command line arguments and populate them as members for Orchestrator Class use.
public class CmdPuzzleParser {
    private String fileInputPath;
    private String fileOutputPath;
    private int threadAmount = 4;
    private boolean isRotate = false;

    CmdPuzzleParser(String[] args) {
        menu(args);
        if (getFileInputPath() == null || getFileOutputPath() == null) {
            printUsage();
        }
    }

    public CmdPuzzleParser() {

    }

    public void menu(String[] args) {
        if (args.length >= 4 && args.length <= 7) {
            for (int i = 0; i < args.length; i++) {
                String key = args[i];
                switch (key) {
                    case "-input":
                        try {
                            setFileInputPath(args[++i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            printUsage();
                        }
                        break;
                    case "-output":
                        try {
                            setFileOutputPath((args[++i]));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            printUsage();
                        }
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
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            printUsage();
                            break;
                        }
                    default:
                        printUsage();
                }
            }
        } else{
            printUsage();
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

}
