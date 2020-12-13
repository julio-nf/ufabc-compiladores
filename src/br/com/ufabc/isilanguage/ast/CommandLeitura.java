package src.br.com.ufabc.isilanguage.ast;

import src.br.com.ufabc.isilanguage.datastructures.IsiVariable;

public class CommandLeitura extends AbstractCommand {

	private String id;
	private IsiVariable var;
	
	public CommandLeitura (String id, IsiVariable var) {
		this.id = id;
		this.var = var;
	}
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		return "\t" + id +"= _key." + (var.getType()==IsiVariable.NUMBER? "nextDouble();": "nextLine();");
	}
	@Override
	public String toString() {
		return "CommandLeitura [id=" + id + "]";
	}

}
