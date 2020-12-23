package pl.bg.javaMonthlyExpenses.dummy;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.SQL.commends.Select;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.Record;
import pl.bg.javaMonthlyExpenses.mainWindow.Tools.SwitchFilter;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TestBuilderRecord extends Connection {

    private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
    public Identify identified;
    public Object fromList;
    public int id;
    public Table table;

    public static class BuilderList {

        public static Identify identified;
        private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
        public Object fromList;
        public int id;
        public Table table;

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
        public BuilderList table (Table val) {
            table = val;
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
        this.table = builderList.table;

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

                    switch (map.get(val)) {

                        case "integer":

                            for(int j= 0 ;j<columnsWith_IntType_copyOf.size();j++) {

                                switch (columnsWith_IntType_copyOf.get(j)) {
                                    case "idAccount" :
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                fromList(rs.getInt(val.toString())).identifyColumn(Identify.IDACCOUNT).id(id[0]).table(identifyTable(table_name)).build());
                                        columnsWith_IntType_copyOf.remove(j);
                                        break;
                                    default:
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                fromList(rs.getInt(val.toString())).identifyColumn(Identify.MAIN_ID).id(id[0]).table(identifyTable(table_name)).build());
                                        columnsWith_IntType_copyOf.remove(j);
                                        break;
                                }
                            }
                        break;

                        case "string":

                            if (table_name.equals("Shop")) {
                                identyfiedObjects.add(new BuilderList().
                                        fromList(rs.getString(val.toString())).identifyColumn(Identify.SHOPNAME).id(id[0]).table(identifyTable(table_name)).build());
                                break;
                            } else if (table_name.equals("Category")) {
                                identyfiedObjects.add(new BuilderList().
                                        fromList(rs.getString(val.toString())).identifyColumn(Identify.CATEGORYNAME).id(id[0]).table(identifyTable(table_name)).build());
                                break;
                            } else if (table_name.equals("Account")) {
                                identyfiedObjects.add(new BuilderList().
                                        fromList(rs.getString(val.toString())).identifyColumn(Identify.ACCOUNTNAME).id(id[0]).table(identifyTable(table_name)).build());
                                break;
                            } else if (table_name.equals("CommonAccount")) {
                                identyfiedObjects.add(new BuilderList().
                                        fromList(rs.getString(val.toString())).identifyColumn(Identify.ISCOMMON).id(id[0]).table(identifyTable(table_name)).build());
                                break;
                            }
                        case "date":
                            identyfiedObjects.add(new BuilderList().
                                    fromList(rs.getString(val.toString())).identifyColumn(Identify.DATE).id(id[0]).table(identifyTable(table_name)).build());
                            break;
                        case "boolean":
                            identyfiedObjects.add(new BuilderList().
                                    fromList(rs.getString(val.toString())).identifyColumn(Identify.ISCOMMON).id(id[0]).table(identifyTable(table_name)).build());
                            break;
                        case "double":
                            if (table_name.equals("Balance")) {
                                for (int j = 0; j < columnsWithDoubleType_copyOf.size(); j++) {
                                    switch (columnsWithDoubleType_copyOf.get(j).toLowerCase(Locale.ROOT)) {
                                        case "debt" :
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                    fromList(rs.getDouble(val.toString())).identifyColumn(Identify.DEBT).id(id[0]).table(identifyTable(table_name)).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;

                                        case "balance" :
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                    fromList(rs.getDouble(val.toString())).identifyColumn(Identify.BALANCE).id(id[0]).table(identifyTable(table_name)).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                        case "result":
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                    fromList(rs.getDouble(val.toString())).identifyColumn(Identify.FINALRESULT).id(id[0]).table(identifyTable(table_name)).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                    }
                                  //break;
                        }
                            } else {
                                identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                        fromList(rs.getDouble(val.toString())).identifyColumn(Identify.AMOUNT).id(id[0]).table(identifyTable(table_name)).build());

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

    public  enum Identify {

        SHOPNAME, CATEGORYNAME, ISCOMMON, DATE, ACCOUNTNAME, DEBT, AMOUNT, BALANCE, FINALRESULT, MAIN_ID,IDACCOUNT;

    }
    public  enum Table {

        BALANCE, SHOP, CATEGORY, COMMONACCOUNT, EXPENSE, ACCOUNT;
    }

    public static void main(String[] args) {

        setConnection();

        List<TestBuilderRecord> wynik = new Select("Balance").selectBasicDemo();
        Looper.forLoop(wynik.size(),i-> {

            if(wynik.get(i).id==1 && wynik.get(i).identified.equals(Identify.IDACCOUNT) || wynik.get(i).id==2 && wynik.get(i).identified.equals(Identify.IDACCOUNT)) {
                Logger.test("" + wynik.get(i).toString());
            }
        });
    }

    public static Table identifyTable(String table_name) {

         Set<Table> tableSet = EnumSet.allOf(Table.class);
         Iterator<Table> it = tableSet.iterator();

        Table val=null;

        while (it.hasNext()) {
            Object matched_table = it.next();
            if(matched_table.toString().toLowerCase(Locale.ROOT).equals(table_name.toLowerCase(Locale.ROOT))) {
                val = (Table) matched_table;
            }
        }
        return val;
    }
}