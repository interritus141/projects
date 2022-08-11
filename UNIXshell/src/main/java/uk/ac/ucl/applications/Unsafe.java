package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Unsafe version of an application is an application that has the same semantics as the original application.
 * Instead of raising exceptions, it prints the error message to its stdout.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Unsafe implements Application{

    private final Application application;

    public Unsafe(Application app) {
        application = app;
    }

    /**
     * Executes Unsafe application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */
    
    public void exec(ArrayList<String> appArgs, BufferedReader input, OutputStreamWriter writer) throws IOException {
        try {
            application.exec(appArgs, input, writer);
        }
        catch (Exception e) {
            writer.write(e.getMessage());
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }  
    }
    
}
