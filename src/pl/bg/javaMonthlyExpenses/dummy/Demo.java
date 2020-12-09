package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;

public class Demo {


    public static void main(String[] args) {
        
        Select.setConnection();
        Looper.forLoopChoseIndex(1,3, i-> {
            new Select.SelectJoin<>().sumJoinRange("2020-11-10","2020-11-18",i);
            Logger.test(Record.list.get(i-1).toString());
        });
       
    
    }

}
