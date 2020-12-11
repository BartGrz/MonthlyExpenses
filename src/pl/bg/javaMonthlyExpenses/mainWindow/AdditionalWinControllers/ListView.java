package pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ListView  implements Initializable {
    
    @FXML
    private TableView tableView_list = new TableView();
    
    static Stage listView = new Stage();
    public static String columnName, field, table_name;
    
    
    public ListView() {
    }
    
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
        
        TablesBuilder.buildCustom(columnName,field,tableView_list);
    
     List <Object> list_results  = new Select(table_name).selectSpecifyColumns(Arrays.asList(field),null);
    
     if (table_name.equals("Shop")) {
         
         Looper.forLoop(list_results.size(),i->Record.list.add(new Record.Builder()
                 .shop((String)list_results.get(i))
                 .build()));
     }else {
         Looper.forLoop(list_results.size(),i->Record.list.add(new Record.Builder()
                 .category((String)list_results.get(i))
                 .build()));
     }
 
     
        Looper.forLoop(Record.list.size(), i -> tableView_list.getItems().add(Record.list.get(i)));
        Record.list.removeAll(Record.list);
        Select.list_results.removeAll(Select.list_results);
        
    }
    
    
}


