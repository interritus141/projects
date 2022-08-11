package uk.ac.ucl.shell;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.applications.CurrentDirectory;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShellTest {
    public ShellTest() {
    }

    PipedInputStream in;
    PipedOutputStream out;
    ArrayList<String> appArgs;
    OutputStreamWriter writer;
    BufferedReader input;


    @Before
    public void setUp() throws Exception {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        appArgs = new ArrayList<>();
        writer = new OutputStreamWriter(out);
        input = new BufferedReader(new InputStreamReader(in));

    }

    @Test
    public void testShell() throws Exception {
        Shell.eval("echo foo", out);
        System.out.println("end eval");
        Scanner scn = new Scanner(in);
        assertEquals(scn.next(),"foo");
    }

    @Test
    public void testMainOneArg() throws Exception{
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        String[] args = new String[]{
                "arg one"
        };
        Shell.main(args);
        out.close();
        String result = input.readLine();

        assertEquals("COMP0010 shell: wrong number of arguments", result);

        System.setOut(originalOut);
    }

    @Test
    public void testMainUnexpectedArg() throws Exception{
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        String[] args = new String[]{
                "arg one",
                "arg two"
        };
        Shell.main(args);
        out.close();
        String result = input.readLine();

        assertEquals("COMP0010 shell: arg one: unexpected argument" , result);

        System.setOut(originalOut);
    }

    @Test
    public void testMainBadAppTwoArgs() throws Exception{
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(out));

        String[] args = new String[]{
                "-c",
                "badapp"
        };
        Shell.main(args);
        out.close();
        String result = input.readLine();

        assertTrue(result.startsWith("COMP0010 shell: "));

        System.setErr(originalErr);
    }



//    @BaseTest
//    public void testAntlr() throws Exception{
//        String expressionString = "echo foo";
//        CharStream parserInput = CharStreams.fromString(expressionString);
//        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
//        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
//        ParseTree tree = parser.eof_command();
//
//        Command command = tree.accept(new CommandConverter());
//
//        command.accept(new EvaluateVisitor());
//
//    }

}

