import acg.project.cli.parser.*;
import acg.project.cli.*;
import acg.project.action.*;
import acg.architecture.datatype.*;

public class ParserTester {
    
    public static void main(String[] args) throws Exception {
        
        // Test a ParseUtils method:  value, datatype
        //TestParseDatatype("a_1_", "ID");
        
        // Test a complete command
        //testParseCommand("DeFINE cat1 OrIGIN 1:1 AZIMUTH 10 WIDTH 5 LIMIT WEIGHT 6 SPEED 7 MISS 80");

        testParseCommand("do aCarrier catapult launch with speed 250 ");

    }
    
    private static void TestParseDatatype(String token, String datatype) throws ParseException {

        switch (datatype) {
        
        case "ACCELERATION":
            System.out.println(ParseUtils.parseACCELERATION(token));
            break;
        case "ALTITUDE":
            System.out.println(ParseUtils.parseALTITUDE(token));
            break;
        case "AZIMUTH":
            System.out.println(ParseUtils.parseAZIMUTH(token));
            break;
        case "COORDINATES":
            System.out.println(ParseUtils.parseCOORDINATES(token));
            break;
        case "COURSE":
            System.out.println(ParseUtils.parseCOURSE(token));
            break;
        case "DISTANCE":
            System.out.println(ParseUtils.parseDISTANCE(token));
            break;
        case "ELEVATION":
            System.out.println(ParseUtils.parseELEVATION(token));
            break;
        case "FLOW":
            System.out.println(ParseUtils.parseFLOW(token));
            break;
        case "ID":
            System.out.println(ParseUtils.parseID(token));
            break;
        case "LATITUDE":
            System.out.println(ParseUtils.parseLATITUDE(token));
            break;
        case "LONGITUDE":
            System.out.println(ParseUtils.parseLONGITUDE(token));
            break;
        case "ORIGIN":
            System.out.println(ParseUtils.parseORIGIN(token));
            break;
        case "PERCENT":
            System.out.println(ParseUtils.parsePERCENT(token));
            break;
        case "RATE":
            System.out.println(ParseUtils.parseRATE(token));
            break;
        case "SPEED":
            System.out.println(ParseUtils.parseSPEED(token));
            break;
        case "STRING":
            System.out.println(ParseUtils.parseSTRING(token));
            break;
        case "TIME":
            System.out.println(ParseUtils.parseTIME(token));
            break;
        case "WEIGHT":
            System.out.println(ParseUtils.parseWEIGHT(token));
            break;
        }
    }
    
    private static void testParseCommand(String cmd) throws ParseException {
        
        ActionSet actionSet = new ActionSet(new CommandLineInterface()); 
        CommandParser parser = new CommandParser(actionSet, cmd);
        
        parser.interpret();
        
    }
}
