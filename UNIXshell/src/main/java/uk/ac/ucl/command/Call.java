package uk.ac.ucl.command;

import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.visitor.Visitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A call command executes an application with specified inputs.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Call implements Command{
    private final String appName;
    private ArrayList<String> appArgs;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Call(String appName, ArrayList<String> appArgs, InputStream inputStream, OutputStream outputStream, CurrentDirectory currentDirectory) throws IOException {
        this.appName = appName;
        this.appArgs = appArgs;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream(){
        return inputStream;
    }

    public OutputStream getOutputStream(){
        return outputStream;
    }

    public String getAppName() {
        return appName;
    }

    public ArrayList<String> getAppArgs() {
        return appArgs;
    }

    @Override
    public void accept(Visitor visitor) throws IOException {
        visitor.visit(this);
    }
}
