package pl.bg.javaMonthlyExpenses.dummy;



import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.Loop;

import java.util.*;

import static pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools.checkIfForeignColumnDemo;
import static pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools.rs;

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

        List<TestBuilderRecord> wynik = new Select("Balance").selectBasicDemo();
        Looper.forLoop(wynik.size(), i -> {

            if (wynik.get(i).id == 1 && wynik.get(i).identified.equals(TestBuilderRecord.Identify.FINALRESULT) || wynik.get(i).id == 2 && wynik.get(i).identified.equals(TestBuilderRecord.Identify.FINALRESULT)) {
                Logger.test("" + wynik.get(i).toString());
            }


        });
    }

}
