package src.br.com.ufabc.isilanguage.ast;

import src.br.com.ufabc.isilanguage.datastructures.IsiVariable;

public class CommandAtribuicao extends AbstractCommand{

	private String id;
	private String expr;
	
	public CommandAtribuicao(String id, String expr) {
		this.id = id;
		this.expr = expr;
	}
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		return "\t" + id + " = "+expr+";";
	}
	@Override
	public String toString() {
		return "CommandAtribuicao [id=" + id + ", expr=" + expr + "]";
	}
	
	

}
