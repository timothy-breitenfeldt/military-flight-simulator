package acg.project.cli.parser;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

import acg.architecture.datatype.*;


public class ParseUtils {
    
    public static Acceleration parseACCELERATION(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+") || token.contains("."))
            throw new ParseException();
        
        Double accel_double;
        try {
            accel_double = Double.parseDouble(token);
        }
        catch (Exception e) {
            throw new ParseException();
        }
        
        return new Acceleration(accel_double);
        
    }//end method
    
    public static Altitude parseALTITUDE(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+") || token.contains("."))
            throw new ParseException();
        
        Double altitude;
        try {
            altitude = Double.parseDouble(token);
        }
        catch(NumberFormatException nfe) {
            throw new ParseException("Invalid altitude");
        }//end catch
        
        return new Altitude(altitude);
        
    }//end method
    
    public static AngleNavigational parseAZIMUTH(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException();
        
        Double angle;
        try {
            angle = Double.parseDouble(token);    
        } catch (Exception e) {
            throw new ParseException();
        }
        
        return new AngleNavigational(angle);
        
    }//end method
    
    public static CoordinateWorld parseCOORDINATES(String token) throws ParseException {
        
        Latitude latitude = null;
        Longitude longitude = null;
        Scanner coordinateScanner = null;
        
        //Insure that the token is in the format int*int'decimal(with or with out .)"/int*int'decimal"
        if ( !token.matches("\\d+\\*\\d+\'\\d+\\.{0,1}\\d+\"/\\d+\\*\\d+\'\\d+\\.{0,1}\\d+\"")) {
            throw new ParseException("Coordinates have invalid format");
        }//end if
        
        coordinateScanner = new Scanner(token);
        coordinateScanner.useDelimiter("/");
        
        try {
            token = coordinateScanner.next();
            latitude = parseLATITUDE(token);
            
            token = coordinateScanner.next();
            longitude = parseLONGITUDE(token);
        } catch(ParseException pe) {
            coordinateScanner.close();
            throw pe;        
        } catch(NoSuchElementException nsee) {  //catch exceptions if there are no characters left in the scanner
            coordinateScanner.close();
            throw new ParseException("Invalid coordinates");
        }//end catch
        
        coordinateScanner.close();
        return new CoordinateWorld(latitude, longitude);
        
    }//end method
    
    public static AngleNavigational parseCOURSE(String token) throws ParseException {

        if (token.isEmpty()) {
            throw new ParseException("Token can not be empty");
        }//end if
        if (token.length() != 3) {
            throw new ParseException("Course must be a 3 digit number");
        }//end if
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException("Cannot be signed");
        
        double course = 0.0;
        
        try {
            course = Double.parseDouble(token);
            
            if (course < 0) {
                throw new ParseException("Course can not be less than 0");
            }//end if
            
        } catch(ParseException pe) {
            throw pe;
        } catch(NumberFormatException nfe) {
            throw new ParseException("Invalid course");
        }//end catch
        
        return new AngleNavigational(course);
        
    }//end method
    
    public static Distance parseDISTANCE(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException();
        
        Double distance;
        try {
            distance = Double.parseDouble(token);
        }
        catch (Exception e) {
            throw new ParseException();
        }
        
        return new Distance(distance);
        
    }//end method
    
    public static AttitudePitch parseELEVATION(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException();
        
        Double elevation;
        try {
            elevation = Double.parseDouble(token);
        }
        catch (Exception e) {
            throw new ParseException();
        }
        
        return new AttitudePitch(elevation);
        
    }//end method
    
    public static Flow parseFLOW(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException();
        
        Double flow;
        try {
            flow = Double.parseDouble(token);
        }
        catch (Exception e) {
            throw new ParseException();
        }
        
        return new Flow(flow);
        
    }//end method
    
    public static Identifier parseID(String token) throws ParseException {
        
        if (token.length() == 0)
            throw new ParseException();
        
        if (token.matches("^[a-zA-Z_]+[a-zA-Z0-9_]+$")) // id's are alphanumeric, plus underscore
            return new Identifier(token);
        
        else
            throw new ParseException();
        
    }//end method
    
    public static Latitude parseLATITUDE(String token) throws ParseException {
        
        int degrees = 0;
        int minutes = 0;
        double seconds = 0.0;
        Scanner latitudeScanner = new Scanner(token);
        
        if (token.contains("-") || token.contains("+")) {
            latitudeScanner.close();
            throw new ParseException("Cannot be signed");
        }
        if ( !token.contains("*") || !token.contains("\'") || !token.contains("\"")) {
            latitudeScanner.close();
            throw new ParseException("Latitude is in incorrect format");
        }//end if
        
        latitudeScanner.useDelimiter("[\\*\'\"]"); 
        
        try {
            degrees = latitudeScanner.nextInt();
            
            if (degrees < 0 || degrees > 90) {
                latitudeScanner.close();
                throw new ParseException("Laditude degrees must be between 0 and 90 inclusive");
            }//end if
            
            minutes = latitudeScanner.nextInt();
            
            if (minutes < 0 || minutes > 59) {
                latitudeScanner.close();
                throw new ParseException("Laditude minutes must be in the range 0 to 59 inclusive");
            }//end if
            
            seconds = latitudeScanner.nextDouble();
            
            if (seconds < 0 || seconds > 59) {
                latitudeScanner.close();
                throw new ParseException("Laditude seconds must be in the range 0 to 59 inclusive");
            }//end if
        } catch(ParseException pe) {
            throw pe;
        } catch(InputMismatchException ime) {  //catch exceptions for if nextInt or nextDouble are unable to parse correctly 
            throw new ParseException("invalid format for latitude");
        } catch(NoSuchElementException nsee) {  //catch exceptions if there are no characters left in the scanner
            throw new ParseException("Invalid latitude");
        }//end catch
        
        latitudeScanner.close();
        
        return new Latitude(degrees, minutes, seconds);
        
    }//end method
    
