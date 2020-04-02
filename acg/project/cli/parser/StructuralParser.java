package acg.project.cli.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import acg.architecture.datatype.Identifier;
import acg.project.action.ActionSet;
import acg.project.action.command.structural.CommandStructuralCommit;
import acg.project.action.command.structural.CommandStructuralPopulateCarrier;
import acg.project.action.command.structural.CommandStructuralPopulateWorld;


public class StructuralParser extends Parser {

    public StructuralParser(ActionSet actionSet) {
        super(actionSet);
    }
    
    @Override
    public void parseCommand(String cmd) throws ParseException {
    	List<Identifier> idList = new ArrayList<Identifier>();
    	Scanner cmdScanner = new Scanner(cmd);
        String token = ""; 
        
        if ( !cmdScanner.hasNext()) {
            throw new ParseException("Command given is empty");
        }//end if
        
        token = cmdScanner.next();  //populate or commit
        
        if (token.equalsIgnoreCase("populate")) {
        	token = cmdScanner.next();  //carrier or world
        	if (token.equalsIgnoreCase("carrier")) {
            	token = cmdScanner.next();  //carrier aid
            	Identifier carrierId = ParseUtils.parseID(token);
            	token = cmdScanner.next();//with
            	
            	if(token.equalsIgnoreCase("with")) {
            		token = cmdScanner.next();
            		if(token.equalsIgnoreCase("fighter")||token.equalsIgnoreCase("fighters")) {
            			while(cmdScanner.hasNext()) {
            				token = cmdScanner.next();
            				Identifier fighterId = ParseUtils.parseID(token);
            				idList.add(fighterId);
            			}
            			this.actionSet.getActionStructural().submit(new CommandStructuralPopulateCarrier(carrierId, idList));
            		}
            		else {
                        cmdScanner.close();
                        throw new ParseException("Invalid command");
                    }//end else
            	}
            	else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }//end else
            }
            else if (token.equalsIgnoreCase("world")) {
            	token = cmdScanner.next();//with
            	
            	if(token.equalsIgnoreCase("with")) {
            	    while(cmdScanner.hasNext()) {
            	    	token = cmdScanner.next();
        				Identifier id = ParseUtils.parseID(token);
        				idList.add(id);
            	    }
            	    this.actionSet.getActionStructural().submit(new CommandStructuralPopulateWorld(idList));
            	}
            	else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }//end else
            }
            else {
                cmdScanner.close();
                throw new ParseException("Invalid command");
            }
        }
        else if (token.equalsIgnoreCase("commit")) {
        	this.actionSet.getActionStructural().submit(new CommandStructuralCommit());
        }
        else {
            cmdScanner.close();
            throw new ParseException("Invalid command");
        }//end else
        
        cmdScanner.close();
    }
	

}
