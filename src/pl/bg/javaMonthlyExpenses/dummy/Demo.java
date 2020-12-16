package pl.bg.javaMonthlyExpenses.dummy;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Demo {
    
    static  List<String> list_names1 = new ArrayList<>();
   static HashMap<String,String> mapa = new HashMap<>();
   
  public static void compareType (String table_name, String type,TestResult testResult ) { //checked dziala
      
      if(checkMappedTableType(table_name,type)) {
          testResult.testResult();
      }else {
          Logger.test("false");
      }
  }
    public static void compareColumn (String table_name, String column, TestResult testResult ) { //checked dziala
        
        if(checkMappedTableColumn(table_name,column)) {
            testResult.testResult();
        }else {
            Logger.test("false");
        }
    }
   
   
 
    
public static boolean checkMappedTableType(String table_name,String type ) {//checked dziala
    
    mapa=  new SQLTools().getMappedTable(table_name);
    Iterator<String>  it = mapa.keySet().iterator();
    

    while(it.hasNext()) {
    
        Object obj = it.next();
        
        if(mapa.get(obj).equals(type)) {
         
            return true;
        }
        
    }
    return false;
}
    public static boolean checkMappedTableColumn(String table_name,String column) {//checked dziala
    
        mapa = new SQLTools().getMappedTable(table_name);
        Iterator<String> it = mapa.keySet().iterator();
    
        while (it.hasNext()) {
        
            Object obj = it.next();
        
            if (obj.toString().toLowerCase(Locale.ROOT).equals(column.toLowerCase(Locale.ROOT))) {
              
                return true;
            
            }
        
        }
        return false;
    }

    public static void main(String[] args) {
    
        list_names1.add("accountName");
        list_names1.add("idAccount");
        
       // mapa.put("accountName", "String");
      //  mapa.put("idAccount", "Integer");
    
        SQLTools.setConnection();
    
  
        compareType("Expense","double",()->Logger.test(" true "));
compareColumn("Expense","idAccount",()-> Logger.test(" true "));

      
    
        checkMappedTableType("Expense","double");
        checkMappedTableColumn("Expense","amount");
      
        
  
    }

}
