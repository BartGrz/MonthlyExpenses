package pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.PopUp;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class RecordModify implements Initializable {


    private static Stage stage = new Stage();
    private LocalDate ld = LocalDate.now();
    @FXML
   private TableView tableView_check = new TableView();
    @FXML
    private  ComboBox comboBox_date = new ComboBox();
    @FXML
    private  ComboBox comboBox_accountName = new ComboBox();
    @FXML
    private ComboBox comboBox_categoryName = new ComboBox();
    @FXML
    private ComboBox comboBox_shopName = new ComboBox();
    @FXML
    private ComboBox comboBox_isCommon = new ComboBox();
    @FXML
    private TextField amount = new TextField();
    @FXML
    Button button_check = new Button();

    private ObservableList<String> list_date = FXCollections.observableArrayList();
    private ObservableList<String> list_accountName = FXCollections.observableArrayList();
    private  ObservableList <String> list_categoryName = FXCollections.observableArrayList();
    private ObservableList <String> list_shopName = FXCollections.observableArrayList();
    private ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true, false);

    List values = new ArrayList<>();

    public void startModify() {

        Parent root = null;
        try {
            root = FXMLLoader.load( getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/recordModify.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 254);

        stage.setScene(scene);
        stage.setTitle("Add");
        stage.show();

    }

    public void closeWindow() {
        RecordModify.stage.close();
    }


    public void clearView() {

        tableView_check.getItems().clear();
        tableView_check.getColumns().clear();
        comboBox_accountName.setValue(null);
        amount.setText(null);
        comboBox_date.setValue(null);
        comboBox_categoryName.setValue(null);
        comboBox_shopName.setValue(null);
        comboBox_isCommon.setValue(null);
        button_check.setVisible(true);
    }

    public void showAdded() {
        
        button_check.setVisible(false);
        values = new ArrayList();

        TablesBuilder.buildMainWithoutId(tableView_check);

        Record record = new Record.Builder().expense(Double.valueOf(amount.getText()))
                .date(comboBox_date.getValue().toString())
                .account(comboBox_accountName.getValue().toString())
                .category(comboBox_categoryName.getValue().toString())
                .shop(comboBox_shopName.getValue().toString())
                .common( comboBox_isCommon.getValue().toString())
                .build();

        tableView_check.getItems().add(record);


        values.add(record.amount);
        values.add(record.date);
        values.add(Select.matchWithId("Account",record.accountName));
        values.add(Select.matchWithId("Category",record.categoryName));
        values.add(Select.matchWithId("Shop",record.shopName));
        values.add(Select.matchWithId("CommonAccount",record.isCommon));

    }

    public void saveAll(){

        SQLModifyMain.setConnection();
        new SQLModifyMain.Insert().insert("Expense",values );

        PopUp popup = new PopUp();

            popup.popUp();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Select.setConnection();

        ComboBoxTools.fillingComboBoxDate(list_date,()->comboBox_date.setItems(list_date));

        ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(),list_accountName,()->comboBox_accountName.setItems(list_accountName));

        ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(),list_categoryName,()->comboBox_categoryName.setItems(list_categoryName));

        ComboBoxTools.fillingComboBox(new Select("Shop").selectBasic(),list_shopName,()->comboBox_shopName.setItems(list_shopName));

        comboBox_isCommon.setItems(list_isCommon);

        
        
    }


}

