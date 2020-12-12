package pl.bg.javaMonthlyExpenses.dummy;


import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;

public class Demo {

    
    public static void main(String[] args) {
    
        SQLModifyMain.setConnection();
        
        
        
       // new SQLModifyMain.Update("Expense").update("Amount",27.5,186);
        
    }

}
