package pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public abstract class TableController  {


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


    abstract void start();
    abstract void addNew();
    abstract void delete();
    abstract void clear();
    abstract void choose();
    abstract void change();



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
