package pl.bg.javaMonthlyExpenses.dummy;



import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.BuildRecord;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.util.*;

public class Demo {

    static List<String> list_names1 = new ArrayList<>();
    static HashMap<String, String> mapa = new HashMap<>();


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

        SQLTools.setConnection();

        List <List<Record>> lista = new ArrayList<>();

        Looper.forLoopChoseIndex(1,3,i-> lista.add( BuildRecord.records( new Select.SelectJoin("Balance").
                selectJoinOneCondDemo("Account",i))));


        for(int i = 0;i<lista.size();i++) {
            Record.list.add(lista.get(i).get(0));
        }
       Looper.forLoop(Record.list.size(),i->Logger.log(Record.list.get(i).toString()));
    }

}
