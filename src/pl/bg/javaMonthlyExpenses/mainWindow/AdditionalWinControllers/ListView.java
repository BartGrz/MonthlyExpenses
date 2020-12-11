package pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListView  implements Initializable {
    
    @FXML
    private TableView tableView_list = new TableView();
    
    static Stage listView = new Stage();
    private String columnName, field, table_name;
    
    public ListView(String table_name, String columnName, String field) {
        
        this.columnName = columnName;
        this.field = field;
        this.table_name =table_name;
    }
    
    @FXML
    public void start()  {
        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/listViewWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root ,172,451);
    
    
        listView.setScene(scene);
        listView.show();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        Select.setConnection();
       List<Object> list_results=  new Select(table_name).selectBasic();
       
        TablesBuilder.buildCustom(columnName,field,tableView_list);
    /*
        while (!list_results.isEmpty()) {
            Record.list.add(new Record.Builder()
                    .shop((String) SwitchFilter.switchBuildingRecord(table_name, "String", list_results, (i) -> list_results.remove(i))).build());
    
           
        }
        Looper.forLoop(Record.list.size(), i -> tableView_list.getItems().add(Record.list.get(i)));
        
        
     */
    }
}


