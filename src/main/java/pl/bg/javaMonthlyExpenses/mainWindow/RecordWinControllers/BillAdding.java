package pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.PopUp;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.LoadToView;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BillAdding  implements Initializable {

    private static Stage stage = new Stage();
    private LocalDate ld = LocalDate.now();
    @FXML
    private TableView tableView_bill = new TableView();
    @FXML
    public ComboBox comboBox_date = new ComboBox();
    @FXML
    private  ComboBox comboBox_account = new ComboBox();
    @FXML
    private ComboBox comboBox_category = new ComboBox();
    @FXML
    private ComboBox comboBox_shop = new ComboBox();
    @FXML
    private ComboBox comboBox_common = new ComboBox();
    @FXML
    private TextField textfield_expense = new TextField();
    @FXML
    private Button button_add = new Button();
    @FXML
    Label label_expAccount = new Label();
    @FXML
    Label label_expCommon =new Label();

    private ObservableList<String> list_date = FXCollections.observableArrayList();
    private ObservableList<String> list_accountName = FXCollections.observableArrayList();
    private  ObservableList <String> list_categoryName = FXCollections.observableArrayList();
    private ObservableList <String> list_shopName = FXCollections.observableArrayList();
    private ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true, false);


    public void start() {


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/AddBill.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,900,400);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void next () {

        Record record = new Record.Builder()
                .expense(Double.parseDouble(textfield_expense.getText()))
                .shop(comboBox_shop.getValue().toString())
                .date(comboBox_date.getValue().toString())
                .account(comboBox_account.getValue().toString())
                .category(comboBox_category.getValue().toString())
                .common(comboBox_common.getValue().toString())
                .build();

        tableView_bill.getItems().add(record);
        Record.list.add(record);
        updateBalance();


    }
    @FXML
    public void updateBalance() {

        double expCommon = 0;
        double expAccount=0;

        for(int i = 0; i< Record.list.size();i++) {

            if (Record.list.get(i).isCommon.equals("true")) {
                expCommon = expCommon + Record.list.get(i).amount;
            } else {
                expAccount = expAccount + Record.list.get(i).amount;
            }

            label_expCommon.setText(""+expCommon);
            label_expAccount.setText(""+expAccount);
        }
    }
    @FXML
    public void saveAll() {

        List values = new ArrayList<>();

        for (int i = 0; i < Record.list.size(); i++) {

            values.add(Record.list.get(i).amount);
            values.add(Record.list.get(i).date);
            values.add(Select.matchWithId("Account", Record.list.get(i).accountName));
            values.add(Select.matchWithId("Category", Record.list.get(i).categoryName));
            values.add(Select.matchWithId("Shop", Record.list.get(i).shopName));
            values.add(Select.matchWithId("CommonAccount", Record.list.get(i).isCommon));

            new SQLModifyMain.Insert().insert("Expense", values);
            values.removeAll(values);

        }
        Record.list.removeAll(Record.list);

       new PopUp().popUp();
       stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TablesBuilder.buildMainWithoutId(tableView_bill);
        Select.checkConnection();

        ComboBoxTools.fillingComboBoxDate(list_date,()->comboBox_date.setItems(list_date));

        ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(),list_accountName,()->comboBox_account.setItems(list_accountName));

        ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(),list_categoryName,()->comboBox_category.setItems(list_categoryName));

        ComboBoxTools.fillingComboBox(new Select("Shop").selectBasic(),list_shopName,()->comboBox_shop.setItems(list_shopName));

        comboBox_common.setItems(list_isCommon);

    }
}
