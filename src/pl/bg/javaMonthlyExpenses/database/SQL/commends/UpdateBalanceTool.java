package pl.bg.javaMonthlyExpenses.database.SQL.commends;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.util.*;

public class UpdateBalanceTool extends SQLEssentials {

    public void sumBalance() {


        Select.setConnection();
        Looper.forLoopChoseIndex(1,3,i-> new SQLModifyMain.Update("Balance")
                .update("Balance", new Select("Expense").selectSumBasic(i), i));
        
        sumDebt();
    }


    private void sumDebt() {
      //  for (int i = 1; i < 3; i++) {
Looper.forLoopChoseIndex(1,3,i->{
    

            new Select.SelectJoin<Boolean, Integer>("Expense")
                    .sumJoin_mixConditions_AND("CommonAccount", true, i);


            switch (i) {
                case 1:
                    new SQLModifyMain.Update("Balance")
                            .update("debt", Record.list.get(i - 1).amount / 2, i + 1);

                    break;

                case 2:
                    new SQLModifyMain.Update("Balance")
                            .update("debt", Record.list.get(i - 1).amount / 2, i - 1);

                    break;
            }
});

        sumResult();

    }

    private void sumResult() {
    
    
    
        Looper.forLoopChoseIndex(1,3,i->{

            new Select("Balance").selectSpecifyColumns(Arrays.asList("Balance", "debt"), i);
        });


    double balance_b = (double) Select.results.get(0);
    double debt_b = (double) Select.results.get(1);
    double balance_u = (double) Select.results.get(2);
    double debt_u = (double) Select.results.get(3);
    
    
        Looper.forLoopChoseIndex(1,3,i->{
        switch (i) {

            case 1 :
                new SQLModifyMain.Update("Balance").update("result",(balance_u - debt_b),i);
                break;

            case 2:
                new SQLModifyMain.Update("Balance").update("result",(balance_b - debt_u),i);
                break;
        }
        });
        
    Logger.result("UPDATE COMPLETED ");
    Logger.end();

Record.list.removeAll(Record.list);

        }
    }


