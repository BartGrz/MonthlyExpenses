package pl.bg.javaMonthlyExpenses.mainWindow.Tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.FilterResult;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.Loop;

import java.util.List;

public class SwitchFilter  {

    private static ObservableList<String> list_date = FXCollections.observableArrayList();
    private static ObservableList<String> list_accountName = FXCollections.observableArrayList();
    private static  ObservableList <String> list_categoryName = FXCollections.observableArrayList();
    private static ObservableList <String> list_shopName = FXCollections.observableArrayList();
    private static ObservableList<Boolean> list_isCommon = FXCollections.observableArrayList(true,false);


    private static Object result;

    public static void switchTablesOptions(ComboBox comboBox,DoIt doIt) {




    }


    public static void switchFilter(ComboBox comboBox, FilterResult filterResult) {

        switch (comboBox.getValue().toString()) {
            case "Account":
                result =  "accountName";
                filterResult.filterResult(result);
                break;

            case "Expense":
                result =  "amount";
                filterResult.filterResult(result);
                break;

            case "Date":
                result =  "date";
                filterResult.filterResult(result);
                break;

            case "Category":
                result =  "categoryName";
                filterResult.filterResult(result);
                break;

            case "Shop":
                result =  "shopName";
                filterResult.filterResult(result);
                break;

            case "Common?":
                result =  "isCommon";
                filterResult.filterResult(result);
                break;


        }
    }
    public static String switchFilterGetColumn(ComboBox comboBox ) {

        switch (comboBox.getValue().toString()) {
            case "Account":
                result =  "accountName";

                break;

            case "Expense":
                result =  "Amount";

                break;

            case "Date":
                result =  "date";

                break;

            case "Category":
                result =  "categoryName";

                break;

            case "Shop":
                result =  "shopName";

                break;

            case "Common?":
                result =  "isCommon";

                break;


        }
        return (String)result;
    }

    public static List<Record> switchFilterUpdateColumn(ComboBox comboBox, Object value , List <Record> list_record) {

        Looper.forLoop(list_record.size(),i-> {


        switch (comboBox.getValue().toString()) {
            case "Account":
             list_record.get(i).accountName= (String)value;
                break;

            case "Expense":
                list_record.get(i).amount=(double)value;
                break;

            case "Date":
                list_record.get(i).date=(String)value;
                break;

            case "Category":
                list_record.get(i).categoryName =(String)value;
                break;

            case "Shop":
                list_record.get(i).shopName=(String)value;
                break;

            case "Common?":
                list_record.get(i).isCommon=String.valueOf(value);
                break;


        }
        });
        return list_record;
    }


    public static String  switchforTypes(String type, FilterResult  filterResult) {

        //trzeba dodac dwie metody public static String getColumnTypeName(String table_name, String type) {}ktora zwraca nazwe kolumny dopasowanej do typu zmiennej

        String result =null ;

        switch (type) {
            case "Integer" :
                result = "Integer";
                filterResult.filterResult(result);


            case "String" :
                result = "String";
                filterResult.filterResult(result);


            case "Double" :
                result = "Double";
                filterResult.filterResult(result);


            case "Boolean" :
                result = "Boolean";
                filterResult.filterResult(result);

        }
        return result;
    }
    public static void switchFillingComboBox(Object condtion , TextField textField ,ComboBox comboBox ){

        switch (condtion.toString()) {

            case "Account" :
                checkIfVisible(textField,()->comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(),list_accountName,()->comboBox.setItems(list_accountName));
                break;

            case "Expense" :
                comboBox.setItems(null);
                comboBox.setVisible(false);
                textField.setVisible(true);
                break;

            case "Date" :
                checkIfVisible(textField,()->comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBoxDate(list_date,()->comboBox.setItems(list_date));

                break;

            case "Category" :
                checkIfVisible(textField,()->comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Category").selectBasic(),list_categoryName,()->comboBox.setItems(list_categoryName));
                break;

            case "Shop" :
                checkIfVisible(textField,()->comboBox.setVisible(true));
                comboBox.setItems(null);
                ComboBoxTools.fillingComboBox(new Select("Shop").selectBasic(),list_shopName,()->comboBox.setItems(list_shopName));
                break;

            case "Common?" :
                checkIfVisible(textField,()->comboBox.setVisible(true));
                comboBox.setItems(null);

                comboBox.setItems(list_isCommon);
                break;

        }

        }
    public static Object switchBuildingRecord (String table_name,String type,List<Object> results,Loop loop) {


        Select select = new Select();
        select.pragmaTable(table_name);

        List list_columnsStings = select.fetchColumnsNamesByType("String");
        List list_columnsDouble =select.fetchColumnsNamesByType("Double");
        List list_columnsInt = select.fetchColumnsNamesByType("Integer");



      Object res = null;


        for (int i = 0; i < results.size(); i++) {

            switch (type) {

                case "Integer":

                    if (select.fetchTablesID(table_name).equals(list_columnsInt.get(0).toString())) {

                       res=  results.get(i);
                    }

                 break;

                case "String":
                   for(int j = 0 ; j < list_columnsStings.size();j++) {

                      switch (list_columnsStings.get(j).toString()) {
                          case "accountName":
                              res = results.get(i).toString();
                              break;

                          case "categoryName":
                              res = results.get(i).toString();
                              break;

                          case "shopName":
                              res = results.get(i).toString();
                              break;

                      }
                   }

                   break;

                case "date":
                   res =  results.get(i);
                break;

                case "Double":
                    for (int j = 0; j < list_columnsDouble.size();j++) {
                        switch (list_columnsDouble.get(j).toString()) {
                            case "Balance" :
                                res  =  results.get(i);
                         break;
                            case "debt" :
                                res  =  results.get(i);
                                break;
                            case "amount" :
                                res  =  results.get(i);
                                break;
                            case "result" :
                                res   = results.get(i);
                                break;
                        }
                    }
              break;

                case "Boolean":
                   res=  results.get(i);

                   break;

            }
            loop.loop(i);
return res;
        }

return  null;



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