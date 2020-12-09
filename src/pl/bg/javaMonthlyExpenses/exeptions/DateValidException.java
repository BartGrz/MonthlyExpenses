package pl.bg.javaMonthlyExpenses.exeptions;

import java.time.LocalDate;

public class DateValidException extends Exception {
    
    LocalDate date_1,date_2;
    
    public DateValidException(LocalDate date_1, LocalDate date_2) {
        this.date_1 = date_1;
        this.date_2 = date_2;
    }
    
    public String toString() {
        
        return "[DATE INPUT INVALID : EXCEPTION TYPE : " ;
    }
    public static class DateValidExceptionTimeRange {
        
        @Override
        public String toString() {
            return super.toString() + " DATE_FROM CANNOT BE BEFORE DATE_TO : SUMBYDATE FILTER ERROR";
        }
        
        public static void checkIfRangeValid(LocalDate date_1, LocalDate date_2) throws DateValidException{
            
            if(date_1.isBefore(date_2)) {
                throw new DateValidException(date_1,date_2);
                
            } else {
            
            }
        }
        
        
    }
}
