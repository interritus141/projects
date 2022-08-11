// Generated from ShellGrammar.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShellGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, SINGLEQUOTED=4, WS=5, NONSPECIAL=6, BACKQUOTED=7, 
		FILE_INPUT=8, FILE_OUTPUT=9, DOUBLEQUOTECONTENT=10;
	public static final int
		RULE_eof_command = 0, RULE_command = 1, RULE_pipe = 2, RULE_call = 3, 
		RULE_redirection = 4, RULE_argument = 5, RULE_atom = 6, RULE_quoted = 7, 
		RULE_doublequoted = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"eof_command", "command", "pipe", "call", "redirection", "argument", 
			"atom", "quoted", "doublequoted"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'|'", "'\"'", null, null, null, null, "'<'", "'>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "SINGLEQUOTED", "WS", "NONSPECIAL", "BACKQUOTED", 
			"FILE_INPUT", "FILE_OUTPUT", "DOUBLEQUOTECONTENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ShellGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Eof_commandContext extends ParserRuleContext {
		public Eof_commandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eof_command; }
	 
		public Eof_commandContext() { }
		public void copyFrom(Eof_commandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FinalExprContext extends Eof_commandContext {
		public CommandContext com;
		public TerminalNode EOF() { return getToken(ShellGrammarParser.EOF, 0); }
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public FinalExprContext(Eof_commandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterFinalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitFinalExpr(this);
		}
	}

	public final Eof_commandContext eof_command() throws RecognitionException {
		Eof_commandContext _localctx = new Eof_commandContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eof_command);
		try {
			_localctx = new FinalExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			((FinalExprContext)_localctx).com = command(0);
			setState(19);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallComContext extends CommandContext {
		public CallContext callcom;
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public CallComContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterCallCom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitCallCom(this);
		}
	}
	public static class SeqComContext extends CommandContext {
		public CommandContext left;
		public CommandContext right;
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public SeqComContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterSeqCom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitSeqCom(this);
		}
	}
	public static class PipeComContext extends CommandContext {
		public PipeContext pipecom;
		public PipeContext pipe() {
			return getRuleContext(PipeContext.class,0);
		}
		public PipeComContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterPipeCom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitPipeCom(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		return command(0);
	}

	private CommandContext command(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CommandContext _localctx = new CommandContext(_ctx, _parentState);
		CommandContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_command, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				_localctx = new PipeComContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(22);
				((PipeComContext)_localctx).pipecom = pipe(0);
				}
				break;
			case 2:
				{
				_localctx = new CallComContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(23);
				((CallComContext)_localctx).callcom = call();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(31);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SeqComContext(new CommandContext(_parentctx, _parentState));
					((SeqComContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_command);
					setState(26);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(27);
					match(T__0);
					setState(28);
					((SeqComContext)_localctx).right = command(3);
					}
					} 
				}
				setState(33);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PipeContext extends ParserRuleContext {
		public PipeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pipe; }
	 
		public PipeContext() { }
		public void copyFrom(PipeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LeftCallContext extends PipeContext {
		public CallContext left;
		public CallContext right;
		public List<CallContext> call() {
			return getRuleContexts(CallContext.class);
		}
		public CallContext call(int i) {
			return getRuleContext(CallContext.class,i);
		}
		public LeftCallContext(PipeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterLeftCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitLeftCall(this);
		}
	}
	public static class LeftPipeContext extends PipeContext {
		public PipeContext left;
		public CallContext right;
		public PipeContext pipe() {
			return getRuleContext(PipeContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public LeftPipeContext(PipeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterLeftPipe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitLeftPipe(this);
		}
	}

	public final PipeContext pipe() throws RecognitionException {
		return pipe(0);
	}

	private PipeContext pipe(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PipeContext _localctx = new PipeContext(_ctx, _parentState);
		PipeContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_pipe, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new LeftCallContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(35);
			((LeftCallContext)_localctx).left = call();
			setState(36);
			match(T__1);
			setState(37);
			((LeftCallContext)_localctx).right = call();
			}
			_ctx.stop = _input.LT(-1);
			setState(44);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LeftPipeContext(new PipeContext(_parentctx, _parentState));
					((LeftPipeContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_pipe);
					setState(39);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(40);
					match(T__1);
					setState(41);
					((LeftPipeContext)_localctx).right = call();
					}
					} 
				}
				setState(46);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CallContext extends ParserRuleContext {
		public RedirectionContext redirection;
		public List<RedirectionContext> redir = new ArrayList<RedirectionContext>();
		public ArgumentContext appName;
		public AtomContext atom;
		public List<AtomContext> arg = new ArrayList<AtomContext>();
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public List<TerminalNode> WS() { return getTokens(ShellGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ShellGrammarParser.WS, i);
		}
		public List<RedirectionContext> redirection() {
			return getRuleContexts(RedirectionContext.class);
		}
		public RedirectionContext redirection(int i) {
			return getRuleContext(RedirectionContext.class,i);
		}
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitCall(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_call);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WS) {
				{
				setState(47);
				match(WS);
				}
			}

			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FILE_INPUT || _la==FILE_OUTPUT) {
				{
				{
				setState(50);
				((CallContext)_localctx).redirection = redirection();
				((CallContext)_localctx).redir.add(((CallContext)_localctx).redirection);
				setState(51);
				match(WS);
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58);
			((CallContext)_localctx).appName = argument();
			setState(63);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(59);
					match(WS);
					setState(60);
					((CallContext)_localctx).atom = atom();
					((CallContext)_localctx).arg.add(((CallContext)_localctx).atom);
					}
					} 
				}
				setState(65);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(66);
				match(WS);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RedirectionContext extends ParserRuleContext {
		public RedirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_redirection; }
	 
		public RedirectionContext() { }
		public void copyFrom(RedirectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InputExprContext extends RedirectionContext {
		public ArgumentContext file;
		public TerminalNode FILE_INPUT() { return getToken(ShellGrammarParser.FILE_INPUT, 0); }
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode WS() { return getToken(ShellGrammarParser.WS, 0); }
		public InputExprContext(RedirectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterInputExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitInputExpr(this);
		}
	}
	public static class OutputExprContext extends RedirectionContext {
		public ArgumentContext file;
		public TerminalNode FILE_OUTPUT() { return getToken(ShellGrammarParser.FILE_OUTPUT, 0); }
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode WS() { return getToken(ShellGrammarParser.WS, 0); }
		public OutputExprContext(RedirectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterOutputExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitOutputExpr(this);
		}
	}

	public final RedirectionContext redirection() throws RecognitionException {
		RedirectionContext _localctx = new RedirectionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_redirection);
		int _la;
		try {
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FILE_INPUT:
				_localctx = new InputExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				match(FILE_INPUT);
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WS) {
					{
					setState(70);
					match(WS);
					}
				}

				setState(73);
				((InputExprContext)_localctx).file = argument();
				}
				break;
			case FILE_OUTPUT:
				_localctx = new OutputExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				match(FILE_OUTPUT);
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WS) {
					{
					setState(75);
					match(WS);
					}
				}

				setState(78);
				((OutputExprContext)_localctx).file = argument();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public List<QuotedContext> quoted() {
			return getRuleContexts(QuotedContext.class);
		}
		public QuotedContext quoted(int i) {
			return getRuleContext(QuotedContext.class,i);
		}
		public List<TerminalNode> NONSPECIAL() { return getTokens(ShellGrammarParser.NONSPECIAL); }
		public TerminalNode NONSPECIAL(int i) {
			return getToken(ShellGrammarParser.NONSPECIAL, i);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitArgument(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_argument);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(83); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(83);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__2:
					case SINGLEQUOTED:
					case BACKQUOTED:
						{
						setState(81);
						quoted();
						}
						break;
					case NONSPECIAL:
						{
						setState(82);
						match(NONSPECIAL);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(85); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArgContext extends AtomContext {
		public ArgumentContext arg;
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public ArgContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitArg(this);
		}
	}
	public static class RedirContext extends AtomContext {
		public RedirectionContext redir;
		public RedirectionContext redirection() {
			return getRuleContext(RedirectionContext.class,0);
		}
		public RedirContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterRedir(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitRedir(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_atom);
		try {
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FILE_INPUT:
			case FILE_OUTPUT:
				_localctx = new RedirContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				((RedirContext)_localctx).redir = redirection();
				}
				break;
			case T__2:
			case SINGLEQUOTED:
			case NONSPECIAL:
			case BACKQUOTED:
				_localctx = new ArgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				((ArgContext)_localctx).arg = argument();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuotedContext extends ParserRuleContext {
		public QuotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quoted; }
	 
		public QuotedContext() { }
		public void copyFrom(QuotedContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SinglequoteContext extends QuotedContext {
		public Token sq;
		public TerminalNode SINGLEQUOTED() { return getToken(ShellGrammarParser.SINGLEQUOTED, 0); }
		public SinglequoteContext(QuotedContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterSinglequote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitSinglequote(this);
		}
	}
	public static class BackquoteContext extends QuotedContext {
		public Token bq;
		public TerminalNode BACKQUOTED() { return getToken(ShellGrammarParser.BACKQUOTED, 0); }
		public BackquoteContext(QuotedContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterBackquote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitBackquote(this);
		}
	}
	public static class DoublequoteContext extends QuotedContext {
		public DoublequotedContext dq;
		public DoublequotedContext doublequoted() {
			return getRuleContext(DoublequotedContext.class,0);
		}
		public DoublequoteContext(QuotedContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterDoublequote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitDoublequote(this);
		}
	}

	public final QuotedContext quoted() throws RecognitionException {
		QuotedContext _localctx = new QuotedContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_quoted);
		try {
			setState(94);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SINGLEQUOTED:
				_localctx = new SinglequoteContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				((SinglequoteContext)_localctx).sq = match(SINGLEQUOTED);
				}
				break;
			case T__2:
				_localctx = new DoublequoteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				((DoublequoteContext)_localctx).dq = doublequoted();
				}
				break;
			case BACKQUOTED:
				_localctx = new BackquoteContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
				((BackquoteContext)_localctx).bq = match(BACKQUOTED);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoublequotedContext extends ParserRuleContext {
		public Token BACKQUOTED;
		public List<Token> bq = new ArrayList<Token>();
		public Token NONSPECIAL;
		public Token WS;
		public List<TerminalNode> BACKQUOTED() { return getTokens(ShellGrammarParser.BACKQUOTED); }
		public TerminalNode BACKQUOTED(int i) {
			return getToken(ShellGrammarParser.BACKQUOTED, i);
		}
		public List<TerminalNode> NONSPECIAL() { return getTokens(ShellGrammarParser.NONSPECIAL); }
		public TerminalNode NONSPECIAL(int i) {
			return getToken(ShellGrammarParser.NONSPECIAL, i);
		}
		public List<TerminalNode> WS() { return getTokens(ShellGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ShellGrammarParser.WS, i);
		}
		public DoublequotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doublequoted; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterDoublequoted(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitDoublequoted(this);
		}
	}

	public final DoublequotedContext doublequoted() throws RecognitionException {
		DoublequotedContext _localctx = new DoublequotedContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_doublequoted);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__2);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WS) | (1L << NONSPECIAL) | (1L << BACKQUOTED))) != 0)) {
				{
				setState(100);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case BACKQUOTED:
					{
					setState(97);
					((DoublequotedContext)_localctx).BACKQUOTED = match(BACKQUOTED);
					((DoublequotedContext)_localctx).bq.add(((DoublequotedContext)_localctx).BACKQUOTED);
					}
					break;
				case NONSPECIAL:
					{
					setState(98);
					((DoublequotedContext)_localctx).NONSPECIAL = match(NONSPECIAL);
					((DoublequotedContext)_localctx).bq.add(((DoublequotedContext)_localctx).NONSPECIAL);
					}
					break;
				case WS:
					{
					setState(99);
					((DoublequotedContext)_localctx).WS = match(WS);
					((DoublequotedContext)_localctx).bq.add(((DoublequotedContext)_localctx).WS);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return command_sempred((CommandContext)_localctx, predIndex);
		case 2:
			return pipe_sempred((PipeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean command_sempred(CommandContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean pipe_sempred(PipeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\fn\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\5\3\33\n\3\3\3\3\3\3\3\7\3 \n\3\f\3\16\3#\13\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\7\4-\n\4\f\4\16\4\60\13\4\3\5\5\5\63\n\5\3\5\3"+
		"\5\3\5\7\58\n\5\f\5\16\5;\13\5\3\5\3\5\3\5\7\5@\n\5\f\5\16\5C\13\5\3\5"+
		"\5\5F\n\5\3\6\3\6\5\6J\n\6\3\6\3\6\3\6\5\6O\n\6\3\6\5\6R\n\6\3\7\3\7\6"+
		"\7V\n\7\r\7\16\7W\3\b\3\b\5\b\\\n\b\3\t\3\t\3\t\5\ta\n\t\3\n\3\n\3\n\3"+
		"\n\7\ng\n\n\f\n\16\nj\13\n\3\n\3\n\3\n\2\4\4\6\13\2\4\6\b\n\f\16\20\22"+
		"\2\2\2v\2\24\3\2\2\2\4\32\3\2\2\2\6$\3\2\2\2\b\62\3\2\2\2\nQ\3\2\2\2\f"+
		"U\3\2\2\2\16[\3\2\2\2\20`\3\2\2\2\22b\3\2\2\2\24\25\5\4\3\2\25\26\7\2"+
		"\2\3\26\3\3\2\2\2\27\30\b\3\1\2\30\33\5\6\4\2\31\33\5\b\5\2\32\27\3\2"+
		"\2\2\32\31\3\2\2\2\33!\3\2\2\2\34\35\f\4\2\2\35\36\7\3\2\2\36 \5\4\3\5"+
		"\37\34\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\5\3\2\2\2#!\3\2\2\2"+
		"$%\b\4\1\2%&\5\b\5\2&\'\7\4\2\2\'(\5\b\5\2(.\3\2\2\2)*\f\3\2\2*+\7\4\2"+
		"\2+-\5\b\5\2,)\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\7\3\2\2\2\60."+
		"\3\2\2\2\61\63\7\7\2\2\62\61\3\2\2\2\62\63\3\2\2\2\639\3\2\2\2\64\65\5"+
		"\n\6\2\65\66\7\7\2\2\668\3\2\2\2\67\64\3\2\2\28;\3\2\2\29\67\3\2\2\29"+
		":\3\2\2\2:<\3\2\2\2;9\3\2\2\2<A\5\f\7\2=>\7\7\2\2>@\5\16\b\2?=\3\2\2\2"+
		"@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BE\3\2\2\2CA\3\2\2\2DF\7\7\2\2ED\3\2\2\2"+
		"EF\3\2\2\2F\t\3\2\2\2GI\7\n\2\2HJ\7\7\2\2IH\3\2\2\2IJ\3\2\2\2JK\3\2\2"+
		"\2KR\5\f\7\2LN\7\13\2\2MO\7\7\2\2NM\3\2\2\2NO\3\2\2\2OP\3\2\2\2PR\5\f"+
		"\7\2QG\3\2\2\2QL\3\2\2\2R\13\3\2\2\2SV\5\20\t\2TV\7\b\2\2US\3\2\2\2UT"+
		"\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\r\3\2\2\2Y\\\5\n\6\2Z\\\5\f\7"+
		"\2[Y\3\2\2\2[Z\3\2\2\2\\\17\3\2\2\2]a\7\6\2\2^a\5\22\n\2_a\7\t\2\2`]\3"+
		"\2\2\2`^\3\2\2\2`_\3\2\2\2a\21\3\2\2\2bh\7\5\2\2cg\7\t\2\2dg\7\b\2\2e"+
		"g\7\7\2\2fc\3\2\2\2fd\3\2\2\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2"+
		"ik\3\2\2\2jh\3\2\2\2kl\7\5\2\2l\23\3\2\2\2\22\32!.\629AEINQUW[`fh";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}