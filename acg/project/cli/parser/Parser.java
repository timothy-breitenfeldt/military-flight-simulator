package acg.project.cli.parser;

import acg.project.action.ActionSet;


public abstract class Parser {
    
    protected ActionSet actionSet;
    
    public Parser(ActionSet actionSet) {
        this.actionSet = actionSet;
    }

    public abstract void parseCommand(String cmd) throws ParseException;

}