package pl.bg.javaMonthlyExpenses.database.SQL.commends;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.util.Arrays;
import java.util.List;

public class UpdateBalancePrivateTool extends Connection {

    synchronized private void sumBalance () {

        checkConnection();
        Looper.forLoopChoseIndex(1,4, i-> new SQLModifyMain.Update("Balance")

                .update("Balance", new Select("Expense").selectSumBasic(i), i));

    }
    synchronized private void updateDebt(){

        double [] sum = new double[1];

        Looper.forLoopChoseIndex(1,4,i->{

            new Select.SelectJoin<Boolean, Integer>("Expense")
                    .sumJoin_mixConditions_AND("CommonAccount", false, i);

        });
        Looper.forLoop(Record.list.size(), i -> {

            Logger.log("debt= " + (Record.list.get(i).amount +sum[0]));

        new SQLModifyMain.Update("Balance")
                .update("debt", (Record.list.get(i).amount +sum[0] ), 1);
        sum[0] = Record.list.get(i).amount;



        });


    }

    synchronized private void updateResult(){

        Looper.forLoopChoseIndex(1,4,i->{

            new Select("Balance").selectSpecifyColumns(Arrays.asList("Balance", "debt"), i);


        });
        double balance_b = (double) Select.list_results.get(0);
        double debt_b = (double) Select.list_results.get(1);
        double balance_credit = (double) Select.list_results.get(2);
        double debt_credit = (double) Select.list_results.get(3);
        double balance_oszcz = (double) Select.list_results.get(4);
        double debt_oszcz = (double) Select.list_results.get(5);

        Looper.forLoopChoseIndex(1,4,i->{
switch (i) {
    case 1:
        new SQLModifyMain.Update("Balance").update("result",(debt_b)*(-1) ,i);
        break;
    case 2:
        new SQLModifyMain.Update("Balance").update("result",(balance_credit+debt_credit)*(-1) ,i);
        break;
    case 3:
        new SQLModifyMain.Update("Balance").update("result",(balance_oszcz+debt_oszcz)*(-1) ,i);
        break;
}

        });


        Record.list.removeAll(Record.list);

    }
    public  void startTool() {

        List<Thread> list_Threads = Arrays.asList(
                new Thread (()-> { sumBalance(); }),
                new Thread(()->{ updateDebt(); }),
                new Thread(()->{ updateResult(); })
        );

        Looper.forLoop(list_Threads.size(),i->{

            list_Threads.get(i).start();

            while (list_Threads.get(i).isAlive()) {

            }
        });
        Select.list_results.removeAll(Select.list_results);
        Logger.end();

    }

}



