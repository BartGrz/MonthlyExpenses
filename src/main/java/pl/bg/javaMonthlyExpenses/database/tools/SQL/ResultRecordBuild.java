package pl.bg.javaMonthlyExpenses.database.tools.SQL;

import pl.bg.javaMonthlyExpenses.holder.Record;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultRecordBuild extends Connection {

   public void recordBuildMain(String sql, ResultSet rs) {

      try {
         rs=statement.executeQuery(sql);

         while (rs.next()) {

            Record.list.add(new Record.Builder()
                    .id(rs.getInt("idExpense"))
                    .account(rs.getString("accountName"))
                    .expense(rs.getDouble("amount"))
                    .category(rs.getString("categoryName"))
                    .date(rs.getString("date"))
                    .shop(rs.getString("shopName"))
                    .common(String.valueOf(rs.getBoolean("isCommon")))
                    .build());

         }
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }


   }


}
