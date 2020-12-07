package pl.bg.javaMonthlyExpenses.database.SQL.commends;


import pl.bg.javaMonthlyExpenses.formatter.Formatter;
import pl.bg.javaMonthlyExpenses.holder.Record;

import java.sql.SQLException;

public class SQLSelectJoinDemo extends SQLEssentials {

    String byColumn;
    Object condition;

    public SQLSelectJoinDemo(Object condition) {
        this.condition=condition;
    }

    public SQLSelectJoinDemo() {
    }

    public void selectJoinCondition_(char equation, String byColumn, Object condition) {


        if (equation == '=' && findObjectType(condition).equals("String")) {

            sql = " Select accountName , amount,date,categoryName,shopName,isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount " +
                    "where + " + byColumn + " like " + condition;

        } else if (equation == '=' && findObjectType(condition).equals("Integer") || findObjectType(condition).equals("Double"))

            sql = " Select accountName , amount,date,categoryName,shopName,isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount " +
                    "where + " + byColumn + " = " + condition;


 try

    {
        rs = statement.executeQuery(sql);
    } catch(
    SQLException e)

    {
        e.printStackTrace();
    }

}
    public void selectJoinMain() {

    if (condition==null ) {

        sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                "join Account a on e.idAccount = a.idAccount "
                + "join Category c on e.idCategory = c.idCategory " +
                "Join Shop s on e.idShop=s.idShop" +
                " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount ;";
    } else {
        sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                "join Account a on e.idAccount = a.idAccount "
                + "join Category c on e.idCategory = c.idCategory " +
                "Join Shop s on e.idShop=s.idShop" +
                " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount where idExpense =  " + condition +  ";";
    }

        try {
            rs =statement.executeQuery(sql);
            while (rs.next()) {
                Record.list.add(new Record.Builder()
                        .id(rs.getInt("idExpense"))
                        .account(rs.getString("accountName"))
                        .expense(rs.getDouble("amount"))
                        .category(rs.getString("categoryName"))
                        .date(rs.getString("date"))
                        .shop(rs.getString("shopName"))
                        .common(String.valueOf( rs.getBoolean("isCommon")))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void selectJoinDog(int id) {

    sql= "Select Sum(Amount), A.accountName from Expense e " +
            "JOIN Account A on e.idAccount = A.idAccount" +
            " JOIN Category c on e.IdCategory = c.IdCategory " +
            "where e.idAccount = " + id + "  AND C.categoryName like '%pies%' OR e.idAccount =" + id+" AND C.categoryName = 'Vet'  ;";

        try {
            rs =statement.executeQuery(sql);
            while (rs.next()) {
                Record.list.add(new Record.Builder()
                        .account(rs.getString("accountName"))
                        .expense_dog(rs.getDouble("Sum(Amount)"))
                        .build());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void selectJoinCondition(String byColumn, Object condition) {

        if (findObjectType(condition).equals("String")) {

            sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount  where " + byColumn + " like " + Formatter.findTypeAndFormat(condition) + " ; ";

        } else {

            sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                    "join Account a on e.idAccount = a.idAccount "
                    + "join Category c on e.idCategory = c.idCategory " +
                    "Join Shop s on e.idShop=s.idShop" +
                    " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount  where " + byColumn + " = " + condition + " ; ";

        }
        try {
            rs =statement.executeQuery(sql);
            while (rs.next()) {
                Record.list.add(new Record.Builder()
                        .id(rs.getInt("idExpense"))
                        .account(rs.getString("accountName"))
                        .expense(rs.getDouble("amount"))
                        .category(rs.getString("categoryName"))
                        .date(rs.getString("date"))
                        .shop(rs.getString("shopName"))
                        .common(String.valueOf( rs.getBoolean("isCommon")))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
