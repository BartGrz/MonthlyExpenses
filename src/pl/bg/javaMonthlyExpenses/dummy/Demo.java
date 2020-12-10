package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    
    public static void checkIfAllive(Thread thread,Runnable runnable) {
    
        while (thread.isAlive()) {
        
        }
        if (!thread.isAlive()) {
    
            runnable.run();
            
        } else {
        
        }
 
    }
    public static void main(String[] args) {
    
        Select.setConnection();
    /*
        Thread thread_first = new Thread(() -> {
        
            new Select.SelectJoin<>().sumJoinRange("2020-11-10", "2020-11-18", 1);
            Logger.test(Record.list.get(0).toString());
            Record.list.removeAll(Record.list);
        
        });
    
        Thread thread_second = new Thread(() -> {
            
            new Select.SelectJoin<>().sumJoinRange("2020-11-10", "2020-11-18", 2);
            Logger.test(Record.list.get(0).toString());
            Record.list.removeAll(Record.list);
            
        
        });
    
        Thread thread_third = new Thread(()-> {
            
            Logger.success();
            
        });
        
        thread_first.start();
    /*
while (thread_first.isAlive()) {

}
        if(!thread_first.isAlive()) {
            thread_second.start();
        }else {
            thread_third.start();
        }
        
     
        checkIfAllive(thread_first, ()-> {
            
            thread_second.start();
            
        });
        checkIfAllive(thread_second,()-> {
            
            thread_third.start();
        });
        */
    
        List list = Arrays.asList(4.95,"2020-12-09",1,10,4,1);
        
        new SQLModifyMain.Insert().insert("Expense",list);
        
    }

}
