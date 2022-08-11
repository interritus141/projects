package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

import java.io.IOException;

/**
 * Executes a sequence of commands separated by semicolons.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Seq implements Command{
    private final Command left;
    private final Command right;

    public Seq(Command left, Command right){
        this.left = left;
        this.right = right;
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
