/* JFlex example: part of Java language lexer specification */
import java_cup.runtime.*;

/**
 * This class is a simple example lexer.
 */
%%
%class Scanner
%unicode
%cup
%line
%column
%{
 
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

RelSymbolName   = [:uppercase:] ([:letter:] | [:digit:])*
FunSymbolName	= [:lowercase:] ([:letter:] | [:digit:])*


%%
<YYINITIAL> {
  /* operators */
  "&"                            { return symbol(sym.AND); }
  "|"                            { return symbol(sym.OR); }
  "->"                           { return symbol(sym.IMP); }
  "<->"                          { return symbol(sym.BIIMP); }
  "-"                            { return symbol(sym.NEG); }
  "("                            { return symbol(sym.LPAREN); }
  ")"                            { return symbol(sym.RPAREN); }
  ","                            { return symbol(sym.COMMA); }
  "EXISTS"                       { return symbol(sym.EXISTS); }
  "FORALL"                       { return symbol(sym.FORALL); }

  /* identifiers */ 
  {RelSymbolName}                { return symbol(sym.RELSYMBOL, new String(yytext())); }
  {FunSymbolName}                { return symbol(sym.FUNSYMBOL, new String(yytext())); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}
 /* error fallback */
.|\n                             { throw new Error("Illegal character <"+
                                                    yytext()+">"); }

