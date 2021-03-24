package pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers;

import javafx.application.Application;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BillAdding  implements Initializable {

    private static Stage stage = new Stage();
    private LocalDate ld = LocalDate.now();
    @FXML
    private TableView tableView_bill = new TableView();
    @FXML
    private ComboBox comboBox_date = new ComboBox();
    @FXML
    private  ComboBox comboBox_account = new ComboBox();
    @FXML
    private ComboBox comboBox_category = new ComboBox();
    @FXML
    private ComboBox comboBox_shop = new ComboBox();
    @FXML
    private ComboBox comboBox_common = new ComboBox();
    @FXML
    private TextField textField_expense = new TextField();
    @FXML
    private Button button_add = new Button();

    private ObservableList<String> list_date = FXCollections.observableArrayList();
    private ObservableList<String> list_accountName = FXCollections.observableArrayList();
    private  ObservableList <String> list_categoryName = FXCollections.observableArrayList();
    private ObservableList <String> list_shopName = FXCollections.observableArrayList();
    private ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true, false);


    public void start() {


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AddBill.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,900,374);
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

      //  Select.checkConnection();

        ComboBoxTools.fillingComboBoxDate(list_date,()->comboBox_date.setItems(list_date));

        ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(),list_accountName,()->comboBox_account.setItems(list_accountName));

        ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(),list_categoryName,()->comboBox_category.setItems(list_categoryName));

        ComboBoxTools.fillingComboBox(new Select("Shop").selectBasic(),list_shopName,()->comboBox_shop.setItems(list_shopName));

        comboBox_common.setItems(list_isCommon);



    }
}
