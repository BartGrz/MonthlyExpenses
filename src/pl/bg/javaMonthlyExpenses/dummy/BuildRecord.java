package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BuildRecord {

    public static List<TestBuilderRecord> show(List<TestBuilderRecord> identifiedResults) {


        return identifiedResults;
    }

    public static List<Record> records(List<TestBuilderRecord> identifiedResults) {

        List<Record> records = new ArrayList<>();

        int k = 0;
        int[] index = new int[1];



        while (!identifiedResults.isEmpty()) {

            add(k, index, identifiedResults, () -> records.add(new Record.Builder().build()));

            String cond = identifiedResults.get(0).identified.toString().toLowerCase(Locale.ROOT);

            switch (cond) {

                case "main_id":
                    records.get(index[0]).main_id = (int) fromIdentifyList(identifiedResults);
                    //   Logger.log("main_id = " + records.get(index[0]).main_id);
                    break;

                case "debt":
                    records.get(index[0]).debt = (double) fromIdentifyList(identifiedResults);
                    // Logger.log("" + records.get(index[0]).debt);
                    break;
                case "sum":
                    records.get(index[0]).sum = (double) fromIdentifyList(identifiedResults);
                    // Logger.log("" + records.get(index[0]).debt);
                    break;
                case "amount":
                    records.get(index[0]).amount = (double) fromIdentifyList(identifiedResults);
                    // Logger.log("recordbuilder amount=" + records.get(index[0]).amount);
                    break;
                case "finalresult":
                    records.get(index[0]).finalResult = (double) fromIdentifyList(identifiedResults);
                    // Logger.log("" + records.get(index[0]).finalResult);
                    break;

                case "balance":
                    records.get(index[0]).balance = (double) fromIdentifyList(identifiedResults);
                    //  Logger.log("balance =" + records.get(index[0]).balance);
                    break;

                case "idaccount":

                    records.get(index[0]).main_id = (int) fromIdentifyList(identifiedResults);
                    //   Logger.log("idaccount =" + records.get(index[0]).main_id);
                    break;

                case "shopname":

                    records.get(index[0]).shopName = (String) fromIdentifyList(identifiedResults);
                    //   Logger.log("shopname =" + records.get(index[0]).shopName);
                    break;
                case "date":

                    records.get(index[0]).date = (String) fromIdentifyList(identifiedResults);
                    //   Logger.log("date =" + records.get(index[0]).date);
                    break;
                case "categoryname":

                    records.get(index[0]).categoryName = (String) fromIdentifyList(identifiedResults);
               //     Logger.log("categoryname =" + records.get(index[0]).categoryName);
                    break;
                case "accountname":

                    records.get(index[0]).accountName = (String) fromIdentifyList(identifiedResults);
                    //   Logger.log("accountname =" + records.get(index[0]).accountName);
                    break;
                case "iscommon":

                    records.get(index[0]).isCommon = String.valueOf(fromIdentifyList(identifiedResults));
                    //   Logger.log("isCommon = " + records.get(index[0]).isCommon);
                    break;
            }


            k += 1;

        }
        /*
        if (records.get(index[0]).main_id == 0) {
            Logger.result("" + records.get(index[0]).toString());
            records.remove(index[0]);
        }
*/
        return records;
    }


    public static Object fromIdentifyList(List<TestBuilderRecord> list) {


        Object val = list.get(0).fromList;
            list.remove(0);
            return val;

    }

    public static boolean addIfNedded(int index, int[] iterator, List<TestBuilderRecord> identifiedResults) {
       String table_name = identifiedResults.get(0).table.toString();
    if (index == 0) {
        return true;

       }else if (index % 2 == 0 && !TestBuilderRecord.flag.toString().equals("WITH_SUM") &&  !table_name.equals("EXPENSE")){

        iterator[0] += 1;
        return true;

       }else if (index % 3 ==0&& TestBuilderRecord.flag.toString().equals("WITH_SUM") ) {
        iterator[0] += 1;
        return true;

        } else if (index == 5 && identifiedResults.get(0).table.toString().toLowerCase(Locale.ROOT).equals("balance")) {
            iterator[0] += 1;
            return true;


        } else if (index % 7 == 0 && identifiedResults.get(0).table.toString().equals("EXPENSE")) {
            iterator[0] += 1;
            return true;

        }

        return false;
    }

    public static void add(int index, int[] iterator, List<TestBuilderRecord> identifiedResults, DoIt doIt) {

        if (addIfNedded(index, iterator, identifiedResults)) {
            doIt.doIt();
        } else {

        }

    }

    public static void main(String[] args) {

        Select.setConnection();
/*
        List<String> cond = new ArrayList<>(Arrays.asList("Vet", "Pies", "jedzenie[p", "UBER", "swinsk", "napoj", "jedzenie", "jedzenie[z"));

        List<List<Record>> lista = new ArrayList<>();

        Looper.forLoop(cond.size(), i -> lista.add(
                records(new Select.SelectJoin("Expense").sumJoin_partialStringsDemo("Category", cond.get(i)))));


for(int i = 0;i< lista.size();i++) {

  Record.list.add(lista.get(i).get(0));
}
        Looper.forLoop(Record.list.size(),j->Logger.log(""+Record.list.get(j).toString()));

/*
List <Record> lis = records(new Select("Shop").selectBasic());
Looper.forLoop(lis.size(),i->Logger.log(lis.get(i).toString()));

 */

        Record.list = records(new Select.SelectJoin().joinMain());
        Logger.warn("" + Record.list.size());

        Looper.forLoop(Record.list.size(),i->Record.list.get(i).toString());
    }
}
