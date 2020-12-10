package pl.bg.javaMonthlyExpenses.mainWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.*;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.exeptions.DateValidException;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.DeleteRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.RecordModify;
import pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers.UpdateRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.Filter;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableCategoriesWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers.TableShopWinController;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MainWindow extends Application implements Initializable {
    
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
    private ComboBox comboBox_rangeFrom = new ComboBox(), comboBox_rangeTo = new ComboBox();
    @FXML
    private Button button_filter = new Button();
    
    private List<String> list_columns = new ArrayList<>();
    private List<String> list_tables = new ArrayList<>();
    private ObservableList<String> list_dates = FXCollections.observableArrayList();
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("FXML/mainWindow.fxml"));
        Scene scene = new Scene(root, 840, 513);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wydatki");
        primaryStage.show();
        
        
    }
    
    public void closeApp() {
        
        Platform.exit();
        System.exit(0);
        
    }
    
    public void filter() {
        
        Filter filter = new Filter();
        filter.startFilter();
    }
    
    public void add() {
        
        RecordModify recordModify = new RecordModify();
        recordModify.startModify();
    }
    
    public void updateSumTable() {
        
      new UpdateBalanceTool().startTool();
      
    }
    
    public void update() {
        
        new UpdateRecord().startModify();
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Select.setConnection();
        
        list_tables.add("Balance");
        list_tables.add("Account");
        list_columns.add("accountName");
        list_columns.add("Balance");
        list_columns.add("debt");
        list_columns.add("result");
        
        fillingTables();
        
        ComboBoxTools.fillingComboBoxDate(list_dates, () -> comboBox_rangeFrom.setItems(list_dates));
        
    button_filter.setVisible(false);
 
    
        TablesBuilder.buildCustom("AccountName", "accountName", tableView_sumFromRange);
        TablesBuilder.buildCustom("SumFromTimeRange", "amount", tableView_sumFromRange);
    }
    
    public void delete() {
        
        DeleteRecord deleteRecord = new DeleteRecord();
        deleteRecord.open();
    }
    
    synchronized  public void refresh() {
        

        
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
    
    synchronized public void fillingTables() {
        
        Select.checkConnection();
       
        
        TablesBuilder.buildMain(tableView_main);
        TablesBuilder.buildBalance(tableView_balance);
        TablesBuilder.buildDog(tableView_dog);
        TablesBuilder.buildExpenseAdd(tableView_addExpense);
        
       Thread thread_first = new Thread(()->  {
           
           new Select.SelectJoin().joinMain();
           Looper.forLoopChoseIndex(Record.list.size() - 20, Record.list.size(), (i) -> tableView_main.getItems().add(Record.list.get(i)));
           Record.list.removeAll(Record.list);
           
        });
       
        Thread thread_second = new Thread(()->  {
            
            Looper.forLoopChoseIndex(1, 3, i ->
                    new Select.SelectJoin("Balance").selectJoinOneCond("Account", i));
            Looper.forLoop(Record.list.size(), (i) -> tableView_balance.getItems().add(Record.list.get(i)));
            Record.list.removeAll(Record.list);
            
        });
    
        Thread thread_third = new Thread(()->  {
            
            Looper.forLoopChoseIndex(1, 3, i -> new Select.SelectJoin<String, String>("Expense")
                    .sumJoin_mixConditions_OR("Category", "Vet", "Pies", i));
            Looper.forLoop(Record.list.size(), (i) -> tableView_dog.getItems().add(Record.list.get(i)));
            Record.list.removeAll(Record.list);
    
            new Select.SelectJoin<>("Expense").sumJoin_partialStrings("Category", Arrays.asList("swinsk", "napoj"));
            Looper.forLoop(Record.list.size(), (i) -> tableView_addExpense.getItems().add(Record.list.get(i)));
            Record.list.removeAll(Record.list);
        });
    
        
        
        thread_first.start();
       
      checkIfAllive(thread_first,()->{ thread_second.start(); });
        
        checkIfAllive(thread_second,()->{ thread_third.start(); });
        
    }
    
    
    public void checkIfAllive(Thread thread,Runnable runnable) {
        
        while (thread.isAlive()) { }
    
        try {
            if (!thread.isAlive() && SQLTools.rs.isClosed()) {
                runnable.run();
                
            } else {
            Logger.warn(thread.getName() + " STILL WORKING ");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    
        Looper.forLoopChoseIndex(1, 3, i -> new Select.SelectJoin<>().sumJoinRange(dateFrom, dateTo, i));
        Looper.forLoop(Record.list.size(), i -> tableView_sumFromRange.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);
        
        
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
        TablesBuilder.buildCustom("Sum", "amount", tableView_sumFromRange);
        
        button_filter.setText("FILTER");
        button_filter.setOnAction(e->filterSumByTimeRange());
        
        
        
    }
}



