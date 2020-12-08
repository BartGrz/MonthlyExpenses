package pl.bg.javaMonthlyExpenses.mainWindow.TableWinControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;

import pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers.PopUp;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TableCategoriesWinController extends TableController implements  Initializable {


    private final String table_name = "Category";
    @Override
    public void start() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/categories.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 140);

        stage.setScene(scene);
        stage.setTitle("Categories");
        stage.show();
    }


    @Override
    public void addNew() {

       new SQLModifyMain.Insert().insert(table_name, Arrays.asList(textField_modifiy.getText()));
        new PopUp().popUp();
    }

    @Override
    public void delete() {
        new SQLModifyMain.Delete().delete(table_name, SQLModifyMain.matchWithId(table_name,comboBox_category.getValue().toString()));
        new PopUp().popUp();
    }

    @Override
    public void change() {
        new SQLModifyMain.Update(table_name).update
                ("categoryName",textField_changeTo.getText(),SQLModifyMain.matchWithId(table_name,comboBox_category.getValue().toString()));
        new PopUp().popUp();
    }

    @Override
    public void clear() {

        comboBox_category.setVisible(false);

        textField_modifiy.setVisible(false);
        textField_modifiy.setText(null);

        textField_changeTo.setVisible(false);
        textField_changeTo.setText(null);

        button_update.setVisible(false);
        button_delORadd.setVisible(false);

        label_to.setVisible(false);

        list_categories.removeAll(list_categories);
        ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(), list_categories, () -> comboBox_category.setItems(list_categories));


    }

    @Override
    public void choose() {


        chooseWhich(button_delORadd,comboBox_options);


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
        ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(), list_categories, () -> comboBox_category.setItems(list_categories));

    }


}
