package acg.project.cli.parser;

import java.util.Scanner;

import acg.project.action.ActionSet;
import acg.project.action.command.A_Command;
import acg.project.action.command.creational.define.*;
import acg.architecture.datatype.*;


public class TemplateParser extends Parser {
    
    public TemplateParser(ActionSet actionSet) {
        super(actionSet);
    }
    
    @Override
    public void parseCommand(String cmd) throws ParseException {
        
        Scanner cmdScanner = new Scanner(cmd);
        
        String token = "";
        
        A_Command the_command = null;
        
        if (cmdScanner.hasNext()) {
            
            token = cmdScanner.next();
        
            if (token.equalsIgnoreCase("DEFINE")) {
                
                // DEFINE... commands
                
                if (cmdScanner.hasNext()) {
                   
                    token = cmdScanner.next();
                    
                    if (token.equalsIgnoreCase("TRAP"))
                        the_command = createCommand_DEFINE_TRAP(cmd);
                    
                    else if (token.equalsIgnoreCase("CATAPULT"))
                        the_command = createCommand_DEFINE_CATAPULT(cmd);
                    
                    else if (token.equalsIgnoreCase("OLS_XMT"))
                        the_command = createCommand_DEFINE_OLS_XMT(cmd);
                    
                    else if (token.equalsIgnoreCase("CARRIER"))
                        the_command = createCommand_DEFINE_CARRIER(cmd);
                    
                    else if (token.equalsIgnoreCase("FIGHTER"))
                        the_command = createCommand_DEFINE_FIGHTER(cmd);
                    
                    else if (token.equalsIgnoreCase("TANKER"))
                        the_command = createCommand_DEFINE_TANKER(cmd);
                    
                    else if (token.equalsIgnoreCase("BOOM")) {
                        
                        if (cmdScanner.hasNext()) {
                            
                            token = cmdScanner.next();
                            
                            if (token.equalsIgnoreCase("MALE"))
                                the_command = createCommand_DEFINE_BOOM_MALE(cmd);
                            
                            else if (token.equalsIgnoreCase("FEMALE"))
                                the_command = createCommand_DEFINE_BOOM_FEMALE(cmd);
                        }
                    }
                    
                    else if (token.equalsIgnoreCase("BARRIER"))
                        the_command = createCommand_DEFINE_BARRIER(cmd);
                }
                
                if (the_command != null) {
                	this.actionSet.getActionCreationalDefine().submit((A_CommandCreationalDefine) the_command);
                }
                
            }
            
            else if (token.equalsIgnoreCase("SHOW")) {
                the_command = createCommand_SHOW_TEMPLATE(cmd);
                
                if (the_command != null)
                	this.actionSet.getActionCreationalDefine().submit((CommandCreationalShowTemplate) the_command);
            }
        }
        cmdScanner.close();
        

        if (the_command == null)
            throw new ParseException("Invalid command");
    }
	
