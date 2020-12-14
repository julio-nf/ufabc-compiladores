package src.br.com.ufabc.isilanguage.ast;

import java.util.ArrayList;

public class CommandDecisao extends AbstractCommand {
 
	private String condition;
	private ArrayList<AbstractCommand> listaTrue;
	private ArrayList<AbstractCommand> listaFalse;
	
	public CommandDecisao(String condition, ArrayList<AbstractCommand> lt, ArrayList<AbstractCommand> lf) {
		this.condition = condition;
		this.listaTrue = lt;
		this.listaFalse = lf;
	}
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append("\tif ("+condition+") {\n\t");
		for (AbstractCommand cmd: listaTrue) {
			str.append(cmd.generateJavaCode());
		}
		str.append("}");
		if (listaFalse.size() > 0) {
			str.append("\n\telse {\n\t");
			for (AbstractCommand cmd: listaFalse) {
				str.append(cmd.generateJavaCode());
			}
			str.append("}\n");
		
		}
		return str.toString();
	}
	@Override
	public String toString() {
		StringBuilder ltT = new StringBuilder();
		StringBuilder ltF = new StringBuilder();
		
    	for(AbstractCommand c: listaTrue)
    	{
    		ltT.append("\t");
    		ltT.append(c);
       	}
    	
    	for(AbstractCommand c: listaFalse)
    	{
    		ltF.append("\t");
    		ltF.append(c);
    	}
    	
		
		
		return "Comando de decisão lido com sucesso! [if (" + condition + " ) {\n" +  ltT + "\t} " +
		"else {\n" + ltF + "\t}\n";
				
	}
	
	

}
