package pl.bg.javaMonthlyExpenses.holder;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Record  {
    
    public  static List<Record> list = new ArrayList<>();
    public  int main_id;
     public  double amount,balance,debt,finalResult,sum;
     public  String accountName, date,categoryName,shopName;
     public  String isCommon;
    public Set<Identify> identifySet = EnumSet.allOf(Identify.class);
    public Identify identify;

    public static class Builder {

        public int main_id;
        public double amount,balance,debt,finalResult,sum;
        public String accountName, date,categoryName,shopName;
        public String isCommon;
        public Set<Identify> identifySet = EnumSet.allOf(Identify.class);
        public Identify identify;

        public Builder id (int val) {
            main_id = val;
            return this;
        }

        public Builder expense(double val) {
            amount = val;
            return this;
        }
        public Builder identifyColumn(Identify val) {
            identify = val;
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
        public Builder sum (double val) {
            sum= val;
            return this;
        }
        public Builder result (double val) {
            finalResult= val;
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
        this.date = builder.date;
        this.isCommon=builder.isCommon;
        this.debt=builder.debt;
        this.finalResult=builder.finalResult;
        this.balance=builder.balance;
        this.identify=builder.identify;
        this.sum=builder.sum;
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

    public double getSum() { return sum; }

    public double getBalance() { return balance; }

    public double getDebt() { return debt; }

    public double getFinalResult() { return finalResult; }


    @Override
    public String toString() {
        return "Record{" +
                "main_id=" + main_id +
                ", accountName='" + accountName + '\'' +
                ", date='" + date + '\'' +
                ", debt='" + debt + '\'' +
                ", balance='" + balance + '\'' +
                ", finalresult='" + finalResult + '\'' +
                ", sum(Amount)='" + sum + '\'' +
                '}';
    }

    public static void addToList(Record record) {

        list.add(record);
    }

    public static enum Identify {

        SHOPNAME, CATEGORYNAME, ISCOMMON, DATE, ACCOUNTNAME, DEBT, AMOUNT, BALANCE, FINALRESULT, MAIN_ID;



    }

}

