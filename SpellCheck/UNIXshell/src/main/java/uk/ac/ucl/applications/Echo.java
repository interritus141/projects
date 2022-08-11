package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Prints its arguments separated by spaces and followed by a newline to stdout
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Echo implements Application{

    /**
     * Executes Echo application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs,BufferedReader input, OutputStreamWriter writer) throws IOException {
        boolean atLeastOnePrinted = false;
        String prefix = "";
        for (String arg : appArgs) {
            writer.write(prefix);
            writer.write(arg);
            prefix = " ";
            writer.flush();
            atLeastOnePrinted = true;
        }
        if (atLeastOnePrinted) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

}
