package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuildRecord {

public static List<TestBuilderRecord> show (List<TestBuilderRecord> identifiedResults){



    return identifiedResults;
}

    public  static List<Record> records (List<TestBuilderRecord> identifiedResults) {

    int size = identifiedResults.size();
        List <Record> records = new ArrayList<>();

        int k =0 ;
        int [] index = new int[1];

    while (!identifiedResults.isEmpty()) {

        add(k,index,identifiedResults,()->records.add(new Record.Builder().build()));

        String cond = identifiedResults.get(0).identified.toString().toLowerCase(Locale.ROOT);

        switch (cond) {

            case "main_id":
            records.get(index[0]).main_id = (int)fromIdentifyList(identifiedResults).fromList;
                break;

            case "debt":
            records.get(index[0]).debt = (double) fromIdentifyList(identifiedResults).fromList;
                break;

            case "finalresult":
                 records.get(index[0]).finalResult = (double) fromIdentifyList(identifiedResults).fromList;
                break;

            case "balance":
                 records.get(index[0]).balance = (double)fromIdentifyList(identifiedResults).fromList;
                break;

            case "idaccount" :

                 records.get(index[0]).main_id = (int) fromIdentifyList(identifiedResults).fromList;
                break;

            case "shopname" :

                records.get(index[0]).shopName = (String) fromIdentifyList(identifiedResults).fromList;
                break;
            case "categoryname" :

                records.get(index[0]).categoryName = (String) fromIdentifyList(identifiedResults).fromList;
                break;
            case "accountname" :

                records.get(index[0]).accountName = (String) fromIdentifyList(identifiedResults).fromList;
                break;
            case "iscommon" :

                records.get(index[0]).isCommon = String.valueOf( fromIdentifyList(identifiedResults).fromList);
                break;
        }
        k+=1;

    }
        return records;
}

    public static void main(String[] args) {

        Select.setConnection();

        List<TestBuilderRecord> wynik = new Select("Account").selectBasic();

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

    public static boolean addIfNedded (int index,int [] iterator, List<TestBuilderRecord> identifiedResults ){

        if(index==0 ){
          //  iterator[0]+=1;
            return true;

        } else if (index%2==0 && !identifiedResults.get(0).table.toString().toLowerCase(Locale.ROOT).equals("balance")){
            iterator[0]+=1;
            return true;


        }else if(index==5 && identifiedResults.get(0).table.toString().toLowerCase(Locale.ROOT).equals("balance")) {
            iterator[0]+=1;
            return true;


        }else if(index==7 && identifiedResults.get(0).table.toString().toLowerCase(Locale.ROOT).equals("expense")){
            iterator[0]+=1;
            return true;

        }

    return false;
    }

    public static void add(int index,int [] iterator, List<TestBuilderRecord>identifiedResults, DoIt doIt ) {

    if(addIfNedded(index,iterator,identifiedResults)) {
        doIt.doIt();
    }else {

    }

    }
}
