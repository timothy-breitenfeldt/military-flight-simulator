package acg.project.cli.parser;

import java.util.Scanner;

import acg.architecture.datatype.Altitude;
import acg.architecture.datatype.AngleNavigational;
import acg.architecture.datatype.CoordinateWorld;
import acg.architecture.datatype.Identifier;
import acg.architecture.datatype.Speed;
import acg.project.action.ActionSet;
import acg.project.action.command.behavioral.CommandBehavioralDoAsk;
import acg.project.action.command.behavioral.CommandBehavioralDoAsk.E_Parameter;
import acg.project.action.command.behavioral.CommandBehavioralDoBoom;
import acg.project.action.command.behavioral.CommandBehavioralDoCatapult;
import acg.project.action.command.behavioral.CommandBehavioralDoForceAll;
import acg.project.action.command.behavioral.CommandBehavioralDoForceCoordinates;
import acg.project.action.command.behavioral.CommandBehavioralDoForceHeading;
import acg.project.action.command.behavioral.CommandBehavioralDoSetHeading;
import acg.project.action.command.behavioral.CommandBehavioralDoSetHeading.E_Direction;


public class BehavioralParser extends Parser {

    public BehavioralParser(ActionSet actionSet) {
        super(actionSet);
    }
    
    @Override
    public void parseCommand(String cmd) throws ParseException {
    	Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        
        if ( !cmdScanner.hasNext()) {
            throw new ParseException("Command given is empty");
        }//end if
        
        token = cmdScanner.next();  //do or @do

        if (token.equalsIgnoreCase("do")) {
        	
            token = cmdScanner.next();// id
            Identifier id = ParseUtils.parseID(token);
            
            token = cmdScanner.next(); // ask, catapult, set, or boom
            
            if (token.equalsIgnoreCase("ask")) {
                token = cmdScanner.next();// all, coordinates, altitude, heading, speed, weight, fuel
                if(token.equalsIgnoreCase("all")) {
                	 this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("ALL")));
                }
                else if(token.equalsIgnoreCase("coordinates")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("COORDINATES")));
                }
                else if(token.equalsIgnoreCase("heading")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("HEADING")));
                }
                else if(token.equalsIgnoreCase("speed")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("SPEED")));
                }
                else if(token.equalsIgnoreCase("weight")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("WEIGHT")));
                }
                else if(token.equalsIgnoreCase("fuel")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoAsk(id, E_Parameter.valueOf("FUEL")));
                }
                else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }
            } 
            else if (token.equalsIgnoreCase("catapult")) {
                token = cmdScanner.next();// launch
                if (token.equalsIgnoreCase("launch")) {
                    token = cmdScanner.next();// with
                    if (token.equalsIgnoreCase("with")) {
                        token = cmdScanner.next();// speed
                        if (token.equalsIgnoreCase("speed")) {
                            token = cmdScanner.next();// the speed
                            Speed theSpeed = ParseUtils.parseSPEED(token);
                            this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoCatapult(id, theSpeed));
                        }
                        else {
                            cmdScanner.close();
                            throw new ParseException("Invalid command");
                        }
                    }
                    else {
                        cmdScanner.close();
                        throw new ParseException("Invalid command");
                    }
                }
                else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }
            }
            else if (token.equalsIgnoreCase("set")) {
                token = cmdScanner.next();// heading
                if(token.equalsIgnoreCase("heading")) {
                	token = cmdScanner.next();// course
                	AngleNavigational theCourse = ParseUtils.parseCOURSE(token);
                	if(cmdScanner.hasNext()) {
                		token = cmdScanner.next();
                		if(token.equalsIgnoreCase("right")) {
                			this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoSetHeading(id, theCourse, E_Direction.RIGHT));
                		}
                		else if(token.equalsIgnoreCase("left")) {
                			this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoSetHeading(id, theCourse, E_Direction.LEFT));
                		}
                	}
                	else {
                		this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoSetHeading(id, theCourse, E_Direction.SHORTEST));
                	}
                }
                else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }
            }//end else
            else if (token.equalsIgnoreCase("boom")) {
                token = cmdScanner.next();// extend or retract
                if(token.equalsIgnoreCase("extend")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoBoom(id, true));
                }
                else if(token.equalsIgnoreCase("retract")) {
                	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoBoom(id, false));
                }
                else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }
            }
            else {
                cmdScanner.close();
                throw new ParseException("Invalid command");
            }//end else
        } 
        else if (token.equalsIgnoreCase("@do")) {
            token = cmdScanner.next();// id
            Identifier id = ParseUtils.parseID(token);
            token = cmdScanner.next();// force
            if(token.equalsIgnoreCase("force")) {
            	token = cmdScanner.next();//Coordinates or heading
                if (token.equalsIgnoreCase("coordinates")) {
                    token = cmdScanner.next();// coordinates
                    CoordinateWorld coords = ParseUtils.parseCOORDINATES(token);
                    if(cmdScanner.hasNext()) {
                    	token = cmdScanner.next();//altitude or heading
                    	if(token.equalsIgnoreCase("altitude")) {
                    		token = cmdScanner.next();//the altitude
                    		Altitude alt = ParseUtils.parseALTITUDE(token);
                    		token = cmdScanner.next();//heading
                    		if(token.equalsIgnoreCase("heading")) {
                    			token = cmdScanner.next();//course
                    			AngleNavigational theCourse = ParseUtils.parseCOURSE(token);
                    			token = cmdScanner.next();//speed
                    			if(token.equalsIgnoreCase("speed")) {
                    				token = cmdScanner.next();//speed
                    				Speed thespeed = ParseUtils.parseSPEED(token);
                    				this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoForceAll(id, coords, alt, theCourse, thespeed));
                    			}
                    			else {
                    	            cmdScanner.close();
                    	            throw new ParseException("Invalid command");
                    	        }
                    		}
                    		else {
                                cmdScanner.close();
                                throw new ParseException("Invalid command");
                            }
                    	}
                    	else if(token.equalsIgnoreCase("heading")) {
                    		token = cmdScanner.next();//course
                			AngleNavigational theCourse = ParseUtils.parseCOURSE(token);
                			token = cmdScanner.next();//speed
                			if(token.equalsIgnoreCase("speed")) {
                				token = cmdScanner.next();//speed
                				Speed thespeed = ParseUtils.parseSPEED(token);
                				this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoForceAll(id, coords, theCourse, thespeed));
                			}
                    	}
                    	else {
                            cmdScanner.close();
                            throw new ParseException("Invalid command");
                        }
                    }
                    else {
                    	this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoForceCoordinates(id, coords));
                    }
                } 
                else if (token.equalsIgnoreCase("heading")) {
                    token = cmdScanner.next();// course
                    AngleNavigational theCourse = ParseUtils.parseCOURSE(token);
                    this.actionSet.getActionBehavioral().submit(new CommandBehavioralDoForceHeading(id, theCourse));
                }
                else {
                    cmdScanner.close();
                    throw new ParseException("Invalid command");
                }
            }
            else {
                cmdScanner.close();
                throw new ParseException("Invalid command");
            }
        }
        else {
            cmdScanner.close();
            throw new ParseException("Invalid command");
        }//end else
        
        
        cmdScanner.close();
    }
	
}
