package uk.ac.ucl.applications;

import java.io.*;
import java.util.ArrayList;

/**
 * Detects and deletes adjacent duplicate lines from an input file/stdin and prints the result to stdout.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Uniq implements Application {

    private boolean ignoreCase = false; // true if case insensitive, false if case sensitive

    /**
     * Executes Uniq application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs, BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();
        BufferedReader readFile;
        String fileName;

        if (appArgs.isEmpty() || appArgs.get(0).equals("-i") && appArgs.size() == 1) {
            readFile = input;
            if (appArgs.size() == 1) {
                ignoreCase = true;
            }
        }
        else {
            if (appArgs.size() > 2) {
                throw new RuntimeException("uniq: wrong number of arguments");
            }
            if (appArgs.size() == 2 && !(appArgs.get(0).equals("-i"))) {
                throw new RuntimeException("uniq: wrong argument " + appArgs.get(0));
            }
            if (appArgs.size() == 2) {
                fileName = appArgs.get(1);
                ignoreCase = true;
            } else {
                fileName = appArgs.get(0);
            }

            File file = new File(currentDirectory + "/" + fileName);
            if (file.exists()) {
                try {
                    readFile = new BufferedReader(new FileReader(file));
                } catch (IOException e) {
                    throw new RuntimeException("cannot open " + fileName);
                }
            } else {
                throw new RuntimeException("file " + fileName + " does not exist");
            }
        }

        String line;
        String tempLine = "";

        while ((line = readFile.readLine()) != null) {
            if (ignoreCase == false && tempLine.compareTo(line)!=0) { // case sensitive, line is unique, write it
                write(line, writer);
            } else if (ignoreCase == true && tempLine.toLowerCase().compareTo(line.toLowerCase())!= 0) { //case insensitive, line is unique, write it
                write(line, writer);
            }
            tempLine= line;
        }
    }

    private void write(String line, OutputStreamWriter output) throws IOException {
        output.write(line);
        output.write(System.getProperty("line.separator"));
        output.flush();
    }  
 
}
