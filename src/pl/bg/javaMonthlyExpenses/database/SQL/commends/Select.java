package pl.bg.javaMonthlyExpenses.database.SQL.commends;


import pl.bg.javaMonthlyExpenses.database.tools.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.dummy.ResultRecordBuild;
import pl.bg.javaMonthlyExpenses.exeptions.ListException;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;
import pl.bg.javaMonthlyExpenses.holder.Record;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


     public  class Select extends SQLEssentials {

          private static String table_name;

          public Select(String table_name) { this.table_name = table_name; }

         public Select() {
         }

         public static  List <Object> results = new ArrayList<>();
          static double sumResult;

     public static List<Object> selectBasic() {

              list_names.removeAll(list_names);
              pragmaTable(table_name);

              sql = "Select *from " + table_name + ";";

              return findAndAdd(list_names, sql);
          }

    public static List<Object> selectSpecifyColumns(List<String> columns,Object condition) {

        list_names.removeAll(list_names);
       pragmaTable(table_name);
List <Object > list_results = new ArrayList<>();

       String columnCondition_name = getColumntypeName(table_name,findObjectType(condition));


        sql = "Select "+ Formatter.listFormatterColumns(columns) + " from " + table_name + " where "
                + columnCondition_name +" = "  +condition + " ;";

              Looper.forLoop(columns.size(),i-> {

                      try {
                          rs = statement.executeQuery(sql);
                          while (rs.next()) {
                              results.add(rs.getObject( columns.get(i)));


                          }

                      } catch (SQLException e) {
                          e.printStackTrace();
                      }

              });

return list_results;
    }


    public static double selectSumBasic(int idAccount) {


              sql = "Select Sum(Amount) from " + table_name + " where idAccount = " + idAccount + " ;";


              ResultRecordBuild res = new ResultRecordBuild() {
                  @Override
                  public void resultSetRecordbuild(String sql) {


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

                  }

              };
              res.resultSetRecordbuild(sql);


              return sumResult;


          }

public static class SelectJoin<T, V>   {

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

              public void joinMain() {

                  sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                          "join Account a on e.idAccount = a.idAccount "
                          + "join Category c on e.idCategory = c.idCategory " +
                          "Join Shop s on e.idShop=s.idShop" +
                          " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount ;";


                  try {
                      rs = statement.executeQuery(sql);
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

                  } catch (SQLException e) {
                      e.printStackTrace();
                  }

              }


    public void selectJoinMainCondition(String byColumn, Object condition) {

                  if (findObjectType(condition).equals("String") || findObjectType(condition).equals("date")) {

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

                  ResultRecordBuild res = new ResultRecordBuild() {
                      @Override
                      public void resultSetRecordbuild(String sql) {
                          try {
                              rs = statement.executeQuery(sql);
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

                          } catch (SQLException e) {
                              e.printStackTrace();
                          }
                      }
                  };

                  res.resultSetRecordbuild(sql);

              }

     public void sumJoin_partialStrings(String tableJoined, List<String> conditions) {


                  try {
                      ListException.ListExceptionSize.checkSize(conditions.size());


                      Looper.forLoop(conditions.size(), i -> {

                          String columnJoined = getColumntypeName(tableJoined, findObjectType(conditions.get(i)));

                          sql = "Select Sum(Amount)" + ", b." + columnJoined + "  from  " + table_name +
                                  " a join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined)
                                  + " where b." + columnJoined + " like " + Formatter.PartialCondition(conditions.get(i));



                          ResultRecordBuild res = new ResultRecordBuild() {
                              @Override
                              public void resultSetRecordbuild(String sql) {
                                  try {
                                      rs = statement.executeQuery(sql);
                                      while (rs.next()) {

                                          Record.list.add(new Record.Builder()
                                                  .expense((double)Math.round(rs.getDouble("Sum(Amount)")*100)/100)
                                                  .category(rs.getString(columnJoined))
                                                  .build());

                                      }
                                  } catch (SQLException e) {
                                      e.printStackTrace();
                                  }


                              }

                          };
                          res.resultSetRecordbuild(sql);
                      });

                  } catch (ListException e) {
                      Logger.error("" + e);

                  }

              }

      public void sumJoin_mixConditions_AND(String tableJoined, T condition_1, V condition_2) {


                  StringBuilder stb = new StringBuilder();


                  sql = "Select " + "Sum(Amount)" + " from " + table_name + "" +
                          " a Join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined)
                          + " where b.";

                  stb.append(sql + getColumntypeName(tableJoined, findObjectType(condition_1)) + " = " + Formatter.findTypeAndFormat(condition_1) + " ");

                  stb.append(" AND a." + getColumntypeName(table_name, findObjectType(condition_2)) + " = " + Formatter.findTypeAndFormat(condition_2));

                  sql = stb.toString();

                  stb.delete(0, stb.length());

                  ResultRecordBuild res = new ResultRecordBuild() {
                      @Override
                      public void resultSetRecordbuild(String sql) {

                          try {
                              rs = statement.executeQuery(sql);
                              while (rs.next()) {

                                  Record.list.add(new Record.Builder().expense(rs.getDouble("Sum(Amount)")).build());

                              }
                          } catch (SQLException e) {
                              e.printStackTrace();
                          }

                      }
                  };
                  res.resultSetRecordbuild(sql);

              }

          }
      }

