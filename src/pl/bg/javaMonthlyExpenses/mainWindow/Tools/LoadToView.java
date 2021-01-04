package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.holder.BuildRecord;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.ArrayList;
import java.util.List;

public class LoadToView extends Connection {


    public static boolean loadMain(int showRecords,TableView tableView, DoIt doIt){


        Record.list = BuildRecord.records(new Select.SelectJoin().joinMain());
        final int showMax = Record.list.size();

        if(showRecords>=showMax){
            showRecords =showMax;
        }
       // Logger.warn("size recoprd lis="+ Record.list.size() + " showMax=" + showMax);
        Looper.forLoopChoseIndex(showMax - showRecords, showMax, (i) -> tableView.getItems().add(Record.list.get(i)));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }

    public static boolean loadCategoriesAndSum(String table_name, List<String> cond, TableView tableView, DoIt doIt) {

        final String main_table = "Expense";
        List<List<Record>> list_records = new ArrayList<>();

        Looper.forLoop(cond.size(), i -> list_records.add(
                BuildRecord.records(new Select.SelectJoin(main_table).sumJoin_partialStrings(table_name, cond.get(i)))));

        Looper.forLoop(list_records.size(),i-> Record.list.add(list_records.get(i).get(0)));

        Looper.forLoop(Record.list.size(), (i) -> tableView.getItems().add(Record.list.get(i)));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }
    public static boolean loadBalanceReview( TableView tableView,int index, DoIt doIt) {

        final String table_name = "Balance";
        final String tableJoined = "Account";

        List <List<Record>> list_records = new ArrayList<>();

        Looper.forLoopChoseIndex(1,index,i-> list_records.add( BuildRecord.records(new Select.SelectJoin(table_name).
                selectJoinOneCond(tableJoined,i))));

       Looper.forLoop(list_records.size(),i-> Record.list.add(list_records.get(i).get(0)));
        Looper.forLoop(Record.list.size(), (i) -> tableView.getItems().add(Record.list.get(i)));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }
    public static boolean loadSumRange(String dateFrom, String dateTo, TableView tableView, DoIt doIt) {

        final String main_table = "Expense";
        List <List<Record>> list_records = new ArrayList<>();

        Looper.forLoopChoseIndex(1,3,i->
                list_records.add( BuildRecord.records(new Select.SelectJoin(main_table).sumJoinRange(dateFrom,dateTo,i))));

        Looper.forLoop(list_records.size(),i->Record.list.add(list_records.get(i).get(0)));
        Looper.forLoop(Record.list.size(), i -> tableView.getItems().add(Record.list.get(i)));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }
    }

    public static boolean loadChangeComparison(TableView oldRecord, TableView newRecord, ComboBox toUpdate,Object updatedTo, int id , DoIt doIt){

        final String byColumn = "idExpense", table_name = "Expense";

        TablesBuilder.buildMain(oldRecord);
        TablesBuilder.buildMain(newRecord);

        Record.list = BuildRecord.records(
                new Select.SelectJoin(table_name).selectJoinMainCondition(byColumn, id));

        oldRecord.getItems().add(Record.list.get(0));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
        }

        Record.list = BuildRecord.records(
                new Select.SelectJoin(table_name).selectJoinMainCondition(byColumn, id));

        Record.list= SwitchFilter.switchFilterUpdateColumn(toUpdate,updatedTo,Record.list);

        newRecord.getItems().add(Record.list.get(0));


        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }

    }
    public static boolean loadMainByID(TableView tableView,int id, DoIt doIt) {

        final String table_name = "Expense", byColumn ="idExpense";

        TablesBuilder.buildMain(tableView);

        Record.list = BuildRecord.records(
                new Select.SelectJoin(table_name).selectJoinMainCondition(byColumn, id));

        tableView.getItems().add(Record.list.get(0));

        if (!Record.list.isEmpty()) {
            doIt.doIt();
            return true;
        } else {
            return false;
        }


    }
}
