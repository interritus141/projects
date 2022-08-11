package uk.ac.ucl.visitor;

import uk.ac.ucl.command.Call;
import uk.ac.ucl.command.Pipe;
import uk.ac.ucl.command.Seq;

import java.io.IOException;

public interface Visitor {
    void visit(Call call) throws IOException;
    void visit(Pipe pipe) throws IOException;
    void visit(Seq seq) throws IOException;

}
