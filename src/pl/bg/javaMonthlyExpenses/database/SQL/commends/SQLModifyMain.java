package pl.bg.javaMonthlyExpenses.database.SQL.commends;

import pl.bg.javaMonthlyExpenses.database.tools.Logger;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;

import static pl.bg.javaMonthlyExpenses.formatter.Formatter.valueFormatter;

public class SQLModifyMain extends SQLEssentials {

    public SQLModifyMain() {
    }

public static class Insert extends SQLModifyMain {

     public void insert(String table_name, List values) {



         pragmaTable(table_name);
         list_names.remove(0); //usuwa ID z listy nazw po komendzie pragma tableinfo();
         if(values.size()==1) {

             Object value = values.get(0);

             sql = "Insert into " + table_name + "(" + Formatter.listFormatterColumns(list_names) + ")" +
                     " values (" + Formatter.findTypeAndFormat(value) + ");";
         }else {


             sql = "Insert into " + table_name + "(" + Formatter.listFormatterColumns(list_names) + ")" +
                     " values " + Formatter.listFormatterValues(valueFormatter(values)) + ";";

         }
            try {
                statement.execute(sql);
            } catch (SQLException e) {
                Logger.error("" + e);
            }

            Logger.success();
            list_names.removeAll(list_names);
            Logger.end();
        }
    }

public static class Delete extends SQLModifyMain {

    public void delete(String table_name, int id) {

            pragmaTable(table_name);

            sql = "Delete from " + table_name + " where " + fetchTablesID(table_name) + " = " + id + ";";
            Logger.test("" + sql);

            try {
                statement.execute(sql);
            } catch (SQLException e) {
                Logger.failure();
                Logger.error("" + e);
            }
            Logger.success();
            Logger.result("row deleted ");
            Logger.end();



            list_names.removeAll(list_names);
        }

    }

public static class Update extends SQLModifyMain {

            private  String table_name ;

       public Update(String table_name) {
                this.table_name = table_name;
            }

            private String id_main, id_fk;

       public void update(String column, Object changeTo, int condition) {

                pragmaTable(table_name);

               List<String> list_id = fetchColumnsNamesByType("Integer");
               id_main= fetchTablesID(table_name);
               id_fk = fetchTablesID(matchIdWithColumn(column));

          if(matchIdWithColumn(column).equals(table_name)) {
              sql = "Update " + table_name + " set " + column + " = " + Formatter.findTypeAndFormat(changeTo)
                      + " where " + id_main + " = " + condition + ";";
          }else {

              sql = "Update " + table_name + " set " + id_fk + " = " + matchWithId(matchIdWithColumn(column), changeTo.toString())
                      + " where " + id_main + " = " + condition + ";";
          }
          
                Logger.result("row [" + column + "] was updated to : " + changeTo);

            try {
                statement.execute(sql);
            } catch (SQLException e) {
                Logger.error("" + e);
                Logger.failure();
            }


                list_names.removeAll(list_names);
                Logger.success();
                Logger.end();

            }
        }
    }
