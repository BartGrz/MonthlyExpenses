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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.dummy.BuildRecord;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Filter implements Initializable {

    private static Stage stage = new Stage();

    @FXML
    private ComboBox comboBox_filterBy =new ComboBox();
    @FXML
    private  ComboBox comboBox_filtered = new ComboBox();
    @FXML
    private TextField amount = new TextField();
    @FXML
    private Button search = new Button("Filter");
    @FXML
    private TableView tableView_Search = new TableView();

    private ObservableList<String> list_filterBy = FXCollections.observableArrayList("Account", "Expense","Date","Category","Shop","Common?");
    private ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList();

    public void startFilter() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/filterWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 400);

        stage.setScene(scene);
        stage.setTitle("Filter");
        stage.show();

    }
    public void filterBy() {

        search.setVisible(true);

        SwitchFilter.switchFillingComboBox(comboBox_filterBy.getValue().toString(), amount, comboBox_filtered);
    }

public void search() {

    search.setVisible(false);

    Select.setConnection();

    if(amount.isVisible()){
        Double  filteredBy = Double.valueOf(amount.getText());
        Record.list = BuildRecord.records(
                new Select.SelectJoin().selectJoinMainCondition(SwitchFilter.switchFilterGetColumn(comboBox_filterBy),filteredBy));

    }else {
        Object filteredBy = comboBox_filtered.getValue();
        Record.list=  BuildRecord.records(
                new Select.SelectJoin().selectJoinMainCondition(SwitchFilter.switchFilterGetColumn(comboBox_filterBy),filteredBy));

    }

    TablesBuilder.buildMain(tableView_Search);
    Logger.log(" size " + Record.list.size());
    Looper.forLoop(Record.list.size(),(i)->tableView_Search.getItems().add(Record.list.get(i)));
    Record.list.removeAll(Record.list);

}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      Select.checkConnection();

        list_isCommon.add(true);
        list_isCommon.add(false);

        amount.setVisible(false);
        search.setVisible(false);
        comboBox_filterBy.setItems(list_filterBy);

    }
    public void clear() {

        tableView_Search.getItems().clear();
        tableView_Search.getColumns().clear();
        if (comboBox_filtered.isVisible()) {
            comboBox_filtered.getItems().clear();
        } else {
            amount.setText(null);
            amount.setVisible(false);
        }

        list_isCommon.add(true);
        list_isCommon.add(false);

        search.setVisible(true);
    }

    public void close() {
        Filter.stage.close();
    }


}
