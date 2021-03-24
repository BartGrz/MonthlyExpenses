package pl.bg.javaMonthlyExpenses.database.tools;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.SQLModifyMain;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InsertingTool   {

    private  String path ;
   private File file =null;
   private List list = new ArrayList();
    private Scanner scanner ;


    public void addShops (String table_name) {
        SQLModifyMain.setConnection();
        path = System.getProperty("user.home")+"\\MonthlyExpenses\\Data\\"+table_name + ".txt";
        Logger.test(" dir " + path);
        file = new File(path);

        try {
            scanner = new Scanner(file);

        } catch (FileNotFoundException e) {
            Logger.error(""+e);
            Logger.failure();
        }

while(scanner.hasNext()) {
list = new ArrayList();
    list.add(scanner.next());

    new SQLModifyMain.Insert().insert(table_name,list);



}

//sqlCommends.select("Shop");


}

}
