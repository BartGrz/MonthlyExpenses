package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.Record;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TestBuilderRecord extends Connection {

    private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
    public Identify identified;
    public Object fromList;

    public static class BuilderList {

        public static Identify identified;
        private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
        public Object fromList;

        public BuilderList identifyColumn(Identify val) {
            identified = val;
            return this;
        }
        public BuilderList fromList (Object val) {
            fromList = val;
            return this;
        }

        public TestBuilderRecord build() {
            return new TestBuilderRecord(this);
        }
    }

    public TestBuilderRecord(BuilderList builderList) {
        this.identified = BuilderList.identified;
        this.fromList=builderList.fromList;

    }
    public static List <TestBuilderRecord> matchWithTypeAndAdd (ResultSet rs , String table_name , String sql) { // problem z identyfikacja objektow tam gdzie sie dubluja typy - > double i int poki co (stringi nie test)

        HashMap <String,String> map = new SQLTools().getMappedTable(table_name);

        List<Object> columns = new ArrayList<>();



        List<TestBuilderRecord> identyfiedObjects = new ArrayList<>();
        List <String> columnsWithDoubleType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Double");
        List <String> columnsWith_IntType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Integer");
        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()) {
            columns.add(it.next());
        }
        Logger.test("" + columnsWith_IntType);


        try {
            rs = statement.executeQuery(sql);

            while (rs.next()) {


                for(int i = 0; i<columns.size();i++) {

                    Object val = columns.get(i);


                    switch (map.get(val)) {

                        case "integer":

                            for(int j= 0 ;j<columnsWith_IntType.size();j++) {

                                if(columnsWith_IntType.get(j).equals("idAccount")) {
                                    identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getInt(val.toString())).identifyColumn(Identify.IDACCOUNT).build());
                                    break;
                                }else {
                                    identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getInt(val.toString())).identifyColumn(Identify.MAIN_ID).build());
                                    break;
                                }
                            }

                        break;
                        case "string":

                            if (table_name.equals("Shop")) {
                                identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.SHOPNAME).build());
                                break;
                            } else if (table_name.equals("Category")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.CATEGORYNAME).build());
                                break;
                            } else if (table_name.equals("Account")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.ACCOUNTNAME).build());
                                break;
                            } else if (table_name.equals("CommonAccount")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.ISCOMMON).build());
                                break;
                            }
                        case "date":
                            identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.DATE).build());
                            break;
                        case "double":
                            if (table_name.equals("Balance")) {
                                for (int j = 0; j < columnsWithDoubleType.size(); j++) {

                                    if (columnsWithDoubleType.get(j).equals("debt")) {
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.DEBT).build());

                                        break;
                                    } else if (columnsWithDoubleType.get(j).toLowerCase(Locale.ROOT).equals("balance")) {
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.BALANCE).build());
                                       // columnsWithDoubleType.remove(j);
                                        break;
                                    } else if (columnsWithDoubleType.get(j).equals("result")) {
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.FINALRESULT).build());
                                       // columnsWithDoubleType.remove(j);
                                        break;
                                    }
                                   // columnsWithDoubleType.remove(j);
                                }
                                        break;
                            } else {
                                identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.AMOUNT).build());

                                break;
                            }

                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return identyfiedObjects;
    }
    public static void mapTerator(String table_name, TestResult testResult) {

        HashMap <String,String> mapa = new SQLTools().getMappedTable(table_name);
        Iterator<String> it = mapa.keySet().iterator();

        while (it.hasNext()) {

            Object obj = it.next();

            testResult.testResult(obj, mapa);
        }

    }
    public static enum Identify {

        SHOPNAME, CATEGORYNAME, ISCOMMON, DATE, ACCOUNTNAME, DEBT, AMOUNT, BALANCE, FINALRESULT, MAIN_ID,IDACCOUNT;


    }
    public static Object identifiedObject(Object val) {


        return val;
    }

    public static void main(String[] args) {

        setConnection();
        List<TestBuilderRecord> wynik = new Select("Balance").selectBasicDemo();
        Looper.forLoop(wynik.size(),i-> Logger.test(""+wynik.get(i).fromList + " identiefied as " + wynik.get(i).identified ));
    }

}
