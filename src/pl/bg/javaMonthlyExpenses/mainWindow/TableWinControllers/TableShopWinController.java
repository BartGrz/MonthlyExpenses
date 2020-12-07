package pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.PopUp;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TableShopWinController  implements Initializable {
    protected static Stage stage = new Stage();
    @FXML
    protected Button button_delORadd = new Button(), button_check = new Button(),button_update=new Button();
    @FXML
    ComboBox comboBox_category, comboBox_options = new ComboBox();
    @FXML
    protected TextField textField_changeTo= new TextField(),textField_modifiy = new TextField();
    @FXML
    protected Label label_to = new Label();
    protected ObservableList list_options = FXCollections.observableArrayList("Delete", "Add New", "Change");
    protected ObservableList list_categories = FXCollections.observableArrayList();
    private final String table_name = "Shop";


    public void start() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/shop.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 140);

        stage.setScene(scene);
        stage.setTitle("Shops");
        stage.show();
    }



    void showList() {

    }


    void addNew() {
        new SQLModifyMain.Insert().insert(table_name, Arrays.asList(textField_modifiy.getText()));
        new PopUp().popUp();
    }


    void delete() {
        new SQLModifyMain.Delete().delete(table_name, SQLModifyMain.matchWithId(table_name,comboBox_category.getValue().toString()));
        new PopUp().popUp();
    }


    void clear() {
        comboBox_category.setVisible(false);

        textField_modifiy.setVisible(false);
        textField_modifiy.setText(null);

        textField_changeTo.setVisible(false);
        textField_changeTo.setText(null);

        button_update.setVisible(false);
        button_delORadd.setVisible(false);

        label_to.setVisible(false);

        list_categories.removeAll(list_categories);
        ComboBoxTools.fillingComboBox(new Select(table_name).selectBasic(), list_categories, () -> comboBox_category.setItems(list_categories));

    }


    void choose() {
        chooseWhich(button_delORadd,comboBox_options);
    }


    void change() {
        new SQLModifyMain.Update(table_name).update
                ("shopName",textField_changeTo.getText(),SQLModifyMain.matchWithId(table_name,comboBox_category.getValue().toString()));
        new PopUp().popUp();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SQLModifyMain.setConnection();

        comboBox_options.setItems(list_options);
        button_delORadd.setVisible(false);
        button_check.setVisible(false);

        label_to.setVisible(false);
        comboBox_category.setVisible(false);
        textField_modifiy.setVisible(false);
        textField_changeTo.setVisible(false);
        button_update.setVisible(false);
        ComboBoxTools.fillingComboBox(new Select(table_name).selectBasic(), list_categories, () -> comboBox_category.setItems(list_categories));

    }
    public void chooseWhich(Button button, ComboBox comboBox) {


        switch (comboBox.getValue().toString()) {

            case "Add New":

                label_to.setVisible(false);
                button_update.setVisible(false);
                textField_changeTo.setVisible(false);
                comboBox_category.setVisible(false);
                textField_modifiy.setVisible(true);
                button.setText("ADD");
                button.setVisible(true);
                button_delORadd.setOnAction(e -> addNew());
                break;

            case "Change":

                comboBox_category.setVisible(true);
                textField_modifiy.setVisible(false);
                textField_changeTo.setVisible(true);
                label_to.setVisible(true);
                button_update.setVisible(true);
                button_delORadd.setVisible(false);
                button_update.setOnAction(e -> change());
                break;

            case "Delete":

                label_to.setVisible(false);
                button_update.setVisible(false);
                textField_changeTo.setVisible(false);
                textField_modifiy.setVisible(false);
                textField_changeTo.setVisible(false);
                comboBox_category.setVisible(true);
                button_delORadd.setText("DELETE");
                button_delORadd.setVisible(true);
                button_delORadd.setOnAction(e -> delete());
                break;

        }
    }
}
