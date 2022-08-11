// Generated from uk\ac\u005Cucl\shell\ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ShellGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ShellGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code finalExpr}
	 * labeled alternative in {@link ShellGrammarParser#eof_command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinalExpr(ShellGrammarParser.FinalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallCom(ShellGrammarParser.CallComContext ctx);
	/**
	 * Visit a parse tree produced by the {@code seqCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqCom(ShellGrammarParser.SeqComContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pipeCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPipeCom(ShellGrammarParser.PipeComContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leftCall}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftCall(ShellGrammarParser.LeftCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leftPipe}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftPipe(ShellGrammarParser.LeftPipeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputExpr(ShellGrammarParser.InputExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code outputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputExpr(ShellGrammarParser.OutputExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ShellGrammarParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code redir}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRedir(ShellGrammarParser.RedirContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arg}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(ShellGrammarParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singlequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSinglequote(ShellGrammarParser.SinglequoteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doublequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoublequote(ShellGrammarParser.DoublequoteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code backquote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBackquote(ShellGrammarParser.BackquoteContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#doublequoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoublequoted(ShellGrammarParser.DoublequotedContext ctx);
}