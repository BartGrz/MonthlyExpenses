package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import pl.bg.javaMonthlyExpenses.holder.Record;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.ColumnsCreator;


import java.util.List;

class Tables <T,V> {
    
    private static String columnName;
    private static String field;
    
    
    public Tables(String columnName, String field) {
        
        this.columnName = columnName;
        this.field = field;
    }
    
    private void columnsCreator(ColumnsCreator columnsCreator) {
        
        TableColumn<T, V> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        columnsCreator.columnsCreator(column);
        
    }
    
    public static void setTableView(Tables tables, TableView tableView) {
        
        tables.columnsCreator((newColumn) -> tableView.getColumns().add(newColumn));
        
    }
    
    
    public static void buildTable(Tables tables, TableView tableView) {
        
        setTableView(getObject(tables), tableView);
    }
    
    
    public static Tables getObject(Tables tables) {
        
        return tables;
    }
    
    
}

public class TablesBuilder {

    public static void buildMain(TableView tableView) {

        Tables.buildTable(new Tables<String, Record>("ID","main_id"),tableView);
        Tables.buildTable(new Tables<String, Record>("Account","accountName"),tableView);
        Tables.buildTable(new Tables<Double,Record>("Amount","amount"),tableView);
        Tables.buildTable(new Tables<String,Record>("Date","date"),tableView);
        Tables.buildTable(new Tables<String,Record>("Catgegory","categoryName"),tableView);
        Tables.buildTable(new Tables<String,Record>("Shop","shopName"),tableView);
        Tables.buildTable(new Tables<String,Record>("isCommon?","isCommon"),tableView);
    }
    public static void buildBalance(TableView tableView) {

        Tables.buildTable(new Tables<String,Record>("Account","accountName"),tableView);
        Tables.buildTable(new Tables<Double,Record>("Expense","balance"),tableView);
        Tables.buildTable(new Tables<Double,Record>("CommonDebt","debt"),tableView);
        Tables.buildTable(new Tables<Double,Record>("Balance","finalResult"),tableView);
    }
    public static void buildForCategories(TableView tableView) {
        
        TablesBuilder.buildCustom("Category","categoryName",tableView);
        TablesBuilder.buildCustom("Expense","amount",tableView);
        
        

    }
    public static void buildMainWithoutId(TableView tableView) {
    
        Tables.buildTable(new Tables<String, Record>("Account","accountName"),tableView);
        Tables.buildTable(new Tables<Double,Record>("Amount","amount"),tableView);
        Tables.buildTable(new Tables<String,Record>("Date","date"),tableView);
        Tables.buildTable(new Tables<String,Record>("Catgegory","categoryName"),tableView);
        Tables.buildTable(new Tables<String,Record>("Shop","shopName"),tableView);
        Tables.buildTable(new Tables<String,Record>("isCommon?","isCommon"),tableView);
    }
    
    public static void  buildCustom(String columnName, String field, TableView tableView) {
        
        Tables.buildTable(new Tables<String,Record>(columnName,field),tableView);
        
        
        
    }
}
