grammar ShellGrammar;

/*
 * Parser Rules
 */

//Command : atomicCommand (';' atomicCommand)*;
eof_command
    : com=command EOF #finalExpr;

command
    : pipecom=pipe #pipeCom
    | left=command ';' right=command #seqCom
    | callcom=call #callCom;


pipe
    : left=call '|' right=call #leftCall
    | left=pipe '|' right=call #leftPipe;

call
    : WS? (redir+=redirection WS)* appName=argument (WS arg+=atom)* WS?;

redirection
    : FILE_INPUT WS? file=argument #inputExpr
    | FILE_OUTPUT WS? file=argument #outputExpr;
argument
    : (quoted|NONSPECIAL)+;
atom
    : redir=redirection #redir
    | arg=argument #arg;

quoted
    : sq=SINGLEQUOTED #singlequote
    | dq=doublequoted #doublequote
    | bq=BACKQUOTED #backquote;

doublequoted
    : '"' (bq+=BACKQUOTED|bq+=NONSPECIAL|bq+=WS)* '"';


/*
 * Lexer Rules
 */
SINGLEQUOTED : '\'' (~('\''|'\n'))* '\'';

WS : [ \r\t\n]+;
NONSPECIAL : ~(['"`;<>| \r\t\n])+;


//DOUBLEQUOTED : '"' (BACKQUOTED | ~('"'|'\n'|'`'))* '"';
BACKQUOTED : '`' (~('`'|'\n'))* '`';
FILE_INPUT : '<';
FILE_OUTPUT : '>';

DOUBLEQUOTECONTENT : ~('"'|'\n'|'`');
