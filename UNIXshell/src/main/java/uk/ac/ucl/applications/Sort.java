package uk.ac.ucl.applications;

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Sorts the contents of a file/stdin line by line and prints the result to stdout.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Sort implements Application {

    /**
     * Executes Sort application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs,BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();

        if (appArgs.size() == 2 && !appArgs.get(0).equals("-r")) {
            throw new RuntimeException("sort: wrong argument: " + appArgs.get(0));
        }

        if (appArgs.size() > 2) {
            throw new RuntimeException("sort: wrong number of arguments");
        }

        boolean reverse = false;
        BufferedReader readfile;
        String filename;
        //sort FILEDIR (sort specified file)
        if (appArgs.size() == 1 && !appArgs.get(0).equals("-r")) {
            filename = appArgs.get(0);
            readfile = readFile(currentDirectory, filename);
        }

        //sort -r FILEDIR (sort specified file and reverse)
        else if (appArgs.size() == 2) {
            filename = appArgs.get(1);
            reverse = true;
            readfile = readFile(currentDirectory, filename);
        } 
        
        //sort -r (stdin but reverse)
        else if (appArgs.size() == 1) {
            reverse = true;
            readfile = input;
        }

        //sort (stdin not reverse)
        else {
            readfile = input; // not specified, uses stdin
        }
        
        writeToFile(writer, readfile, reverse);
    }

    private BufferedReader readFile(String currentDirectory, String filename) {
        BufferedReader readfile;
        File file = new File(currentDirectory + "/" + filename);
        
        if (file.isFile()) {
            try {
                readfile = new BufferedReader(new FileReader(file));
            }
            catch (IOException e) {
                throw new RuntimeException("cannot open " + filename);
            }
        } else {
            throw new RuntimeException("file " + filename + " does not exist");
        }
        return readfile;
    }

    private void writeToFile(OutputStreamWriter writer, BufferedReader readfile, boolean reverse) throws IOException {
        ArrayList<String> contents = new ArrayList<>();
        String line;

        while ((line = readfile.readLine()) != null) {
            contents.add(line);
        }
        java.util.Collections.sort(contents);
        
        //sorted non-reverse
        if (reverse == false) {
            sortNonRev(writer, contents);
        } 

        //sorted reverse
        else {
            sortRev(writer, contents);
        }
    }

    private void sortRev(OutputStreamWriter writer, ArrayList<String> contents) throws IOException {
        ListIterator<String> li = contents.listIterator(contents.size());

        while (li.hasPrevious()) {
            writer.write(li.previous());
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    private void sortNonRev(OutputStreamWriter writer, ArrayList<String> contents) throws IOException {
        for (String sortedLine : contents) {
            writer.write(sortedLine);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
