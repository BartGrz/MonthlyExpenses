package pl.bg.javaMonthlyExpenses.database.tools;


import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.Loop;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.Remove;

import java.sql.SQLException;
import java.util.List;

public class Looper {

    public static void forLoop(int size, Loop loop ) {

        for (int i = 0; i<size;i++ ) {
            loop.loop(i);
        }


    }
    public static void forLoopChoseIndex(int beginIndex,int endIndex, Loop loop ) {
        
        for (int i = beginIndex; i<endIndex;i++ ) {
            loop.loop(i);
        }
        
        
    }

public static void whileLoop(boolean condition, DoIt doIt) {

        while(condition) {

            doIt.doIt();

        }

}
    public static void whileLoopResultSet(String sql,boolean condition, DoIt doIt) {

        Select.checkConnection();
        try {
            Select.statement.executeQuery(sql);
            while(condition) {
                doIt.doIt();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public static void doWhileLoop(boolean condition , DoIt doIt) {
        
        do {
            doIt.doIt();
            
        }while(condition);
        
    }
    
}
