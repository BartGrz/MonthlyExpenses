package pl.bg.javaMonthlyExpenses.mainWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.*;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.DeleteRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.RecordModify;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.UpdateRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.Filter;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableCategoriesWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends Application implements Initializable {

    @FXML
    private TableView tableView_main = new TableView();
    @FXML
    private TableView tableView_balance = new TableView();
    @FXML
    private TableView tableView_dog = new TableView();
    @FXML
    private TableView tableView_addExpense = new TableView();

    private List <String> list_columns =new ArrayList<>();
    private List <String> list_tables=new ArrayList<>();

     private SQLSelectJoinDemo selectJoinDemo = new SQLSelectJoinDemo();
    private SQLSelectJoin selectJoin = new SQLSelectJoin();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXML/mainWindow.fxml"));
        Scene scene = new Scene(root,800,513);
         primaryStage.setScene(scene);
        primaryStage.setTitle("Wydatki");
        primaryStage.show();


    }

    public void closeApp () {
        Platform.exit();
        System.exit(0);

    }
    public void filter() {
      Filter filter = new Filter();
      filter.startFilter();
    }
    public void add (){

        RecordModify recordModify = new RecordModify();
        recordModify.startModify();
    }
    public void updateSumTable() {

        SQLSum sum = new SQLSum();
        sum.setConnection();
        sum.sumBalance();
        tableView_balance.refresh();
    }
    public void update(){
  new UpdateRecord().startModify();

    }

    @Override
     public void initialize(URL location, ResourceBundle resources) {

        list_tables.add("Balance");
        list_tables.add("Account");
        list_columns.add("accountName");
        list_columns.add("Balance");
        list_columns.add("debt");
        list_columns.add("result");
        fillingTables();
    }
    public void delete() {

      DeleteRecord deleteRecord = new DeleteRecord();
      deleteRecord.open();
    }
    public void refresh() {

        tableView_main.getItems().clear();
        tableView_balance.getItems().clear();
        tableView_dog.getItems().clear();
        tableView_addExpense.getItems().clear();


        list_tables.add("Balance");
        list_tables.add("Account");


        tableView_balance.getColumns().removeAll(tableView_balance.getColumns());
        tableView_main.getColumns().removeAll(tableView_main.getColumns());
        tableView_dog.getColumns().removeAll(tableView_dog.getColumns());
        tableView_addExpense.getColumns().removeAll(tableView_addExpense.getColumns());

        fillingTables();

        tableView_balance.refresh();
        tableView_main.refresh();
        tableView_addExpense.refresh();
        tableView_dog.refresh();

    }

    public void fillingTables() {



        Select.setConnection();



        TablesBuilder.buildMain(tableView_main);
        TablesBuilder.buildBalance(tableView_balance);
        TablesBuilder.buildDog(tableView_dog);
         TablesBuilder.buildExpenseAdd(tableView_addExpense);

        new Select.SelectJoin().joinMain();
        Looper.forLoop(Record.list.size(),(i)->tableView_main.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);

        selectJoin.selectJoin(list_columns,list_tables,null,null);
        Looper.forLoop(Record.list.size(),(i)->tableView_balance.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);

        selectJoinDemo.selectJoinDog(1); //doz miany JoinOR z idtabeli do zrobienia z tego sumJoin_mixConditions_AND w klasie SelectJoin ;
        selectJoinDemo.selectJoinDog(2);
        Looper.forLoop(Record.list.size(),(i)->tableView_dog.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);

        new Select.SelectJoin<>("Expense").sumJoin_partialStrings("Category",Arrays.asList("swinsk","napoj"));
        Looper.forLoop(Record.list.size(),(i)->tableView_addExpense.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);
        //
    }
    // TODO: 2020-12-07
    public void tableCategories() {

        TableCategoriesWinController tabCatController = new TableCategoriesWinController();
        tabCatController.start();

    }

    // TODO: 2020-12-08
    public void tableShops() {

    }


}



