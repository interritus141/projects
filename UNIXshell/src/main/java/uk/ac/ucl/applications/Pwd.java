package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Outputs the current working directory followed by a newline.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Pwd implements Application {

    /**
     * Executes Pwd application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs, BufferedReader input,OutputStreamWriter writer) throws IOException {
        writer.write(directory.getCurrentDirectory());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}