    public CommandCreationalDefineTrap createCommand_DEFINE_TRAP(String cmd) throws ParseException {
        
        // DEFINE TRAP <tid> ORIGIN <origin> AZIMUTH <azimuth> WIDTH <distance> LIMIT WEIGHT <weight> 
        // SPEED <speed> MISS <percent>
        
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try { // We use the try block to catch for cmdScanner.hasNext() exceptions so we dont have to check with ifs
            
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // TRAP
        	if(!token.equalsIgnoreCase("DEFINE TRAP")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
            token = cmdScanner.next(); // <tid>
            Identifier tid = ParseUtils.parseID(token);
            
            token = cmdScanner.next(); // ORIGIN
            if (!token.equalsIgnoreCase("ORIGIN")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <origin>
            CoordinateCartesianRelative origin = ParseUtils.parseORIGIN(token);
            
            token = cmdScanner.next(); // AZIMUTH
            if (!token.equalsIgnoreCase("AZIMUTH")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <azimuth>
            AngleNavigational azimuth = ParseUtils.parseAZIMUTH(token);
            
            token = cmdScanner.next(); // WIDTH
            if (!token.equalsIgnoreCase("WIDTH")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <width>
            Distance width = ParseUtils.parseDISTANCE(token);
            
            token = cmdScanner.next(); // LIMIT
            token += " " + cmdScanner.next(); // WEIGHT
            if (!token.equalsIgnoreCase("LIMIT WEIGHT")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <weight>
            Weight weight = ParseUtils.parseWEIGHT(token);
            
            token = cmdScanner.next(); // SPEED
            if (!token.equalsIgnoreCase("SPEED")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <speed>
            Speed speed = ParseUtils.parseSPEED(token);
            
            token = cmdScanner.next(); // MISS
            if (!token.equalsIgnoreCase("MISS")) {
            	cmdScanner.close();
            	throw new ParseException();
            }
            
            token = cmdScanner.next(); // <percent>
            Percent percent = ParseUtils.parsePERCENT(token);
            
            if (cmdScanner.hasNext()) { // there should be nothing left in the command text
            	cmdScanner.close();
            	throw new ParseException();
            }
            cmdScanner.close();
            
            return new CommandCreationalDefineTrap (tid, origin, azimuth, width, weight, speed, percent);
        }
        catch (Exception e) {
            throw new ParseException();
        }
    }
    
	public CommandCreationalDefineCatapult createCommand_DEFINE_CATAPULT(String cmd) throws ParseException {
		
		// DEFINE CATAPULT <tid> ORIGIN <origin> AZIMUTH <azimuth> LENGTH <distance>
		// ACCELERATION <accceleration> LIMIT WEIGHT <weight> SPEED <speed> RESET <time>
		
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // CATAPULT
        	if (!token.equalsIgnoreCase("DEFINE CATAPULT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // ORIGIN
        	if (!token.equalsIgnoreCase("ORIGIN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <origin>
        	CoordinateCartesianRelative origin = ParseUtils.parseORIGIN(token);
        	
        	token = cmdScanner.next(); // AZIMUTH
        	if (!token.equalsIgnoreCase("AZIMUTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational azimuth = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // LENGTH
        	if(!token.equalsIgnoreCase("LENGTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance distance = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // ACCELERATION
        	if (!token.equalsIgnoreCase("ACCELERATION")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <acceleration>
        	Acceleration acceleration = ParseUtils.parseACCELERATION(token);
        	
        	token = cmdScanner.next(); // LIMIT
        	token += " " + cmdScanner.next(); // WEIGHT
        	if (!token.equalsIgnoreCase("LIMIT WEIGHT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Weight weight = ParseUtils.parseWEIGHT(token);
        	
        	token = cmdScanner.next(); // SPEED
        	if (!token.equalsIgnoreCase("SPEED")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speed = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // RESET
        	if (!token.equalsIgnoreCase("RESET")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <time>
        	Time time = ParseUtils.parseTIME(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineCatapult(tid, origin, azimuth, distance, acceleration, weight, speed, time);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineOLSTransmitter createCommand_DEFINE_OLS_XMT(String cmd) throws ParseException {
		
		// DEFINE OLS_XMT <tid> ORIGIN <origin> AZIMUTH <azimuth> ELEVATION <elevation> RANGE <distance> DIAMETER <distance>
        
		Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // OLS_XMT
        	if (!token.equalsIgnoreCase("DEFINE OLS_XMT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // ORIGIN
        	if (!token.equalsIgnoreCase("ORIGIN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <origin>
        	CoordinateCartesianRelative origin = ParseUtils.parseORIGIN(token);
        	
        	token = cmdScanner.next(); // AZIMUTH
        	if (!token.equalsIgnoreCase("AZIMUTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational azimuth = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // ELEVATION
        	if (!token.equalsIgnoreCase("ELEVATION")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <elevation>
        	AttitudePitch elevation = ParseUtils.parseELEVATION(token);
        	
        	token = cmdScanner.next(); // RANGE
        	if (!token.equalsIgnoreCase("RANGE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance range = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // DIAMETER
        	if (!token.equalsIgnoreCase("DIAMETER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance diameter = ParseUtils.parseDISTANCE(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineOLSTransmitter(tid, origin, azimuth, elevation, range, diameter);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineCarrier createCommand_DEFINE_CARRIER(String cmd) throws ParseException {

		// DEFINE CARRIER <tid> SPEED MAX <speed> DELTA INCREASE <speed> DECREASE <speed> TURN <azimuth> LAYOUT <string> 
        
		Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // CARRIER
        	if (!token.equalsIgnoreCase("DEFINE CARRIER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // SPEED
        	token += " " + cmdScanner.next(); // MAX
        	if (!token.equalsIgnoreCase("SPEED MAX")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speedMax = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DELTA
        	token += " " + cmdScanner.next(); // INCREASE
        	if (!token.equalsIgnoreCase("DELTA INCREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dInc = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DECREASE
        	if (!token.equalsIgnoreCase("DECREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dDec = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // TURN
        	if (!token.equalsIgnoreCase("TURN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational turn = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // LAYOUT
        	if (!token.equalsIgnoreCase("LAYOUT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}

        	String layout = "";
        	try {
        		int firstQuote = cmd.indexOf("'");
        		int secondQuote = cmd.indexOf("'", firstQuote + 1);
        		
        		layout = cmd.substring(firstQuote + 1, secondQuote);
        		token = cmd.substring(secondQuote+1);
        		
        		cmdScanner.close();
        		cmdScanner = new Scanner(token);
        		
        		if (cmdScanner.hasNext()) {
        			cmdScanner.close();
        			throw new ParseException();
        		}
        	}
        	catch (Exception e) {
        		cmdScanner.close();
        		throw new ParseException();
        	}

        	cmdScanner.close();
        	
        	return new CommandCreationalDefineCarrier(tid, speedMax, dInc, dDec, turn, layout);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineFighter createCommand_DEFINE_FIGHTER(String cmd) throws ParseException {
		
		// DEFINE FIGHTER <tid> SPEED MIN <speed> MAX <speed> DELTA INCREASE <speed>
		// DECREASE <speed> TURN <azimuth> CLIMB <altitude> DESCENT <altitude> EMPTY WEIGHT <weight>
		// FUEL INITIAL <weight> DELTA <weight>
		
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // FIGHTER
        	if (!token.equalsIgnoreCase("DEFINE FIGHTER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // SPEED
        	token += " " + cmdScanner.next(); // MIN
        	if (!token.equalsIgnoreCase("SPEED MIN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speedMin = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // MAX
        	if (!token.equalsIgnoreCase("MAX")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speedMax = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DELTA
        	token += " " + cmdScanner.next(); // INCREASE
        	if (!token.equalsIgnoreCase("DELTA INCREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dInc = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DECREASE
        	if (!token.equalsIgnoreCase("DECREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dDec = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // TURN
        	if (!token.equalsIgnoreCase("TURN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational turn = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // CLIMB
        	if (!token.equalsIgnoreCase("CLIMB")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <altitude>
        	Altitude climb = ParseUtils.parseALTITUDE(token);
        	
        	token = cmdScanner.next(); // DESCENT
        	if (!token.equalsIgnoreCase("DESCENT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <altitude>
        	Altitude descent = ParseUtils.parseALTITUDE(token);
        	
        	token = cmdScanner.next(); // EMPTY
        	token += " " + cmdScanner.next(); // WEIGHT
        	if (!token.equalsIgnoreCase("EMPTY WEIGHT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Weight emptyWeight = ParseUtils.parseWEIGHT(token);
        	
        	token = cmdScanner.next(); // FUEL
        	token += " " + cmdScanner.next(); // INITIAL
        	if (!token.equalsIgnoreCase("FUEL INITIAL")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Weight fuelInitial = ParseUtils.parseWEIGHT(token);
        	
        	token = cmdScanner.next(); // DELTA
        	if (!token.equalsIgnoreCase("DELTA")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Weight deltaWeight = ParseUtils.parseWEIGHT(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineFighter(tid, speedMin, speedMax, dInc, dDec, turn, climb, descent, emptyWeight, fuelInitial, deltaWeight);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineTanker createCommand_DEFINE_TANKER(String cmd) throws ParseException {
		
		// DEFINE TANKER <tid> SPEED MIN <speed> MAX <speed> DELTA INCREASE <speed> DECREASE <speed>
		// TURN <azimuth> CLIMB <altitude> DESCENT <altitude> TANK <weight>
		
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // TANKER
        	if (!token.equalsIgnoreCase("DEFINE TANKER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // SPEED
        	token += " " + cmdScanner.next(); // MIN
        	if (!token.equalsIgnoreCase("SPEED MIN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speedMin = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // MAX
        	if (!token.equalsIgnoreCase("MAX")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed speedMax = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DELTA
        	token += " " + cmdScanner.next(); // INCREASE
        	if (!token.equalsIgnoreCase("DELTA INCREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dInc = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // DECREASE
        	if (!token.equalsIgnoreCase("DECREASE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <speed>
        	Speed dDec = ParseUtils.parseSPEED(token);
        	
        	token = cmdScanner.next(); // TURN
        	if (!token.equalsIgnoreCase("TURN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational turn = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // CLIMB
        	if (!token.equalsIgnoreCase("CLIMB")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <altitude>
        	Altitude climb = ParseUtils.parseALTITUDE(token);
        	
        	token = cmdScanner.next(); // DESCENT
        	if (!token.equalsIgnoreCase("DESCENT")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <altitude>
        	Altitude descent = ParseUtils.parseALTITUDE(token);
        	
        	token = cmdScanner.next(); // TANK
        	if (!token.equalsIgnoreCase("TANK")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Weight tank = ParseUtils.parseWEIGHT(token);
        	
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineTanker(tid, speedMin, speedMax, dInc, dDec, turn, climb, descent, tank);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineBoomMale createCommand_DEFINE_BOOM_MALE(String cmd) throws ParseException {
		
		//DEFINE BOOM MALE <tid> LENGTH <distance> DIAMETER <distance> FLOW <flow>
		
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // BOOM
        	token += " " + cmdScanner.next(); // MALE
        	if (!token.equalsIgnoreCase("DEFINE BOOM MALE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // LENGTH
        	if (!token.equalsIgnoreCase("LENGTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance length = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // DIAMETER
        	if (!token.equalsIgnoreCase("DIAMETER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance diameter = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // FLOW
        	if (!token.equalsIgnoreCase("FLOW")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <flow>
        	Flow flow = ParseUtils.parseFLOW(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineBoomMale(tid, length, diameter, flow);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}

	public CommandCreationalDefineBoomFemale createCommand_DEFINE_BOOM_FEMALE(String cmd) throws ParseException {
		
		// DEFINE BOOM FEMALE <tid> LENGTH <distance> DIAMETER <distance> ELEVATION <elevation>
		// FLOW <flow>
		
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // BOOM
        	token += " " + cmdScanner.next(); // FEMALE
        	if (!token.equalsIgnoreCase("DEFINE BOOM FEMALE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // LENGTH
        	if (!token.equalsIgnoreCase("LENGTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance length = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // DIAMETER
        	if (!token.equalsIgnoreCase("DIAMETER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance diameter = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // ELEVATION
        	if (!token.equalsIgnoreCase("ELEVATION")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <elevation>
        	AttitudePitch elevation = ParseUtils.parseELEVATION(token);
        	
        	token = cmdScanner.next(); // FLOW
        	if (!token.equalsIgnoreCase("FLOW")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <weight>
        	Flow flow = ParseUtils.parseFLOW(token);        	
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineBoomFemale(tid, length, diameter, elevation, flow);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalDefineBarrier createCommand_DEFINE_BARRIER(String cmd) throws ParseException {
		
		// DEFINE BARRIER <tid> ORIGIN <origin> AZIMUTH <azimuth> WIDTH <distance> TIME <time>
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // DEFINE
        	token += " " + cmdScanner.next(); // BARRIER
        	if (!token.equalsIgnoreCase("DEFINE BARRIER")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	token = cmdScanner.next(); // ORIGIN
        	if (!token.equalsIgnoreCase("ORIGIN")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <origin>
        	CoordinateCartesianRelative origin = ParseUtils.parseORIGIN(token);
        	
        	token = cmdScanner.next(); // AZIMUTH
        	if (!token.equalsIgnoreCase("AZIMUTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <azimuth>
        	AngleNavigational azimuth = ParseUtils.parseAZIMUTH(token);
        	
        	token = cmdScanner.next(); // WIDTH
        	if (!token.equalsIgnoreCase("WIDTH")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <distance>
        	Distance width = ParseUtils.parseDISTANCE(token);
        	
        	token = cmdScanner.next(); // TIME
        	if (!token.equalsIgnoreCase("TIME")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <time>
        	Time time = ParseUtils.parseTIME(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalDefineBarrier(tid, origin, azimuth, width, time);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
	
	public CommandCreationalShowTemplate createCommand_SHOW_TEMPLATE(String cmd) throws ParseException {
		
    	// SHOW TEMPLATE <tid>
    	
        Scanner cmdScanner = new Scanner(cmd);
        String token = "";
        
        try {
        	token = cmdScanner.next(); // SHOW
        	token += " " + cmdScanner.next(); // TEMPLATE
        	if (!token.equalsIgnoreCase("SHOW TEMPLATE")) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	
        	token = cmdScanner.next(); // <tid>
        	Identifier tid = ParseUtils.parseID(token);
        	
        	if (cmdScanner.hasNext()) {
        		cmdScanner.close();
        		throw new ParseException();
        	}
        	cmdScanner.close();
        	
        	return new CommandCreationalShowTemplate(tid);
        }
        catch (Exception e) {
        	throw new ParseException();
        }
	}
}
