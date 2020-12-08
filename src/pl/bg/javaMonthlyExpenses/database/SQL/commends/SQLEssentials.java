/*

Pierwotna metoda z metodami zasilającymi pakiet SQLCommends

package pl.bg.javaMonthlyExpenses.database.SQL.commends;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SQLEssentials {

    public static final String url = "jdbc:sqlite:Data\\MonthlyExpenses.db";
    public static Connection connection;
    public static Statement statement;
    public static List list = new ArrayList<>();
    public static List list_names = new ArrayList<>();
    public static List list_types = new ArrayList();
    private static String idTables;
    public List<Object> copyOf = new ArrayList<>();
    public static List<Object> result = new ArrayList<>();
    public static List<String> columns = new ArrayList<>();
    public static String sql;
    public String sql_add;
    public static HashMap<String, String> map = new HashMap<String, String>();
    public static ResultSet rs;
    
    public static void setConnection() { //connection


        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            Logger.conn_status("Connected");
        } catch (SQLException e) {
            Logger.error("" + e);
        }
    }
    
    public void disconnect() { //connection
        try {
            statement.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.conn_status("Disconnected");
    }

    public static void pragmaTable(String table_name) { //sqlTool
        checkConnection();
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
    
    public Object returnValue(List values) { //ObjectInfo

        Object obj;

        for (int i = 0; i < values.size(); i++) {

            obj = values.get(i);
            values.remove(i);
            return obj;
        }

        return null;
    }
    
    public List findTypeFromList(List test) { //ObjectInfo

        List list = new ArrayList();

        for (int i = 0; i < test.size(); i++) {
            list.add(test.get(i).getClass().getSimpleName());
        }

        return list;
    }

    public static String findObjectType(Object val) {//ObjectInfo

        String result = val.getClass().getSimpleName();

        if (val.toString().contains("-")) {
            result = "date";
            return result;
        }
        return result;
    }
    
    public static String fetchTablesID(String mainTable) {

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
    public static List findAndAdd(List list, String sql) { //do usuniecia na 90%

        checkConnection();

      List<Object> result = new ArrayList<>();

        try {
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {

                for (int i = 0; i < list.size(); i++) {


                    if (map.get(list.get(i).toString()).equals("integer")) {
                        result.add(rs.getInt(list.get(i).toString()));

                    } else if (map.get(list.get(i).toString()).equals("double")) {
                        result.add(rs.getDouble(list.get(i).toString()));

                    } else if (map.get(list.get(i).toString()).equals("boolean")) {
                        result.add(rs.getBoolean(list.get(i).toString()));

                    } else {
                        result.add(rs.getString((String) list.get(i)));

                    }
                }
            }
            list_names.removeAll(list_names);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;



    }
    public static String onlyNames(List list) {
        String name;
        for (int i = 0; i < list.size(); i++) {
            if (findObjectType(list.get(i)).equals("String")) {
                name = list.get(i).toString();
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
              //  list_names.remove(i);

            } else {

            }
        }
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
    
    
    public static void checkConnection() { //connection

        try {
            if (!statement.isClosed()) {
            } else {
                setConnection();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void returnSumAvailable(String table_name) {

        pragmaTable(table_name);

        for (int i = 0; i < list_names.size(); i++) {

            if (map.get(list_names.get(i)).equals("double") && !list_names.get(i).equals("result")) {
                columns.add("Sum(" + list_names.get(i).toString() + ")");

            } else {

            }
        }
        list_names.removeAll(list_names);
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
      //  list_names.removeAll(list_names);
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
    
}


 */