package pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public interface ColumnsCreatorDemo <T,V> {

    void columnsCreator(TableColumn <T,V> column);

}
