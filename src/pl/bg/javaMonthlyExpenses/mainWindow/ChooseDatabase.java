package pl.bg.javaMonthlyExpenses.mainWindow;



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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ChooseDatabase extends Application implements Initializable {

    private String fxmlFile ;
   public static Stage stageMain = new Stage();
    private static Stage stage = new Stage();

   @FXML
   private ComboBox comboBox_database = new ComboBox();
   @FXML
   private PasswordField password = new PasswordField();
   @FXML
   private Button open = new Button();
   @FXML
   private Label label_passInfo = new Label(), label_passwordText=new Label();



   private ObservableList<String> database = FXCollections.observableArrayList("Common","Personal"); //


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

        label_passInfo.setVisible(false);
        label_passwordText.setVisible(false);
        password.setVisible(false);
        open.setVisible(false);
       Looper.forLoop(database.size(),i-> comboBox_database.getItems().add(database.get(i)));


    }
    public void choose() {

        switch (comboBox_database.getValue().toString()) {
            case "Common":

                label_passwordText.setVisible(false);
                label_passInfo.setVisible(false);
                stage.setWidth(343);
                password.setVisible(false);
                open.setVisible(true);

                break;

            case "Personal" :

                label_passwordText.setVisible(true);
                password.setVisible(true);
                open.setVisible(true);

                break;
        }

    }
    public void open() {

        if(password.isVisible()  && checkPassword(password.getText().hashCode())) {

            fxmlFile = "mainWindowPersonal.fxml";

            openMainWindow();
            isOpened(stage);

        }else if (password.isVisible() && password.getText().equals(null) || ! checkPassword(password.getText().hashCode())) {

            stage.setWidth(500);
            label_passInfo.setVisible(true);
            label_passInfo.setText("PASSWORD INCORRECT");
            password.setText(null);
            Connection.disconnect();

        }else if (!password.isVisible()){

         Connection.database="MonthlyExpenses.db";
         Connection.setConnection();
         fxmlFile = "mainWindow.fxml";
            openMainWindow();
            isOpened(stage);

            }
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

    public static boolean checkPassword(int pass) {

        Connection.database="MonthlyExpenses_personal.db";
        Connection.setConnection();

        List <Integer> passwords = new ArrayList<>();
        ResultSet rs = null;

        String sql = "Select password from Password";

        try {
            rs = Connection.statement.executeQuery(sql);
            while (rs.next()) {
            passwords.add(rs.getInt("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i< passwords.size();i++) {
            Logger.log("z textu " + pass + " z bazy " +passwords.get(i));
            if(pass == passwords.get(i).hashCode()) {

                return true;
            }else {
                return false;
            }
        }
        return false;
    }


}
