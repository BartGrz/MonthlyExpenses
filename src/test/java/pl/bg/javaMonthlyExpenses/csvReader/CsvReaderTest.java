package pl.bg.javaMonthlyExpenses.csvReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;

import static org.junit.Assert.*;

public class CsvReaderTest   {

    @Test
    public void csvReader() throws Exception {

        final String devPath = System.getProperty("user.home") +"\\Desktop\\MonthlyExpenses_devTest.db";

        Connection.database = "MonthlyExpenses_devTest.db";
        Connection.setConnection();



    }





}