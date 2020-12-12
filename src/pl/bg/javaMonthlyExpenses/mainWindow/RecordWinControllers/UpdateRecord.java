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
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateRecord implements Initializable {

    private static Stage stage = new Stage();
    @FXML
    private ComboBox comboBox_columnToUpdate, comboBox_updateTo = new ComboBox();
    @FXML
    private TextField textField_id , textField_amount = new TextField();
    @FXML
    private TableView tableView_RecordNow, tableView_RecordWas = new TableView();
    @FXML
    private Button button_check = new Button();

    private ObservableList<String> list_filterBy = FXCollections.observableArrayList("Account", "Expense","Date","Category","Shop","Common?");
    private ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true,false);


    public void startModify() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/updateRecord.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 350);

        stage.setScene(scene);
        stage.setTitle("Add");
        stage.show();

    }
    public void updateBy() {
    
        button_check.setVisible(true);

        SwitchFilter.switchFillingComboBox(comboBox_columnToUpdate.getValue().toString(),textField_amount,comboBox_updateTo);


    }
public void check() {
    
    
    Select.checkConnection();

    TablesBuilder.buildMainWithoutId(tableView_RecordWas);

    SwitchFilter.switchFilter(comboBox_columnToUpdate,(a)->new Select.SelectJoin().selectJoinMainCondition("idExpense",textField_id.getText()));

    Looper.forLoop(Record.list.size(),i-> {

        tableView_RecordWas.getItems().add(Record.list.get(i));

    });

Record.list.removeAll(Record.list);

    SwitchFilter.switchFilter(comboBox_columnToUpdate,(a)->new Select.SelectJoin().selectJoinMainCondition("idExpense",textField_id.getText()));

    TablesBuilder.buildMainWithoutId(tableView_RecordNow);

    Looper.forLoop(Record.list.size(),i-> {

    if(textField_amount.isVisible()) {

        tableView_RecordNow.getItems().add( SwitchFilter.switchFilterUpdateColumn
                (comboBox_columnToUpdate,Double.valueOf(textField_amount.getText()),Record.list).get(i));
    }else {

        SwitchFilter.switchFilterUpdateColumn(comboBox_columnToUpdate,comboBox_updateTo.getValue(),Record.list);

         tableView_RecordNow.getItems().add( SwitchFilter.switchFilterUpdateColumn
                 (comboBox_columnToUpdate,comboBox_updateTo.getValue(),Record.list).get(i));
    }


    });

    Record.list.removeAll(Record.list);

}

public void update() {
    if(textField_amount.isVisible()) {

        new SQLModifyMain.Update("Expense").
                update(SwitchFilter.switchFilterGetColumn(comboBox_columnToUpdate)
                        , Double.valueOf(textField_amount.getText()), Integer.valueOf(textField_id.getText()));
    }else {

        new SQLModifyMain.Update("Expense").
                update(SwitchFilter.switchFilterGetColumn(comboBox_columnToUpdate)
                        , comboBox_updateTo.getValue(), Integer.valueOf(textField_id.getText()));
    }
    button_check.setVisible(false);
}
public void clear() {

    tableView_RecordNow.getItems().clear();
    tableView_RecordNow.getColumns().clear();

    tableView_RecordWas.getItems().clear();
    tableView_RecordWas.getColumns().clear();

    textField_id.setText(null);


    if (comboBox_updateTo.isVisible()) {
        comboBox_updateTo.getItems().clear();
    } else {
        textField_amount.setText(null);
        textField_amount.setVisible(false);

    }

    list_isCommon.add(true);
    list_isCommon.add(false);
    
    button_check.setVisible(false);
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        list_isCommon.add(true);
        list_isCommon.add(false);

        textField_amount.setVisible(false);
        button_check.setVisible(false);

        comboBox_columnToUpdate.setItems(list_filterBy);
    }

//
}