    public static Longitude parseLONGITUDE(String token) throws ParseException {
        
        int degrees = 0;
        int minutes = 0;
        double seconds = 0.0;
        Scanner longitudeScanner = new Scanner(token);
        
        if (token.contains("-") || token.contains("+")) {
            longitudeScanner.close();
            throw new ParseException("Cannot be signed");
        }
        if ( !token.contains("*") || !token.contains("\'") || !token.contains("\"")) {
            longitudeScanner.close();
            throw new ParseException("Latitude is in incorrect format");
        }//end if
        
        longitudeScanner.useDelimiter("[\\*\'\"]");
        
        try {
            degrees = longitudeScanner.nextInt();
            
            if (degrees < 0 || degrees > 180) {
                longitudeScanner.close();
                throw new ParseException("Longitude degrees must be between 0 and 180 inclusive");
            }//end if
            
            minutes = longitudeScanner.nextInt();
            
            if (minutes < 0 || minutes > 59) {
                longitudeScanner.close();
                throw new ParseException("Longitude minutes must be in the range 0 to 59 inclusive");
            }//end if
            
            seconds = longitudeScanner.nextDouble();
            
            if (seconds < 0 || seconds > 59) {
                longitudeScanner.close();
                throw new ParseException("Longitude seconds must be in the range 0 to 59 inclusive");
            }//end if
            
        } catch(ParseException pe) {
            throw pe;
        } catch(InputMismatchException ime) {  //catch exceptions for if nextInt or nextDouble are unable to parse correctly 
            throw new ParseException("invalid format for longitude");
        } catch(NoSuchElementException nsee) {  //catch exceptions if there are no characters left in the scanner
            throw new ParseException("Invalid longitude");
        }//end catch
        
        longitudeScanner.close();
        
        return new Longitude(degrees, minutes, seconds);
        
    }//end method
    
    public static CoordinateCartesianRelative parseORIGIN(String token) throws ParseException {
        
        String tokenLeft = token.substring(0, token.indexOf(":"));
        String tokenRight = token.substring(token.indexOf(":")+1, token.length());
        
        Double doubleLeft;
        Double doubleRight;
        try {
            doubleLeft = Double.parseDouble(tokenLeft);
            doubleRight = Double.parseDouble(tokenRight);    
            
        } catch (Exception e) {
            throw new ParseException();
        }
        
        return new CoordinateCartesianRelative(doubleLeft, doubleRight);
        
    }//end method
    
    public static Percent parsePERCENT(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+") || token.contains("."))
            throw new ParseException();
        
        Double percent;
        try {
            percent = Double.parseDouble(token);
            
        } catch (Exception e) {
            throw new ParseException();
        }
        
        if (percent < 0 || percent > 100)
            throw new ParseException();
        
        return new Percent(percent);
        
    }//end method

    public static Rate parseRATE(String token) throws ParseException {
        
        if (token.isEmpty()) {
            throw new ParseException("token can not be empty");
        }//end if
        
        if (token.contains("."))
            throw new ParseException();
        
        if (token.contains("-") || token.contains("+")) {
            throw new ParseException("Cannot be signed");
        }
        
        int rate = 0;
        
        try {
            rate = Integer.parseInt(token);
            
        } catch(NumberFormatException nfe) {
            throw new ParseException("Invalid rate");
        }//end catch
        
        if (rate < 0)
            throw new ParseException();
        
        return new Rate(rate);
        
    }//end method
    
    public static Speed parseSPEED(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+") || token.contains("."))
            throw new ParseException();
        
        Double speed;
        try {
            speed = Double.parseDouble(token);
            
        } catch (Exception e) {
            throw new ParseException();
        }
        
        return new Speed(speed);
        
    }//end method
    
    public static String parseSTRING(String token) throws ParseException {
        
    	if (token.charAt(0) == '\'' && token.charAt(token.length()-1) == '\'') {
    		if (token.length() <= 2)
    			throw new ParseException();
    		
    		token = token.substring(1, token.length()-1);
    		
    		return token;
    	}
        
        else
            throw new ParseException();
        
    }//end method
    
    public static Time parseTIME(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+"))
            throw new ParseException();
        
        Double time;
        try {
            time = Double.parseDouble(token);
        }
        catch (Exception e) {
            throw new ParseException();
        }
        
        return new Time(time);
        
    }//end method
    
    public static Weight parseWEIGHT(String token) throws ParseException {
        
        if (token.contains("-") || token.contains("+") || token.contains("."))
            throw new ParseException();
        
        Double weight;
        try {
            weight = Double.parseDouble(token);
        
        } catch (Exception e) {
            throw new ParseException();
        }
        
        return new Weight(weight);
    }//end method
    
    public static String parseSTRING(Scanner s) throws ParseException {
    
        String token = s.next();
        String result = "";
        
        if ( !token.startsWith("\'")) {
            s.close();
            throw new ParseException("missing open quote");
        }//end if
        
        result += token;
        
        while( s.hasNext() && !token.endsWith("\'")) {
            token = s.next();
            result += " " + token;
        }//end while loop
        
        if (!token.endsWith("\'")) {
        	throw new ParseException();
        }
        
        //asumes that there is a close quote, if there isn't one, then it will have unexpected behavior 
        
        result = result.trim();
        result = result.replaceAll("\'", "");
        return result;
    
    }//end method
    
}//end class