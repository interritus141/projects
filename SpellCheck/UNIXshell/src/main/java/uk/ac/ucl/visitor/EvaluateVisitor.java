package uk.ac.ucl.visitor;

import uk.ac.ucl.applications.*;
import uk.ac.ucl.command.Call;
import uk.ac.ucl.command.Command;
import uk.ac.ucl.command.Pipe;
import uk.ac.ucl.command.Seq;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Evaluates a Command object
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
 */

public class EvaluateVisitor implements  Visitor{
    private InputStream inputStream;
    private OutputStream outputStream;

    public EvaluateVisitor(InputStream inputStream, OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void setInputStream(InputStream in){
        inputStream = in;
    }
    public void setOutputStream(OutputStream out){
        outputStream = out;
    }

    /**
     * Evaluates Call object and executes application
     * @param call Call object to be executed
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
     */

    @Override
    public void visit(Call call) throws IOException {
        // only change input, output if specified - not null
        setInputStream(Optional.ofNullable(call.getInputStream()).orElse(inputStream));
        setOutputStream(Optional.ofNullable(call.getOutputStream()).orElse(outputStream));
        String appName = call.getAppName();
        ArrayList<String> appArgs = call.getAppArgs();

        // determine unsafe/not
        boolean unsafe = false;
        if (appName.startsWith("_")){
            unsafe = true;
            appName = appName.substring(1);
        }

        Application app;
        switch (appName) {
            case "cd":
                app = new Cd();
                break;
            case "pwd":
                app = new Pwd();
                break;
            case "ls":
                app = new Ls();
                break;
            case "cat":
                app = new Cat();
                break;
            case "echo":
                app = new Echo();
                break;
            case "head":
                app = new Head();
                break;
            case "tail":
                app = new Tail();
                break;
            case "grep":
                app = new Grep();
                break;
            case "find":
                app = new Find();
                break;
            case "cut":
                app = new Cut();
                break;
            case "uniq":
                app = new Uniq();
                break;
            case "sort":
                app = new Sort();
                break;

            default:
                throw new RuntimeException(appName + ": unknown application");
        }

        if (unsafe){
            app = new Unsafe(app);
        }

        BufferedReader br;
        if (inputStream == null){
            br = null;
        } else {
            br= new BufferedReader(new InputStreamReader(inputStream));
        }
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        app.exec(appArgs, br, osw);
    }

    /**
     * Evaluates Pipe object and streams output of pipe.left into pipe.right
     * @param pipe Pipe object containing subcommands
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
     */

    @Override
    public void visit(Pipe pipe) throws IOException {
        Command left = pipe.getLeft();
        Command right = pipe.getRight();

        // create new output stream, evaluate left side - pipe operator happens first (lowest precedence)
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        // save copy of current i/o streams
        OutputStream pastOutput = getPastOutput();
        InputStream pastInput = getPastInput();

        // set output stream to be piped into right side
        outputStream = out;
        left.accept(this);
        out.close();

        // pipe output stream into right side
        inputStream = in;
        // restore output stream
        outputStream = pastOutput;
        right.accept(this);
        // restore input stream
        inputStream = pastInput;
    }

    /**
     * Evaluates Seq object by evaluating seq.left then seq.right with same i/o streams
     * @param seq Seq object containing two subcommands
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
     */

    @Override
    public void visit(Seq seq) throws IOException {
        Command left = seq.getLeft();
        Command right = seq.getRight();

        OutputStream pastOutput = getPastOutput();
        InputStream pastInput = getPastInput();

        left.accept(this);
        // restore i/o streams
        inputStream = pastInput;
        outputStream = pastOutput;
        right.accept(this);
    }

    private OutputStream getPastOutput(){
        if (outputStream == System.out){
            return System.out;
        }
        else{
            return new BufferedOutputStream(outputStream);
        }
    }

    private InputStream getPastInput (){
        if (inputStream == null){
            return null;
        }
        else{
            return new BufferedInputStream(inputStream);
        }

    }
}
