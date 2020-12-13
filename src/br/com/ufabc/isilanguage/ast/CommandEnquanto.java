package src.br.com.ufabc.isilanguage.ast;

import java.util.ArrayList;

public class CommandEnquanto extends AbstractCommand {

    private String condition;
    private ArrayList<AbstractCommand> enquantoCommands;

    public CommandEnquanto(String condition, ArrayList<AbstractCommand> enquantoCommands) {
        this.condition = condition;
        this.enquantoCommands = enquantoCommands;
    }

    @Override
    public String generateJavaCode() {
        StringBuilder str = new StringBuilder();
        str.append("\twhile(").append(condition).append("){\n\t");
        for(AbstractCommand cmd: this.enquantoCommands){
            str.append(cmd.generateJavaCode());
        }
        str.append("\n\t}");
        return str.toString();
    }

    @Override
    public String toString() {
        return "CommandEnquanto{" +
                "condition='" + condition + '\'' +
                ", enquantoCommands=" + enquantoCommands +
                '}';
    }
}
