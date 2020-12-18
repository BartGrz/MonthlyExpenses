package pl.bg.javaMonthlyExpenses.dummy;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools.checkIfForeignColumnDemo;

public class Demo {
    
    static  List<String> list_names1 = new ArrayList<>();
   static HashMap<String,String> mapa = new HashMap<>();
   
    
    public static void testITerator(String table_name, TestResult testResult) {
    
        mapa = new SQLTools().getMappedTable(table_name);
        Iterator<String> it = mapa.keySet().iterator();
    
        while (it.hasNext()) {
        
            Object obj = it.next();
        
            testResult.testResult(obj, mapa);
        }
    
    }
    
    
    public static void main(String[] args) {
    
        list_names1.add("accountName");
        list_names1.add("idAccount");
        
       // mapa.put("accountName", "String");
      //  mapa.put("idAccount", "Integer");
    
        SQLTools.setConnection();
    

       // Logger.log(""+SQLTools.fetchColumnsNamesByTypeDemo("Expense","Integer"));
        //Logger.log("" + SQLTools.getColumntypeNameDemo("Shop","String"));
        //Logger.test("" + matchIdWithColumnDemo("shopName"));
       // Logger.test("" + checkIfForeignColumnDemo("Expense","Amount"));
    }

}
