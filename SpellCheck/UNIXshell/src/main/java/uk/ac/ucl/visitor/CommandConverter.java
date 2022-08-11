package uk.ac.ucl.visitor;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.command.*;
import uk.ac.ucl.shell.ShellGrammarBaseVisitor;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static uk.ac.ucl.shell.Shell.parseString;

public class CommandConverter extends ShellGrammarBaseVisitor<Command> {
    private CurrentDirectory currentDirectory;

    public void setCurrentDirectory(CurrentDirectory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    /**
     * Visit a parse tree produced by the {@code finalExpr}
     * labeled alternative in {@link ShellGrammarParser#eof_command}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitFinalExpr(ShellGrammarParser.FinalExprContext ctx) {
        return this.visit(ctx.com);
    }

    /**
     * Visit a parse tree produced by the {@code callCom}
     * labeled alternative in .
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitCallCom(ShellGrammarParser.CallComContext ctx) {
        return this.visit(ctx.callcom);
    }

    /**
     * Visit a parse tree produced by the {@code seqCom}
     * labeled alternative in {@link }.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitSeqCom(ShellGrammarParser.SeqComContext ctx) {
        Command left = this.visit(ctx.left);
        Command right = this.visit(ctx.right);

        return new Seq(left, right);
    }

    /**
     * Visit a parse tree produced by the {@code pipeCom}
     * labeled alternative in .
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitPipeCom(ShellGrammarParser.PipeComContext ctx) {
        return this.visit(ctx.pipecom);
    }

    /**
     * Visit a parse tree produced by the {@code singlePipe}
     * labeled alternative in .
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitLeftCall(ShellGrammarParser.LeftCallContext ctx) {
        Command input = this.visit(ctx.left);
        Command piped = this.visit(ctx.right);
        return new Pipe(input, piped);

    }

    /**
     * Visit a parse tree produced by the {@code seqPipe}
     * labeled alternative in .
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitLeftPipe(ShellGrammarParser.LeftPipeContext ctx) {
        Command pipeCom = this.visit(ctx.left);
        Command piped = this.visit(ctx.right);
        return new Pipe(pipeCom, piped);
    }

    /**
     * Visit a parse tree produced by {@link ShellGrammarParser#call}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitCall(ShellGrammarParser.CallContext ctx)  {
        List<ShellGrammarParser.AtomContext> args = ctx.arg;
        ArrayList<String> appArgs = new ArrayList<>();
        // set input and output, if > 1 then throw error
        InputStream is = null;
        OutputStream os = null;

        for (ShellGrammarParser.AtomContext ac :
                args) {
            Command c = this.visit(ac);
            // quoted
            if (c instanceof Content){
                appArgs.add(((Content) c).getString());
            }
            // nonspecial
            else if (c instanceof MultipleContent){
                appArgs.addAll(((MultipleContent) c).getLs());
            }
            // io redirection from atom
            else{
                if (c instanceof InputRedirection){
                    is = returnInputRedir(c, is);
                }
                if (c instanceof OutputRedirection){
                    os = returnOutputRedir(c, os);
                }
            }
        }
        List<ShellGrammarParser.RedirectionContext> redirs = ctx.redir;

        // io redirection as redir
        for (ShellGrammarParser.RedirectionContext rc :
             redirs) {
            Command c = this.visit(rc);

            if (c instanceof InputRedirection){
                is = returnInputRedir(c, is);
            }

            if (c instanceof OutputRedirection){
                os = returnOutputRedir(c, os);
            }
        }
        // call app with appropriate in/out
        try {
            Command c = this.visit(ctx.appName);
            String appName;
            if (c instanceof Content){
                appName=((Content) c).getString();
            }
            else if (c instanceof MultipleContent){
                appName = ((MultipleContent) c).getLs().get(0);
            }
            else {
                throw new Exception();
            }
            return new Call(appName, appArgs, is, os, currentDirectory);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }



    private InputStream returnInputRedir(Command c, InputStream is)  {
        if (is == null) {
            return ((InputRedirection) c).getInputStream();
        } else {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Too many redirections (input)");
        }
    }

    private OutputStream returnOutputRedir(Command c, OutputStream os) {
        if (os == null) {
            return ((OutputRedirection) c).getOutputStream();
        } else {
            try {
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Too many redirections (output)");
        }
    }



    /**
     * Visit a parse tree produced by the {@code inputExpr}
     * labeled alternative in {@link ShellGrammarParser#redirection}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitInputExpr(ShellGrammarParser.InputExprContext ctx) {
        String fileName = ctx.file.getText();
        File file = new File(currentDirectory.getCurrentDirectory() + "/" + fileName);

        try {
            return new InputRedirection(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Visit a parse tree produced by the {@code outputExpr}
     * labeled alternative in {@link ShellGrammarParser#redirection}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitOutputExpr(ShellGrammarParser.OutputExprContext ctx) {

        String fileName = ctx.file.getText();
        File file = new File(currentDirectory.getCurrentDirectory() + "/" + fileName);

        try {
            file.createNewFile();
            return new OutputRedirection(file);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Visit a parse tree produced by the {@code redir}
     * labeled alternative in {@link ShellGrammarParser#atom}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitRedir(ShellGrammarParser.RedirContext ctx) {
        return this.visit(ctx.redir);
    }

    /**
     * Visit a parse tree produced by the {@code arg}
     * labeled alternative in {@link ShellGrammarParser#atom}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitArg(ShellGrammarParser.ArgContext ctx) {
        return this.visit(ctx.arg);
    }


    @Override
    public Command visitArgument(ShellGrammarParser.ArgumentContext ctx){
        boolean nonSpecial = false;
        StringBuilder arg = new StringBuilder();
        for (ParseTree child : ctx.children) {
            Command c = this.visit(child);
            // this is a NONSPECIAL token
            if (c == null){
                nonSpecial = true;
                arg.append(child.getText());
            }
            // else QUOTED
            else{
                arg.append(((Content) c).getString());
            }
        }

        if (nonSpecial){
            try {
                return new MultipleContent(arg.toString(), currentDirectory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Content(arg.toString());
    }

    /**
     * Visit a parse tree produced by the {@code singlequote}
     * labeled alternative in {@link ShellGrammarParser#quoted}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitSinglequote(ShellGrammarParser.SinglequoteContext ctx) {
        String s = ctx.sq.getText();
        return new Content( s.replace("'", ""));
    }

    /**
     * Visit a parse tree produced by the {@code doublequote}
     * labeled alternative in {@link ShellGrammarParser#quoted}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitDoublequote(ShellGrammarParser.DoublequoteContext ctx) {
        return this.visit(ctx.dq);
    }


    /**
     * Visit a parse tree produced by the {@code backquote}
     * labeled alternative in {@link ShellGrammarParser#quoted}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitBackquote(ShellGrammarParser.BackquoteContext ctx) {
        String s = ctx.bq.getText();
        try{

            String toReturn = evaluateString(s);
            toReturn = toReturn.replace("\n", " ");
            return new Content(toReturn);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String evaluateString (String s){

        Command c = parseBackquote(s);
        try{
            PipedInputStream in = new PipedInputStream();
            PipedOutputStream out = new PipedOutputStream(in);
            c.accept(new EvaluateVisitor(null, out));
            out.close();
            byte[] bytes = in.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);

            // remove last byte
            return content.substring(0, content.length()-1);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Command parseBackquote(String s){
        String s1 = s.replace("`", "");
        return parseString(s1, currentDirectory);

    }


    /**
     * Visit a parse tree produced by {@link ShellGrammarParser#doublequoted}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Command visitDoublequoted(ShellGrammarParser.DoublequotedContext ctx) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Token t :
                ctx.bq) {
            if (t.getType() == ShellGrammarLexer.BACKQUOTED){
                Command c = parseBackquote(t.getText());
                PipedInputStream in = new PipedInputStream();
                PipedOutputStream out;
                try {
                    out = new PipedOutputStream(in);
                    EvaluateVisitor evaluateVisitor = new EvaluateVisitor(null, out);
                    c.accept(evaluateVisitor);
                    out.close();
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
                try {
                    for (int ch; (ch = in.read()) != -1; ) {
                        stringBuilder.append((char) ch);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
//                String s = t.getText();
//                if (s.contains("\"") || s.contains("`") || s.contains("\n")){
//                    throw new RuntimeException("Invalid double quote content");
//                }
                stringBuilder.append(t.getText());
            }
        }
        return new Content(stringBuilder.toString());
    }

}
