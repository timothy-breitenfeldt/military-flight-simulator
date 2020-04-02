/**
 * @author TJ, Coel, Richard
 * team 6
 * CSCD350
 * project part 2
*/

package acg.project.cli.parser;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import acg.project.action.ActionSet;

// Richard: Template
// TJ: Agent, Misc
// Coel: Structural, Behavioral


public class CommandParser {
    
    private String cmd;
    private Hashtable<String, Parser> parsers;
    private ActionSet actionSet;

    public CommandParser(acg.project.action.ActionSet actionSet, String cmd) {
        this.cmd = cmd;
        this.actionSet = actionSet;
        this.parsers = new Hashtable<String, Parser>();
        
        parsers.put("DEFINE", new TemplateParser(actionSet));
        parsers.put("SHOW", new TemplateParser(actionSet));
        parsers.put("CREATE", new AgentParser(actionSet)); 
        parsers.put("POPULATE", new StructuralParser(actionSet)); 
        parsers.put("COMMIT", new StructuralParser(actionSet));
        parsers.put("DO", new BehavioralParser(actionSet));
        parsers.put("@DO", new BehavioralParser(actionSet));
        parsers.put("@CLOCK", new MiscParser(actionSet));
        parsers.put("@RUN", new MiscParser(actionSet));
        parsers.put("@WAIT", new MiscParser(actionSet));
    }

    public void interpret() throws ParseException {
        
        if (this.cmd.isEmpty()) {
            throw new ParseException("Command is empty");
        }//end if
        
        ArrayList<String> commands = new ArrayList<String>();
        loadCommands(commands, this.cmd);
        
        for (String command: commands) {
            
            Scanner cmdScanner = new Scanner(command);
            String firstWord = "";
            Parser parser = null;
            
            if (cmdScanner.hasNext()) {
                
                firstWord = cmdScanner.next();
                firstWord = firstWord.toUpperCase();
                cmdScanner.close();
                cmdScanner = null;
                
                parser = parsers.get(firstWord);
                
                if (parser != null) {
                    parser.parseCommand(command);
                } else {
                    throw new ParseException("Invalid command");        
                }
            }
            
            if (cmdScanner != null) {
                cmdScanner.close();
                cmdScanner = null;
            }
        }
    }//end method
    
    private static void loadCommands(ArrayList<String> commands, String commandText) {
    
        Scanner cmdScanner = new Scanner(commandText);
        String command = "";
        String[] temp = null;
        
        while (cmdScanner.hasNextLine()) {
            command = cmdScanner.nextLine();
            command = command.trim();
            
            //check if the command contains a comment 
            if (command.contains("//")) {
                
                //if the command starts with a comment, set the command to an empty string to be ignored
                if (command.startsWith("//")) {
                    command = "";
                } else {
                    command = command.substring(0, command.indexOf("//"));
                }//end else
            }//end if
            
            //check if the command is not empty, which means it is neither a blank line, or a comment 
            if ( !command.isEmpty()) {
            
            //check if there are more than 1 command on a single line
                if (command.contains(";")) {
                    temp = command.split(";");
                    commands.addAll(Arrays.asList(temp));
                } else {
                    commands.add(command);
                }//end else 
            }//end if
        }//end while loop
        
        cmdScanner.close();
        
    }//end method
    
}//end class
