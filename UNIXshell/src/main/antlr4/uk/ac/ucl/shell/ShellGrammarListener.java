// Generated from ShellGrammar.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ShellGrammarParser}.
 */
public interface ShellGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code finalExpr}
	 * labeled alternative in {@link ShellGrammarParser#eof_command}.
	 * @param ctx the parse tree
	 */
	void enterFinalExpr(ShellGrammarParser.FinalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code finalExpr}
	 * labeled alternative in {@link ShellGrammarParser#eof_command}.
	 * @param ctx the parse tree
	 */
	void exitFinalExpr(ShellGrammarParser.FinalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCallCom(ShellGrammarParser.CallComContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCallCom(ShellGrammarParser.CallComContext ctx);
	/**
	 * Enter a parse tree produced by the {@code seqCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterSeqCom(ShellGrammarParser.SeqComContext ctx);
	/**
	 * Exit a parse tree produced by the {@code seqCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitSeqCom(ShellGrammarParser.SeqComContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pipeCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterPipeCom(ShellGrammarParser.PipeComContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pipeCom}
	 * labeled alternative in {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitPipeCom(ShellGrammarParser.PipeComContext ctx);
	/**
	 * Enter a parse tree produced by the {@code leftCall}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void enterLeftCall(ShellGrammarParser.LeftCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code leftCall}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void exitLeftCall(ShellGrammarParser.LeftCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code leftPipe}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void enterLeftPipe(ShellGrammarParser.LeftPipeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code leftPipe}
	 * labeled alternative in {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void exitLeftPipe(ShellGrammarParser.LeftPipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterInputExpr(ShellGrammarParser.InputExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitInputExpr(ShellGrammarParser.InputExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code outputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterOutputExpr(ShellGrammarParser.OutputExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code outputExpr}
	 * labeled alternative in {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitOutputExpr(ShellGrammarParser.OutputExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ShellGrammarParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ShellGrammarParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code redir}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterRedir(ShellGrammarParser.RedirContext ctx);
	/**
	 * Exit a parse tree produced by the {@code redir}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitRedir(ShellGrammarParser.RedirContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arg}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterArg(ShellGrammarParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arg}
	 * labeled alternative in {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitArg(ShellGrammarParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singlequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterSinglequote(ShellGrammarParser.SinglequoteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singlequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitSinglequote(ShellGrammarParser.SinglequoteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doublequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterDoublequote(ShellGrammarParser.DoublequoteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doublequote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitDoublequote(ShellGrammarParser.DoublequoteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code backquote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterBackquote(ShellGrammarParser.BackquoteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code backquote}
	 * labeled alternative in {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitBackquote(ShellGrammarParser.BackquoteContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#doublequoted}.
	 * @param ctx the parse tree
	 */
	void enterDoublequoted(ShellGrammarParser.DoublequotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#doublequoted}.
	 * @param ctx the parse tree
	 */
	void exitDoublequoted(ShellGrammarParser.DoublequotedContext ctx);
}