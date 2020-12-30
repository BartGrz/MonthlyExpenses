package pl.bg.javaMonthlyExpenses.database.tools.SQL;

import com.sun.javafx.tools.ant.Platform;
import pl.bg.javaMonthlyExpenses.Logger.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connection {
    
    
    public static final String url = "jdbc:sqlite:" + System.getProperty("user.home") +"\\Desktop\\MonthlyExpenses.db" ;
    public static java.sql.Connection connection;
    public static Statement statement;
    
    public static void setConnection() { //connection

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            Logger.conn_status("Connected");
        } catch (SQLException e) {
            Logger.error("" + e);
        }
    }
    
    public void disconnect() { //connection
        try {
            statement.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.conn_status("Disconnected");
    }
    
    public static void checkConnection( ) { //connection
        
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
