package src.br.com.ufabc.isilanguage.main;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import src.br.com.ufabc.isilanguage.exceptions.IsiSemanticException;
import src.br.com.ufabc.isilanguage.parser.IsiLangLexer;
import src.br.com.ufabc.isilanguage.parser.IsiLangParser;

/* esta √© a classe que √© respons√°vel por criar a intera√ß√£o com o usu√°rio
 * instanciando nosso parser e apontando para o arquivo fonte
 * 
 * Arquivo fonte: extensao .isi
 * 
 */
public class MainClass {
	public static void main(String[] args) {
		try {
			IsiLangLexer lexer;
			IsiLangParser parser;
			
			// leio o arquivo "input.isi" e isso √© entrada para o Analisador Lexico
			lexer = new IsiLangLexer(CharStreams.fromFileName("input.isi"));
			
			// crio um "fluxo de tokens" para passar para o PARSER
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			
			// crio meu parser a partir desse tokenStream
			parser = new IsiLangParser(tokenStream);
			
			parser.prog();
			
			System.out.println("*** Iniciando o processo de compilaÁ„o... ***\n");
			long tempoInicial = System.currentTimeMillis();

			parser.exibeVariaveis();
			
			System.out.println("\n--------------------------------------------------\n");
			
			parser.exibeComandos();

			parser.checkUnusedVars();
			
			parser.generateCode();
			
			System.out.printf("\n\n*** CompilaÁ„o finalizada com sucesso em %.3f segundo(s) ***\n", (System.currentTimeMillis() - tempoInicial)  / 1000d);
		}
		catch(IsiSemanticException ex) {
			System.err.println("Semantic error - "+ex.getMessage());
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("ERROR "+ex.getMessage());
		}
		
	}

}
