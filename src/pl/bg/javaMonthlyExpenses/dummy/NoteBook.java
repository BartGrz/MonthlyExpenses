package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteBook {

    static class GetRecord {

        static private String table_name, type;

        public GetRecord(String table_name) {
            this.table_name = table_name;

        }


        public  static String [] fields () {

            final int size = Record.Builder.class.getFields().length;

            String [] tab = new String[size];

            Looper.forLoop(size, i->tab[i] = Record.Builder.class.getFields()[i].getName());

            return tab;
        }
        public static List <String> specificFields(String table_name){

            List <String> list = new ArrayList<>();
            String [] tab = fields();

            if(table_name.equals("Expense")) {

                Looper.forLoop(tab.length,i->list.add(tab[i]));

                return list;

            }else if (table_name.equals("CommonAccount")) {
                list.add("main_id");
                Looper.forLoop(tab.length,i-> {
                    if (tab[i].contains(table_name.toLowerCase(Locale.ROOT))) {
                        list.add(tab[i]);
                    }
                });

                return list;

            }else {

                list.add("main_id");
                Looper.forLoop(tab.length,i-> {
                    if (tab[i].contains(table_name.toLowerCase(Locale.ROOT))) {
                        list.add(tab[i]);
                    }
                });


                return list;

            }

        }

        public static List<Record> buildCustom ( String table_name, List<Object> results) {

            List <String> fieldList = specificFields(table_name);



            int i =0;
            while(!results.isEmpty()) {

                Record record=  new Record.Builder().build();

                Looper.forLoop(fieldList.size(), j -> {

                    switch (fieldList.get(j)) {

                        case "main_id":
                            record.main_id = (int) results.get(j);

                            //    results.remove(j);
                            break;
                        case "amount":
                            record.amount = (double) results.get(j);
                            results.remove(j);
                            break;
                        case "balance":
                            record.balance = (double) results.get(j);
                            results.remove(j);
                            break;
                        case "debt":
                            record.debt = (double) results.get(j);
                            results.remove(j);
                            break;
                        case "finalResult":
                            record.finalResult = (double) results.get(j);
                            results.remove(j);
                            break;
                        case "accountName":
                            record.accountName = (String) results.get(j);
                            results.remove(j);
                            break;
                        case "date":
                            record.date = (String) results.get(j);
                            results.remove(j);
                            break;
                        case "shopName":
                            record.shopName = (String )results.get(j);
                            //  results.remove(j);
                            break;
                        case "categoryName":
                            record.categoryName = (String) results.get(j);
                            results.remove(j);
                            break;
                        case "isCommon":
                            record.isCommon = String.valueOf(results.get(j));
                            //  results.remove(j);
                            break;

                    }

                });

//results.remove(i);
                // i += 1;
                // results.remove(i);

                Record.list.add(record);


            }

            return Record.list;
        }

    }
    public static enum Identify {

        SHOPNAME, CATEGORYNAME, ISCOMMON, DATE, ACCOUNTNAME, DEBT, AMOUNT, BALANCE, FINALRESULT, MAIN_ID;



    }

    public Object identifVal(Object val){



        return val;

    }
}
