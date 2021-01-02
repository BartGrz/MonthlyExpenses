package pl.bg.javaMonthlyExpenses.mainWindow;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.UpdateBalanceTool;
import pl.bg.javaMonthlyExpenses.dummy.Demo;
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
import java.util.ResourceBundle;

public class MainWindowPersonal extends Application implements MainWindow, Initializable {

    @FXML
    private TableView tableView_main = new TableView();
    @FXML
    private TableView tableView_balance = new TableView();


    @FXML
    @Override
    public void add() {

        new RecordModify().startModify();

    }

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

        tableView_balance.getColumns().removeAll(tableView_balance.getColumns());
        tableView_main.getColumns().removeAll(tableView_main.getColumns());

        fillingTables();

        tableView_balance.refresh();
        tableView_main.refresh();

    }

    @Override
    public void updateSumTable() {
        new UpdateBalanceTool().startTool();
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

        Thread thread_first = new Thread(()->  {

            LoadToView.loadMain(20,tableView_main,()-> Record.list.removeAll(Record.list));

            LoadToView.loadBalanceReview(tableView_balance,()->Record.list.removeAll(Record.list));
        });

        thread_first.start();

    }

    @Override
    public void changeDatabase() {

        Stage stage = new Stage();
        stage.setHeight(188);
        stage.setWidth(264);

        try {
            new Demo().startApp(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXML/mainWindowPersonal.fxml"));
        Scene scene = new Scene(root, 860, 520);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MonthlyExpenses");
        primaryStage.show();
        primaryStage.maxHeightProperty().set(530);
        primaryStage.maxWidthProperty().set(860);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillingTables();

    }
}
