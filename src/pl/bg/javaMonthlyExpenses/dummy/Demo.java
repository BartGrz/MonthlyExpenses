package pl.bg.javaMonthlyExpenses.dummy;



import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.BuildRecord;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.interfaces.MainWindow;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Demo extends Application implements Initializable {

    private String fxmlFile ;
   private static Stage stageMain = new Stage(), stage = new Stage();

   @FXML
   private ComboBox comboBox_database = new ComboBox();
   @FXML
   private PasswordField password = new PasswordField();
   @FXML
   private Button open = new Button();
   public static boolean isOpened ;


   private ObservableList<String> database = FXCollections.observableArrayList("Common","Personal"); //
   final String pass ="u" ;



    public void start(Stage stage)  {

        this.stage = stage;

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/validationWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 264, 188);

        stage.setScene(scene);
        stage.setTitle("validator");
        stage.show();
        //isOpened = true;

    }

    @FXML
    private void openMainWindow(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/"+fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 860, 520);
        stageMain.setScene(scene);
        stageMain.setTitle("MonthlyExpenses");
        stageMain.show();
        stageMain.maxHeightProperty().set(530);
        stageMain.maxWidthProperty().set(860);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        password.setVisible(false);
        open.setVisible(false);
       Looper.forLoop(database.size(),i-> comboBox_database.getItems().add(database.get(i)));


    }
    public void choose() {

        switch (comboBox_database.getValue().toString()) {
            case "Common":

                password.setVisible(false);
                open.setVisible(true);

                break;

            case "Personal" :

                password.setVisible(true);
                open.setVisible(true);

                break;
        }

    }
    public void open() {

        if(password.isVisible() && password.getText().equals(pass)) {

            Connection.database="MonthlyExpenses_personal.db";
           Connection.setConnection();
            fxmlFile = "mainWindowPersonal.fxml";

        }else{

         Connection.database="MonthlyExpenses.db";
         Connection.setConnection();
         fxmlFile = "mainWindow.fxml";

            }
        openMainWindow();
        isOpened(stage);




    }
    public void startApp(Stage stage) {
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void isOpened(Stage stage){

        if(stage.isShowing()){

            stage.close();
        }

    }
}
