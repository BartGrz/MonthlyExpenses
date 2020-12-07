package pl.bg.javaMonthlyExpenses.database.tools;

import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private final static String NAME = "DATABASE ";
   static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");


    public static void conn_status(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [CONNECTION STATUS "+ getMethodName(0) +" ] : " + message);
        System.out.flush();
    }
    public static void log(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [INFO] : " + message);
        System.out.flush();
    }

    public static void warn(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [WARNING " +getMethodName(1) + "] : "  + message);
        System.out.flush();
    }
     public static void error(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [ERROR] : " + message);
        System.out.flush();
    }
    public static void result(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [RESULT] : --> " + message);
        System.out.flush();
    }
    public static void success () {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [OPERATION " + getMethodName(1).toUpperCase() +"] : -> SUCCESS ");
        System.out.flush();
    }
    public static void failure () {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [OPERATION " + getMethodName(1).toUpperCase() +"] : --> FAILURE " );
        System.out.flush();
    }
    public static void end() {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [END]");
    }
    public static void test(String message) {
        LocalTime lt = LocalTime.now();
        System.out.println(NAME + " " + dtf.format(lt) + " [TESTING " + getMethodName(1) +  " ]" + message) ;
    }
    public  static String getMethodName(final int depth) { //generuje info o uzywanej metodzie

        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[ste.length - 1 - depth].getMethodName();
    }
}
