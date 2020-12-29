package pl.bg.javaMonthlyExpenses.database.SQL.commends;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.holder.TestBuilderRecord;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools.findObjectType;
import static pl.bg.javaMonthlyExpenses.formatter.Formatter.findTypeAndFormat;


public  class Select extends SQLTools {

    public static List<Object> list_results = new ArrayList<>();
    private static String table_name;

    public Select(String table_name) {
        this.table_name = table_name;
    }

    public Select() {
    }

    public static List<Object> results = new ArrayList<>();
    static double sumResult;


    public static List<TestBuilderRecord> selectBasic() {

        sql = "Select *from " + table_name + ";";

        return TestBuilderRecord.matchWithTypeAndAdd(rs,table_name,sql);
    }

    public static List<Object> selectSpecifyColumns(List<String> columns, Object condition) {

        list_names.removeAll(list_names);
        pragmaTable(table_name);


        if (condition != null) {


            String columnCondition_name = getColumntypeName(table_name, findObjectType(condition));

            sql = "Select " + Formatter.listFormatterColumns(columns) + " from " + table_name + " where "
                    + columnCondition_name + " = " + condition + " ;";
        } else {

            sql = "Select " + Formatter.listFormatterColumns(columns) + " from " + table_name + ";";
        }
        Looper.forLoop(columns.size(), i -> {

            try {
                rs = statement.executeQuery(sql);

                while (rs.next()) {
                    list_results.add(rs.getObject(columns.get(i)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        return list_results;
    }


    public static double selectSumBasic(int idAccount) {


        sql = "Select Sum(Amount) from " + table_name + " where idAccount = " + idAccount + " ;";


        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {

                sumResult = rs.getDouble("Sum(Amount)");
            }

        } catch (SQLException e) {
            Logger.error("" + e);
        } catch (NullPointerException e) {
            Logger.error("" + e);
        }


        return sumResult;


    }

    public static class SelectJoin<T, V> {

        private Object id;
        private String table_name;

        public SelectJoin(String table_name) {
            this.table_name = table_name;
        }

        public SelectJoin() {
        }

        public SelectJoin(Object id) {
            this.id = id;
        }

        public SelectJoin(Object id, String table_name) {
            this.id = id;
            this.table_name = table_name;
        }

        private String sql;


        public List<TestBuilderRecord> joinMainTest() {

            sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount ;";


            return  TestBuilderRecord.matchWithTypeAndAdd(rs,"Expense",sql);
        }
        public List<TestBuilderRecord> joinMain() {



            sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount ;";


            return  new TestBuilderRecord.BuilderList().addFlag(TestBuilderRecord.Flag.IRREGULAR).build().matchWithTypeAndAdd(rs,"Expense",sql);
        }

        public List <TestBuilderRecord> joinMainCondition(int id) {

            sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount where idExpense =  " + id + ";";


            return  TestBuilderRecord.matchWithTypeAndAdd(rs,"Expense",sql);
        }

        public List<TestBuilderRecord> selectJoinMainCondition(String byColumn, Object condition) {

            if (findObjectType(condition).equals("String") || findObjectType(condition).equals("date")) {

                sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                        "join Account a on e.idAccount = a.idAccount "
                        + "join Category c on e.idCategory = c.idCategory " +
                        "Join Shop s on e.idShop=s.idShop" +
                        " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount  where " + byColumn + " like " + findTypeAndFormat(condition) + " ; ";

            } else {

                sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                        "join Account a on e.idAccount = a.idAccount "
                        + "join Category c on e.idCategory = c.idCategory " +
                        "Join Shop s on e.idShop=s.idShop" +
                        " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount  where " + byColumn + " = " + condition + " ; ";

            }


            return  TestBuilderRecord.matchWithTypeAndAdd(rs,"Expense",sql);
        }




        public List<TestBuilderRecord> sumJoin_partialStrings(String tableJoined, String condition) {

                String columnJoined = getColumntypeName(tableJoined, findObjectType(condition));
                String foreignColumn = checkIfForeignColumn(table_name, columnJoined);

                sql = "Select Sum(Amount)" + ", b.IdCategory, " + foreignColumn + " from  " + table_name +
                        " a join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined) +
                        " where " + foreignColumn + " like " + Formatter.PartialCondition(condition);


                return  new TestBuilderRecord.BuilderList().addFlag(TestBuilderRecord.Flag.WITH_SUM)
                        .build().matchWithTypeAndAdd(rs,tableJoined,sql);
            }



        public void sumJoin_mixConditions_AND(String tableJoined, T condition_1, V condition_2) {


            StringBuilder stb = new StringBuilder();


            sql = "Select " + "Sum(Amount)" + " from " + table_name + "" +
                    " a Join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined)
                    + " where b.";

            stb.append(sql + getColumntypeName(tableJoined, findObjectType(condition_1)) + " = " + findTypeAndFormat(condition_1) + " ");

            stb.append(" AND a." + getColumntypeName(table_name, findObjectType(condition_2)) + " = " + findTypeAndFormat(condition_2));

            sql = stb.toString();

            stb.delete(0, stb.length());


            try {
                rs = statement.executeQuery(sql);
                while (rs.next()) {

                    Record.list.add(new Record.Builder().expense(rs.getDouble("Sum(Amount)")).build());

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void sumJoin_mixConditions_OR(String tableJoined, T condition_1, V condition_2, int id) {


            StringBuilder stb = new StringBuilder();
            final String tableAccount = "Account";

            sql = "Select " + "Sum(a.Amount)" + ",c.accountName , b.categoryName from " + table_name + "" +
                    " a Join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined)
                    + " Join Account c on a." + fetchTablesID(tableAccount) + " = c." + fetchTablesID(tableAccount) + " where b.";

            stb.append(sql + getColumntypeName(tableJoined, findObjectType(condition_1)) + " = " + findTypeAndFormat(condition_1) + " ");
            stb.append(" AND a." + getColumntypeName(table_name, "Integer") + " = " + id);
            stb.append(" OR b." + getColumntypeName(tableJoined, findObjectType(condition_2)) + " = " + findTypeAndFormat(condition_2));
            stb.append(" AND a." + getColumntypeName(table_name, "Integer") + " = " + id);

            sql = stb.toString();

            stb.delete(0, stb.length());


            try {
                rs = statement.executeQuery(sql);
                while (rs.next()) {

                    Record.list.add(new Record.Builder().expense(rs.getDouble("Sum(a.Amount)"))
                            .category(rs.getString("categoryName"))
                            .account(rs.getString("accountName"))
                            .build());

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        public void selectJoinOneCond(String tableJoined, Object condition) {

            sql = "Select " + checkIfForeignColumn(table_name, getColumntypeName(tableJoined, "String"))
                    + ", * from " + table_name + " a Join " + tableJoined + " b  on a." + fetchTablesID(tableJoined)
                    + " = b." + fetchTablesID(tableJoined) + "  where a." + fetchTablesID(tableJoined) + " = " + condition;


            try {
                rs = statement.executeQuery(sql);
                while (rs.next()) {

                    Record.list.add(new Record.Builder()
                            .balance(Math.round(rs.getDouble("Balance") * 100) / 100)
                            .account(rs.getString("accountName"))
                            .debt(Math.round(rs.getDouble("debt") * 100) / 100)
                            .result(Math.round(rs.getDouble("result") * 100) / 100)
                            .build());

                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
        public List<TestBuilderRecord> selectJoinOneCondDemo(String tableJoined, Object condition) {

            sql = "Select " + checkIfForeignColumn(table_name, getColumntypeName(tableJoined, "String"))
                    + ", * from " + table_name + " a Join " + tableJoined + " b  on a." + fetchTablesID(tableJoined)
                    + " = b." + fetchTablesID(tableJoined) + "  where a." + fetchTablesID(tableJoined) + " = " + condition;



            return new TestBuilderRecord.BuilderList().addFlag(TestBuilderRecord.Flag.REGULAR).build().matchWithTypeAndAdd(rs,table_name,sql);

        }
        public void sumJoinRange(String dateFrom, String dateTo, int id) {

            sql = "Select Sum(e.Amount) , b.accountName from Expense e " +
                    "Join Account b on e." + fetchTablesID("Account") + " = b." + fetchTablesID("Account")
                    + " where e.date >= " + findTypeAndFormat(dateFrom)
                    + " AND e.date <= " + findTypeAndFormat(dateTo)
                    + " And e." + fetchTablesID("Account") + " = " + id;


            try {
                rs = statement.executeQuery(sql);
                while (rs.next()) {

                    Record.list.add(new Record.Builder()
                            .expense(rs.getDouble("Sum(e.Amount)"))
                            .account(rs.getString("accountName"))
                            .build());
                }
            } catch (SQLException throwables) {
                Logger.error("" + throwables);
            }


        }
    }
}
   


