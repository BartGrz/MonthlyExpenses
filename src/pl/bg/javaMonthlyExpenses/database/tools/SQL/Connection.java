package pl.bg.javaMonthlyExpenses.database.tools.SQL;

import com.sun.javafx.tools.ant.Platform;
import pl.bg.javaMonthlyExpenses.Logger.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connection {

   // public static final String url = "jdbc:sqlite:" + System.getProperty("user.home") +"\\Desktop\\" ;
    public static java.sql.Connection connection;
    public static Statement statement;
    public static String database;

    public static void setConnection() {

        final String chosenUrl = "jdbc:sqlite:" + System.getProperty("user.home") + "\\Desktop\\"+ database;
        try {
            connection = DriverManager.getConnection(chosenUrl);
            statement = connection.createStatement();
            Logger.conn_status("Connected to " + database);
        } catch (SQLException e) {
            Logger.error("" + e);
        }
    }

/*
    public static void setConnectionDemo() {



        try {
            connection = DriverManager.getConnection(chosenUrl);
            Logger.log("" +url+database);
            statement = connection.createStatement();
            Logger.conn_status("Connected");
        } catch (SQLException e) {
            Logger.error("" + e);
        }
    }


 */

    
    public static void disconnect() {
        try {
            statement.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.conn_status("Disconnected from " +database);
    }
    
    public static void checkConnection( ) {
        
        try {
            
            if (!getStatement(statement).isClosed()) {
            
            } else {
                
                setConnection();
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Statement getStatement(Statement statement) {
        
        return statement;
    }
    
}
