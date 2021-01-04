package pl.bg.javaMonthlyExpenses.mainWindow;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.UpdateBalancePrivateTool;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.UpdateBalanceTool;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.ListView;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.DeleteRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.RecordModify;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.UpdateRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.Filter;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableCategoriesWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableShopWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.LoadToView;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;
import pl.bg.javaMonthlyExpenses.mainWindow.interfaces.MainWindow;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainWindowPersonal  implements MainWindow, Initializable {

    @FXML
    private TableView tableView_main = new TableView();
    @FXML
    private TableView tableView_balance = new TableView(), tableView_expenses=new TableView(), tableView_addExpenses = new TableView();
    private Stage stage_main = new Stage();


    @FXML
    @Override
    public void add() { new RecordModify().startModify(); }

    @Override
    public void delete() {
      new DeleteRecord().open();
    }

    @Override
    public void update() {
        new UpdateRecord().startModify();
    }

    @FXML
    @Override
    public void refresh() {

        tableView_main.getItems().clear();
        tableView_balance.getItems().clear();
        tableView_expenses.getItems().clear();
        tableView_addExpenses.getItems().clear();

        tableView_balance.getColumns().removeAll(tableView_balance.getColumns());
        tableView_main.getColumns().removeAll(tableView_main.getColumns());
        tableView_expenses.getColumns().removeAll(tableView_expenses.getColumns());
        tableView_addExpenses.getColumns().removeAll(tableView_addExpenses.getColumns());

        fillingTables();

        tableView_balance.refresh();
        tableView_main.refresh();
        tableView_expenses.refresh();
        tableView_addExpenses.refresh();

    }

    @Override
    public void updateSumTable() {
        //new UpdateBalanceTool().startTool();
        new UpdateBalancePrivateTool().startTool();
    }

    @Override
    public void filter() {
    new Filter().startFilter();
    }

    @Override
    public void tableCategories() {

        new TableCategoriesWinController().start();

    }

    @Override
    public void listCategories() {

        ListView listView = new ListView();
        ListView.columnName= "CATEGORY";
        ListView.field ="categoryName";
        ListView.table_name ="Category";
        listView.start();

    }

    @Override
    public void tableShops() {
        new TableShopWinController().start();
    }

    @Override
    public void listShops() {

        ListView listView = new ListView();
        ListView.columnName= "SHOP";
        ListView.field ="shopName";
        ListView.table_name ="Shop";
        listView.start();
    }

    @Override
    public void fillingTables() {

        Select.checkConnection();

        TablesBuilder.buildMain(tableView_main);
        TablesBuilder.buildBalance(tableView_balance);
        TablesBuilder.buildForCategories(tableView_expenses);
        TablesBuilder.buildForCategories(tableView_addExpenses);

        Thread thread_first = new Thread(()->  {

            LoadToView.loadMain(20,tableView_main,()-> Record.list.removeAll(Record.list));

            LoadToView.loadBalanceReview(tableView_balance,4,()->Record.list.removeAll(Record.list));


        });

Thread thread_second = new Thread(()-> {

    LoadToView.loadCategoriesAndSum("Category", Arrays.asList("opla","nauk"),tableView_expenses,
            ()->Record.list.removeAll(Record.list));

    LoadToView.loadCategoriesAndSum("Category", Arrays.asList("jedzenie","fajki","alkoh"),tableView_addExpenses,
            ()->Record.list.removeAll(Record.list));


});
        thread_first.start();
        checkIfAllive(thread_first,()->{ thread_second.start(); });

    }

    @Override
    public void changeDatabase() {

        ChooseDatabase.stageMain.close();
        Stage stage = new Stage();
        stage.setHeight(210.0);
        stage.setWidth(343.0);

        try {
            new ChooseDatabase().startApp(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillingTables();

    }
    public void checkIfAllive(Thread thread,Runnable runnable) {

        while (thread.isAlive()) {}
        try {
            if (!thread.isAlive() && SQLTools.rs.isClosed()) {
                runnable.run();

            } else {
                Logger.warn(thread.getName() + " STILL WORKING ");
                Thread.sleep(500);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Logger.success();
    }

}
