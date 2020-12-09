package pl.bg.javaMonthlyExpenses.exeptions;

import pl.bg.javaMonthlyExpenses.mainWindow.functionInterfaces.DoIt;

import java.time.LocalDate;

public class DateValidException extends Exception {
    
    LocalDate date_1,date_2;
    
    public DateValidException(LocalDate date_1, LocalDate date_2) {
        this.date_1 = date_1;
        this.date_2 = date_2;
    }
    
    public String toString() {
        
        return "[DATE INPUT INVALID] DATE_FROM CANNOT BE BEFORE DATE_TO : SUMBYDATE FILTER ERROR POSSIBLE" ;
    }
    public static class DateValidExceptionTimeRange {
        
        public static void checkIfRangeValid(LocalDate date_1, LocalDate date_2 ) throws DateValidException{
            
            if(date_1.isAfter(date_2)) {
                
         
                
                throw new DateValidException(date_1,date_2);
              
                
            } else {
            
            }
        }
        
        
    }
}
