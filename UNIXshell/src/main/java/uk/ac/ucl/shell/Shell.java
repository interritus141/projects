package uk.ac.ucl.shell;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.command.Command;
import uk.ac.ucl.visitor.CommandConverter;
import uk.ac.ucl.visitor.EvaluateVisitor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Main class of the programme.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Shell {

    private static CurrentDirectory currentDirectory = CurrentDirectory.getInstance();

    public static void eval(String cmdline, OutputStream output) throws IOException {
        Command c = parseString(cmdline, currentDirectory);
        c.accept(new EvaluateVisitor(null, output));

    }

    public static Command parseString (String expressionString, CurrentDirectory currentDirectory){
        CharStream parserInput = CharStreams.fromString(expressionString);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
        ParseTree tree = parser.command();


        CommandConverter commandConverter = new CommandConverter();
        commandConverter.setCurrentDirectory(currentDirectory);
        return tree.accept(commandConverter);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("COMP0010 shell: wrong number of arguments");
                return;
            }
            if (!args[0].equals("-c")) {
                System.out.println("COMP0010 shell: " + args[0] + ": unexpected argument");
            }
            try {
                eval(args[1], System.out);
            } catch (Exception e) {
                System.err.println("COMP0010 shell: " + e.getMessage());
            }
        } else {
            Scanner input = new Scanner(System.in);
            try {
                while (true) {
                    String prompt = currentDirectory + "> ";
                    System.out.print(prompt);
                    try {
                        String cmdline = input.nextLine();
                        eval(cmdline, System.out);
                    } catch (Exception e) {
//                        System.out.println("COMP0010 shell: " + e.getMessage());
                        System.err.println("COMP0010 shell: " + e.getMessage());
                    }
                }
            } finally {
                input.close();
            }
        }
    }

}
