package pl.bg.javaMonthlyExpenses.holder;

import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuildRecord {


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
                    records.get(index[0]).debt = SQLTools.round((double) fromIdentifyList(identifiedResults));;
                 //    Logger.log("" + records.get(index[0]).debt);
                    break;
                case "sum":
                    records.get(index[0]).sum = SQLTools.round((double) fromIdentifyList(identifiedResults));;
                    // Logger.log("" + records.get(index[0]).debt);
                    break;
                case "amount":
                    records.get(index[0]).amount =  (double) fromIdentifyList(identifiedResults);
                    // Logger.log("recordbuilder amount=" + records.get(index[0]).amount);
                    break;
                case "finalresult":
                    records.get(index[0]).finalResult = SQLTools.round((double) fromIdentifyList(identifiedResults));;
                    // Logger.log("" + records.get(index[0]).finalResult);
                    break;

                case "balance":
                    records.get(index[0]).balance = SQLTools.round((double) fromIdentifyList(identifiedResults));;
                    //  Logger.log("balance =" + records.get(index[0]).balance);
                    break;

                case "idaccount":

                    records.get(index[0]).main_id = (int) fromIdentifyList(identifiedResults);
                    //  Logger.log("idaccount =" + records.get(index[0]).main_id);
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
                      // Logger.log("accountname =" + records.get(index[0]).accountName);
                    break;
                case "iscommon":

                    records.get(index[0]).isCommon = String.valueOf(fromIdentifyList(identifiedResults));
                    //   Logger.log("isCommon = " + records.get(index[0]).isCommon);
                    break;
            }


            k += 1;

        }

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

       }else if (index % 2 == 0 && !TestBuilderRecord.flag.toString().equals("WITH_SUM") &&  !table_name.equals("EXPENSE") &&!table_name.equals("BALANCE")){

        iterator[0] += 1;
        return true;

       }else if (index % 3 ==0&& TestBuilderRecord.flag.toString().equals("WITH_SUM") ) {
        iterator[0] += 1;
        return true;

        } else if (index == 6 && table_name.equals("BALANCE")) {
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

}
