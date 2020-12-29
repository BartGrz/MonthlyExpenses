package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.scene.control.TableView;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.holder.BuildRecord;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.ArrayList;
import java.util.List;

public class LoadToView extends Connection {

    public static boolean loadCategoriesAndSum(String table_name, List<String> cond, TableView tableView, DoIt doIt) {

        final String main_table = "Expense";
        List<List<Record>> list_records = new ArrayList<>();

        Looper.forLoop(cond.size(), i -> list_records.add(
                BuildRecord.records(new Select.SelectJoin(main_table).sumJoin_partialStrings(table_name, cond.get(i)))));

        for (int i = 0; i < list_records.size(); i++) {

            Record.list.add(list_records.get(i).get(0));
        }

        Looper.forLoop(Record.list.size(), (i) -> tableView.getItems().add(Record.list.get(i)));
        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }
    public static boolean loadBalanceReview( TableView tableView, DoIt doIt) {

        final String table_name = "Balance";
        final String tableJoined = "Account";

        List <List<Record>> lista = new ArrayList<>();

        Looper.forLoopChoseIndex(1,3,i-> lista.add( BuildRecord.records( new Select.SelectJoin(table_name).
                selectJoinOneCondDemo(tableJoined,i))));

        for(int i = 0;i<lista.size();i++) {
            Record.list.add(lista.get(i).get(0));
        }

        Looper.forLoop(Record.list.size(), (i) -> tableView.getItems().add(Record.list.get(i)));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }
}
