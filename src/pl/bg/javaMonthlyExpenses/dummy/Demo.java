package pl.bg.javaMonthlyExpenses.dummy;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;

import java.util.*;

import static pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools.checkIfForeignColumnDemo;
import static pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools.rs;

public class Demo {

    static List<String> list_names1 = new ArrayList<>();
    static HashMap<String, String> mapa = new HashMap<>();


    public static void testITerator(String table_name, TestResult testResult) {

        mapa = new SQLTools().getMappedTable(table_name);
        Iterator<String> it = mapa.keySet().iterator();

        while (it.hasNext()) {

            Object obj = it.next();

            testResult.testResult(obj, mapa);
        }

    }


    public static void main(String[] args) {

        list_names1.add("accountName");
        list_names1.add("idAccount");

        // mapa.put("accountName", "String");
        //  mapa.put("idAccount", "Integer");

        SQLTools.setConnection();


        // Logger.log(""+SQLTools.fetchColumnsNamesByTypeDemo("Expense","Integer"));
        //Logger.log("" + SQLTools.getColumntypeNameDemo("Shop","String"));
        //Logger.test("" + matchIdWithColumnDemo("shopName"));
        // Logger.test("" + checkIfForeignColumnDemo("Expense","Amount"));

        // Logger.warn("lista " + new Select("Expense").selectBasic());

        /*
        List<Object> list = new Select("Shop").selectBasic();

        int j = 0;

        while (!list.isEmpty()) {

            Record.list.add(new Record.Builder().id((Integer) SwitchFilter.
                    switchBuildingRecord("Shop", "Integer", list, (i) -> list.remove(i)))
                    .shop((String)SwitchFilter.
                            switchBuildingRecord("Shop", "String", list, (i) -> list.remove(i)))
                    .build());
        }
j+=1;
        Looper.forLoop(Record.list.size(),i->{
            Logger.test("" + Record.list.get(i).toString());
        });
    }



        List <String> lista_tables = Arrays.asList("Account","Shop","Category");
        Looper.forLoop(lista_tables.size(),i-> {


            List<Record> lista=new GetRecord(lista_tables.get(i)).fromBasicSelect();
            Logger.warn("" + lista.size());
            Looper.forLoop(lista.size(), j -> Logger.test("" + lista.get(j).toString()));
        });
   */
List<Record> test_expense = new GetRecord().fromJoinedSelect();
        Looper.forLoop(test_expense.size(), j -> Logger.test("" + test_expense.get(j).toString()));


    }

    static class GetRecord {

        static private String table_name, type;

        public GetRecord(String table_name) {
            this.table_name = table_name;

        }

        public GetRecord() {
        }

        static public List<Record> fromBasicSelect() {

            if (table_name.equals("Expense")) {
                Logger.error(" Wrong table ");
            }else {

                List<Object> list = new Select(table_name).selectBasic();

                while (!list.isEmpty()) {

                    String type = ObjectTools.findObjectType(list.get(0));

                    switch (table_name) {

                        case "Shop":
                            Record.list.add(new Record.Builder().id((int) SwitchFilter.
                                    switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .shop((String) SwitchFilter.
                                            switchBuildingRecord(table_name, ObjectTools.findObjectType(list.get(0)), list, (i) -> list.remove(i)))
                                    .build());
                            break;
                        case "Account":
                            Record.list.add(new Record.Builder().id((int) SwitchFilter.
                                    switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .account((String) SwitchFilter.
                                            switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .build());
                            break;
                        case "Category":
                            Record.list.add(new Record.Builder().id((int) SwitchFilter.
                                    switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .category((String) SwitchFilter.
                                            switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .build());
                            break;
                        case "CommonAccount":
                            Record.list.add(new Record.Builder().id((int) SwitchFilter.
                                    switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                                    .common(String.valueOf(SwitchFilter.
                                            switchBuildingRecord(table_name, type, list, (i) -> list.remove(i))))
                                    .build());
                            break;
                    }

                }


                return Record.list;
            }
            return null;
        }
        static public List<Record> fromJoinedSelect(){

           final String table_name = "Expense";

            List<Object> list = new Select.SelectJoin(table_name).joinMainTest();
Logger.warn(""+ list.size());
            while (!list.isEmpty()) {

                String type = ObjectTools.findObjectType(list.get(0));

                Record.list.add(new Record.Builder()
                        .id((int)SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .account((String) SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .expense((double)SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .category((String) SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .date((String) SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .shop((String)SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i)))
                        .common(String.valueOf(SwitchFilter.
                                switchBuildingRecord(table_name, type, list, (i) -> list.remove(i))))
                        .build());

            }

            return Record.list;
        }

    }
}