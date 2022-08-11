package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

import java.io.IOException;

/**
 * Provides accept method for commands.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public interface Command {
void accept(Visitor visitor) throws IOException;
}
