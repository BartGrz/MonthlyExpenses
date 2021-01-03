package pl.bg.javaMonthlyExpenses.database.tools.SQL;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.dummy.TestResult;
import pl.bg.javaMonthlyExpenses.holder.TestBuilderRecord;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class SQLTools extends Connection {
    
    public static String sql;
    public static ResultSet rs;
    public static List list_types = new ArrayList();
  public static List list_names = new ArrayList<>();
    public static HashMap<String, String> map = new HashMap<String, String>();
    
    
    public static void pragmaTable(String table_name) {

        checkConnection();
        //List<String> column_names = new ArrayList();

        sql = "pragma table_info(" + table_name + ");";
    
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
    
                if (rs.getString("type").contains("varchar")) {
                    list_types.add("string");
                    list_names.add(rs.getString("name"));
                    map.put(rs.getString("name"), "string");
                } else {
                    list_types.add(rs.getString("type"));
                    list_names.add(rs.getString("name"));
                    map.put(rs.getString("name"), rs.getString("type"));
                }
            }
        } catch (SQLException e) {
            Logger.warn("" + e);
        }
    }

  public static List <String> getNames(HashMap<String, String> tableMap){

        List<String> column_names = new ArrayList<>();
        Iterator it = tableMap.keySet().iterator();

        while (it.hasNext()) {
         column_names.add( it.next().toString());

        }

        return column_names;
  }

    public static HashMap<String,String> getMappedTable (String table_name) {
        checkConnection();
        
        HashMap<String,String> mappedTable = new HashMap<>();
        List<String> joinColumns = Arrays.asList("Expense","Shop","Category","CommonAccount","Account");

        if(table_name.equals("Expense")){

            Looper.forLoop(joinColumns.size(),i-> {

             sql = "pragma table_info(" + joinColumns.get(i)+ ");";

                try {
                    rs = statement.executeQuery(sql);
                    while (rs.next()) {

                        if (rs.getString("type").contains("varchar")) {

                            mappedTable.put(rs.getString("name"), "string");
                        } else if (rs.getString("name").toLowerCase(Locale.ROOT).equals("idexpense")) {

                            mappedTable.put(rs.getString("name"), rs.getString("type"));
                        } else {
                            if (rs.getString("name").toLowerCase(Locale.ROOT).contains("id")) {

                            } else {
                                mappedTable.put(rs.getString("name"), rs.getString("type"));
                            }
                        }
                    }
                } catch (SQLException e) {
                    Logger.warn("" + e);
                }
            });

        }else {

            sql = "pragma table_info(" + table_name + ");";
            try {
                rs = statement.executeQuery(sql);
                while (rs.next()) {

                    if (rs.getString("type").contains("varchar")) {

                        mappedTable.put(rs.getString("name"), "string");
                    } else {

                        mappedTable.put(rs.getString("name"), rs.getString("type"));
                    }
                }
            } catch (SQLException e) {
                Logger.warn("" + e);
            }
        }

        return mappedTable;
    }

    
    public static String fetchTablesID(String mainTable) {
        
        String idTables = null;
        checkConnection();
        
        sql = "pragma table_info (" + mainTable + ");";
        
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt("pk") == 1) {
                    idTables = (rs.getString("name"));
                    return idTables;
                    
                } else {
                
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return null;
        
    }
    public static String onlyNames(List<TestBuilderRecord> list) {
        String name;
        for (int i = 0; i < list.size(); i++) {

            if (ObjectTools.findObjectType(list.get(i).fromList).equals("String")) {
                name = list.get(i).fromList.toString();
                list.remove(i);
                return name;
                
            } else {
            
            }
            
        }
        return null;
    }

    public static List<String> fetchColumnsNamesByType(String type) {
        
        List<String>  list_columnNames = new ArrayList<>();

        for (int i = 0; i < list_names.size(); i++) {
            
            if (map.get(list_names.get(i)).equals(type.toLowerCase())) {
                list_columnNames.add(list_names.get(i).toString());
                
            } else {
            
            }
        }
        return list_columnNames;
    }
    public static List<String> fetchColumnsNamesByTypeDemo(String table_name, String type) {
        
        List<String>  list_columnNames = new ArrayList<>();


        columnITerator(table_name,(obj,mapa)->  {
    
            if (mapa.get(obj).equals(type.toLowerCase())) {
                list_columnNames.add(obj.toString());
        
            } else {
        
            }
            
        });
        
        return list_columnNames;
    }
    public static int matchWithId(String table_name, String val) {
        int id = 0;
        
        if (table_name.equals("CommonAccount")) {
            sql = " Select idCommonAccount from CommonAccount where isCommon = " + val + ";";
            table_name = "CommonAccount";
        } else {
            
            sql = "Select " + fetchTablesID(table_name) + " from " + table_name + " where "
                    + table_name.toLowerCase() + "Name like " + Formatter.findTypeAndFormat(val) + ";";
        }
        try {
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id" + table_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return id;
    }
   
    public static String getColumntypeName(String table_name, String type) {
        String columnTypeName = null;
        list_names.removeAll(list_names);
        pragmaTable(table_name);
        
        
        for (int i = 0; i < list_names.size(); i++) {
            
            switch (type) {
                case "String":
                    
                    if (map.get(list_names.get(i)).equals("string")) {
                        columnTypeName = list_names.get(i).toString();
                        
                    }
                case "date":
                    
                    if (map.get(list_names.get(i)).equals("date")) {
                        columnTypeName = list_names.get(i).toString();
                        
                    }
                case "Double":
                    if (map.get(list_names.get(i)).equals("double")) {
                        columnTypeName = list_names.get(i).toString();
                        
                    }
                case "Integer":
                    
                    if (map.get(list_names.get(i)).equals(type.toLowerCase())&& list_names.get(i).equals("idAccount")) {
                        columnTypeName = list_names.get(i).toString();
                        
                    }
                    break;
                
                default:
                    if (map.get(list_names.get(i)).equals(type.toLowerCase())) {
                        columnTypeName = list_names.get(i).toString();
                        
                    }
            }
            
            
        }
        return columnTypeName;
    }
    

    public static String matchIdWithColumn(String column){ //sqlTool
        
        checkConnection();
        List<String> list_tables = Arrays.asList("Expense","CommonAccount","Account","Category","Shop","Balance");
        
        for (int i = 0; i< list_tables.size();i++) {
            pragmaTable(list_tables.get(i));
            for (int j = 0; j< list_names.size();j++) {
                
                if(list_names.get(j).equals(column)) {
                    
                    list_names.removeAll(list_names);
                    return list_tables.get(i);
                }
                
            }
        }
        
        return null;
        
    }
    
    public static String matchIdWithColumnDemo( String column){ //sqlTool
        
        checkConnection();
        
        List<String> list_tables = Arrays.asList("Expense","CommonAccount","Account","Category","Shop","Balance");
        
        String [] resultTable = new String[1];
        
        for (int i = 0; i< list_tables.size();i++) {
            
            String table = list_tables.get(i);

            columnITerator(table, (obj, mapa) -> {
            
                    if (obj.equals(column)) {
                        resultTable[0] = table;
                      
                    }
            });
            
        }
    
        return resultTable[0];
        
    }
    public  static String checkIfForeignColumn(String table_name, String columnJoined) {
        
        pragmaTable(table_name);
        
        for (int i = 0; i<list_names.size();i++) {
            if(list_names.get(i).equals(columnJoined)) {
                
                return columnJoined;
            }else {
                return "b."+columnJoined;
            }
        }
        return null;
    }
    
    public  static String checkIfForeignColumnDemo(String table_name, String columnJoined) {
        
        String [] resultTable = new String[1];

        columnITerator(table_name,(obj,mapa)-> {
           
           if (!obj.equals(columnJoined)) {
    
               resultTable[0] = "b." + columnJoined;
    
           }
         
       });
        return resultTable[0];
       
    }
    

    public static double round (double val) {
        
        return Math.round((val)*100)/100;
    }

    public static void columnITerator(String table_name, TestResult testResult) {

         HashMap<String, String> mapa = new SQLTools().getMappedTable(table_name);

        Iterator<String> it = mapa.keySet().iterator();

        while (it.hasNext()) {

            Object obj = it.next();

            testResult.testResult(obj, mapa);
        }

    }

    public static void main(String[] args) {

    }
}
