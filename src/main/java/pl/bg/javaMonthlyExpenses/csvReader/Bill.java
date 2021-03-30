package pl.bg.javaMonthlyExpenses.csvReader;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.JavaBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JavaBean
public class Bill {

    @CsvBindByPosition(position = 0)
    private double expense;
    @CsvBindByPosition(position = 2)
    private String category;
    @CsvBindByPosition(position = 3)
    private boolean common;

}
