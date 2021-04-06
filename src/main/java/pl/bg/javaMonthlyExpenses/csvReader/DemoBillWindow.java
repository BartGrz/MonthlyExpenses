package pl.bg.javaMonthlyExpenses.csvReader;

import com.sun.source.tree.LambdaExpressionTree;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.ComboBoxTools;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.LoadToView;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.TablesBuilder;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DemoBillWindow     {

    ObservableList<String> list_accounts = FXCollections.observableArrayList();
    ObservableList<String> list_shop = FXCollections.observableArrayList();
    ObservableList<String> list_date = FXCollections.observableArrayList();
    TableView tableView = new TableView();
    Pane pane = new Pane();
    static Stage stage = new Stage();


    public void start( ) throws Exception {

        Connection.checkConnection();

        Label label = new Label("Wprowadz nazwe pliku CSV :");
        Label label_account = new Label("Account");
        Label label_shop= new Label("Shop");
        Label label_date = new Label("Date");




        ComboBox comboBox_account = new ComboBox();
        ComboBox comboBox_shop = new ComboBox();
        ComboBox comboBox_date = new ComboBox();




        Button button_confirm = new Button("CONFIRM");

        TextField textField_csv = new TextField();

        comboBox_account.getItems().addAll(list_accounts);
        comboBox_shop.getItems().addAll(list_shop);
        ComboBoxTools.fillingComboBoxDate(list_date,
                () -> comboBox_date.getItems().addAll(list_date));
        ComboBoxTools.fillingComboBox(new Select("shop").selectBasic(),list_shop,()->comboBox_shop.setItems(list_shop));
        ComboBoxTools.fillingComboBox(new Select("Account").selectBasic(),list_accounts,()->comboBox_account.setItems(list_accounts));

        label_account.setLayoutX(80);
        label_account.setLayoutY(50);
        comboBox_account.setLayoutX(40);
        comboBox_account.setLayoutY(80);
        comboBox_account.setPrefWidth(120);

        label_shop.setLayoutX(220);
        label_shop.setLayoutY(50);
        comboBox_shop.setLayoutX(180);
        comboBox_shop.setLayoutY(80);
        comboBox_shop.setPrefWidth(120);

        label_date.setLayoutX(360);
        label_date.setLayoutY(50);
        comboBox_date.setLayoutX(320);
        comboBox_date.setLayoutY(80);
        comboBox_date.setPrefWidth(120);

        textField_csv.setLayoutX(180);
        textField_csv.setLayoutY(150);

        label.setLayoutX(15);
        label.setLayoutY(150);

        button_confirm.setLayoutX(350);
        button_confirm.setLayoutY(150);


        button_confirm.setVisible(true);


        pane.getChildren().addAll
                (button_confirm,label,textField_csv,label_account,
                        label_date,label_shop,comboBox_account,comboBox_date,comboBox_shop);



        Scene scene = new Scene(pane, 500, 500);

        stage.setScene(scene);
        stage.show();


            button_confirm.setOnAction(e-> {


                String shop = comboBox_shop.getValue().toString();
                String account = comboBox_account.getValue().toString();
                String date = comboBox_date.getValue().toString();
                confirm(textField_csv.getText(),account,date,shop);
            });


}

    public void confirm (String path,String accountName,String date, String shopName) {

        Button button_save = new Button("SAVE");

        Label common = new Label("common expense : ");
        Label own = new Label("self expesne : ");
        Label common_exp = new Label();
        Label own_expense = new Label();

        button_save.setLayoutX(250);
        button_save.setLayoutY(450);


        common.setLayoutX(50);
        common.setLayoutY(450);

        common_exp.setLayoutX(160);
        common_exp.setLayoutY(450);

        own.setLayoutX(50);
        own.setLayoutY(470);

        own_expense.setLayoutX(160);
        own_expense.setLayoutY(470);





        try {

            List<Bill> bills = new CsvReader().csvReader(path);
           double commonExpense =0;
           double ownExpenes =0;

            for(int i =0;i<bills.size();i++) {

                if (bills.get(i).isCommon()) {

                    commonExpense = commonExpense + bills.get(i).getExpense();
                }else {
                    ownExpenes = ownExpenes + bills.get(i).getExpense();
                }
            }

            for (int i = 0; i < bills.size(); i++) {

                Record.list.add(new Record.Builder()
                        .id(i)
                        .expense(bills.get(i).getExpense())
                        .shop(shopName)
                        .category(bills.get(i).getCategory())
                        .date(date)
                        .account(accountName)
                        .common(String.valueOf(bills.get(i).isCommon()))
                        .build());

            }

            common_exp.setText(String.valueOf(commonExpense));
            own_expense.setText(String.valueOf(ownExpenes));

            TablesBuilder.buildMain(tableView);
            tableView.getItems().addAll(Record.list);
            pane.getChildren().addAll(tableView,button_save,common,common_exp,own,own_expense);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
button_save.setOnAction(e-> save());



}

     public void save () {
         List values = new ArrayList<>();
         for (int i = 0; i < Record.list.size(); i++) {


             values.add(Record.list.get(i).amount);
             values.add(Record.list.get(i).date);
             values.add(Select.matchWithId("Account", Record.list.get(i).accountName));
             values.add(Select.matchWithId("Category", Record.list.get(i).categoryName));
             values.add(Select.matchWithId("Shop", Record.list.get(i).shopName));
             values.add(Select.matchWithId("CommonAccount", Record.list.get(i).isCommon));


             new SQLModifyMain.Insert().insert("Expense", values);
             values.removeAll(values);


         }
         Record.list.removeAll( Record.list);

         stage.close();
     }
    public static void main(String[] args) {
     //   Application.launch(args);
    }


}
