package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputRedirection implements Command{

    private final OutputStream os;

    public OutputRedirection(File file) throws FileNotFoundException {
        os = new FileOutputStream(file);
    }

    public OutputStream getOutputStream (){
        return os;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
