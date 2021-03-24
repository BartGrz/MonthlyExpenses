package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.TestBuilderRecord;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.FilterResult;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.Loop;

import java.util.List;

public class SwitchFilter {

    private static ObservableList<String> list_date = FXCollections.observableArrayList();
    private static ObservableList<String> list_accountName = FXCollections.observableArrayList();
    private static ObservableList<String> list_categoryName = FXCollections.observableArrayList();
    private static ObservableList<String> list_shopName = FXCollections.observableArrayList();
    private static ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true, false);


    private static Object result;

    public static String switchFilterGetColumn(ComboBox comboBox) {

        switch (comboBox.getValue().toString()) {
            case "Account":
                result = "accountName";

                break;

            case "Expense":
                result = "amount";
                return (String) result;
            // break;

            case "Date":
                result = "date";
                return (String) result;
            //  break;

            case "Category":
                result = "categoryName";

                break;

            case "Shop":
                result = "shopName";

                break;

            case "Common?":
                result = "isCommon";

                break;
        }

        return (String) result;
    }

    public static List<Record> switchFilterUpdateColumn(ComboBox comboBox, Object value, List<Record> list_record) {

        switch (comboBox.getValue().toString()) {
            case "Account":
                list_record.get(0).accountName = (String) value;
                break;

            case "Expense":
                list_record.get(0).amount = (double) value;
                break;

            case "Date":
                list_record.get(0).date = (String) value;
                break;

            case "Category":
                list_record.get(0).categoryName = (String) value;
                break;

            case "Shop":
                list_record.get(0).shopName = (String) value;
                break;

            case "Common?":
                list_record.get(0).isCommon = String.valueOf(value);
                break;


        }
        Logger.log("" + list_record.get(0).date);
        return list_record;
    }


    public static void switchFillingComboBox(Object condtion, TextField textField, ComboBox comboBox) {

        switch (condtion.toString()) {

            case "Account":
                checkIfVisible(textField, () -> comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(), list_accountName, () -> comboBox.setItems(list_accountName));
                break;

            case "Expense":
                comboBox.setItems(null);
                comboBox.setVisible(false);
                textField.setVisible(true);
                break;

            case "Date":
                checkIfVisible(textField, () -> comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBoxDate(list_date, () -> comboBox.setItems(list_date));

                break;

            case "Category":
                checkIfVisible(textField, () -> comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(), list_categoryName, () -> comboBox.setItems(list_categoryName));
                break;

            case "Shop":
                checkIfVisible(textField, () -> comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Shop").selectBasic(), list_shopName, () -> comboBox.setItems(list_shopName));
                break;

            case "Common?":
                checkIfVisible(textField, () -> comboBox.setVisible(true));
                comboBox.setItems(null);

                comboBox.setItems(list_isCommon);
                break;

        }
    }
    private static void checkIfVisible(TextField textField, DoIt doIt) {

        if(textField.isVisible()) {
            textField.setVisible(false);
            doIt.doIt();
        }else {
            doIt.doIt();
        }
    }
}