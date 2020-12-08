package pl.bg.javaMonthlyExpenses.dummy;



import pl.bg.javaMonthlyExpenses.database.tools.Logger;

import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLEssentials;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;


import java.lang.reflect.Type;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Demo {
    static List values = new ArrayList();
    static List val = new ArrayList();
static LocalDate ld = LocalDate.now();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:mm:dd");
    static Hashtable<String, Type> hashtable = new Hashtable<>();

    


   static Statement statement;
    private static List<String> list_columns = new ArrayList<>();
    private static List<String> list_tables = new ArrayList<>();
    private static List list_values= Arrays.asList(0.0,1,"2020-10-31",1,1,1);

    public static void main(String[] args) {
        String table_name = "Category";
        list_columns.add("accountName");
        list_columns.add("Balance");
        list_columns.add("debt");
        list_columns.add("result");
    
        list_tables.add("Balance");
        list_tables.add("Account");
    
    
        SQLEssentials.setConnection();
    
        List<Object> wynik = new Select(table_name).selectBasic();




/*
    Record.addToList( new Record.Builder().id((int) SwitchFilter.switchBuildingRecord(table_name, "Integer", wynik, j -> wynik.remove(j)))
            .category((String) SwitchFilter.switchBuildingRecord(table_name, "String", wynik, j -> wynik.remove(j)))
            .build());


        Looper.forLoop(Record.list.size(),i->Logger.test(Record.list.get(i).toString()));
 */
    
   
       // Logger.warn(""+Select.checkIfForeignColumn("Expense","accountName"));
        Looper.forLoop(Record.list.size(),i->Logger.warn(""+ Record.list.get(i).toString()));
    }
  


}
