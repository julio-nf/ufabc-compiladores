package src.br.com.ufabc.isilanguage.ast;

public class CommandEscrita extends AbstractCommand {

	private String id;
	
	public CommandEscrita(String id) {
		this.id = id;
	}
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		return "\tSystem.out.println("+id+");\n\t";
	}
	@Override
	public String toString() {
		return "Comando de escrita lido com sucesso!" + "[ escreva(" + id +"); ]\n";
	}
	

}
