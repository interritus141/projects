package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Prints the first N lines of a given file or stdin. 
 * If there are less than N lines, prints only the existing lines without raising an exception.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Head implements Application {

    /**
     * Executes Head application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs,BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();

        if (appArgs.size() > 3) {
            throw new RuntimeException("head: wrong arguments");
        }
        if (appArgs.size() == 2 && !appArgs.get(0).equals("-n")) { // redirects the file after < to stdin of the program before <.
            throw new RuntimeException("head: wrong argument " + appArgs.get(0));
        }

        if (appArgs.size() == 3 && !appArgs.get(0).equals("-n")) {
            throw new RuntimeException("head: wrong argument " + appArgs.get(0));
        }

        if (appArgs.size() ==0 || appArgs.size() == 2) {
            int headlines ;
            if (appArgs.size() == 0){
                headlines =10;                
            }
            else {
                headlines = getheadLines(appArgs.get(1));
            }
            BufferedReader reader = input;
            write(reader, writer, headlines);
        }
        else {
            int headLines = 10;
            String headArg;
            if (appArgs.size() == 3) {
                headLines = getheadLines(appArgs.get(1));
                headArg = appArgs.get(2);
            } else {
                headArg = appArgs.get(0);
            }
            File headFile = new File(currentDirectory + File.separator + headArg);
            if (headFile.exists()) {
                Charset encoding = StandardCharsets.UTF_8;
                Path filePath = Paths.get((String) currentDirectory + File.separator + headArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    write(reader, writer,headLines);
                } catch (IOException e) {
                    throw new RuntimeException("head: cannot open " + headArg);
                }
            } else {
                throw new RuntimeException("head: " + headArg + " does not exist");
            }
        }
    }


    private void write(BufferedReader reader,OutputStreamWriter  output, int headLines) throws IOException {
        for (int i = 0; i < headLines; i++) {
            String line = null;
            if ((line = reader.readLine()) != null) {
                output.write(line);
                output.write(System.getProperty("line.separator"));
                output.flush();
            }
        }
    }

    private int getheadLines(String argument){
        int num = Integer.parseInt(argument);
        if (num < 0) { // fixed : added head -n 0
            throw new RuntimeException("head: line count " + argument + " is not allowed. ");
        }
        return num;
    }

}