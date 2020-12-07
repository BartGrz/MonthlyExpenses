package pl.bg.javaMonthlyExpenses.holder;

import java.util.ArrayList;
import java.util.List;

public class Record {


    public  static List<Record> list = new ArrayList<>();
    public  int main_id;
     public  double amount,balance,debt,finalResult,dogExpense;
     public  String accountName, date,categoryName,shopName;
     public  String isCommon;

    public static class Builder {

        public int main_id;
        public double amount,balance,debt,finalResult,dogExpense;
        public String accountName, date,categoryName,shopName;
        public String isCommon;

        public Builder id (int val) {
            main_id = val;
            return this;
        }

        public Builder expense(double val) {
            amount = val;
            return this;
        }
        public Builder balance(double val) {
             balance= val;
            return this;
        }
        public Builder debt (double val) {
            debt= val;
            return this;
        }
        public Builder result (double val) {
            finalResult= val;
            return this;
        }
        public Builder expense_dog (double val) {
            dogExpense= val;
            return this;
        }

        public Builder account(String val) {
            accountName = val;
            return this;
        }
        public Builder category(String val) {
            categoryName = val;
            return this;
        }
        public Builder shop(String val) {
            shopName = val;
            return this;
        }

        public Builder date(String val) {
            date = val;
            return this;
        }
        public Builder common(String val) {
            isCommon = val;
            return this;
        }

        public Record build() {
            return new Record(this);
        }
    }

    public Record(Builder builder) {
        this.main_id = builder.main_id;
        this.accountName=builder.accountName;
        this.categoryName = builder.categoryName;
        this.shopName=builder.shopName;
        this.amount = builder.amount;
        this.dogExpense=builder.dogExpense;
        this.date = builder.date;
        this.isCommon=builder.isCommon;
        this.debt=builder.debt;
        this.finalResult=builder.finalResult;
        this.balance=builder.balance;
    }



    public int getMain_id() {
        return main_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getDate() {
        return date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getIsCommon() {
        return isCommon;
    }

    public double getDogExpense() { return dogExpense; }

    public double getBalance() { return balance; }

    public double getDebt() { return debt; }

    public double getFinalResult() { return finalResult; }


    @Override
    public String toString() {
        return "Record{" +
                "main_id=" + main_id +
                ", amount=" + amount +
                ", accountName='" + accountName + '\'' +
                ", date='" + date + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", isCommon='" + isCommon + '\'' +
                '}';
    }
    public static void addToList(Record record) {

        list.add(record);
    }
}

