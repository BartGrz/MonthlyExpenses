package pl.bg.javaMonthlyExpenses.holder;

import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.Connection;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TestBuilderRecord extends Connection {

    private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
    public Identify identified;
    public Object fromList;
    public int id;
    public Table table;
    public static Flag flag;

    public static class BuilderList {

        public static Identify identified;
        private Set<Identify> identifySet = EnumSet.allOf(Identify.class);
        public Object fromList;
        public int id;
        public Table table;
      static   public Flag flag;

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
        public BuilderList addFlag (Flag val) {
            flag = val;
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
        this.flag = builderList.flag;

    }
    public static List <TestBuilderRecord> matchWithTypeAndAdd (ResultSet rs , String table_name , String sql) {

        List <String> columnsWithDoubleType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Double");
        List <String> columnsWith_IntType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"Integer");
        List <String> columnsWith_StringType = SQLTools.fetchColumnsNamesByTypeDemo(table_name,"String");

        HashMap<String, String> map = new SQLTools().getMappedTable(table_name);
        map = validateMap(map);
/*
        if(flag.toString().equals("WITH_SUM")){ //te sprawdzanie trzeba opakowac w metode bo jest burdel, nie dziala dla sum bo usuwaja sie wyniki z listy, zrobic metode bedzie dzialac

            map.put("Sum(Amount)","double");
            columnsWithDoubleType.add("Sum(amount)");

        }else if (table_name.equals("Balance")) {
            map.put("accountName","string");
            columnsWith_StringType.add("accountName");

        }else if (flag.toString().equals("SUM_RANGE")) {

            map = new HashMap<>();
            map.put("accountName","string");
            map.put("Sum(amount)","double");


            columnsWithDoubleType.removeAll(columnsWithDoubleType);
            columnsWith_StringType.removeAll(columnsWith_StringType);

            columnsWithDoubleType.add("Sum(amount)");
            columnsWith_StringType.add("accountName");
        }


 */
        List<Object> columns = new ArrayList<>();

        List<TestBuilderRecord> identyfiedObjects = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()) {
            columns.add(it.next());
        }

        int [] id = new int[1];

        try {
            rs = statement.executeQuery(sql);

            while (rs.next()) {

                if(flag.toString().equals("SUM_RANGE")){
                    id[0]=0;
                }else {
                    id[0] = rs.getInt("id" + table_name);
                }

                List <String> copy_of = new ArrayList<>(validateStringColumns(columnsWith_StringType));
                List <String> columnsWithDoubleType_copyOf = new ArrayList<>(validateDoubleColumns(columnsWithDoubleType));
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

                                    default :
                                        identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                fromList(rs.getInt(val.toString())).identifyColumn(Identify.MAIN_ID).id(id[0]).table(identifyTable(table_name)).build());
                                        columnsWith_IntType_copyOf.remove(j);
                                        break;
                                }
                            }
                        break;

                        case "string":
                            for(int j=0;j<copy_of.size();j++) {
                                switch (copy_of.get(j)) {
                                    case "shopName":
                                        identyfiedObjects.add(new BuilderList().
                                                fromList(rs.getString(val.toString())).identifyColumn(Identify.SHOPNAME).id(id[0]).table(identifyTable(table_name)).build());
                                        if (table_name.equals("Expense")) {
                                            copy_of.remove(j);

                                        }
                                        break;
                                    case "categoryName":
                                        identyfiedObjects.add(new BuilderList().
                                                fromList(rs.getString(val.toString())).identifyColumn(Identify.CATEGORYNAME).id(id[0]).table(identifyTable(table_name)).build());
                                        if (table_name.equals("Expense")) {
                                            copy_of.remove(j);

                                        }
                                        break;
                                    case "accountName":

                                        identyfiedObjects.add(new BuilderList().
                                                fromList(rs.getString(val.toString())).identifyColumn(Identify.ACCOUNTNAME).id(id[0]).table(identifyTable(table_name)).build());

                                        if (table_name.equals("Expense")) {
                                            copy_of.remove(j);

                                        }
                                    break;
                                    case "isCommon":

                                        identyfiedObjects.add(new BuilderList().
                                                fromList(rs.getString(val.toString())).identifyColumn(Identify.ISCOMMON).id(id[0]).table(identifyTable(table_name)).build());
                                        if (table_name.equals("Expense")) {
                                            copy_of.remove(j);

                                        }
                                        break;
                                }
                                break;
                            }
                        break;
                        case "date":
                            Logger.log(val.toString());
                            identyfiedObjects.add(new BuilderList().
                                    fromList(rs.getString(val.toString())).identifyColumn(Identify.DATE).id(id[0]).table(identifyTable(table_name)).build());

                            break;
                        case "boolean":
                            identyfiedObjects.add(new BuilderList().
                                    fromList(rs.getBoolean(val.toString())).identifyColumn(Identify.ISCOMMON).id(id[0]).table(identifyTable(table_name)).build());
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

                                break;

                                }
                            } else  {
                                for(int j =0; j<columnsWithDoubleType_copyOf.size();j++) {

                                    switch (columnsWithDoubleType_copyOf.get(j).toLowerCase(Locale.ROOT)) {
                                        case "amount":
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                    fromList(rs.getDouble(val.toString())).identifyColumn(Identify.AMOUNT).id(id[0]).table(identifyTable(table_name)).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                        case "sum(amount)" :
                                            identyfiedObjects.add(new TestBuilderRecord.BuilderList().
                                                    fromList(rs.getDouble(val.toString())).identifyColumn(Identify.SUM).id(id[0]).table(identifyTable(table_name)).build());
                                            columnsWithDoubleType_copyOf.remove(j);
                                            break;
                                    }
                                }
                                break;
                            }
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return identyfiedObjects;

        }

    public  enum Identify {

        SHOPNAME, CATEGORYNAME, ISCOMMON, DATE, ACCOUNTNAME, DEBT, AMOUNT, BALANCE,
        FINALRESULT, MAIN_ID,IDACCOUNT,SUM;

    }
    public  enum Table {

        BALANCE, SHOP, CATEGORY, COMMONACCOUNT, EXPENSE, ACCOUNT;
    }
    public  enum Flag {

        REGULAR,WITH_SUM,IRREGULAR,SUM_RANGE;
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

    public static List<String> validateDoubleColumns (List<String> columnsWithDoubleType) {


        if (flag.toString().equals("WITH_SUM")) {

            columnsWithDoubleType.add("Sum(amount)");

        } else if (flag.toString().equals("SUM_RANGE")) {

            columnsWithDoubleType.removeAll(columnsWithDoubleType);
            columnsWithDoubleType.add("Sum(amount)");

        }
        return columnsWithDoubleType;
    }
    public static List<String> validateStringColumns (List<String> columnsWith_StringType) {

     if (flag.toString().equals("IRREGULAR")) {

            columnsWith_StringType.add("accountName");

        } else if (flag.toString().equals("SUM_RANGE")) {

            columnsWith_StringType.removeAll(columnsWith_StringType);
            columnsWith_StringType.add("accountName");
        }


        return columnsWith_StringType;
    }

    public static HashMap<String,String> validateMap(HashMap<String,String> map ) {

        if (flag.toString().equals("WITH_SUM")) {

            map.put("Sum(Amount)", "double");

        } else if (flag.toString().equals("IRREGULAR")) {
            map.put("accountName", "string");

        } else if (flag.toString().equals("SUM_RANGE")) {

            map = new HashMap<>();
            map.put("accountName", "string");
            map.put("Sum(amount)", "double");

    }

        return map;
    }
}
