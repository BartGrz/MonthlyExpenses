package pl.bg.javaMonthlyExpenses.database.SQL.commends;

import pl.bg.javaMonthlyExpenses.database.tools.Logger;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.util.ArrayList;
import java.util.List;



public class SQLSelectJoin extends SQLEssentials {

    List copyOf = new ArrayList();

    public void selectJoin(List<String> columns, List<String> tables, String byColumn, Object condition) {

        for (int i = 0; i < tables.size(); i++) {
            copyOf.add(tables.get(i));
        }
String mainTable = tables.get(1);

        for (int i = 0; i < tables.size(); i++) {
            pragmaTable((String) returnValue(copyOf));
        }

        if (byColumn == null) {
            sql = "Select b." + Formatter.listFormatterColumns(columns) + " from " + returnValue(tables) +
                    " a Join " + returnValue(tables) + " b on a." + fetchTablesID(mainTable) + "=b." + fetchTablesID(mainTable) + ";";


        } else {

            if (findObjectType(condition).equals("String")) {

                sql = "Select " + Formatter.listFormatterColumns(columns) + " from " + returnValue(tables) +
                        " a Join " + returnValue(tables) + " b on a." + fetchTablesID(mainTable) + "=b." + fetchTablesID(mainTable)
                        + " where " + byColumn + " like " + Formatter.findTypeAndFormat(condition) + " ; ";


            } else {

                sql = "Select " + Formatter.listFormatterColumns(columns) +
                        " from " + returnValue(tables) + " a Join " + returnValue(tables) + " b " +
                        "on a." + fetchTablesID(mainTable) + "=b." + fetchTablesID(mainTable)
                        + " where b." + byColumn + " = " + Formatter.findTypeAndFormat(condition) + " ; ";

            }
        }
                findAndAdd(columns,sql);

                if(!result.isEmpty()) {

                    for(int i =0;i<2;i++) {

                        Record.list.add(new Record.Builder()
                                .account(returnValue(result).toString())
                                .balance(Math.round((double) returnValue(result)))
                                .debt(Math.round((double) returnValue(result)))
                                .result(Math.round((double) returnValue(result))).build());

                        Logger.success();
                    }


                }else {

                    Logger.failure();
                }
        }
}
