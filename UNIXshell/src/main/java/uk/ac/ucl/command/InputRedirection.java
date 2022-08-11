package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

import java.io.*;

public class InputRedirection implements Command{
    private final InputStream is;

    public InputRedirection(File file) throws FileNotFoundException {
        is = new DataInputStream(new FileInputStream(file));
    }

    public InputStream getInputStream (){
        return is;
    }

    @Override
    public void accept(Visitor visitor) {
    }
}
