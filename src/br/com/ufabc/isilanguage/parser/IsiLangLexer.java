// Generated from IsiLang.g4 by ANTLR 4.7.1
package src.br.com.ufabc.isilanguage.parser;

	import src.br.com.ufabc.isilanguage.datastructures.IsiSymbol;
	import src.br.com.ufabc.isilanguage.datastructures.IsiVariable;
	import src.br.com.ufabc.isilanguage.datastructures.IsiSymbolTable;
	import src.br.com.ufabc.isilanguage.exceptions.IsiSemanticException;
	import src.br.com.ufabc.isilanguage.ast.IsiProgram;
	import src.br.com.ufabc.isilanguage.ast.AbstractCommand;
	import src.br.com.ufabc.isilanguage.ast.CommandLeitura;
	import src.br.com.ufabc.isilanguage.ast.CommandEscrita;
	import src.br.com.ufabc.isilanguage.ast.CommandAtribuicao;
	import src.br.com.ufabc.isilanguage.ast.CommandDecisao;
	import src.br.com.ufabc.isilanguage.ast.CommandEnquanto;
	import java.util.ArrayList;
	import java.util.Stack;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IsiLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AP=11, FP=12, SC=13, OP=14, ATTR=15, VIR=16, ACH=17, FCH=18, 
		OPREL=19, ID=20, NUMBER=21, WS=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "AP", "FP", "SC", "OP", "ATTR", "VIR", "ACH", "FCH", "OPREL", 
		"ID", "NUMBER", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'programa'", "'fimprog;'", "'declare'", "'numero'", "'texto'", 
		"'enquanto'", "'leia'", "'escreva'", "'se'", "'senao'", "'('", "')'", 
		"';'", null, "'='", "','", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "AP", 
		"FP", "SC", "OP", "ATTR", "VIR", "ACH", "FCH", "OPREL", "ID", "NUMBER", 
		"WS"
	};
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


		private int _tipo;
		private String _varName;
		private String _varValue;
		private IsiSymbolTable symbolTable = new IsiSymbolTable();
		private IsiSymbol symbol;
		private IsiProgram program = new IsiProgram();
		private ArrayList<AbstractCommand> curThread;
		private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
		private String _readID;
		private String _writeID;
		private String _exprID;
		private String _exprContent;
		private String _exprDecision;
		private ArrayList<AbstractCommand> listaTrue;
		private ArrayList<AbstractCommand> listaFalse;
		private ArrayList<String> listaNaoUsados;

		public void verificaID(String id) {
			if (!symbolTable.exists(id)){
				throw new IsiSemanticException("Symbol "+id+" not declared");
			}
		}

		public void exibeVariaveis(){
			ArrayList<IsiSymbol> ls = symbolTable.getAll();
			for(IsiSymbol s: ls)
			{
				System.out.println(s);
			}
		}

		public void exibeComandos() {
			for (AbstractCommand c: program.getComandos()){
				System.out.print(c);
			}
		}

		public boolean verificaInputNumeros(String input) {
	        return input.matches("[0-9()+\\-*/.]");
		}
		
		public void verificaVarsNaoUtilizadas() {
			listaNaoUsados = new ArrayList<String>();
	        for (IsiSymbol is : symbolTable.getAll()) {
	        	IsiVariable isiVar = (IsiVariable)is;
	            if (isiVar.getValue() == null) {
	            	listaNaoUsados.add(isiVar.getName());
	            }
			}

			if (listaNaoUsados.size() != 0) {
	        	System.out.println("\nWARNING - As variaveis foram declaradas, mas nao foram utilizadas: "
	        	    + String.join(", ", listaNaoUsados));
			}
		}

		public void generateCode() {
			program.generateTarget();
		}
		
		
		public void verificaSimboloNaTabSimbolos(String varName)
		{
			_varName = varName; /*Captura o nome do identificador*/
		    _varValue = null; /*Valor do identificador*/
		    symbol = new IsiVariable(_varName, _tipo, _varValue); /*Cria��o de um novo e inclus�o na tabela de s�mbolos*/
		    if (!symbolTable.exists(_varName)){
		     	symbolTable.add(symbol);	
		    }	                
		    else{
		    	throw new IsiSemanticException("Symbol "+_varName+" already declared");
		    }
		}
		
		public void verificaVariavelNaoUtilizada()
		{
			
		}


	public IsiLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "IsiLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u00a8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u008f\n\24\3\25\3\25"+
		"\7\25\u0093\n\25\f\25\16\25\u0096\13\25\3\26\6\26\u0099\n\26\r\26\16\26"+
		"\u009a\3\26\3\26\6\26\u009f\n\26\r\26\16\26\u00a0\5\26\u00a3\n\26\3\27"+
		"\3\27\3\27\3\27\2\2\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30\3\2\b\5\2,-//"+
		"\61\61\4\2>>@@\3\2c|\5\2\62;C\\c|\3\2\62;\5\2\13\f\17\17\"\"\2\u00af\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2"+
		"\58\3\2\2\2\7A\3\2\2\2\tI\3\2\2\2\13P\3\2\2\2\rV\3\2\2\2\17_\3\2\2\2\21"+
		"d\3\2\2\2\23l\3\2\2\2\25o\3\2\2\2\27u\3\2\2\2\31w\3\2\2\2\33y\3\2\2\2"+
		"\35{\3\2\2\2\37}\3\2\2\2!\177\3\2\2\2#\u0081\3\2\2\2%\u0083\3\2\2\2\'"+
		"\u008e\3\2\2\2)\u0090\3\2\2\2+\u0098\3\2\2\2-\u00a4\3\2\2\2/\60\7r\2\2"+
		"\60\61\7t\2\2\61\62\7q\2\2\62\63\7i\2\2\63\64\7t\2\2\64\65\7c\2\2\65\66"+
		"\7o\2\2\66\67\7c\2\2\67\4\3\2\2\289\7h\2\29:\7k\2\2:;\7o\2\2;<\7r\2\2"+
		"<=\7t\2\2=>\7q\2\2>?\7i\2\2?@\7=\2\2@\6\3\2\2\2AB\7f\2\2BC\7g\2\2CD\7"+
		"e\2\2DE\7n\2\2EF\7c\2\2FG\7t\2\2GH\7g\2\2H\b\3\2\2\2IJ\7p\2\2JK\7w\2\2"+
		"KL\7o\2\2LM\7g\2\2MN\7t\2\2NO\7q\2\2O\n\3\2\2\2PQ\7v\2\2QR\7g\2\2RS\7"+
		"z\2\2ST\7v\2\2TU\7q\2\2U\f\3\2\2\2VW\7g\2\2WX\7p\2\2XY\7s\2\2YZ\7w\2\2"+
		"Z[\7c\2\2[\\\7p\2\2\\]\7v\2\2]^\7q\2\2^\16\3\2\2\2_`\7n\2\2`a\7g\2\2a"+
		"b\7k\2\2bc\7c\2\2c\20\3\2\2\2de\7g\2\2ef\7u\2\2fg\7e\2\2gh\7t\2\2hi\7"+
		"g\2\2ij\7x\2\2jk\7c\2\2k\22\3\2\2\2lm\7u\2\2mn\7g\2\2n\24\3\2\2\2op\7"+
		"u\2\2pq\7g\2\2qr\7p\2\2rs\7c\2\2st\7q\2\2t\26\3\2\2\2uv\7*\2\2v\30\3\2"+
		"\2\2wx\7+\2\2x\32\3\2\2\2yz\7=\2\2z\34\3\2\2\2{|\t\2\2\2|\36\3\2\2\2}"+
		"~\7?\2\2~ \3\2\2\2\177\u0080\7.\2\2\u0080\"\3\2\2\2\u0081\u0082\7}\2\2"+
		"\u0082$\3\2\2\2\u0083\u0084\7\177\2\2\u0084&\3\2\2\2\u0085\u008f\t\3\2"+
		"\2\u0086\u0087\7@\2\2\u0087\u008f\7?\2\2\u0088\u0089\7>\2\2\u0089\u008f"+
		"\7?\2\2\u008a\u008b\7?\2\2\u008b\u008f\7?\2\2\u008c\u008d\7#\2\2\u008d"+
		"\u008f\7?\2\2\u008e\u0085\3\2\2\2\u008e\u0086\3\2\2\2\u008e\u0088\3\2"+
		"\2\2\u008e\u008a\3\2\2\2\u008e\u008c\3\2\2\2\u008f(\3\2\2\2\u0090\u0094"+
		"\t\4\2\2\u0091\u0093\t\5\2\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094"+
		"\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095*\3\2\2\2\u0096\u0094\3\2\2\2"+
		"\u0097\u0099\t\6\2\2\u0098\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u0098"+
		"\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u00a2\3\2\2\2\u009c\u009e\7\60\2\2"+
		"\u009d\u009f\t\6\2\2\u009e\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u009e"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u009c\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3,\3\2\2\2\u00a4\u00a5\t\7\2\2\u00a5\u00a6\3\2\2\2"+
		"\u00a6\u00a7\b\27\2\2\u00a7.\3\2\2\2\t\2\u008e\u0092\u0094\u009a\u00a0"+
		"\u00a2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}