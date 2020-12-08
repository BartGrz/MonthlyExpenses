package pl.bg.javaMonthlyExpenses.exeptions;

import pl.bg.javaMonthlyExpenses.Logger.Logger;

public class ListException extends Exception {

    int size;

    public ListException(int a) {
        size = a;
    }

    public String toString() {

        return " [ LIST OVERSIZED ] SIZE =  " + size ;
    }


   public static class ListExceptionSize {

        public static void checkSize(int a) throws ListException {

            if (a > 2) {
                throw new ListException(a);
            } else {
                Logger.log(" INPUT OK ");
            }

        }

    }
}