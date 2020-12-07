package pl.bg.javaMonthlyExpenses.mainWindow.RecordWinControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLSelectJoinDemo;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.PopUp;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;

public class  DeleteRecord {

    @FXML
    private TextField id = new TextField();
    @FXML
    private TableView tableView_delete = new TableView();
    @FXML
    private Button check = new Button();

   static Stage stage = new Stage();



    public void check() {

        int id_delete = Integer.valueOf(id.getText());
        SQLSelectJoinDemo sqlSelectJoin = new SQLSelectJoinDemo(id_delete);
        sqlSelectJoin.setConnection();
        sqlSelectJoin.selectJoinMain();

        TablesBuilder.buildMainWithoutId(tableView_delete);

        Looper.forLoop(Record.list.size(),(i)->tableView_delete.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);
        check.setVisible(false);
    }

    public void delete() {

        SQLModifyMain.setConnection();
        new SQLModifyMain.Delete().delete("Expense",Integer.valueOf(id.getText()));

        popUp();
    }

    public void open () {

    Parent root = null;
    try {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/deleteWindow.fxml"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    Scene scene = new Scene(root, 600, 254);

    stage.setScene(scene);
    stage.setTitle("Delete");
    stage.show();
}
    public void clear(){

    tableView_delete.getItems().clear();
    id.setText(null);
    tableView_delete.getColumns().clear();
        check.setVisible(true);

    }
    public void exit() {
        DeleteRecord.stage.close();
    }

  public void popUp() {

      PopUp popUp = new PopUp();

          popUp.popUp();

  }


//
}
