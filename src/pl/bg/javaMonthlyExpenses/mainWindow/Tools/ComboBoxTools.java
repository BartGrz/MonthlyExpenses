package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.collections.ObservableList;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.holder.TestBuilderRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;

import java.time.LocalDate;
import java.util.List;


public class ComboBoxTools {

    private static LocalDate ld = LocalDate.now();


    public static void fillingComboBoxDate(ObservableList list,DoIt doIt) {
        Looper.forLoop(31,(i)->list.add(ld.minusDays(i).toString()));
        doIt.doIt();
    }
    public static void fillingComboBox(List<TestBuilderRecord> list_res, ObservableList list, DoIt doIt) {

      for(int i = 0; i<list_res.size();i++) {
            list.add(Select.onlyNames(list_res));
     }

        if(!list.isEmpty()) {
            doIt.doIt();

        }

    }
}
