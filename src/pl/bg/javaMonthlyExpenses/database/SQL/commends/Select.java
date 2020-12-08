package pl.bg.javaMonthlyExpenses.database.SQL.commends;


import pl.bg.javaMonthlyExpenses.Logger.Logger;
import pl.bg.javaMonthlyExpenses.database.tools.Looper;
import pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.ResultRecordBuild;
import pl.bg.javaMonthlyExpenses.database.tools.SQL.SQLTools;
import pl.bg.javaMonthlyExpenses.exeptions.ListException;
import pl.bg.javaMonthlyExpenses.formatter.Formatter;
import pl.bg.javaMonthlyExpenses.holder.Record;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static pl.bg.javaMonthlyExpenses.database.tools.Objects.ObjectTools.findObjectType;


public  class Select extends SQLTools {

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

       String columnCondition_name = getColumntypeName(table_name, findObjectType(condition));


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
              public void joinMainCondition(int id ) {
                  
                  sql = " Select idExpense, a.accountName , amount,date,c.categoryName,s.shopName,ca.isCommon from Expense e " +
                          "join Account a on e.idAccount = a.idAccount "
                          + "join Category c on e.idCategory = c.idCategory " +
                          "Join Shop s on e.idShop=s.idShop" +
                          " join CommonAccount ca on e.idCommonAccount=ca.idCommonAccount where idExpense =  " + id +  ";";
              

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
              
    public void sumJoin_mixConditions_OR(String tableJoined, T condition_1, V condition_2,int id) {
        
        
        StringBuilder stb = new StringBuilder();
        final String tableAccount = "Account";
        
        sql = "Select " + "Sum(a.Amount)" + ",c.accountName , b.categoryName from " + table_name + "" +
                " a Join " + tableJoined + " b on a." + fetchTablesID(tableJoined) + " = b." + fetchTablesID(tableJoined)
                + " Join Account c on a." +fetchTablesID(tableAccount)   + " = c."+fetchTablesID(tableAccount)  +" where b.";
        
        stb.append(sql + getColumntypeName(tableJoined, findObjectType(condition_1)) + " = " + Formatter.findTypeAndFormat(condition_1) + " ");
        stb.append(" AND a." + getColumntypeName(table_name,"Integer") + " = " + id );
        stb.append(" OR b." + getColumntypeName(tableJoined, findObjectType(condition_2)) + " = " + Formatter.findTypeAndFormat(condition_2));
        stb.append(" AND a." + getColumntypeName(table_name,"Integer") + " = " + id );
        
        sql = stb.toString();
        
        stb.delete(0, stb.length());
        
        ResultRecordBuild res = new ResultRecordBuild() {
            @Override
            public void resultSetRecordbuild(String sql) {
                
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
        };
        res.resultSetRecordbuild(sql);
        
    }
    
        public void selectJoinOneCond(String tableJoined, Object condition ) {
                  
                  sql = "Select " + checkIfForeignColumn(table_name,getColumntypeName(tableJoined,"String"))
                          + ", * from " + table_name + " a Join " + tableJoined + " b  on a."+ fetchTablesID(tableJoined)
                          +" = b."+ fetchTablesID(tableJoined) +  "  where a." + fetchTablesID(tableJoined) +" = " +condition;
                  
                  ResultRecordBuild res = new ResultRecordBuild() {
                      @Override
                      public void resultSetRecordbuild(String sql) {
                          try {
                              rs= statement.executeQuery(sql);
                              while (rs.next()) {
                                  
                                  Record.list.add(new Record.Builder()
                                          .balance(Math.round(rs.getDouble("Balance")*100)/100)
                                          .account(rs.getString("accountName"))
                                          .debt(Math.round(rs.getDouble("debt")*100)/100)
                                          .result(Math.round(rs.getDouble("result")*100)/100)
                                          .build());
                                  
                              }
                              
                          } catch (SQLException throwables) {
                              throwables.printStackTrace();
                          }
                      }
                  };
                  res.resultSetRecordbuild(sql);
                  
        }
          }
          
          public void selectJoinRange() {
         
         // TODO: 2020-12-08
              //trzeba stworzyc metode filtrujaco baze przez wzglad na <x AND >x  <= => np suma wydatkow od 1.12 do 20.12
         
          }
      }

