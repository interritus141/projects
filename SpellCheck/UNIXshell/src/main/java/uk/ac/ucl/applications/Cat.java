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
 * Concatenates the content of given files and prints it to stdout.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Cat implements Application {

    /**
     * Executes Cat application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs, BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();
        Charset encoding = StandardCharsets.UTF_8;

        if (appArgs.isEmpty()) {
            write(input,writer);
        } 
        else {
            for (String arg : appArgs) {
                readTheFile(currentDirectory, arg, encoding, writer) ;
            }
        }
    }

    public void readTheFile(String currentDirectory, String arg, Charset encoding, OutputStreamWriter writer) {
        BufferedReader reader;
        File currFile = new File(currentDirectory + File.separator + arg);
        if (currFile.exists()) {
            Path filePath = Paths.get(currentDirectory + File.separator + arg);
            try{
                reader = Files.newBufferedReader(filePath, encoding);
            } catch (IOException e) {
                throw new RuntimeException("cat: cannot open " + arg);
            }
            try{
                write(reader, writer);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        } else {
            throw new RuntimeException("cat: file does not exist");
        }
    }

    private void write(BufferedReader reader,OutputStreamWriter  output) throws IOException {
        String line = "";
        while ((line = reader.readLine()) != null) {
            output.write(String.valueOf(line));
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
   }

}

