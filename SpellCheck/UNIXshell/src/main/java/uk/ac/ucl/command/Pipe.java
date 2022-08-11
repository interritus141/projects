package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

import java.io.IOException;

/**
 * The output of each command in a pipeline is connected via a pipe to the input of the next command. 
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Pipe implements Command{
    private final Command left;
    private final Command right;

    public Pipe (Command toPipe, Command takesPipe){
        this.left = toPipe;
        this.right = takesPipe;
    }

    public Command getLeft() {
        return left;
    }

    public Command getRight() {
        return right;
    }

    @Override
    public void accept(Visitor visitor) throws IOException {
        visitor.visit(this);
    }
}
