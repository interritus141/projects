package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches for lines containing a match to the specified pattern. 
 * The output of the command is the list of lines. Each line is printed followed by a newline.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Grep implements Application {

    /**
     * Executes Grep application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs, BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();

        if (appArgs.size() == 0) {
            throw new RuntimeException("grep: missing arguments");
        }

        else {
            Pattern grepPattern = Pattern.compile(appArgs.get(0));
            int numOfFiles;
            if (appArgs.size() == 1) {
                numOfFiles = 0;
                write(writer, grepPattern, numOfFiles, null, input);
            }
            else {
                multFilesWrite(appArgs, writer, currentDirectory, grepPattern);
            }
        }
    }

    private void multFilesWrite(ArrayList<String> appArgs, OutputStreamWriter writer, String currentDirectory,
            Pattern grepPattern) {
        int numOfFiles;
        numOfFiles = appArgs.size() - 1;
        Path[] filePathArray = new Path[numOfFiles];
        Path currentDir = Paths.get(currentDirectory);
        resolveFilePath(appArgs, numOfFiles, filePathArray, currentDir);
        for (int j = 0; j < filePathArray.length; j++) {
            Charset encoding = StandardCharsets.UTF_8;
            String str = appArgs.get(j + 1);
            try (BufferedReader reader = Files.newBufferedReader(filePathArray[j], encoding)) {
                write(writer, grepPattern, numOfFiles, str, reader);
            } catch (IOException e) {
                throw new RuntimeException("grep: cannot open " + str);
            }
        }
    }

    private void write(OutputStreamWriter writer, Pattern grepPattern, int numOfFiles, String str,
            BufferedReader reader) throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = grepPattern.matcher(line);
            if (matcher.find()) {
                if (numOfFiles > 1) {
                    writer.write(str);
                    writer.write(":");
                }
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        }
    }

    private void resolveFilePath(ArrayList<String> appArgs, int numOfFiles, Path[] filePathArray, Path currentDir) {
        Path filePath;
        for (int i = 0; i < numOfFiles; i++) {
            filePath = currentDir.resolve(appArgs.get(i + 1));
            if (Files.notExists(filePath) || Files.isDirectory(filePath) ||
                    !Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new RuntimeException("grep: wrong file argument");
            }
            filePathArray[i] = filePath;
        }
    }
}
