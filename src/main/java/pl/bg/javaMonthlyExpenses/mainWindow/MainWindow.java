package pl.bg.javaMonthlyExpenses.mainWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.csvReader.DemoBillWindow;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.*;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.exeptions.DateValidException;
import pl.bg.javaMonthlyExpenses.holder.Record;

import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.ListView;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.BillAdding;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.DeleteRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.RecordModify;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.UpdateRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.Filter;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableCategoriesWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableShopWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.LoadToView;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MainWindow  implements Initializable {
    
    @FXML
    private TableView tableView_main = new TableView();
    @FXML
    private TableView tableView_balance = new TableView();
    @FXML
    private TableView tableView_dog = new TableView();
    @FXML
    private TableView tableView_addExpense = new TableView();
    @FXML
    private TableView tableView_sumFromRange = new TableView();
    @FXML
    private TableView tableView_homeExpense = new TableView();
    @FXML
    private ComboBox comboBox_rangeFrom = new ComboBox(), comboBox_rangeTo = new ComboBox();
    @FXML
    private Button button_filter = new Button();

    private ObservableList<String> list_dates = FXCollections.observableArrayList();


    @FXML
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

    @FXML
    public void filter() {
        
        Filter filter = new Filter();
        filter.startFilter();
    }
    @FXML
    public void add() {
        
        RecordModify recordModify = new RecordModify();
        recordModify.startModify();
    }
    @FXML
    public void addBillCSV() {
        try {
            new DemoBillWindow().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addBill() {

            new BillAdding().start();

    }

    @FXML
    public void updateSumTable() {
        
      new UpdateBalanceTool().startTool();
      
    }
    @FXML
    public void update() {
        
        new UpdateRecord().startModify();
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Connection.checkConnection();

        fillingTables();
        
        ComboBoxTools.fillingComboBoxDate(list_dates, () -> comboBox_rangeFrom.setItems(list_dates));
        
    button_filter.setVisible(false);
 
    
        TablesBuilder.buildCustom("Account", "accountName", tableView_sumFromRange);
        TablesBuilder.buildCustom("Sum", "sum", tableView_sumFromRange);
    }
    @FXML
    public void delete() {
        
        DeleteRecord deleteRecord = new DeleteRecord();
        deleteRecord.open();
    }
    
    
    synchronized  public void refresh() {
        

        
        tableView_main.getItems().clear();
        tableView_balance.getItems().clear();
        tableView_dog.getItems().clear();
        tableView_addExpense.getItems().clear();
        tableView_homeExpense.getItems().clear();
        
        
        tableView_balance.getColumns().removeAll(tableView_balance.getColumns());
        tableView_main.getColumns().removeAll(tableView_main.getColumns());
        tableView_dog.getColumns().removeAll(tableView_dog.getColumns());
        tableView_addExpense.getColumns().removeAll(tableView_addExpense.getColumns());
        tableView_homeExpense.getColumns().removeAll(tableView_homeExpense.getColumns());
        
        fillingTables();
        
        tableView_balance.refresh();
        tableView_main.refresh();
        tableView_addExpense.refresh();
        tableView_dog.refresh();
        tableView_homeExpense.refresh();
        
        
        
    }
    
    synchronized public void fillingTables() {
        
        Select.checkConnection();

        TablesBuilder.buildMain(tableView_main);
        TablesBuilder.buildBalance(tableView_balance);
        TablesBuilder.buildForCategories(tableView_dog);
        TablesBuilder.buildForCategories(tableView_addExpense);
        TablesBuilder.buildForCategories(tableView_homeExpense);
        
        Thread thread_first = new Thread(()->  {

            LoadToView.loadMain(50,tableView_main,()->Record.list.removeAll(Record.list));

            LoadToView.loadBalanceReview(tableView_balance,3,()->Record.list.removeAll(Record.list));
        });
    
    Thread thread_second = new Thread(()->  {

        LoadToView.loadCategoriesAndSum("Category",Arrays.asList("swinsk", "napoj","jedzenie","jedzenie[z"),
                tableView_addExpense,()->Record.list.removeAll(Record.list));

        LoadToView.loadCategoriesAndSum("Category",Arrays.asList("Vet", "Pies","jedzenie[p","UBER"),
                tableView_dog,()->Record.list.removeAll(Record.list));

        LoadToView.loadCategoriesAndSum("Category", Arrays.asList("dom", "kosmet","leki","chem"),
                tableView_homeExpense,()->Record.list.removeAll(Record.list));

        });

        thread_first.start();

        checkIfAllive(thread_first,()->{ thread_second.start(); });

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
    
  
    public void tableCategories() {
        
         new TableCategoriesWinController().start();
        
    }
    

    public void tableShops() {
        
      new TableShopWinController().start();
      
    }



    @FXML
    public void listShops () {
       
       ListView listView = new ListView();
       ListView.columnName= "SHOP";
       ListView.field ="shopName";
       ListView.table_name ="Shop";
       listView.start();
       
    }
    @FXML
    public void listCategories() {
    
        ListView listView = new ListView();
        ListView.columnName= "CATEGORY";
        ListView.field ="categoryName";
        ListView.table_name ="Category";
        listView.start();
    }
   
    
    @FXML
    public void filterSumByTimeRange() {
        
        Select.checkConnection();
        
        String dateFrom = comboBox_rangeFrom.getValue().toString();
        String dateTo = comboBox_rangeTo.getValue().toString();
    
        try {
           DateValidException.DateValidExceptionTimeRange
                    .checkIfRangeValid(LocalDate.parse(dateFrom),LocalDate.parse(dateTo));
           
        } catch (DateValidException e) {
            Logger.error("" + e);
        }

        LoadToView.loadSumRange(dateFrom,dateTo,tableView_sumFromRange,()->Record.list.removeAll(Record.list));

        button_filter.setText("REFRESH");
        button_filter.setOnAction(e->refreshTimeRangeFilter());
     
    }
    
    @FXML
    public void chooseRange() {
        
        ComboBoxTools.fillingComboBoxDate(list_dates, () -> comboBox_rangeTo.setItems(list_dates));
        button_filter.setVisible(true);
  
    }
    
    
    @FXML
    public void refreshTimeRangeFilter() {
        
        comboBox_rangeTo.setValue(null);
        comboBox_rangeFrom.setValue(null);
        
        tableView_sumFromRange.getItems().clear();
        tableView_sumFromRange.getColumns().removeAll(tableView_sumFromRange.getColumns());
    
        TablesBuilder.buildCustom("Account", "accountName", tableView_sumFromRange);
        TablesBuilder.buildCustom("Sum", "sum", tableView_sumFromRange);
        
        button_filter.setText("FILTER");
        button_filter.setOnAction(e->filterSumByTimeRange());
        
        
        
    }
}



