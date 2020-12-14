grammar IsiLang;

@header{
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
}

@members{
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
	
	public void verificaID(String id){
		if (!symbolTable.exists(id)){
			throw new IsiSemanticException("Symbol "+id+" not declared");
		}
	}
	
	public void exibeComandos(){
		for (AbstractCommand c: program.getComandos()){
			System.out.println(c);
		}
	}
	
	public void checkUnusedVars() {
		listaNaoUsados = new ArrayList<String>();
        for (IsiSymbol is : symbolTable.getAll()) {
        	IsiVariable isiVar = (IsiVariable)is;
            if (isiVar.getValue() == null) {
            	listaNaoUsados.add(isiVar.getName());
            }
		}

		if (listaNaoUsados.size() != 0) {
        	System.out.println("WARNING - As variáveis foram declaradas, mas não foram utilizadas: "
        	    + String.join(", ", listaNaoUsados));
		}
	}

	public void generateCode() {
		program.generateTarget();
	}
}

prog	: 'programa' decl bloco 'fimprog;'
           {
           	 program.setVarTable(symbolTable);
           	 program.setComandos(stack.pop());

           }
		;

decl    :  (declaravar)+
        ;


declaravar :  'declare' tipo ID  {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new IsiVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);
	                  }
	                  else{
	                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
	                  }
                    }
              (  VIR
              	 ID {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new IsiVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);
	                  }
	                  else{
	                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
	                  }
                    }
              )*
               SC
           ;

tipo       : 'numero' { _tipo = IsiVariable.NUMBER;  }
           | 'texto'  { _tipo = IsiVariable.TEXT;  }
           ;

bloco	: { curThread = new ArrayList<AbstractCommand>();
	        stack.push(curThread);
          }
          (cmd)+
		;


cmd		:  cmdleitura
 		|  cmdescrita
 		|  cmdattrib
 		|  cmdselecao
 		|  cmdEnquanto
		;

cmdEnquanto  :  'enquanto' AP
                    ID    { _exprDecision = _input.LT(-1).getText(); }
                    OPREL { _exprDecision += _input.LT(-1).getText(); }
                    (ID | NUMBER) {_exprDecision += _input.LT(-1).getText(); }
                    FP
                    ACH
                    { curThread = new ArrayList<AbstractCommand>();
                      ArrayList<AbstractCommand> enquantoLista = new ArrayList<AbstractCommand>();
                      stack.push(curThread);
                    }
                    (cmd)+
                    FCH
                    {
                       enquantoLista = stack.pop();
                       CommandEnquanto cmd = new CommandEnquanto(_exprDecision, enquantoLista);
                       stack.peek().add(cmd);
                    };

cmdleitura	: 'leia' AP
                     ID { verificaID(_input.LT(-1).getText());
                     	  _readID = _input.LT(-1).getText();
                        }
                     FP
                     SC

              {
              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
              	CommandLeitura cmd = new CommandLeitura(_readID, var);
              	stack.peek().add(cmd);
              };

cmdescrita	: 'escreva'
                 AP
                 ID { verificaID(_input.LT(-1).getText());
	                  _writeID = _input.LT(-1).getText();
                     }
                 FP
                 SC
               {
                  IsiVariable isiVar = (IsiVariable)symbolTable.get(_writeID);
                  if (isiVar.getValue() == null) {
                      throw new IsiSemanticException("Symbol " + _writeID + " não foi inicializado.");
                  }
               	  CommandEscrita cmd = new CommandEscrita(_writeID);
               	  stack.peek().add(cmd);
               }
			;

cmdattrib	:  ID { verificaID(_input.LT(-1).getText());
                    _exprID = _input.LT(-1).getText();
                   }
               ATTR { _exprContent = ""; }
               expr
               SC
               {
               	 IsiVariable var = (IsiVariable)symbolTable.get(_exprID);
              	 var.setValue(_exprContent);
               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
               	 stack.peek().add(cmd);
               }
			;


cmdselecao  :  'se' AP
                    ID    { _exprDecision = _input.LT(-1).getText(); }
                    OPREL { _exprDecision += _input.LT(-1).getText(); }
                    (ID | NUMBER) {_exprDecision += _input.LT(-1).getText(); }
                    FP
                    ACH
                    { curThread = new ArrayList<AbstractCommand>();
                      stack.push(curThread);
                    }
                    (cmd)+

                    FCH
                    {
                       listaTrue = stack.pop();
                    }
                   ('senao'
                   	 ACH
                   	 {
                   	 	curThread = new ArrayList<AbstractCommand>();
                   	 	stack.push(curThread);
                   	 }
                   	(cmd+)
                   	FCH
                   	{
                   		listaFalse = stack.pop();
                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
                   		stack.peek().add(cmd);
                   	}
                   )?
            ;

expr		:  termo (
	             OP  { _exprContent += _input.LT(-1).getText();}
	            termo
	            )*
			;

termo		: ID { verificaID(_input.LT(-1).getText());
	               _exprContent += _input.LT(-1).getText();
                 }
            |
              NUMBER
              {
              	_exprContent += _input.LT(-1).getText();
              }
			;
	
AP	: '('
	;
	
FP	: ')'
	;
	
SC	: ';'
	;
	
OP	: '+' | '-' | '*' | '/'
	;
	
ATTR : '='
	 ;
	 
VIR  : ','
     ;
     
ACH  : '{'
     ;
     
FCH  : '}'
     ;
	 
	 
OPREL : '>' | '<' | '>=' | '<=' | '==' | '!='
      ;
      
ID	: [a-z] ([a-z] | [A-Z] | [0-9])*
	;
	
NUMBER	: [0-9]+ ('.' [0-9]+)?
		;
		
WS	: (' ' | '\t' | '\n' | '\r') -> skip;