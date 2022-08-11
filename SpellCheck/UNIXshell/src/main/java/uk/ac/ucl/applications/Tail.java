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
 * Prints the last N lines of a given file or stdin. 
 * If there are less than N lines, prints only the existing lines without raising an exception.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Tail implements Application {

    /**
     * Executes Tail application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs,BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();

        if (appArgs.size() > 3) {
            throw new RuntimeException("tail: too many arguments");
        }

        if (appArgs.size() == 2 && !appArgs.get(0).equals("-n")) { 
            throw new RuntimeException("tail: wrong argument " + appArgs.get(0));
        }

        if (appArgs.size() == 3 && !appArgs.get(0).equals("-n")) {
            throw new RuntimeException("tail: wrong argument " + appArgs.get(0));
        }
        
        int tailLines = 10;
        String tailArg;
        
        if (appArgs.size() == 0 || appArgs.size() == 2) {
            if (appArgs.size() == 2) {
                tailLines = getTailLines(appArgs);
            }
            write(writer, tailLines, input);
        }

        else {
            if (appArgs.size() == 3) {
                tailLines = getTailLines(appArgs);
                tailArg = appArgs.get(2);
            } 
            else {
                tailArg = appArgs.get(0);
            }
            getFile(writer, currentDirectory, tailLines, tailArg);
        }
    }

    private void getFile(OutputStreamWriter writer, String currentDirectory, int tailLines, String tailArg) {
        File tailFile = new File(currentDirectory + File.separator + tailArg);
        if (tailFile.exists()) {
            Charset encoding = StandardCharsets.UTF_8;
            Path filePath = Paths.get((String) currentDirectory + File.separator + tailArg);
            try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                write(writer, tailLines, reader);
            } catch (IOException e) {
                throw new RuntimeException("tail: cannot open " + tailArg);
            }
        } else {
            throw new RuntimeException("tail: " + tailArg + " does not exist");
        }
    }

    private void write(OutputStreamWriter writer, int tailLines, BufferedReader reader)
            throws IOException {
        ArrayList<String> storage = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            storage.add(line);
        }
        int index = 0;
        if (tailLines > storage.size()) {   
            index = 0;
        } else {
            index = storage.size() - tailLines;
        }
        for (int i = index; i < storage.size(); i++) {
            writer.write(storage.get(i) + System.getProperty("line.separator"));
            writer.flush();
        }
    }

    private int getTailLines(ArrayList<String> appArgs) {
        int tailLines;
        try {
            tailLines = Integer.parseInt(appArgs.get(1));
        } catch (Exception e) {
            throw new RuntimeException("tail: wrong argument " + appArgs.get(1));
        }

        if (tailLines < 0) {
            throw new RuntimeException("tail: value after -n must be non-negative");
        }
        return tailLines;
    }
}
