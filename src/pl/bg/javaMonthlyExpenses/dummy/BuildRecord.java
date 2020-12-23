package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuildRecord {

public static List<TestBuilderRecord> show (List<TestBuilderRecord> identifiedResults){



    return identifiedResults;
}

    public  static List<Record> records (List<TestBuilderRecord> identifiedResults) {

        List <Record> records = new ArrayList<>();

        int k =0 ;
        int index =0;

    while (!identifiedResults.isEmpty()) {

        if(k==0 ){

        records.add( new Record.Builder().build());

        } else if (k==5){

        records.add( new Record.Builder().build());
        index+=1;

        }

    String cond = identifiedResults.get(0).identified.toString().toLowerCase(Locale.ROOT);

        switch (cond) {

            case "main_id":
            records.get(index).main_id = (int)fromIdentifyList(identifiedResults).fromList;
            break;

            case "debt":
            records.get(index).debt = (double) fromIdentifyList(identifiedResults).fromList;
            break;

            case "finalresult":
            records.get(index).finalResult = (double) fromIdentifyList(identifiedResults).fromList;
            break;

            case "balance":
            records.get(index).balance = (double)fromIdentifyList(identifiedResults).fromList;
            break;

            case "idaccount" :

            records.get(index).main_id = (int) fromIdentifyList(identifiedResults).fromList;
            break;
        }
        k+=1;

    }
        return records;
}

    public static void main(String[] args) {

        Select.setConnection();

        List<TestBuilderRecord> wynik = new Select("Balance").selectBasicDemo();

        Record.list = new ArrayList<>(records(wynik));
        Looper.forLoop(Record.list.size(), i -> Logger.warn(Record.list.get(i).toString()));
    }

    public static TestBuilderRecord fromIdentifyList(List<TestBuilderRecord> list) {

    TestBuilderRecord val = null;

        for (int i = 0; i<list.size();i++) {
           val = list.get(i);

            list.remove(i);
            return val;
        }
       return null;
    }
}
