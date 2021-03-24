package pl.bg.javaMonthlyExpenses.formatter;

import java.util.ArrayList;
import java.util.List;

import static pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools.findObjectType;

public class Formatter {


    public static String listFormatterValues(List list) {

        StringBuilder output = new StringBuilder();
/*
        for (int i = 0; i < list.size(); i++) {
            if (i < 1) {
                output.append(list.get(i));
            } else {
                output.append(", " + list.get(i));
                return "" + output;
            }
        }
        return null;

 */
      
        output.append(list.get(0) );
        

        return ""+ output;
    }
    public static String listFormatterColumns(List list) {

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (i < 1) {
                output.append(list.get(i));
            } else {
                output.append(", " + list.get(i));

            }
        }
        return "" + output;

    }

    public static List valueFormatter( List values) {

        //WAZNE ,jesli w tablicy values sa tylko nie Stringi, to mozna dodac tylko () i gotowe bo jest zrobiona na to metoda listFormatter
        StringBuilder stb = new StringBuilder();
        List returnedValues = new ArrayList();

        stb.append("(");

        for (int i = 0; i < values.size(); i++) {

            if(findObjectType(values.get(i)).equals("String") ||
                    findObjectType(values.get(i)).equals("date") ) {
                
            String changed = values.get(i).toString();
            
                if(!values.get(i+1).equals("String") || !values.get(i+1).equals("date") ) {
                    
                     stb.append("'" + changed + "'");
                     returnedValues.add(stb);
                     
            }else {
                    
                    stb.append("'" + changed + "')");
                    returnedValues.add(stb);
            }

        } else {
            
            Object notString = values.get(i);
            stb.append(notString );
            returnedValues.add(stb);
        }

        if(values.size()==i+1 && !findObjectType(values.get(i)).equals("String")) {
            stb.append(")");

            return returnedValues;

        }else if (values.size()==i+1 && findObjectType(values.get(i)).equals("String")){

            return returnedValues;

        }else {

            stb.append(",");

          }
    }
       return null;
    }

    public static Object PartialCondition( Object value) {


        StringBuilder stb = new StringBuilder();

        if (findObjectType(value).equals("String") )  {
                stb.append("'" + value + "%'");
                return "" + stb;
                
            } else {
            
            }
        return value;
    }
    public static Object findTypeAndFormat(Object value) {

        StringBuilder stb = new StringBuilder();
        
            if (findObjectType(value).equals("String") || findObjectType(value).equals("date"))  {
                stb.append("'" + value + "'");
                return  ""+stb;
                
            } else {

            }

        return value;
    }
}