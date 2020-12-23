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
    public int id;

    public static class BuilderList {

        public static Identify identified;
        private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
        public Object fromList;
        public int id;

        public BuilderList identifyColumn(Identify val) {
            identified = val;
            return this;
        }
        public BuilderList fromList (Object val) {
            fromList = val;
            return this;
        }
        public BuilderList id (int val) {
            id = val;
            return this;
        }
        public TestBuilderRecord build() {
            return new TestBuilderRecord(this);
        }
    }

    public TestBuilderRecord(BuilderList builderList) {
        this.identified = BuilderList.identified;
        this.fromList=builderList.fromList;
        this.id=builderList.id;

    }
    public static List <TestBuilderRecord> matchWithTypeAndAdd (ResultSet rs , String table_name , String sql) {

        HashMap <String,String> map = new SQLTools().getMappedTable(table_name);

        List<Object> columns = new ArrayList<>();



        List<TestBuilderRecord> identyfiedObjects = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()) {
            columns.add(it.next());
        }

        List <String> columnsWithDoubleType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Double");
        List <String> columnsWith_IntType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Integer");
        int [] id = new int[1];


        try {

            rs = statement.executeQuery(sql);

            while (rs.next()) {

id[0] = rs.getInt("id"+table_name);

                List <String> columnsWithDoubleType_copyOf = new ArrayList<>(columnsWithDoubleType);
                List <String> columnsWith_IntType_copyOf =  new ArrayList<>(columnsWith_IntType);



                for(int i = 0; i<columns.size();i++) {

                    Object val = columns.get(i);





                    switch (map.get(val)) { //zmieniam listy in i double na ich kopie (za ucina wyciaganie danych

                        case "integer":

                            for(int j= 0 ;j<columnsWith_IntType_copyOf.size();j++) {

                                switch (columnsWith_IntType_copyOf.get(j)) {
                                    case "idAccount" :
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getInt(val.toString())).identifyColumn(Identify.IDACCOUNT).id(id[0]).build());
                                        columnsWith_IntType_copyOf.remove(j);
                                        break;
                                    default:
                                        //id=rs.getInt(columnsWith_IntType_copyOf.get(j));
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getInt(val.toString())).identifyColumn(Identify.MAIN_ID).id(id[0]).build());
                                        columnsWith_IntType_copyOf.remove(j);
                                        break;
                                }

                            }

                        break;

                        case "string":

                            if (table_name.equals("Shop")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.SHOPNAME).id(id[0]).build());
                                break;
                            } else if (table_name.equals("Category")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.CATEGORYNAME).id(id[0]).build());
                                break;
                            } else if (table_name.equals("Account")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.ACCOUNTNAME).id(id[0]).build());
                                break;
                            } else if (table_name.equals("CommonAccount")) {
                                identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.ISCOMMON).id(id[0]).build());
                                break;
                            }
                        case "date":
                            identyfiedObjects.add(new BuilderList().fromList(rs.getString(val.toString())).identifyColumn(Identify.DATE).id(id[0]).build());
                            break;
                        case "double":
                            if (table_name.equals("Balance")) {
                                for (int j = 0; j < columnsWithDoubleType_copyOf.size(); j++) {
                                    switch (columnsWithDoubleType_copyOf.get(j).toLowerCase(Locale.ROOT)) {
                                        case "debt" :
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.DEBT).id(id[0]).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;

                                        case "balance" :
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.BALANCE).id(id[0]).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                        case "result":
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.FINALRESULT).id(id[0]).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                    }
                                  break;
                                }
                            } else {
                                identyfiedObjects.add(new TestBuilderRecord.BuilderList().fromList(rs.getDouble(val.toString())).identifyColumn(Identify.AMOUNT).id(id[0]).build());

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
        Looper.forLoop(wynik.size(),i-> {

            if(wynik.get(i).id==1 || wynik.get(i).id==2) {
                Logger.test("" + wynik.get(i).toString());
            }


        });
    }


    @Override
    public String toString() {
        return "{" +
                "identified=" + identified +
                ", fromList=" + fromList +
                ", id = " + id +
                '}';
    }
}
