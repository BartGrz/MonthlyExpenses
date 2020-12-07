package pl.bg.javaMonthlyExpenses.mainWindow.AdditionalWinControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PopUp {

 static Stage popUp = new Stage();

    public void popUp()  {

        Parent  root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("pl/bg/javaMonthlyExpenses/mainWindow/FXML/infoWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

            Scene scene = new Scene(root ,102,98);


            popUp.setScene(scene);
            popUp.show();
    }
    public void closePopUp() {

        PopUp.popUp.close();
    }
    //
}
